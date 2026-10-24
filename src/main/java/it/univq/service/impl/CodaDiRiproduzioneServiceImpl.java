package it.univq.service.impl;
 
import it.univq.datamodel.Album;
import it.univq.datamodel.Brano;
import it.univq.datamodel.CodaDiRiproduzione;
import it.univq.datamodel.Playlist;
import it.univq.exception.RiproducibileVuotoException;
import it.univq.service.CodaDiRiproduzioneService;
import it.univq.service.Riproducibile;
 
import java.util.List;
 
public class CodaDiRiproduzioneServiceImpl implements CodaDiRiproduzioneService {
 
 
    @Override   //quando aggiungo se è un album o una playlist svuoto la coda
    public void aggiungiAllaCoda(Riproducibile r){
        if(r.getDaRiprodurre().isEmpty()){
            throw new RiproducibileVuotoException("Impossibile riprodurre: non contiene brani.");
        }
        List<Brano> coda=CodaDiRiproduzione.getIstanza().getCoda();
        if (r instanceof Album || r instanceof Playlist){
            svuotaCoda();
            coda.addAll(r.getDaRiprodurre());
            CodaDiRiproduzione.getIstanza().setIndiceBranoCorrente(0);
           
        }else{
            coda.addAll(r.getDaRiprodurre());
           
        }
    }
 
    //metodo per svuotare la coda a seguto della riproduzione di una playlist
    @Override
    public void svuotaCoda() {
        List<Brano> coda=CodaDiRiproduzione.getIstanza().getCoda();
        coda.clear();
        CodaDiRiproduzione.getIstanza().setIndiceBranoCorrente(-1);
    }
 
 
   
   
 
 
}