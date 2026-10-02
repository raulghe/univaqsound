package it.univq.datamodel;

public class VoceCronologia {


    private Brano branoVoce;
    private int istanteAscolto;

    public Brano getBranoVoce() {
        return branoVoce;
    }

    public void setBranoVoce(Brano branoVoce) {
        this.branoVoce = branoVoce;
    }

    public int getIstanteAscolto() {
        return istanteAscolto;
    }

    public void setIstanteAscolto(int istanteAscolto) {
        this.istanteAscolto = istanteAscolto;
    }

    public int getDurataAscolto() {
        return durataAscolto;
    }

    public void setDurataAscolto(int durataAscolto) {
        this.durataAscolto = durataAscolto;
    }

    int durataAscolto;
}
