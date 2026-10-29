package it.univq.service;

/**
 * interfaccia per gli oggetti che possono essere ricercati tramite il testo.
 */
public interface Ricercabile {

    /**
     * verifica se l'oggetto contiene il testo di ricerca.
     * @param testo il testo da cercare
     * @return {@code boolean} true se l'oggetto corrisponde al testo, false altrimenti
     */
    public boolean trovato(String testo);
}
