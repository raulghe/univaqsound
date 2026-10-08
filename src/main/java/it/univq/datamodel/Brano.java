package it.univq.datamodel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Brano implements Riproducibile ,Ricercabile{
    private String titolo;
    private Integer durata;
    private GenereMusicale genereBrano;
    private Album albumBrano;
    private Integer numeroAscolti;
    private LocalDateTime dataAggiunta;

    public Brano(String titolo, int durata, GenereMusicale genereBrano, Album albumBrano) {
        this.titolo = titolo;
        this.durata = durata;
        this.genereBrano = genereBrano;
        this.albumBrano = albumBrano;
        this.numeroAscolti = 0;
        this.dataAggiunta=LocalDateTime.now();
    }

    public LocalDateTime getDataAggiunta() {
        return dataAggiunta;
    }

    public void setDataAggiunta(LocalDateTime dataAggiunta) {
        this.dataAggiunta = dataAggiunta;
    }

    public Integer getNumeroAscolti() {
        return numeroAscolti;
    }

    public void setNumeroAscolti(Integer numeroAscolti) {
        this.numeroAscolti = numeroAscolti;
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

    // due brani sono uguali se hanno stesso titolo e la stessa durata (potrei avere
    // stesso brano ma con versioni diverse in due album)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Brano brano = (Brano) o;
        return this.titolo.equals(brano.titolo) && this.durata == brano.durata;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, durata);
    }

    @Override
    public String toString() {
        return titolo + " - " + albumBrano.getArtistaAlbum() + " (" + albumBrano.getTitolo() + ")";
    }
    @Override
    public List<Brano> getDaRiprodurre(){
        return List.of(this);
    }
    @Override
    public boolean trovato(String testo){
        if(testo.isBlank())return false;
        return this.titolo.toLowerCase().contains(testo.toLowerCase());

    }

}
