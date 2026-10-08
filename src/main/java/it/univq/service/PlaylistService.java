package it.univq.service.impl;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Playlist;

public interface PlaylistService {

    public void aggiungiBrano(Playlist p, Brano b);
    public void rimuoviBrano(Playlist p, Brano b);
}
