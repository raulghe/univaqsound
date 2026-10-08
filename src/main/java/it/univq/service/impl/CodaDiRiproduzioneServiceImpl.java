package it.univq.service.impl;

import it.univq.datamodel.Album;
import it.univq.datamodel.Brano;
import it.univq.datamodel.CodaDiRiproduzione;
import it.univq.datamodel.Playlist;
import it.univq.datamodel.Riproducibile;
import it.univq.service.CodaDiRiproduzioneService;

import java.util.List;

public class CodaDiRiproduzioneServiceImpl implements CodaDiRiproduzioneService {


    @Override   //quando aggiungo se è un album o una playlist svuoto la coda
    public void aggiungiAllaCoda(Riproducibile r){
        List<Brano> coda=CodaDiRiproduzione.getIstanza().getCoda();
        if (r instanceof Album || r instanceof Playlist) svuotaCoda();
        if (coda.containsAll(r.getDaRiprodurre()))return;//da implementare eccezione
        else coda.addAll(r.getDaRiprodurre());
    }

    //metodo per svuotare la coda a seguto della riproduzione di una playlist
    @Override
    public void svuotaCoda() {
        List<Brano> coda=CodaDiRiproduzione.getIstanza().getCoda();
        coda.clear();
        CodaDiRiproduzione.getIstanza().setIndiceBranoCorrente(-1);
    }


}
