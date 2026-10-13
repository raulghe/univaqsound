package it.univq.service;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Filtro;
import it.univq.datamodel.Ordine;
import it.univq.datamodel.Ricercabile;

import java.util.List;
import java.util.Set;

public interface RicercaService {

    public List<Ricercabile> cerca(String testo, Filtro f, Ordine o);

}
