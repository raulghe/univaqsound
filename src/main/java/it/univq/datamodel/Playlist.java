package it.univq.datamodel;

import it.univq.service.Riproducibile;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * classe che definisce una playlist formata da piu brani e associata a un utente
 */
public class Playlist implements Riproducibile, Serializable {
    private String nome;
    private String descrizione;
    private LocalDateTime dataCreazione;
    private Utente proprietario;
    private List<Brano> braniPlaylist= new ArrayList<>();

    /**
     * costruttore della classe {@code Playlist}
     * @param nome il nome della playlist
     * @param descrizione la descrizione della playlist
     * @param proprietario l utente proprietario della playlist
     */
    public Playlist(String nome, String descrizione, Utente proprietario) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.dataCreazione = LocalDateTime.now();
        this.proprietario = proprietario;
    }

    /**
     * metodo che serve per ottenere il nome della playlist
     * @return {@code String} nome della playlist
     */
    public String getNome() {
        return nome;
    }

    /**
     * metodo che serve per impostare il nome della playlist
     * @param nome il nuovo nome della playlist
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * metodo che serve per ottenere la descrizione della playlist
     * @return {@code String} descrizione della playlist
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * metodo che serve per impostare la descrizione della playlist
     * @param descrizione la nuova descrizione della playlist
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * metodo che serve per ottenere la data di creazione della playlist
     * @return {@code LocalDateTime} data di creazione della playlist
     */
    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    /**
     * metodo che serve per impostare la data di creazione della playlist
     * @param dataCreazione la nuova data di creazione della playlist
     */
    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    /**
     * metodo che serve per ottenere il proprietario della playlist
     * @return {@code Utente} proprietario della playlist
     */
    public Utente getProprietario() {
        return proprietario;
    }

    /**
     * metodo che serve per impostare il proprietario della playlist
     * @param proprietario il nuovo proprietario della playlist
     */
    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * metodo che serve per ottenere la lista di brani della playlist
     * @return {@code List<Brano>} lista dei brani della playlist
     */
    public List<Brano> getBrani() {
        return braniPlaylist;
    }

    /**
     * metodo che serve per ottenere la lista di brani da riprodurre
     * @return {@code List<Brano>} lista di brani da riprodurre
     */
    @Override
    public List<Brano> getDaRiprodurre(){
        return List.copyOf(this.getBrani());
    }

    /**
     * metodo che serve per ottenere il nome della playlist
     * @return {@code String} nome della playlist
     */
    @Override 
    public String toString() {
        return nome;
    }


}
