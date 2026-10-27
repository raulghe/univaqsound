package it.univq.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * questa classe estende da Arista e serve a rappresentare un gruppo formato da piu artisti singoli
 */
public class ArtistaGruppo extends Artista implements Serializable {
    private List<Artista> componenti;

    /**
     * costruttore di artista gruppo
     * @param nomeArte nome d'arte del gruppo
     * @param biografia biografia del gruppo
     * @param genere genere musicale del gruppo
     */
    public ArtistaGruppo(String nomeArte, String biografia, GenereMusicale genere){
        super(nomeArte, biografia, genere);
        this.componenti=new ArrayList<>();
    }

    /**
     * aggiunge unartista alla lista di componenti
     * @param artista artista da aggiungere
     */
    public void aggiungiComponente(Artista artista) {
        componenti.add(artista);
    }

    /**
     * ritorna la lista di componenti di un Artista Gruppo 
     * @return
     */
    public List<Artista> getComponenti(){
        return componenti;
    }
}
