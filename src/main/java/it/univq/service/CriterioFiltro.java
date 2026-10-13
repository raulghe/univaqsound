package it.univq.service;


@FunctionalInterface
public interface CriterioFiltro<T> {
    boolean accetta(T elemento);
}