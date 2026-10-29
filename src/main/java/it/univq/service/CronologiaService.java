package it.univq.service;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Utente;

/**
 * Servizio per la gestione della cronologia degli ascolti degli utenti.
 */
public interface CronologiaService {
    /**
     * Aggiunge una nuova voce alla cronologia degli ascolti dell'utente.
     *
     * @param u      l'utente alla cui cronologia aggiungere la voce
     * @param b      brano ascoltato
     * @param durata il tempo di ascolto del brano
     */
    public void aggiungiVoce(Utente u, Brano b, int durata);

}
