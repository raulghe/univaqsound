package it.univq.exception;

public class PlaylistNonTrovataException extends RuntimeException{
    public PlaylistNonTrovataException(String messaggio){
        super(messaggio);
    }
}
