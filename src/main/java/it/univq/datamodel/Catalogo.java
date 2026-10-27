package it.univq.datamodel;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe generica usata per rappresentare un insieme di elementi
 * @param <T>  tipo di elementi contenuti nel catalogo
 */
public class Catalogo<T> implements Serializable {

    private Set<T> elementi; //uso set perché non voglio duplicati nei 3 cataloghi

    /**
     * costruttore di Catalogo
     * crea il set di elementi
     */
    public Catalogo() {
        this.elementi = new HashSet<>();
    }

    /**
     * metodo che restituisce il set di elementi del catalogo
     * @return {@code Set<T>} set di elementi del catalogo
     */
    public Set<T> getElementi() {
        return elementi;
    }

    /**
     * metood che aggiunge un elemento al set di elementi del catalogo 
     * @param elemento  elemento da aggiungere
     */
    public void aggiungiElemento(T elemento) {
        this.elementi.add(elemento);
    }

    /**
     * metodo che rimuove un elemento dal set di elementi del catalogo 
     * @param elemento  elemento da rimuovere
     */
    public void rimuoviElemento(T elemento) {
        this.elementi.remove(elemento);
    }



}
