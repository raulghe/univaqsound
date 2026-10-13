package it.univq.service.impl;

import it.univq.datamodel.Album;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Libreria;
import it.univq.service.BranoService;

import java.util.Set;
import java.util.stream.Collectors;

public class BranoServiceImpl implements BranoService {
    private final Libreria libreria= Libreria.getIstanza();
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
    }
    @Override
    public void rimuoviBrano(Brano b){
        libreria.getBrani().rimuoviElemento(b);
    }



}
