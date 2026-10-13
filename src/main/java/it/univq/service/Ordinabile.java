package it.univq.service;

import it.univq.datamodel.Ordine;

public interface Ordinabile <T>{
    public void ordina(T o, Ordine parametroOrdinamento);
}
