package it.univq.datamodel;

import java.util.ArrayList;
import java.util.List;

public class ArtistaGruppo extends Artista {
    private List<Artista> componenti;

    public ArtistaGruppo(String nomeArte, String biografia, GenereMusicale genere){
        super(nomeArte, biografia, genere);
        this.componenti=new ArrayList<>();
    }

    public void aggiungiComponente(Artista artista) {
        componenti.add(artista);
    }
    public List<Artista> getComponenti(){
        return componenti;
    }



}
