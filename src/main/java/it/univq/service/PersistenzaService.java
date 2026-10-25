package it.univq.service;

import java.io.IOException;

public interface PersistenzaService {
    void salvaStato(String percorsoFile) throws IOException;
    void caricaStato(String percorsoFile) throws IOException, ClassNotFoundException;
}
