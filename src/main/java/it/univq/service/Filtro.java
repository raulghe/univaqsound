package it.univq.service;

/**
 * Interfaccia funzionale che definisce un criterio di filtraggio.
 * @param <T> il tipo degli elementi da filtrare
 */
@FunctionalInterface
public interface Filtro<T> {

    /**
     *Controlla se l'elemento specificato soddisfa il criterio di filtraggio.
     * @param elemento l'elemento da verificare
     * @return {@code boolean} true se l'elemento soddisfa il criterio, false se non lo verifica
     */
    public boolean filtra(T elemento);

}
