package it.univq.service;

import it.univq.datamodel.CodaDiRiproduzione;

public interface PlayerService {

    public void play(CodaDiRiproduzione coda );
    public void pausa(CodaDiRiproduzione coda);
    public void avanti(CodaDiRiproduzione coda);
    public void indietro(CodaDiRiproduzione coda);

}
