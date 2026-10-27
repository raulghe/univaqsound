package it.univq.service;

/**
 * interfaccia che definisce i servizi relativi alla coda di riproduzione.
 */
public interface CodaDiRiproduzioneService {

    /**
     * svuota la coda di riproduzione e quindi elimina tutti i brani contenuti in essa
     */
    public void svuotaCoda();

    /**
     * aggiunge un elemento riproducibile alla coda di riproduzione, se l'elemento è un brano lo aggiunge alla fine della coda, se l'elemento è una playlist
     * o album svuota la coda e aggiunge tutti i brani alla coda
     * @param r l'elemento riproducibile da aggiungere alla coda di riproduzione
     */
    public void aggiungiAllaCoda(Riproducibile r);

    /**
     * imposta l'indice sull ultimo elemento della coda di riproduzione
     */
    public void impostaUltimoIndice();
}
