package it.univq.datamodel;

import java.util.ArrayList;

import java.util.List;

/**
 *  Classe che rappresenta la coda di riproduzione e implementa il pattern singleton
 *  
 */
public class CodaDiRiproduzione {
    //final la protegge all interno della classe mentre private all esterno della classe
    private static final CodaDiRiproduzione istanza = new CodaDiRiproduzione();
    private List<Brano> coda;
    private boolean shuffle;
    private boolean repeat;
    private int indiceBranoCorrente; // tiene traccia del brano attuale in cui si trova

    /**
     * metodo che restituisce l indice del brano corrente
     * @return {@code int}  indice brano corrente
     */
    public int getIndiceBranoCorrente() {
        return indiceBranoCorrente;
    }

    /**
     * metodo che serve a spostare l'indice della coda
     * @param indiceBranoCorrente {@code int} indice da impostare
     */
    public void setIndiceBranoCorrente(int indiceBranoCorrente) {
        this.indiceBranoCorrente = indiceBranoCorrente;
    }

    /**
     * metodo che implementa il pattern singleton per ritornare sempre la stessa istanza della coda di riproduzione
     * @return {@code CodaDiRiproduzione} istanza della coda
     */
    public static CodaDiRiproduzione getIstanza() {
        return istanza;
    }

    /**
     * metodo che serve a inizializzare la coda di riproduzione
     */
    private CodaDiRiproduzione() {
        this.coda = new ArrayList<>();
        this.shuffle = false;
        this.repeat = false;
        indiceBranoCorrente = -1;
    }

    /**
     * metodo che restituisce lo stato dello shuffle
     * @return {@code boolean} shuffle
     */
    public boolean isShuffle() {
        return shuffle;
    }

    /**
     * metodo che imposta lo stato dello shuffle
     * @param shuffle {@code boolean} shuffle
     */
    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    /**
     * metodo che restituisce lo stato del repeat
     * @return {@code boolean} repeat
     */
    public boolean isRepeat() {
        return repeat;
    }

    /**
     * metodo che imposta lo stato del repeat
     * @param repeat {@code boolean} repeat
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    /**
     * metodo che restituisce la lista di brani nella coda
     * @return {@code List<Brano>} coda di riproduzione
     */
    public List<Brano> getCoda() {
        return coda;
    }
}
