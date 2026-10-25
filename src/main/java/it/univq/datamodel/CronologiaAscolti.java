package it.univq.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CronologiaAscolti implements Serializable {

    private List<VoceCronologia> vociCronologia= new ArrayList<>();

    public List<VoceCronologia> getVociCronologia() {
        return vociCronologia;
    }

    public void setVociCronologia(List<VoceCronologia> vociCronologia) {
        this.vociCronologia = vociCronologia;
    }
}
