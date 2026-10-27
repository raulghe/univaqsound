package it.univq.service.impl;
 
import java.io.File;
import java.net.URL;

import javafx.util.Duration;
import java.util.Random;
 
import it.univq.datamodel.Brano;
import it.univq.datamodel.CodaDiRiproduzione;
import it.univq.datamodel.Utente;
import it.univq.exception.FileAudioNonTrovatoException;
import it.univq.service.CronologiaService;
import it.univq.service.PlayerService;
import it.univq.service.LoggerService;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
 
public class PlayerServiceImpl implements PlayerService {
    private static final PlayerService istanza=new PlayerServiceImpl();
    private final CodaDiRiproduzione coda=CodaDiRiproduzione.getIstanza();
    private MediaPlayer mediaPlayer;
    private int ultimoIndice=-1;//lo uso per capire se play viene dallo stesso brano  o a seguito di avanti o indietro
    private double volume = 0.5;
    private Utente utenteCorrente;
    private final CronologiaService cronologiaService=new CronologiaServiceImpl();
    private final LoggerService logger=LoggerServiceImpl.getIstanza();

    private PlayerServiceImpl(){}

    public static PlayerService getIstanza(){
        return istanza;
    }

    @Override
    public void play(){
        if(coda.getCoda().isEmpty())
            return;
        if(coda.getIndiceBranoCorrente()==-1){
            coda.setIndiceBranoCorrente(0);
        }
        //se premo play dopo aver prmuto pausa e ho gli stessi indici riprendo la riproduzione
        if(mediaPlayer != null && mediaPlayer.getStatus()== MediaPlayer.Status.PAUSED && ultimoIndice==coda.getIndiceBranoCorrente()){
            mediaPlayer.play();
            return;
        }
        //ci arrivo da tasto avanto indietro ,da una riproduzione di un brano cercato , oppure se ho il repeat attivo e ho un solo brano
        if(ultimoIndice!=coda.getIndiceBranoCorrente() || mediaPlayer.getStatus()==MediaPlayer.Status.STOPPED && ultimoIndice==coda.getIndiceBranoCorrente()){
            Brano branoCorrente = coda.getCoda().get(coda.getIndiceBranoCorrente());
            String percorso = branoCorrente.getPercorsoFile();
            URL risorsa = (percorso == null || percorso.isBlank()) ? null : getClass().getResource(percorso.trim());
            if (risorsa == null) {
                throw new FileAudioNonTrovatoException("Impossibile riprodurre il brano: " + branoCorrente.getTitolo() + ": file audio non trovato (" + percorso + ")");
            }
            Media media = new Media(risorsa.toExternalForm());
        //fermo il brano che c'è nel player e devo aggiornare la cronologia 
            if (mediaPlayer!=null){
            mediaPlayer.pause();
            if(utenteCorrente!=null && ultimoIndice>=0 && ultimoIndice< coda.getCoda().size()&& (int) mediaPlayer.getCurrentTime().toSeconds()!=0){
                cronologiaService.aggiungiVoce(utenteCorrente, coda.getCoda().get(ultimoIndice), (int) mediaPlayer.getCurrentTime().toSeconds());
                
            }
            
            mediaPlayer.stop();
            mediaPlayer.dispose();
         }        
         //carico il nuovo brano e lo riproduco
         mediaPlayer = new MediaPlayer(media);
         mediaPlayer.setVolume(volume);
         logger.log("AVVIO_RIPRODUZIONE", "Brano riprodotto: " + branoCorrente.getTitolo());
         mediaPlayer.play();
         mediaPlayer.setOnEndOfMedia(() -> this.avanti());
         ultimoIndice=coda.getIndiceBranoCorrente();
         branoCorrente.setNumeroAscolti(branoCorrente.getNumeroAscolti() + 1); //usato nelle statistiche
         
         }
    }
   
    @Override
    public void pausa(){
        if(mediaPlayer != null) mediaPlayer.pause();  
 
    }
 
   
    @Override
    public void avanti(){
        Integer indice=coda.getIndiceBranoCorrente();
        if(coda.getCoda().isEmpty())return;
       //se impostato su casuale e ho piu di un brano nella coda  genero un indice casuale,altrimenti non fa nulla(non posso riprodurre brani casuali avendone solo 1 in coda)
        if (coda.isShuffle() && coda.getCoda().size()>1){
            Random casuale=new Random();
            int nuovoIndice;
            do{
                 nuovoIndice=casuale.nextInt(coda.getCoda().size());
            }while (nuovoIndice==ultimoIndice);
            coda.setIndiceBranoCorrente(nuovoIndice);
            this.play();
            return;
        }
    //se mi trovo a fine coda e ho attivato repeat,ripeto la coda, imposto quindi l indice sul primo elemento
        if (coda.isRepeat() && indice==coda.getCoda().size()-1){
            mediaPlayer.pause();
            if(utenteCorrente!=null && ultimoIndice>=0 && ultimoIndice< coda.getCoda().size()&& (int) mediaPlayer.getCurrentTime().toSeconds()!=0){
                cronologiaService.aggiungiVoce(utenteCorrente, coda.getCoda().get(ultimoIndice), (int) mediaPlayer.getCurrentTime().toSeconds());
                
            }
            
            coda.setIndiceBranoCorrente(0);
            mediaPlayer.stop();
            this.play();
            return;
        }
        //se non ho casuale incremento l indice di 1 per passare al brano successivo
        if(indice<coda.getCoda().size()-1){
            coda.setIndiceBranoCorrente(indice+1);
            this.play();
            return;
        }
        //sono arrivato alla fine non ho il repeat ne shuffle salvo quindi l ultimo brano nella cronologia e fermo il player
         if(utenteCorrente!=null && ultimoIndice>=0 && ultimoIndice< coda.getCoda().size()){
                cronologiaService.aggiungiVoce(utenteCorrente, coda.getCoda().get(ultimoIndice), (int) mediaPlayer.getCurrentTime().toSeconds());
                
            }
        mediaPlayer.stop();      
 
    }
    @Override
    public void indietro(){
        Integer indice=coda.getIndiceBranoCorrente();
        if(indice==0 || indice==-1)return; //mi trovo al primo brano o la coda è vuota
        indice--;
        coda.setIndiceBranoCorrente(indice);
        this.play(); //faccio play del brano precedente
 
       
    }
    @Override
    public void cambiaShuffle(){
        if(coda.isShuffle()) coda.setShuffle(false);
        else coda.setShuffle(true);
    }
    @Override
    public void cambiaModalitaRipetizione(){
        if(coda.isRepeat()) coda.setRepeat(false);
        else coda.setRepeat(true);
    }
    @Override 
    public double getTempoCorrenteSecondi(){
        if(mediaPlayer == null) return 0;
        return mediaPlayer.getCurrentTime().toSeconds(); 
    }
    @Override 
    public double getDurataTotaleSecondi(){
        if (mediaPlayer == null) {return 0;}
        Duration durata = mediaPlayer.getTotalDuration();
        if (durata.isUnknown()){
            return 0;
        }
        return durata.toSeconds();
    }
    @Override 
    public void setVolume(double volume){
        this.volume = volume;
        if(mediaPlayer != null){
            mediaPlayer.setVolume(volume);
        }
    }
    @Override 
    public Brano getBranoCorrente(){
        int indice = coda.getIndiceBranoCorrente();
        if(indice < 0 || indice >= coda.getCoda().size()){
            return null;
        }
        return coda.getCoda().get(indice);
    }
    public void setUtente(Utente u){
        this.utenteCorrente=u;
    
    }
    
 
}