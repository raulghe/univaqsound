package it.univq.service.impl;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;
import it.univq.datamodel.Libreria;
import it.univq.exception.AlbumNonTrovatoException;
import it.univq.service.LoggerService;
import it.univq.service.AlbumService;

import java.util.Set;
import java.util.stream.Collectors;

public class AlbumServiceImpl implements AlbumService {
    private final Libreria libreria= Libreria.getIstanza();
    private final LoggerService logger = LoggerServiceImpl.getIstanza();

    @Override
    public Set<Album> getTuttiAlbum(){
        return libreria.getAlbum().getElementi();
    }
    @Override
    public Set<Album> getAlbumByArtista(Artista a){

        return libreria.getAlbum().getElementi().stream().filter(album -> album.getArtistaAlbum().equals(a)).collect(Collectors.toSet());

    }
    @Override
    public void aggiungiAlbum(Album a){
        libreria.getAlbum().aggiungiElemento(a);
        logger.log("AGGIUNTA_ALBUM", "Album aggiunto alla libreria: " + a.getTitolo());
    }
    //qui devo rimuovere anche tutti i brani dell album
    @Override
    public void rimuoviAlbum(Album a){
        if(!libreria.getAlbum().getElementi().contains(a)){
            throw new AlbumNonTrovatoException("Operazione fallita. L'album"+ a.getTitolo()+" non esiste.");
        }
        libreria.getBrani().getElementi().removeAll(a.getBrani());
        libreria.getAlbum().rimuoviElemento(a);
        logger.log("RIMOZIONE_ALBUM", "Album rimosso dalla libreria: " + a.getTitolo());
    }

}
