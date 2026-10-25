package it.univq.service.impl;

import it.univq.datamodel.Album;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Libreria;
import it.univq.exception.BranoNonTrovatoException;
import it.univq.service.LoggerService;
import it.univq.service.BranoService;

import java.util.Set;
import java.util.stream.Collectors;

public class BranoServiceImpl implements BranoService {
    private final Libreria libreria= Libreria.getIstanza();
    private static final LoggerService logger = LoggerServiceImpl.getIstanza();

    @Override
    public Set<Brano> getTuttiBrani(){
        return libreria.getBrani().getElementi();
    }

    @Override
    public Set<Brano> getBranoByAlbum(Album a){
        return libreria.getBrani().getElementi().stream().filter(brano ->brano.getAlbumBrano().equals(a)).collect(Collectors.toSet());
    }

    @Override
    public void aggiungiBrano(Brano b){
        libreria.getBrani().aggiungiElemento(b);
        logger.log("AGGIUNTA_BRANO", "Brano aggiunto: " + b.getTitolo());
    }

    @Override
    public void rimuoviBrano(Brano b){
        if(!libreria.getBrani().getElementi().contains(b)){
            throw new BranoNonTrovatoException("Operazione fallita. Il brano" +b.getTitolo()+ "non è presente.");
        }
        libreria.getBrani().rimuoviElemento(b);
        logger.log("RIMOZIONE_BRANO", "Brano rimosso: " + b.getTitolo());
    }

}
