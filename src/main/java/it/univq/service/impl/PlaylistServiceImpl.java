package it.univq.service.impl;

import it.univq.datamodel.Brano;

import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;
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
    public void ordina(Playlist p, String ordine) {
        switch (ordine) {
            case ("NOME") -> p.getBrani().sort((b1, b2) -> b1.getTitolo().compareToIgnoreCase(b2.getTitolo()));
            case ("DURATA") -> p.getBrani().sort((b1, b2) -> Integer.compare(b1.getDurata(), b2.getDurata()));
            case ("NUMEROASCOLTI") -> p.getBrani().sort((b1, b2) -> Integer.compare(b2.getNumeroAscolti(), b1.getNumeroAscolti()));
            case ("DATA") -> p.getBrani().sort((b1, b2) -> b1.getDataAggiunta().compareTo(b2.getDataAggiunta()));
        }
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
