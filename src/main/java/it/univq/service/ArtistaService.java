package it.univq.service;

import it.univq.datamodel.Artista;

import java.util.Set;

public interface ArtistaService {
    public Set<Artista> getTuttiArtisti();
    public void aggiungiArtista(Artista a);
    public void rimuoviArtista(Artista a);
    public int getAscoltiTotali(Artista a);
}
