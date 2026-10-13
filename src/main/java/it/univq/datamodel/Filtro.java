package it.univq.datamodel;
// da aggiungere al diagramma
public class Filtro {
    //filtri per brano = generemusicale-durata-anno
    public enum TipoFiltro{
        GENERE,DURATA,ANNO
    };
    private TipoFiltro tipo;
    private String valore;

    public Filtro(TipoFiltro tipo, String valore){
        this.tipo=tipo;
        this.valore=valore;
    }

    public TipoFiltro getTipo() {
        return tipo;
    }

    public void setTipo(TipoFiltro tipo) {
        this.tipo = tipo;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }
}
