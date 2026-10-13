package it.univq.service;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;

import java.util.Set;

public interface AlbumService {
    public Set<Album> getTuttiAlbum();
    public Set<Album> getAlbumByArtista(Artista a);
    public void aggiungiAlbum(Album a);
    public void rimuoviAlbum(Album a);
}
