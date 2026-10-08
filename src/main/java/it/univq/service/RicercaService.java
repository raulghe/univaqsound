package it.univq.service;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Ricercabile;

import java.util.Set;

public interface RicercaService {

    public Set<Ricercabile> cerca(String testo);
    public Set<Brano> cercaFiltro(String testo, String filtro, String valore);
}
