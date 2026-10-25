package it.univq.datamodel;

import java.io.Serializable;
import java.time.LocalDateTime;

public class VoceCronologia implements Serializable {


    private Brano branoVoce;
    private LocalDateTime istanteAscolto;
    private int durataAscolto;
    
    public VoceCronologia(Brano branoVoce, int durataAscolto) {
        this.branoVoce = branoVoce;
        this.istanteAscolto = LocalDateTime.now();
        this.durataAscolto = durataAscolto;
    }

    public Brano getBranoVoce() {
        return branoVoce;
    }

    public void setBranoVoce(Brano branoVoce) {
        this.branoVoce = branoVoce;
    }

    public LocalDateTime getIstanteAscolto() {
        return istanteAscolto;
    }



    public int getDurataAscolto() {
        return durataAscolto;
    }

    public void setDurataAscolto(int durataAscolto) {
        this.durataAscolto = durataAscolto;
    }

    
}
