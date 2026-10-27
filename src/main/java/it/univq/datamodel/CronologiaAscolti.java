package it.univq.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * classe che serve per registrare la cronologia di un utente nell'app composta 
 * da {@link VociCronologia} voci cronologia 
 */
public class CronologiaAscolti implements Serializable {

    private List<VoceCronologia> vociCronologia= new ArrayList<>();

    /**
     * metodo che serve per recuperare la lista di voci cronologia
     * @return {@code List<VoceCronologia>} lista di voci cronologia
     */
    public List<VoceCronologia> getVociCronologia() {
        return vociCronologia;
    }

    /**
     * metodo che serve per impostare la lista di voci cronologia
     * @param vociCronologia  lista di voci cronologia
     */
    public void setVociCronologia(List<VoceCronologia> vociCronologia) {
        this.vociCronologia = vociCronologia;
    }
}
