package it.univq.exception;

/**
 * eccezione che serve per segnalare che si chiede di riprodurre qualcosa che non contiene brani
 */
public class RiproducibileVuotoException extends RuntimeException{
    /**
     * costruttore della classe {@code RiproducibileVuotoException}
     * @param messaggio il messaggio che descrive l'errore
     */
    public RiproducibileVuotoException(String messaggio){
        super(messaggio);
    }
}
