package it.univq.datamodel;

import it.univq.service.Ricercabile;
import it.univq.service.Riproducibile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Rappresenta un album musicale all'interno dell applicaizone.
 * Un album appartiene a un {@link Artista} e  contiene una lista di {@link Brano}
 * ed implementa le interfacce {@link Riproducibile}, {@link Ricercabile} e {@link Serializable}.
 */
public class Album implements Riproducibile, Ricercabile, Serializable {

    private String titolo;
    private int annoPubblicazione;
    private GenereMusicale genereAlbum;
    private String percorsoCopertina;
    private Artista artistaAlbum;
    private List<Brano> brani = new ArrayList<>();

    /**
     * Costruttore per album
     *
     * @param titolo             il titolo dell'album
     * @param annoPubblicazione  l'anno di pubblicazione
     * @param genereAlbum        il genere musicale prevalente dell'album
     * @param percorsoCopertina  il percorso o nome del file dell'immagine di copertina non ancora usato 
     * @param artistaAlbum       l'artista che puo essere solita o gruppo autore dell'album
     */
    public Album(String titolo, int annoPubblicazione, GenereMusicale genereAlbum, String percorsoCopertina,
                 Artista artistaAlbum) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.genereAlbum = genereAlbum;
        this.percorsoCopertina = percorsoCopertina;
        this.artistaAlbum = artistaAlbum;
    }

    /**
     * Restituisce il genere musicale dell'album.
     *
     * @return il {@link GenereMusicale} dell'album
     */
    public GenereMusicale getGenereAlbum() {
        return genereAlbum;
    }

    /**
     * Imposta il genere musicale dell'album.
     *
     * @param genereAlbum il nuovo genere musicale
     */
    public void setGenereAlbum(GenereMusicale genereAlbum) {
        this.genereAlbum = genereAlbum;
    }

    /**
     * Restituisce la lista dei brani che compongono l'album.
     *
     * @return la lista dei {@link Brano}
     */
    public List<Brano> getBrani() {
        return brani;
    }

    /**
     * Imposta la lista dei brani dell'album.
     *@param brani la nuova lista di brani
     */
    public void setBrani(List<Brano> brani) {
        this.brani = brani;
    }

    /**
     * Restituisce l'anno di pubblicazione dell'album.
     *
     * @return l'anno di uscita
     */
    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    /**
     * Imposta l'anno di pubblicazione dell'album.
     *
     * @param annoPubblicazione il nuovo anno di pubblicazione
     */
    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    /**
     * Restituisce il titolo dell'album.
     *
     * @return il titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il titolo dell'album.
     *
     * @param titolo il nuovo titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce il percorso dell'immagine di copertina.
     *
     * @return il percorso del file copertina
     */
    public String getPercorsoCopertina() {
        return percorsoCopertina;
    }

    /**
     * Imposta il percorso dell'immagine di copertina.
     *
     * @param percorsoCopertina il nuovo percorso del file copertina
     */
    public void setPercorsoCopertina(String percorsoCopertina) {
        this.percorsoCopertina = percorsoCopertina;
    }

    /**
     * Restituisce l'artista dell album.
     *
     * @return l'oggetto {@link Artista}
     */
    public Artista getArtistaAlbum() {
        return artistaAlbum;
    }

    /**
     * Imposta l'artista proprietario dell'album.
     *
     * @param artistaAlbum il nuovo artista
     */
    public void setArtistaAlbum(Artista artistaAlbum) {
        this.artistaAlbum = artistaAlbum;
    }

    /**
     * Verifica l'uguaglianza tra questo album e un altro oggetto.
     * Due album sono considerati uguali se hanno lo stesso titolo e lo stesso artista.
     *
     * @param o l'oggetto da confrontare
     * @return {@code true} se gli album sono uguali, {@code false} altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Album album = (Album) o;
        return this.titolo.equals(album.titolo) && this.artistaAlbum.equals(album.artistaAlbum);
    }

    /**
     * Calcola l'hash code per l'album basato su titolo e artista.
     *
     * @return il valore hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(titolo, artistaAlbum);
    }

    /**
     * Restituisce la lista di brani di un album da aggiungere alla coda di riproduzione.
     *
     * @return lista immutabile di {@link Brano} dell'album
     */
    @Override
    public List<Brano> getDaRiprodurre() {
        return List.copyOf(this.getBrani());
    }

    /**
     * Controlla se il titolo dell'album contiene il testo cercato..
     *
     * @param testo la stringa di ricerca inserita dall'utente
     * @return {@code true} se il testo è contenuto nel titolo, {@code false} altrimenti
     */
    @Override
    public boolean trovato(String testo) {
        if (testo.isBlank()) return false;
        return this.titolo.toLowerCase().contains(testo.toLowerCase());
    }
}
