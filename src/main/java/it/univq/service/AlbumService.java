package it.univq.service;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;

import java.util.Set;

/**
 * interfaccia che definisce i servizi relativi agli album.
 */
public interface AlbumService {
    /**
     * restituisce tutti gli album presenti nella libreria.
     * @return un {@code Set<Album>} contenente tutti gli album
     */
    public Set<Album> getTuttiAlbum();

    /**
     * recupera tutti gli album pubblicati da uno specifico artista.
     * @param a l'artista di cui si vogliono ottenere gli album
     * @return un {@code Set<Album>} contenente gli album associati all'artista specificato
     */
    public Set<Album> getAlbumByArtista(Artista a);

    /**
     * aggiunge un nuovo album alla libreria.
     * @param a l'album da aggiungere alla libreria
     */
    public void aggiungiAlbum(Album a);

    /**
     * rimuove un album dalla libreria.
     * @param a l'album da rimuovere dalla libreria
     */
    public void rimuoviAlbum(Album a);
}
