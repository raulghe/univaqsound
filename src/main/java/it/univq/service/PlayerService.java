package it.univq.service;

import it.univq.datamodel.Utente;
import it.univq.datamodel.Brano;

public interface PlayerService {
 
    public void play( );
    public void pausa();
    public void avanti();
    public void indietro();
    public void cambiaShuffle();
    public void cambiaModalitaRipetizione();
    public void play(Utente u );
    public double getTempoCorrenteSecondi();
    public double getDurataTotaleSecondi();
    public void setPosizione(double secondi);
    public void setVolume(double volume);
    public double getVolume();
    public String getStato();
    public Brano getBranoCorrente();
 
}