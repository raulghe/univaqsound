package it.univq.service;

import it.univq.datamodel.Utente;
import it.univq.datamodel.Brano;

/**
 * servizio per il controllo della riproduzione dei brani.
 */
public interface PlayerService {

    /**
     * avvia la riproduzione del brano corrente.
     */
    public void play( );

    /**
     * mette in pausa la riproduzione.
     */
    public void pausa();

    /**
     * passa al brano successivo.
     */
    public void avanti();

    /**
     * torna al brano precedente.
     */
    public void indietro();

    /**
     * cambia la riproduzione casuale (shuffle).
     */
    public void cambiaShuffle();

    /**
     * cambia la modalità di ripetizione della coda.
     */
    public void cambiaModalitaRipetizione();

    /**
     * imposta l'utente a cui è associata la riproduzione.
     * @param u l'utente da impostare
     */
    public void setUtente(Utente u );

    /**
     * restituisce il tempo di riproduzione trascorso del brano corrente.
     * @return {@code double} il tempo trascorso in secondi
     */
    public double getTempoCorrenteSecondi();

    /**
     * restituisce la durata totale del brano corrente.
     * @return {@code double} la durata totale in secondi
     */
    public double getDurataTotaleSecondi();

    /**
     * imposta il volume della riproduzione.
     * @param volume il valore del volume da impostare
     */
    public void setVolume(double volume);

    /**
     * restituisce il brano in riproduzione.
     * @return {@code Brano} il brano corrente
     */
    public Brano getBranoCorrente();

}