package it.univq.service;

import it.univq.datamodel.CodaDiRiproduzione;

public interface PlayerService {

    public void play( );
    public void pausa();
    public void avanti();
    public void indietro();
    public void cambiaShuffle();
    public void cambiaModalitaRipetizione();

}
