package it.univq.service.impl;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Libreria;
import it.univq.service.LibreriaService;
import java.util.Set;
import java.util.stream.Collectors;

public class LibreriaServiceImpl implements LibreriaService {

    private final Libreria libreria= Libreria.getIstanza();


    @Override
    public Set<Brano> getTuttiBrani(){
    return libreria.getBrani().getElementi();
    }
    @Override
    public Set<Album> getTuttiAlbum(){
    return libreria.getAlbum().getElementi();
    }
    @Override
    public Set<Artista> getTuttiArtisti(){
    return libreria.getArtisti().getElementi();
    }



    @Override
    public Set<Album> getAlbumByArtista(Artista a){

        return libreria.getAlbum().getElementi().stream().filter(album -> album.getArtistaAlbum().equals(a)).collect(Collectors.toSet());

    }
    @Override
    public Set<Brano> getBranoByAlbum(Album a){
        return libreria.getBrani().getElementi().stream().filter(brano ->brano.getAlbumBrano().equals(a)).collect(Collectors.toSet());
    }

    @Override
    public void aggiungiBrano(Brano b){
        libreria.getBrani().aggiungiElemento(b);
    }
    @Override
    public void aggiungiAlbum(Album a){
        libreria.getAlbum().aggiungiElemento(a);
    }
    @Override
    public void aggiungiArtista(Artista a){
        libreria.getArtisti().aggiungiElemento(a);
    }
    @Override
    public void rimuoviBrano(Brano b){
        libreria.getBrani().rimuoviElemento(b);
    }
    //qui devo rimuovere anche tutti i brani dell album
    @Override
    public void rimuoviAlbum(Album a){
        libreria.getBrani().getElementi().removeAll(a.getBrani());
        libreria.getAlbum().rimuoviElemento(a);
    }
    //qui devo rimuovere tutti gli album (brani associati all album) e artista selezionato
    @Override
    public void rimuoviArtista(Artista a){
        Set<Album>  daEliminare= libreria.getAlbum().getElementi().stream().filter(album -> album.getArtistaAlbum().equals(a)).collect(Collectors.toSet());
        for (Album album : daEliminare) {
            this.rimuoviAlbum(album);
        }
        libreria.getArtisti().getElementi().remove(a);
    }

}
