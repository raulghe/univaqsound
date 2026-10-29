package it.univq.service;

import it.univq.datamodel.Brano;

import java.util.List;

/**
 * interfaccia per gli elementi che possono essere riprodotti.
 * un elemento riproducibile restituisce la lista di {@link Brano} da inserire nella coda di riproduzione.
 */
public interface Riproducibile {

    /**
     * restituisce la lista di brani da riprodurre.
     * @return lista di {@link Brano} da riprodurre
     */
    public List<Brano> getDaRiprodurre();
}
