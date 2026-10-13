package it.univq.service;

import it.univq.datamodel.Album;
import it.univq.datamodel.Brano;

import java.util.Set;

public interface BranoService {
    public Set<Brano> getTuttiBrani();
    public void aggiungiBrano(Brano b);
    public void rimuoviBrano(Brano b);
    public Set<Brano> getBranoByAlbum(Album a);
}
