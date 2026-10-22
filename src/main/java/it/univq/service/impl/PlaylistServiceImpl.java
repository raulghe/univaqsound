package it.univq.service.impl;
 
import it.univq.datamodel.Brano;
 
import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;
import it.univq.service.CodaDiRiproduzioneService;
import it.univq.service.CriterioOrdinamento;
import it.univq.service.PlayerService;
import it.univq.service.PlaylistService;
 
import java.util.Collections;
import java.util.List;
 
public class PlaylistServiceImpl implements PlaylistService{
    private CodaDiRiproduzioneService coda=new CodaDiRiproduzioneServiceImpl();
    private PlayerService player= new PlayerServiceImpl();
   
    @Override
    public void aggiungiBrano(Playlist p, Brano b) {
 
        p.getBrani().add(b);
    }
 
    @Override
    public void rimuoviBrano(Playlist p, Brano b) {
 
        p.getBrani().remove(b);
 
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
    }
 
    @Override
    public void eliminaPlaylist(Playlist p, Utente u) {
        if(u.getPlaylist().contains(p)) u.getPlaylist().remove(p);
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
    if(!p.getBrani().contains(b)) return;
    int posizione = p.getBrani().indexOf(b);
    if(posizione > 0)
        Collections.swap(p.getBrani(), posizione, posizione-1);
}
    @Override
    public void spostaGiu(Playlist p, Brano b) {
    if(!p.getBrani().contains(b)) return;
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