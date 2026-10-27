package it.univq.datamodel;
 
import it.univq.service.Ricercabile;
import it.univq.service.Riproducibile;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
 
/**
 *  Classe che rappresenta un brano musicale all'interno dell'applicazione.
 *  Un brano appartiene a un {@link Album}, implementa le interfacce {@link Riproducibile} e {@link Ricercabile}.
 */
public class Brano implements Riproducibile, Ricercabile, Serializable {
    private String titolo;
    private int durata;
    private GenereMusicale genereBrano;
    private Album albumBrano;
    private String percorsoFile;
    private Integer numeroAscolti;
    private LocalDateTime dataAggiunta;
 
    /**
     * Costruttore di Brano
     * @param titolo titolo del brano
     * @param durata durata del brano in secondi
     * @param genereBrano genere musicale del brano
     * @param albumBrano album a cui appartiene il brano
     */
    public Brano(String titolo, int durata, GenereMusicale genereBrano, Album albumBrano) {
        this.titolo = titolo;
        this.durata = durata;
        this.genereBrano = genereBrano;
        this.albumBrano = albumBrano;
        this.numeroAscolti = 0;
        this.dataAggiunta=LocalDateTime.now();
    }
 
    /**
     * restituisce la data di aggiunta del brano
     * @return data di aggiunta del brano
     */
    public LocalDateTime getDataAggiunta() {
        return dataAggiunta;
    }
 
    /**
     * imposta la data del brano
     * @param dataAggiunta data di aggiunta del brano
     */
    public void setDataAggiunta(LocalDateTime dataAggiunta) {
        this.dataAggiunta = dataAggiunta;
    }
 
    /**
     * restituisce il numero di ascolti del brano
     * @return numero di ascolti del brano
     */
    public Integer getNumeroAscolti() {
        return numeroAscolti;
    }
 
    /**
     * imposta il numero di ascolti del brano
     * @param numeroAscolti numero di ascolti del brano
     */
    public void setNumeroAscolti(Integer numeroAscolti) {
        this.numeroAscolti = numeroAscolti;
    }
 
    /**
     * restituisce il titolo del brano
     * @return titolo del brano
     */
    public String getTitolo() {
        return titolo;
    }
 
    /**
     * imposta il titolo del brano
     * @param titolo titolo del brano
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
 
    /**
     * restituisce la durata del brano in secondi
     * @return  {@code int} durata del brano in secondi
     */
    public int getDurata() {
        return durata;
    }
 
    /**
     * imposta la durata del brano in secondi
     * @param durata  {@code int} durata del brano in secondi
     */
    public void setDurata(int durata) {
        this.durata = durata;
    }
 
    /**
     * restituisce il genere musicale del brano
     * @return {@code GenereMusicale} genere musicale del brano
     */
    public GenereMusicale getGenereBrano() {
        return genereBrano;
    }
 
    /**
     * imposta il genere musicale del brano
     * @param genereBrano  {@code GenereMusicale} genere musicale del brano
     */
    public void setGenereBrano(GenereMusicale genereBrano) {
        this.genereBrano = genereBrano;
    }
 
    /**
     * restituisce l'album a cui appartiene il brano
     * @return  {@code Album} album a cui appartiene il brano
     */
    public Album getAlbumBrano() {
        return albumBrano;
    }
 
    /**
     * imposta l'album a cui appartiene il brano
     * @param albumBrano  {@code Album} album a cui appartiene il brano
     */
    public void setAlbumBrano(Album albumBrano) {
        this.albumBrano = albumBrano;
    }
 
    /**
     * metodo che verifica se il brano è uguale a un altro confrontando titolo e durata
     * @param o  brano da confrontare
     * @return  {@code true} se il brano è uguale all'altro, {@code false} altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Brano brano = (Brano) o;
        return this.titolo.equals(brano.titolo) && this.durata == brano.durata;
    }
 
    /**
     * metodo che calcola il codice hash del brano confrontando titolo e durata
     * @return  {@code int} codice hash del brano
     */
    @Override
    public int hashCode() {
        return Objects.hash(titolo, durata);
    }
 
    /**
     * metodo che restituisce una stringa contenente il titolo del brano, l'artista e l'album
     * @return {@code String} stringa contenente il titolo del brano, l'artista e l'album
     */
    @Override
    public String toString() {
        return titolo + " - " + albumBrano.getArtistaAlbum() + " (" + albumBrano.getTitolo() + ")";
    }

    /**
     * metodo che restituisce il brano come lista composta da un brano solo per la riproduzione
     * @return {@code List<Brano>} lista di brani contenente il brano
     */
    @Override
    public List<Brano> getDaRiprodurre(){
        return List.of(this);
    }

    /**
     * metodo che verifica se il titolo del brano contiene il testo cercato
     * @param testo  testo da cercare
     * @return  {@code true} se il titolo del brano contiene il testo cercato, {@code false} altrimenti
     */
    @Override
    public boolean trovato(String testo){
        if(testo.isBlank())return false;
        return this.titolo.toLowerCase().contains(testo.toLowerCase());
 
    }

    /**
     * metodo che imposta il percorso del file del brano
     * @param percorsoFile  percorso del file del brano
     */
    public void setPercorsoFile(String percorsoFile){
        this.percorsoFile=percorsoFile;
    }

    /**
     * metodo che restituisce il percorso del file del brano
     * @return {@code String} percorso del file del brano
     */
    public String getPercorsoFile(){
        return this.percorsoFile;
    }
 
}