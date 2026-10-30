package it.univq.exception;

/**
 * eccezione che serve per segnalare che l'album non è presente nel catalogo
 */
public class AlbumNonTrovatoException extends RuntimeException {
    /**
     * costruttore della classe {@code AlbumNonTrovatoException}
     * @param messaggio il messaggio che descrive l'errore
     */
    public AlbumNonTrovatoException(String messaggio){
        super(messaggio);
    }
    
}
