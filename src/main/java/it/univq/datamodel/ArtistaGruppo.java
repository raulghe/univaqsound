package it.univq.datamodel;

import java.util.ArrayList;
import java.util.List;

public class ArtistaGruppo extends Artista {
    private List<Artista> componenti;

    public ArtistaGruppo(String nomeArte, String biografia){
        super(nomeArte,biografia,true);
        this.componenti=new ArrayList<>();
    }

    public void aggiungiComponente(Artista artista) {
        componenti.add(artista);
    }
    public List<Artista> getComponenti(){
        return componenti;
    }



}
