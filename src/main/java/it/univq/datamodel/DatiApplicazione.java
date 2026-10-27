package it.univq.datamodel;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * classe che serve per contenere i dati permanenti dell applicazione, quali brani, album, artisti e utenti
 */
public class DatiApplicazione implements Serializable { //contenitore di tutti i dati
    private static final long serialVersionUID = 1L;

    private Set<Brano> brani;
    private Set<Album> album;
    private Set<Artista> artisti;
    private List<Utente> utenti;

    /**
     * costruttore della classe {@code DatiApplicazione}
     * @param brani  {@code Set<Brano>} set di brani
     * @param album  {@code Set<Album>} set di album
     * @param artisti  {@code Set<Artista>} set di artisti
     * @param utenti  {@code List<Utente>} lista di utenti
     */
    public DatiApplicazione(Set<Brano> brani, Set<Album> album, Set<Artista> artisti, List<Utente> utenti) {
        this.brani = brani;
        this.album = album;
        this.artisti = artisti;
        this.utenti = utenti;
    }

    /**
     * metodo che serve per restituire i brani
     * @return {@code Set<Brano>} set di brani
     */
    public Set<Brano> getBrani() { return brani; }

    /**
     * metodo che serve per restituire gli album
     * @return {@code Set<Album>} set di album
     */
    public Set<Album> getAlbum() { return album; }

    /**
     * metodo che serve per restituire gli artisti
     * @return {@code Set<Artista>} set di artisti
     */
    public Set<Artista> getArtisti() { return artisti; }

    /**
     * metodo che serve per restituire gli utenti
     * @return {@code List<Utente>} lista di utenti
     * 
     */
    public List<Utente> getUtenti() { return utenti; }
}
