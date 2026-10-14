package it.univq.service.impl;

import it.univq.datamodel.Brano;

import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;
import it.univq.service.CriterioOrdinamento;
import it.univq.service.PlaylistService;

import java.util.List;

public class PlaylistServiceImpl implements PlaylistService{

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
}
