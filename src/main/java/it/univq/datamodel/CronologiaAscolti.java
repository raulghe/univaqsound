package it.univq.datamodel;

import java.util.ArrayList;
import java.util.List;

public class CronologiaAscolti {

    private List<VoceCronologia> cronologia= new ArrayList<>();

    public List<VoceCronologia> getCronologia() {
        return cronologia;
    }

    public void setCronologia(List<VoceCronologia> cronologia) {
        this.cronologia = cronologia;
    }
}
