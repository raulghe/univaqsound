package it.univq.service;



/**
 * interfaccia funzionale generica per definire un ordinamento personalizzato .
 * @param <T> il tipo di elementi da confrontare
 */
@FunctionalInterface
public interface CriterioOrdinamento<T> {
    /**
     * confronta due elementi specificati ai fini dell'ordinamento.
     * @param o1 il primo elemento 
     * @param o2 il secondo elemento
     * @return un valore negativo se o1 precede o2, zero se sono equivalenti, un valore positivo se o1 segue o2
     */
    public int ordina(T o1, T o2);
}