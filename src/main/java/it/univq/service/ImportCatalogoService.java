package it.univq.service;

import java.io.IOException;

public interface ImportCatalogoService {
    /**
     * Importa artisti, album e brani da un CSV presente nel classpath.
     * @param percorsoRisorsa percorso della risorsa, es. "/data/catalogo.csv"
     * @return numero di brani importati
     */
    int importaCatalogoDaCsv(String percorsoRisorsa) throws IOException;
}