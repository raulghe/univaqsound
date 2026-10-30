package it.univq.service.impl;

import it.univq.datamodel.DatiApplicazione;
import it.univq.datamodel.Libreria;
import it.univq.datamodel.Utenti;
import it.univq.service.LoggerService;
import it.univq.service.PersistenzaService;

import java.io.*;

public class PersistenzaServiceImpl implements PersistenzaService {

    private static final String DEFAULT_FILE = "stato_app.bin";
    private final LoggerService logger = LoggerServiceImpl.getIstanza();

    @Override
    public void salvaStato(String percorsoFile) throws IOException {
        String percorso = (percorsoFile != null && !percorsoFile.isBlank()) ? percorsoFile : DEFAULT_FILE;

        Libreria libreria = Libreria.getIstanza();
        Utenti utenti = Utenti.getIstanza();

        DatiApplicazione dati = new DatiApplicazione(
                libreria.getBrani().getElementi(),
                libreria.getAlbum().getElementi(),
                libreria.getArtisti().getElementi(),
                utenti.getListaUtenti()
        );

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(percorso))) {
            oos.writeObject(dati);
            logger.log("SALVATAGGIO_DATI", "Stato salvato correttamente su " + percorso);
        } catch (IOException elem) {
            logger.log("ERRORE_SALVATAGGIO", "Errore nel salvataggio: " + elem.getMessage());
            throw elem;
        }
    }

    @Override
    public void caricaStato(String percorsoFile) throws IOException, ClassNotFoundException {
        String percorso = (percorsoFile != null && !percorsoFile.isBlank()) ? percorsoFile : DEFAULT_FILE;

        File file = new File(percorso);
        if (!file.exists()) {
            throw new FileNotFoundException("File di stato non trovato: " + percorso);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            DatiApplicazione dati = (DatiApplicazione) ois.readObject();

            //ripristino dati libreria
            Libreria libreria = Libreria.getIstanza();
            libreria.getBrani().getElementi().clear();
            libreria.getBrani().getElementi().addAll(dati.getBrani());

            libreria.getAlbum().getElementi().clear();
            libreria.getAlbum().getElementi().addAll(dati.getAlbum());

            libreria.getArtisti().getElementi().clear();
            libreria.getArtisti().getElementi().addAll(dati.getArtisti());

            //ripristino dati utenti
            Utenti utenti = Utenti.getIstanza();
            utenti.getListaUtenti().clear();
            utenti.getListaUtenti().addAll(dati.getUtenti());

            logger.log("CARICAMENTO_DATI", "Stato caricato con successo da " + percorso);
        } catch (IOException | ClassNotFoundException elem) {
            logger.log("ERRORE_CARICAMENTO", "Errore nel caricamento: " + elem.getMessage());
          throw elem;
        }
    }
}
