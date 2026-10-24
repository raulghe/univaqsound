package it.univq.service;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Utente;

public interface CronologiaService {
    public void aggiungiVoce(Utente u, Brano b, int durata);

}
