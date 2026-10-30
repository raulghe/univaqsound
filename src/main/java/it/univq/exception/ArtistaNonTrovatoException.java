package it.univq.exception;

/**
 * eccezione che serve per segnalare che l'artista non è presente nel catalogo
 */
public class ArtistaNonTrovatoException extends RuntimeException{
    /**
     * costruttore della classe {@code ArtistaNonTrovatoException}
     * @param messaggio il messaggio che descrive l'errore
     */
    public ArtistaNonTrovatoException(String messaggio){
        super(messaggio);
    }
    
}
