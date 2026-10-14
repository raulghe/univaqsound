package it.univq.service;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;

import java.util.List;

public interface PlaylistService {

    public void aggiungiBrano(Playlist p, Brano b);
    public void rimuoviBrano(Playlist p, Brano b);
    public void creaPlaylist(Playlist p, Utente u);
    public void eliminaPlaylist (Playlist p, Utente u);
    public List<Playlist> getPlaylistUtente(Utente U);
    public void ordinaBrani(Playlist p, CriterioOrdinamento<Brano> criterio);
    public Integer calcolaDurataPlaylist(Playlist p);
    public void spostaSU(Playlist p , Brano b);
    public void spostaGiu(Playlist p, Brano b);
    public void riproduciPlaylist(Playlist p);
}
