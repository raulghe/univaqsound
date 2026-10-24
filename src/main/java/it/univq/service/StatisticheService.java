package it.univq.service;
import java.util.List;

import it.univq.datamodel.Artista;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Utente;

public interface StatisticheService {
  
    public List<Brano> braniPiuAscoltati();
    public List<Artista> artistiPiuAscoltati();
    public Integer tempoTotaleAscoltato(Utente u);
    public List<Brano> braniProposti(Utente u);




}
