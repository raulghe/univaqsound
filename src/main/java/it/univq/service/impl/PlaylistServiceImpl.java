package it.univq.service.impl;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;
import it.univq.service.CodaDiRiproduzioneService;
import it.univq.service.Ordinabile;
import it.univq.service.PlaylistService;

import java.util.List;

public class PlaylistServiceImpl implements PlaylistService, Ordinabile<Playlist> {

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
    public void ordina(Playlist p, String parametro) {
        if (parametro.equalsIgnoreCase("nome"))
            p.getBrani().sort((b1, b2) -> b1.getTitolo().compareToIgnoreCase(b2.getTitolo()));
        if (parametro.equalsIgnoreCase("durata"))
            p.getBrani().sort((b1, b2) -> Integer.compare(b1.getDurata(), b2.getDurata()));
        if (parametro.equalsIgnoreCase("numeroAscolti"))
            p.getBrani().sort((b1, b2) -> Integer.compare(b1.getNumeroAscolti(), b2.getNumeroAscolti()));
        if (parametro.equalsIgnoreCase("data"))
            p.getBrani().sort((b1, b2) -> b1.getDataAggiunta().compareTo(b2.getDataAggiunta()));
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
