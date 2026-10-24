package it.univq.exception;

public class AlbumNonTrovatoException extends RuntimeException {
    public AlbumNonTrovatoException(String messaggio){
        super(messaggio);
    }
    
}
