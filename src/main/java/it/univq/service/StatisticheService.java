package it.univq.service;
import java.util.List;

import it.univq.datamodel.Artista;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Utente;

/**
 * servizio che calcola le statistiche di ascolto e suggerisce brani.
 * fornisce le classifiche di brani e artisti e i dati sull'ascolto dell'{@link Utente}.
 */
public interface StatisticheService {

    /**
     * restituisce i brani più ascoltati della libreria.
     * @return lista dei brani più ascoltati
     */
    public List<Brano> braniPiuAscoltati();

    /**
     * restituisce gli artisti più ascoltati della libreria.
     * @return lista degli {@link Artista} più ascoltati
     */
    public List<Artista> artistiPiuAscoltati();

    /**
     * calcola il tempo totale di ascolto di un utente.
     * @param u l'{@link Utente} di cui calcolare il tempo di ascolto
     * @return tempo totale di ascolto
     */
    public Integer tempoTotaleAscoltato(Utente u);

    /**
     * restituisce i brani suggeriti a un utente in base ai suoi generi musicali preferiti.
     * @param u l'{@link Utente} a cui proporre i brani
     * @return lista di brani non ancora ascoltati appartenenti ai generi preferiti dell'utente
     */
    public List<Brano> braniProposti(Utente u);

}
