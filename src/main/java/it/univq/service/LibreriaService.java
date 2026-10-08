package it.univq.service;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;
import it.univq.datamodel.Brano;

import java.util.Set;

public interface LibreriaService {

    public Set<Brano> getTuttiBrani();
    public Set<Album> getTuttiAlbum();
    public Set<Artista> getTuttiArtisti();



    public Set<Album> getAlbumByArtista(Artista a);
    public Set<Brano> getBranoByAlbum(Album a);


    public void aggiungiBrano(Brano b);
    public void aggiungiAlbum(Album a);
    public void aggiungiArtista(Artista a);
    public void rimuoviBrano(Brano b);
    public void rimuoviAlbum(Album a);
    public void rimuoviArtista(Artista a);

}
