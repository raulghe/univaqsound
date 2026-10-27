package it.univq.datamodel;

import it.univq.service.Ricercabile;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Rappresenta un artista generico all'interno del catalogo dell'applicazione.
 * È una classe astratta estesa da {@link ArtistaSolista} e {@link ArtistaGruppo}.
 * Implementa le interfacce {@link Ricercabile} e {@link Serializable}.
 */
public abstract class Artista implements Ricercabile, Serializable {
    private String nomeArte;
    private String biografia;
    private GenereMusicale generePrincipale;
    private Set<Album> discografia = new HashSet<>();

    /**
     * Costruttore per inizializzare le informazioni di base di un artista.
     * @param nomeArte il nome d'arte dell'artista
     * @param biografia biografia o descrizione
     * @param generePrincipale genere musicale principale dell'artista
     */
    public Artista(String nomeArte, String biografia, GenereMusicale generePrincipale) {
        this.nomeArte = nomeArte;
        this.biografia = biografia;
        this.generePrincipale = generePrincipale;
    }

    /**
     * Verifica l'uguaglianza tra due artisti.
     * Due artisti sono considerati uguali se hanno lo stesso nome d'arte.
     * @param o l'oggetto con cui effettuare il confronto
     * @return {@code true} se gli artisti sono uguali, {@code false} altrimenti
     */
    @Override 
    public boolean equals(Object o) {
        if (o == this) 
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Artista artista = (Artista) o;
        return this.nomeArte.equals(artista.nomeArte);
    }

    /**
     * Calcola il valore hash per l'artista basato sul nome d'arte.
     * @return il valore hash
     */
    @Override 
    public int hashCode() {
        return Objects.hash(nomeArte);
    }

    /**
     * Restituisce il nome d'arte dell'artista.
     * @return il nome d'arte
     */
    public String getNomeArte() {
        return nomeArte;
    }

    /**
     * Imposta il nome d'arte dell'artista.
     * @param nomeArte il nuovo nome d'arte
     */
    public void setNomeArte(String nomeArte) {
        this.nomeArte = nomeArte;
    }

    /**
     * Restituisce l'insieme degli album dell'asrtista.
     * @return il {@link Set} di {@link Album}
     */
    public Set<Album> getDiscografia() {
        return discografia;
    }

    /**
     * Imposta la discografia dell'artista.
     * @param discografia insieme di album
     */
    public void setDiscografia(Set<Album> discografia) {
        this.discografia = discografia;
    }

    /**
     * Restituisce la biografia dell'artista.
     * @return il testo della biografia
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * Imposta la biografia dell'artista.
     * @param biografia la biografia
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * Restituisce il genere musicale dell'artista.
     * @return il {@link GenereMusicale} principale
     */
    public GenereMusicale getGenereArtista() {
        return generePrincipale;
    }

    /**
     * Imposta il genere musicale ell'artista.
     * @param genereArtista nuovo genere musicale
     */
    public void setGenereArtista(GenereMusicale genereArtista) {
        this.generePrincipale = genereArtista;
    }

    /**
     * Verifica se il nome d'arte dell'artista contiene il testo cercato.
     * @param testo la stringa di ricerca 
     * @return {@code true} se la ricerca corrsiponde , {@code false} altrimenti
     */
    @Override
    public boolean trovato(String testo) {
        if (testo.isBlank()) return false;
        return this.nomeArte.toLowerCase().contains(testo.toLowerCase());
    }
}