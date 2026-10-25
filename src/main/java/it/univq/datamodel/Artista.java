package it.univq.datamodel;

import it.univq.service.Ricercabile;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Artista implements Ricercabile, Serializable {
    private String nomeArte;
    private String biografia;
    private GenereMusicale generePrincipale;
    private Set<Album> discografia= new HashSet<>();

    public Artista(String nomeArte, String biografia, GenereMusicale generePrincipale) {
        this.nomeArte = nomeArte;
        this.biografia = biografia;
        this.generePrincipale = generePrincipale;
    }

    
    @Override 
    public boolean equals(Object o){
        if(o == this) 
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Artista artista = (Artista) o;
        return this.nomeArte.equals(artista.nomeArte);
    }

    @Override 
    public int hashCode(){
        return Objects.hash(nomeArte);
    }


    public String getNomeArte() {
        return nomeArte;
    }

    public void setNomeArte(String nomeArte) {
        this.nomeArte = nomeArte;
    }

    public Set<Album> getDiscografia() {
        return discografia;
    }

    public void setDiscografia(Set<Album> discografia) {
        this.discografia = discografia;
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