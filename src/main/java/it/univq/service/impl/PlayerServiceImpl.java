package it.univq.service.impl;

import it.univq.datamodel.CodaDiRiproduzione;
import it.univq.service.PlayerService;

public class PlayerServiceImpl implements PlayerService {
    private final CodaDiRiproduzione coda=CodaDiRiproduzione.getIstanza();

    @Override
    public void play(){
      
    }
    
    @Override
    public void pausa(){
        
    }
    
    @Override
    public void avanti(){

    }
    @Override
    public void indietro(){
        
    }
    @Override 
    public void cambiaShuffle(){
        if(coda.isShuffle()) coda.setShuffle(false);
        else coda.setShuffle(true);
    }
    @Override
    public void cambiaModalitaRipetizione(){
        if(coda.isRepeat()) coda.setRepeat(false);
        else coda.setRepeat(true);
    }

}
