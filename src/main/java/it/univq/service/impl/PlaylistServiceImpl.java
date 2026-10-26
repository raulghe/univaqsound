package it.univq.service.impl;
 
import it.univq.datamodel.Brano;
 
import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;
import it.univq.exception.BranoNonTrovatoException;
import it.univq.exception.DuplicatoException;
import it.univq.exception.PlaylistNonTrovataException;
import it.univq.service.*;
 
import java.util.Collections;
import java.util.List;
 
public class PlaylistServiceImpl implements PlaylistService{
    private CodaDiRiproduzioneService coda=new CodaDiRiproduzioneServiceImpl();
    private PlayerService player= PlayerServiceImpl.getIstanza();
    private final LoggerService logger = LoggerServiceImpl.getIstanza();
   
    @Override
    public void aggiungiBrano(Playlist p, Brano b) {
        if(p.getBrani().contains(b)){
            throw new DuplicatoException("Operazione fallita. Il brano" +b.getTitolo()+ " è gia presente in: " + p.getNome()+".");
        }
        p.getBrani().add(b);
        logger.log("AGGIUNTA_BRANO_PLAYLIST", "Brano aggiunto alla playlist: " + p.getNome());
    }
 
    @Override
    public void rimuoviBrano(Playlist p, Brano b) {
        if(!p.getBrani().contains(b)){
            throw new BranoNonTrovatoException("Operazione fallita. Il brano" +b.getTitolo()+ "non è presente in " +p.getNome()+".");
        }
        p.getBrani().remove(b);
        logger.log("RIMOZIONE_BRANO_PLAYLIST", "Brano rimosso dalla playlist: " + p.getNome());
    }
 
    // metodo per ordinare i brani di una playlist secondo un criterio scelto
    // dall'utente
    @Override
    public void ordinaBrani(Playlist p, CriterioOrdinamento<Brano> criterio){
       p.getBrani().sort((b1, b2)-> criterio.ordina(b1,b2));
    }
 
    @Override
    public void creaPlaylist(Playlist p, Utente u) {
        u.getPlaylist().add(p);
        logger.log("CREAZIONE_PLAYLIST", "Playlist creata: " + p.getNome());
    }
 
    @Override
    public void eliminaPlaylist(Playlist p, Utente u) {
        if(!u.getPlaylist().contains(p)){
            throw new PlaylistNonTrovataException("Operazione fallita. La playlist" +p.getNome()+" non esiste.");
        }
        u.getPlaylist().remove(p);
        logger.log("ELIMINAZIONE_PLAYLIST", "Playlist eliminata: " + p.getNome());
    }
 
    @Override
    public List<Playlist> getPlaylistUtente(Utente u) {
        return u.getPlaylist();
    }
    @Override
    public Integer calcolaDurataPlaylist(Playlist p) {
   
    return p.getBrani().stream().mapToInt(b -> b.getDurata()).sum();
}
 
    @Override
    public void spostaSU(Playlist p , Brano b) {
    if(!p.getBrani().contains(b)){
        throw new BranoNonTrovatoException("Il brano" + b.getTitolo() + " non è presente in " + p.getNome()+".");
    }
    int posizione = p.getBrani().indexOf(b);
    if(posizione > 0)
        Collections.swap(p.getBrani(), posizione, posizione-1);
}
    @Override
    public void spostaGiu(Playlist p, Brano b) {
    if(!p.getBrani().contains(b)){
        throw new BranoNonTrovatoException("Il brano" + b.getTitolo() + " non è presente in " + p.getNome()+".");
    }
    int posizione = p.getBrani().indexOf(b);
    if(posizione < p.getBrani().size()-1)
        Collections.swap(p.getBrani(), posizione, posizione+1);
}
    @Override
    public void riproduciPlaylist(Playlist p){
        
        coda.aggiungiAllaCoda(p);
       
        player.play();
    }
 
 
}