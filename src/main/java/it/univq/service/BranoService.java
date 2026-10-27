package it.univq.service;

import it.univq.datamodel.Album;
import it.univq.datamodel.Brano;

import java.util.Set;

/**
 * interfaccia che definisce i servizi relativi ai brani.
 */
public interface BranoService {
    /**
     * restituisce tutti i brani presenti nella libreria.
     * @return un {@code Set<Brano>} contenente tutti i brani
     */
    public Set<Brano> getTuttiBrani();

    /**
     * aggiunge un nuovo brano alla libreria.
     * @param b il brano da aggiungere alla libreria
     */
    public void aggiungiBrano(Brano b);

    /**
     * rimuove un brano dalla libreria.
     * @param b il brano da rimuovere dalla libreria
     */
    public void rimuoviBrano(Brano b);

    /**
     * recupera tutti i brani appartenenti a uno specifico album.
     * @param a l'album di cui si vogliono ottenere i brani
     * @return un {@code Set<Brano>} contenente i brani dell'album specificato
     */
    public Set<Brano> getBranoByAlbum(Album a);
}
