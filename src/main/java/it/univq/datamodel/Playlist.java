package it.univq.datamodel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String nome;
    private String descrizione;
    private LocalDateTime dataCreazione;
    private Utente proprietario;
    private List<Brano> braniPlaylist;

    public Playlist(String nome, String descrizione, LocalDateTime dataCreazione, Utente proprietario) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.dataCreazione = dataCreazione;
        this.proprietario = proprietario;
        this.braniPlaylist= new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public List<Brano> getBrani() {
        return braniPlaylist;
    }

    public void aggiungiBrano(Brano brano) {
        if (brano == null) {
            throw new IllegalArgumentException("Il brano da aggiungere non può essere nullo.");
        }
        this.braniPlaylist.add(brano);
    }

    public void rimuoviBrano(Brano brano) {
        if (brano == null) {
            throw new IllegalArgumentException("Il brano da rimuovere non può essere nullo.");
        }
        this.braniPlaylist.remove(brano);
    }

}
