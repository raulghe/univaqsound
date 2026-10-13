package it.univq.service;

import it.univq.datamodel.Filtro;
import it.univq.datamodel.Ordine;

import java.util.List;

public interface RicercaService {

    public List<Ricercabile> cerca(String testo, Filtro f, Ordine o);

}
