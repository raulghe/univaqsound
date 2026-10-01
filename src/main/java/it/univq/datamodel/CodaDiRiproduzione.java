package it.univq.datamodel;

import java.util.ArrayList;

import java.util.List;

public class CodaDiRiproduzione {
    //final la protegge all interno della classe mentre private all esterno della classe
    private static final CodaDiRiproduzione istanza = new CodaDiRiproduzione();
    private List<Brano> coda;
    private boolean shuffle;
    private boolean repeat;
    private int indiceBranoCorrente; // tiene traccia del brano attuale in cui si trova

    public static CodaDiRiproduzione getIstanza() {
        return istanza;
    }

    private CodaDiRiproduzione() {
        this.coda = new ArrayList<>();
        this.shuffle = false;
        this.repeat = false;
        indiceBranoCorrente = -1;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public List<Brano> getCoda() {
        return coda;
    }
}
