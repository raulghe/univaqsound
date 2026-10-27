package it.univq.service;

import it.univq.datamodel.Artista;

import java.util.Set;

/**
 * interfaccia che definisce i servizi relativi agli artisti.
 */
public interface ArtistaService {
    /**
     * restituisce tutti gli artisti presenti nella libreria.
     * @return un {@code Set<Artista>} contenente tutti gli artisti
     */
    public Set<Artista> getTuttiArtisti();

    /**
     * aggiunge un nuovo artista alla libreria.
     * @param a l'artista da aggiungere alla libreria
     */
    public void aggiungiArtista(Artista a);

    /**
     * rimuove un artista dalla libreria.
     * @param a l'artista da rimuovere dalla libreria
     */
    public void rimuoviArtista(Artista a);

    /**
     * calcola e restituisce il numero totale di ascolti complessivi di un artista, usato nelle statistiche
     * @param a l'artista di cui calcolare gli ascolti totali
     * @return il numero totale di ascolti da tutti i brani dell'artista
     */
    public int getAscoltiTotali(Artista a);
}
