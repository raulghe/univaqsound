package it.univq.exception;

/**
 * eccezione che serve per segnalare che il brano non è presente nella collezione indicata, che sia una playlist o il catalogo
 */
public class BranoNonTrovatoException extends RuntimeException{
    /**
     * costruttore della classe {@code BranoNonTrovatoException}
     * @param messaggio il messaggio che descrive l'errore
     */
    public BranoNonTrovatoException(String messaggio){
        super(messaggio);
    }
}
