package it.univq.datamodel;

import java.util.HashSet;
import java.util.Set;

public abstract class Artista implements Ricercabile {
    private String nomeArte;
    private String biografia;
    private GenereMusicale generePrincipale;
    private Set<Album> discografia= new HashSet<>();

    public Artista(String nomeArte, String biografia, boolean isGroup) {
        this.nomeArte = nomeArte;
        this.biografia = biografia;
    }

    public String getNomeArte() {
        return nomeArte;
    }

    public void setNomeArte(String nomeArte) {
        this.nomeArte = nomeArte;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public GenereMusicale getGenereArtista() {
        return generePrincipale;
    }

    public void setGenereArtista(GenereMusicale genereArtista) {
        this.generePrincipale = genereArtista;
    }
    @Override
    public boolean trovato(String testo){
        if(testo.isBlank())return false;
        return this.nomeArte.toLowerCase().contains(testo.toLowerCase());
    }
}