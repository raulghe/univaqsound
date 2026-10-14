package it.univq.service;



import java.util.List;

import it.univq.datamodel.Brano;

public interface RicercaService {

    public List<Ricercabile> cerca(String testo);
    
    public List<Brano> filtraBrani(Filtro<Brano> filtro,CriterioOrdinamento<Brano> criterio);

}
