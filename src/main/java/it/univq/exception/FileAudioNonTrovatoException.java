package it.univq.exception;

/**
 * eccezione che serve per segnalare che il percorso del brano non corrisponde a un file audio presente
 */
public class FileAudioNonTrovatoException extends RuntimeException{
    /**
     * costruttore della classe {@code FileAudioNonTrovatoException}
     * @param messaggio il messaggio che descrive l'errore
     */
    public FileAudioNonTrovatoException(String messaggio){
        super(messaggio);
    }
}
