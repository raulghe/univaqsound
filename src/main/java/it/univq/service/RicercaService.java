package it.univq.service;



import java.util.List;

import it.univq.datamodel.Brano;

/**
 * servizio per la ricerca di elementi e il filtraggio dei brani.
 */
public interface RicercaService {

    /**
     * cerca gli elementi che corrispondono al testo specificato.
     * @param testo il testo da cercare
     * @return {@code List<Ricercabile>} lista degli elementi trovati
     */
    public List<Ricercabile> cerca(String testo);

    /**
     * filtra i brani secondo il filtro specificato e li ordina secondo il criterio indicato.
     * @param filtro il filtro da applicare ai brani
     * @param criterio il criterio di ordinamento
     * @return {@code List<Brano>} lista dei brani filtrati e ordinati
     */
    public List<Brano> filtraBrani(Filtro<Brano> filtro,CriterioOrdinamento<Brano> criterio);

}
