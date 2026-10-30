package it.univq.exception;

/**
 * eccezione che serve per segnalare che l'elemento è già presente e la lista lo accetterebbe una seconda volta
 */
public class DuplicatoException extends RuntimeException{
    
    /**
     * costruttore della classe {@code DuplicatoException}
     * @param messaggio il messaggio che descrive l'errore
     */
    public DuplicatoException(String messaggio){
        super(messaggio);
    }
}
