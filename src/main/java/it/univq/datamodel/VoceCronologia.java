package it.univq.datamodel;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * CLasse che rappresenta una voce nel cronologia
 */
public class VoceCronologia implements Serializable {


    private Brano branoVoce;
    private LocalDateTime istanteAscolto;
    private int durataAscolto;
    
    /**
     * costruttore della classe {@code VoceCronologia}
     * @param branoVoce il brano ascoltato
     * @param durataAscolto la durata dell  relativa al brano
     */
    public VoceCronologia(Brano branoVoce, int durataAscolto) {
        this.branoVoce = branoVoce;
        this.istanteAscolto = LocalDateTime.now();
        this.durataAscolto = durataAscolto;
    }

    /**
     * metodo che serve per ottenere il brano
     * @return {@code Brano} brano
     */
    public Brano getBranoVoce() {
        return branoVoce;
    }

    /**
     * metodo che serve per impostare il brano
     * @param branoVoce il brano da impostare
     */
    public void setBranoVoce(Brano branoVoce) {
        this.branoVoce = branoVoce;
    }

    /**
     * metodo che serve per ottenere l'istante di ascolto
     * @return {@code LocalDateTime} istante di ascolto
     */
    public LocalDateTime getIstanteAscolto() {
        return istanteAscolto;
    }



    /**
     * metodo che serve per ottenere la durata dell ascolto
     * @return {@code int} durata dell ascolto
     */
    public int getDurataAscolto() {
        return durataAscolto;
    }

    /**
     * metodo che serve per impostare la durata dell ascolto
     * @param durataAscolto la durata dell ascolto da impostare
     */
    public void setDurataAscolto(int durataAscolto) {
        this.durataAscolto = durataAscolto;
    }

    
}
