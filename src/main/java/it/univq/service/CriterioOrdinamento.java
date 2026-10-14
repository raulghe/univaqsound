package it.univq.service;



@FunctionalInterface
public interface CriterioOrdinamento<T> {
    public int ordina(T o1, T o2);
}