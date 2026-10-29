package it.univq.service;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;

import java.util.List;

/**
 * servizio per la gestione delle playlist.
 */
public interface PlaylistService {

    /**
     * aggiunge un brano alla playlist.
     * @param p la playlist a cui aggiungere il brano
     * @param b il brano da aggiungere
     */
    public void aggiungiBrano(Playlist p, Brano b);

    /**
     * rimuove un brano dalla playlist.
     * @param p la playlist da cui rimuovere il brano
     * @param b il brano da rimuovere
     */
    public void rimuoviBrano(Playlist p, Brano b);

    /**
     * crea una nuova playlist associata all'utente.
     * @param p la playlist da creare
     * @param u l'utente proprietario della playlist
     */
    public void creaPlaylist(Playlist p, Utente u);

    /**
     * elimina una playlist dell'utente.
     * @param p la playlist da eliminare
     * @param u l'utente proprietario della playlist
     */
    public void eliminaPlaylist (Playlist p, Utente u);

    /**
     * restituisce le playlist associate all'utente.
     * @param U l'utente di cui recuperare le playlist
     * @return {@code List<Playlist>} lista delle playlist dell'utente
     */
    public List<Playlist> getPlaylistUtente(Utente U);

    /**
     * ordina i brani della playlist secondo un criterio specificato.
     * @param p  playlist da ordinare
     * @param criterio il criterio di ordinamento
     */
    public void ordinaBrani(Playlist p, CriterioOrdinamento<Brano> criterio);

    /**
     * calcola la durata totale della playlist.
     * @param p la playlist di cui calcolare la durata
     * @return {@code Integer} la durata totale della playlist
     */
    public Integer calcolaDurataPlaylist(Playlist p);

    /**
     * sposta un brano di una posizione verso l'alto nella playlist.
     * @param p la playlist che contiene il brano
     * @param b il brano da spostare
     */
    public void spostaSU(Playlist p , Brano b);

    /**
     * sposta un brano di una posizione verso il basso nella playlist.
     * @param p la playlist che contiene il brano
     * @param b il brano da spostare
     */
    public void spostaGiu(Playlist p, Brano b);

    /**
     * riproduce la playlist
     * @param p la playlist da riprodurre
     */
    public void riproduciPlaylist(Playlist p);
}
