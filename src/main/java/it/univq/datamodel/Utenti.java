package it.univq.datamodel;

import java.util.ArrayList;
import java.util.List;
//questa classe viene usata per salvare gli utenti dell app
public class Utenti {

    
    private static final Utenti istanza = new Utenti();

 
    private List<Utente> listaUtenti;

   
    private Utenti() {
        this.listaUtenti = new ArrayList<>();
    }

   
    public static Utenti getIstanza() {
        return istanza;
    }

 
    public List<Utente> getListaUtenti() {
        return listaUtenti;
    }

    public void aggiungiUtente(Utente u) {
        if (u != null && !listaUtenti.contains(u)) {
            listaUtenti.add(u);
        }
    }
    public void rimuoviUtente(Utente u) {
        listaUtenti.remove(u);
    }
}

