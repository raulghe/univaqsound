package it.univq.service.impl;

import it.univq.datamodel.Brano;
import it.univq.datamodel.CronologiaAscolti;
import it.univq.datamodel.Utente;
import it.univq.datamodel.VoceCronologia;
import it.univq.service.CronologiaService;

public class CronologiaServiceImpl implements CronologiaService {
    @Override
    public void aggiungiVoce(Utente u, Brano b, int durata){
       CronologiaAscolti c= u.getCronologia();
       c.getVociCronologia().add(new VoceCronologia(b,durata));

    }
}
