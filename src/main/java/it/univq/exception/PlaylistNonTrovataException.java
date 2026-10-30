package it.univq.exception;

/**
 * eccezione che serve per segnalare che la playlist non è presente tra quelle dell'utente
 */
public class PlaylistNonTrovataException extends RuntimeException{
    /**
     * costruttore della classe {@code PlaylistNonTrovataException}
     * @param messaggio il messaggio che descrive l'errore
     */
    public PlaylistNonTrovataException(String messaggio){
        super(messaggio);
    }
}
