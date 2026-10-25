package it.univq.datamodel;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DatiApplicazione implements Serializable { //contenitore di tutti i dati
    private static final long serialVersionUID = 1L;

    private Set<Brano> brani;
    private Set<Album> album;
    private Set<Artista> artisti;
    private List<Utente> utenti;

    public DatiApplicazione(Set<Brano> brani, Set<Album> album, Set<Artista> artisti, List<Utente> utenti) {
        this.brani = brani;
        this.album = album;
        this.artisti = artisti;
        this.utenti = utenti;
    }

    public Set<Brano> getBrani() { return brani; }
    public Set<Album> getAlbum() { return album; }
    public Set<Artista> getArtisti() { return artisti; }
    public List<Utente> getUtenti() { return utenti; }
}
