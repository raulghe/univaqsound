package it.univq.service;

@FunctionalInterface
public interface Filtro<T> {

    public boolean filtra(T elemento);

}
