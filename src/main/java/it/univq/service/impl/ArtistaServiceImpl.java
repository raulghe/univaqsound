package it.univq.service.impl;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;
import it.univq.datamodel.Libreria;
import it.univq.datamodel.Brano;
import it.univq.exception.ArtistaNonTrovatoException;

import it.univq.service.ArtistaService;

import java.util.Set;
import java.util.stream.Collectors;

public class ArtistaServiceImpl implements ArtistaService {
    private final Libreria libreria= Libreria.getIstanza();
    @Override
    public Set<Artista> getTuttiArtisti(){
        return libreria.getArtisti().getElementi();
    }
    @Override
    public void aggiungiArtista(Artista a){
        libreria.getArtisti().aggiungiElemento(a);
    }
    //qui devo rimuovere tutti gli album (brani associati all album) e artista selezionato
    @Override
    public void rimuoviArtista(Artista a){
        if(!libreria.getArtisti().getElementi().contains(a)){
            throw new ArtistaNonTrovatoException("Operazione fallita. L'artista"+ a.getNomeArte()+" non è presente.");
        }
        Set<Album>  daEliminare= libreria.getAlbum().getElementi().stream().filter(album -> album.getArtistaAlbum().equals(a)).collect(Collectors.toSet());
        for (Album album : daEliminare) {
                libreria.getBrani().getElementi().removeAll(album.getBrani());
                libreria.getAlbum().rimuoviElemento(album);
        }
        libreria.getArtisti().rimuoviElemento(a);
    }
      @Override
    public int getAscoltiTotali(Artista a) {
        int ascolti=0;
        for(Album album: a.getDiscografia()){
            for(Brano b: album.getBrani()){
                ascolti=ascolti+b.getNumeroAscolti();
            }
        } return ascolti;
    }
}
