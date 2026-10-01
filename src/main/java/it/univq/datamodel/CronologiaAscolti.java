package it.univq.datamodel;

import java.util.ArrayList;
import java.util.List;

public class CronologiaAscolti {

    private List<Brano> cronologia= new ArrayList<>();


    public List<Brano> getCronologia() {
        return cronologia;
    }

    public void setCronologia(List<Brano> cronologia) {
        this.cronologia = cronologia;
    }
}
