package it.univq.datamodel;

import java.util.Objects;

public class Album {

    private String titolo;
    private int annoPubblicazione;
    private GenereMusicale genereAlbum;
    private String percorsoCopertina;
    private Artista artistaAlbum;

    public Album(String titolo, int annoPubblicazione, GenereMusicale genereBrano, String percorsoCopertina,
            Artista artistaAlbum) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.genereAlbum = genereBrano;
        this.percorsoCopertina = percorsoCopertina;
        this.artistaAlbum = artistaAlbum;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public GenereMusicale getGenereBrano() {
        return genereAlbum;
    }

    public void setGenereBrano(GenereMusicale genereBrano) {
        this.genereAlbum = genereBrano;
    }

    public String getPercorsoCopertina() {
        return percorsoCopertina;
    }

    public void setPercorsoCopertina(String percorsoCopertina) {
        this.percorsoCopertina = percorsoCopertina;
    }

    public Artista getArtistaAlbum() {
        return artistaAlbum;
    }

    public void setArtistaAlbum(Artista artistaAlbum) {
        this.artistaAlbum = artistaAlbum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Album album = (Album) o;
        return this.titolo.equals(album.titolo) && this.artistaAlbum.equals(album.artistaAlbum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, artistaAlbum);
    }

}
