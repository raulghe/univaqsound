package it.univq.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 
/**
 * clase che rappresenta l utente (ascoltatore o amministratore)
 */
public class Utente implements Serializable {
 
   
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
    private List<Playlist> playlist;
    private CronologiaAscolti cronologia;
    private Set<GenereMusicale> preferenzeMusicali=new HashSet<>(); //uso il set perche non volglio avere duplicati
    private boolean isAmministratore;
   
    /**
     * metodo che serve per ottenere la lista di generi musicali preferiti dell'utente
     * @return {@code Set<GenereMusicale>} lista di generi musicali preferiti dell'utente
     */
    public Set<GenereMusicale> getPreferenzeMusicali() {
        return preferenzeMusicali;
    }
 
    /**
     * metodo che serve per impostare la lista di generi musicali preferiti dell'utente
     * @param preferenzeMusicali la nuova lista di generi musicali preferiti dell'utente
     */
    public void setPreferenzeMusicali(GenereMusicale... generi) {
        Collections.addAll(this.preferenzeMusicali, generi);
    }
 
    /**
     * metodo che serve per ottenere la lista di playlist dell'utente
     * @return {@code List<Playlist>} lista di playlist dell'utente
     */
    public List<Playlist> getPlaylist() {
        return playlist;
    }
 
    /**
     * metodo che serve per impostare la lista di playlist dell'utente
     * @param playlist la nuova lista di playlist dell'utente
     */
    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
    }
 
    /**
     * metodo che serve per ottenere la cronologia degli ascolti dell'utente
     * @return {@code CronologiaAscolti} cronologia degli ascolti dell'utente
     */
    public CronologiaAscolti getCronologia() {
        return cronologia;
    }
 
    /**
     * metodo che serve per impostare la cronologia degli ascolti dell'utente
     * @param cronologia la nuova cronologia degli ascolti dell'utente
     */
    public void setCronologia(CronologiaAscolti cronologia) {
        this.cronologia = cronologia;
    }
 
    /**
     * metodo che serve per ottenere la data di nascita dell'utente
     * @return {@code LocalDate} data di nascita dell'utente
     */
    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }
 
    /**
     * metodo che serve per impostare la data di nascita dell'utente
     * @param dataDiNascita la nuova data di nascita dell'utente
     */
    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }
 
 
    /**
     * metodo che serve per ottenere l'email dell'utente
     * @return {@code String} email dell'utente
     */
    public String getEmail() {
        return email;
    }
 
    /**
     * metodo che serve per impostare l'email dell'utente
     * @param email la nuova email dell'utente
     */
    public void setEmail(String email) {
        this.email = email;
    }
 
    /**
     * metodo che serve per ottenere il cognome dell'utente
     * @return {@code String} cognome dell'utente
     */
    public String getCognome() {
        return cognome;
    }
 
    /**
     * metodo che serve per impostare il cognome dell'utente
     * @param cognome il nuovo cognome dell'utente
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
 
    /**
     * metodo che serve per ottenere il nome dell'utente
     * @return {@code String} nome dell'utente
     */
    public String getNome() {
        return nome;
    }
 
    /**
     * metodo che serve per impostare il nome dell'utente
     * @param nome il nuovo nome dell'utente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * metodo che serve per ottenere lo stato di amministratore dell'utente
     * @return {@code boolean} stato di amministratore dell'utente
     */
    public boolean isAdministrator()
    {
        return this.isAmministratore;
    }
 
    /**
     * metodo che serve per creare un utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param password password dell'utente
     * @param email email dell'utente
     * @param dataDiNascita data di nascita dell'utente
     * @param isAdministrator parametro che indica se l'utente è un amministratore
     */
    public Utente(String nome, String cognome, String password, String email, LocalDate dataDiNascita, boolean isAdministrator) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataDiNascita = dataDiNascita;
        this.playlist=new ArrayList<>();
        this.cronologia=new CronologiaAscolti();
        this.isAmministratore=isAdministrator;
 
    }

    /**
     * metodo che serve per stampare l'utente
     * @return {@code String} rappresentazione testuale dell'utente
     */
    @Override
        public String toString() {
    return nome + " " + cognome + (isAmministratore ? "  [Admin]" : "  [Utente]");
}
}