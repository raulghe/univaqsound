package it.univq.service;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Riproducibile;

public interface CodaDiRiproduzioneService {

    public void svuotaCoda();
    public void aggiungiAllaCoda(Riproducibile r);
}
