package it.univq.service;



@FunctionalInterface
public interface CriterioOrdinamento<T> {
    int confronta(T o1, T o2);
}