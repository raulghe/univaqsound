package it.univq.service.impl;
 
import java.io.File;
import java.util.Random;
 
import it.univq.datamodel.Brano;
import it.univq.datamodel.CodaDiRiproduzione;
import it.univq.service.PlayerService;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
 
public class PlayerServiceImpl implements PlayerService {
    private final CodaDiRiproduzione coda=CodaDiRiproduzione.getIstanza();
    private MediaPlayer mediaPlayer;
    private int ultimoIndice=-1;  //lo uso per capire se play viene dallo stesso brano  o a seguito di avanti o indietro
 
    @Override
    public void play(){
        if(coda.getCoda().isEmpty())return;
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
         //fermo il brano che c'è nel player
            if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.dispose();
         }
         //carico il nuovo brano e lo riproduco
         Brano branoCorrente =coda.getCoda().get(coda.getIndiceBranoCorrente());
         File file= new File(branoCorrente.getPercorsoFile());
         Media media=new Media(file.toURI().toString());
         mediaPlayer = new MediaPlayer(media);
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
       //se impostato su casuale genero un indice casuale
        if (coda.isShuffle()){
            Random casuale=new Random();
            int nuovoIndice=casuale.nextInt(coda.getCoda().size());
            coda.setIndiceBranoCorrente(nuovoIndice);
            this.play();
            return;
        }
    //se mi trovo a fine coda e ho attivato repeat ripeto la coda, imposto quindi l indice sul primo elemento
        if (coda.isRepeat() && indice==coda.getCoda().size()-1){
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
        //sono arrivato alla fine non ho il repeat ne shuffle
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
 
}