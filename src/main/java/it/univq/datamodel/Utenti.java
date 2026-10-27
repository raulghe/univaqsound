package it.univq.datamodel;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe singleton che rappresenta gli utenti registrati all'applicazione
 */
//questa classe viene usata per salvare gli utenti dell app
public class Utenti {

    
    private static final Utenti istanza = new Utenti();

 
    private List<Utente> listaUtenti;

   
    /**
     * Costruttore privato per implementare il pattern singleton
     */
    private Utenti() {
        this.listaUtenti = new ArrayList<>();
    }

   
    /**
     * metodo che restituisce l'istanza di Utenti
     * @return {@code Utenti} istanza di Utenti
     */
    public static Utenti getIstanza() {
        return istanza;
    }

 
    /**
     * metodo che restituisce la lista degli utenti
     * @return {@code List<Utente>} lista degli utenti
     */
    public List<Utente> getListaUtenti() {
        return listaUtenti;
    }

    /**
     * metodo che aggiunge un utente alla lista degli utenti registrati
     * @param u {@code Utente} utente da aggiungere alla lista
     */
    public void aggiungiUtente(Utente u) {
        if (u != null && !listaUtenti.contains(u)) {
            listaUtenti.add(u);
        }
    }

    /**
     * metodo che rimuove un utente dalla lista degli utenti registrati
     * @param u {@code Utente} utente da rimuovere dalla lista
     */
    public void rimuoviUtente(Utente u) {
        listaUtenti.remove(u);
    }
}

