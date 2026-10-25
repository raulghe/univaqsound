package it.univq.datamodel;

import java.io.Serializable;

public class ArtistaSolista extends Artista implements Serializable {
    public ArtistaSolista(String nomeArte, String biografia, GenereMusicale genere){
        super(nomeArte, biografia, genere);
    }

}
