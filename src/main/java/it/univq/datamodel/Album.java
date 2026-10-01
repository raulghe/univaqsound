package it.univq.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Album {

    private String titolo;
    private int annoPubblicazione;
    private GenereMusicale genereAlbum;
    private String percorsoCopertina;
    private Artista artistaAlbum;
    private List<Brano> brani=new ArrayList<>();

    public GenereMusicale getGenereAlbum() {
        return genereAlbum;
    }

    public void setGenereAlbum(GenereMusicale genereAlbum) {
        this.genereAlbum = genereAlbum;
    }

    public List<Brano> getBrani() {
        return brani;
    }

    public void setBrani(List<Brano> brani) {
        this.brani = brani;
    }

    public Album(String titolo, int annoPubblicazione, GenereMusicale genereAlbum, String percorsoCopertina,
                 Artista artistaAlbum) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.genereAlbum = genereAlbum;
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
