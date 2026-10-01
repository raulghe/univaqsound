package it.univq.datamodel;


import java.util.LinkedHashSet;
import java.util.Set;

public class Catalogo<T> {

    private Set<T> elementi; //uso set perché non voglio duplicati nei 3 cataloghi

    public Catalogo() {
        this.elementi = new LinkedHashSet<>();
    }

    public Set<T> getElementi() {
        return elementi;
    }

    public void aggiungiElemento(T elemento) {
        this.elementi.add(elemento);
    }

    public void rimuoviElemento(T elemento) {
        this.elementi.remove(elemento);
    }



}
