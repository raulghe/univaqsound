package it.univq.datamodel;

import java.util.Objects;

public class Brano {
    private String titolo;
    private int durata;
    private GenereMusicale genereBrano;
    private Album albumBrano;

    public Brano(String titolo, int durata, GenereMusicale genereBrano, Album albumBrano) {
        this.titolo = titolo;
        this.durata = durata;
        this.genereBrano = genereBrano;
        this.albumBrano = albumBrano;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public GenereMusicale getGenereBrano() {
        return genereBrano;
    }

    public void setGenereBrano(GenereMusicale genereBrano) {
        this.genereBrano = genereBrano;
    }

    public Album getAlbumBrano() {
        return albumBrano;
    }

    public void setAlbumBrano(Album albumBrano) {
        this.albumBrano = albumBrano;
    }

    // due brani sono uguali se hanno stesso titolo e la stessa durata (potrei avere stesso brano ma con versioni diverse in due album)
    @Override
    public boolean equals (Object o){
        if(this==o) return true;
        if(o==null || o.getClass() != this.getClass() )return false;
        Brano brano= (Brano)o;
        return this.titolo.equals(brano.titolo) && this.durata==brano.durata;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, durata);
    }

    @Override
    public String toString() {
        return titolo + " - " + albumBrano.getArtistaAlbum() + " (" + albumBrano.getTitolo() + ")";
    }
}
