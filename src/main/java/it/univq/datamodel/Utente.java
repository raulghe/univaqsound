package it.univq.datamodel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utente {
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
    private List<Playlist> playlist;
    private CronologiaAscolti cronologia;
    private Set<GenereMusicale> preferenzeMusicali=new HashSet<>(); //uso il set perche non volglio avere duplicati

    public Set<GenereMusicale> getPreferenzeMusicali() {
        return preferenzeMusicali;
    }

    public void setPreferenzeMusicali(Set<GenereMusicale> preferenzeMusicali) {
        this.preferenzeMusicali = preferenzeMusicali;
    }

    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
    }

    public CronologiaAscolti getCronologia() {
        return cronologia;
    }

    public void setCronologia(CronologiaAscolti cronologia) {
        this.cronologia = cronologia;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Utente(String nome, String cognome, String password, String email, LocalDate dataDiNascita, boolean isAdministrator) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataDiNascita = dataDiNascita;
        this.playlist=new ArrayList<>();

    }
}
