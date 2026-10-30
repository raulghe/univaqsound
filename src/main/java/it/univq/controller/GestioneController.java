package it.univq.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;
import it.univq.datamodel.ArtistaGruppo;
import it.univq.datamodel.ArtistaSolista;
import it.univq.datamodel.Brano;
import it.univq.datamodel.GenereMusicale;
import it.univq.service.AlbumService;
import it.univq.service.ArtistaService;
import it.univq.service.BranoService;
import it.univq.service.PersistenzaService;
import it.univq.service.impl.AlbumServiceImpl;
import it.univq.service.impl.ArtistaServiceImpl;
import it.univq.service.impl.BranoServiceImpl;
import it.univq.service.impl.PersistenzaServiceImpl;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * controller della vista di gestione del catalogo.
 * permette all'amministratore di aggiungere ed eliminare {@link Brano}, {@link Album} e {@link Artista}
 */
public class GestioneController implements Initializable {

    // CampiBrano
    @FXML private TextField campoBranoArtista;
    @FXML private TextField campoBranoAlbum;
    @FXML private TextField campoBranoTitolo;
    @FXML private TextField campoBranoPercorso;
    @FXML private TextField campoBranoGenere;
    // Campi Album
    @FXML private TextField campoAlbumArtista;
    @FXML private TextField campoAlbumTitolo;
    @FXML private TextField campoAlbumAnno;
    @FXML private TextField campoAlbumGenere;
    @FXML private TextField percorsoCopertina;
    // Campi Artista
    @FXML private TextField campoArtistaNome;
    @FXML private TextField campoArtistaBiografia;
    @FXML private TextField campoArtistaGenere;
    @FXML private TextField tipoArtista;

    // Campi Eliminazione
    @FXML private TextField branoDaEliminare;
    @FXML private TextField albumDaEliminare;
    @FXML private TextField artistaDaEliminare;

    // Servizi
    private final BranoService branoService = new BranoServiceImpl();
    private final AlbumService albumService = new AlbumServiceImpl();
    private final ArtistaService artistaService = new ArtistaServiceImpl();
    private final PersistenzaService persis = new PersistenzaServiceImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    /**
     * aggiunge un nuovo brano alla libreria controllando se esiste l'album associato,il percorso del file audio e il genere del brano
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void aggiungiBrano(ActionEvent event) {
        String albumBrano = campoBranoAlbum.getText();
        String titoloBrano = campoBranoTitolo.getText();
        String percorsoBrano = "/audio/"+campoBranoPercorso.getText();
        String genereBrano = campoBranoGenere.getText().trim();

        Album album;
        if (!(albumService.getTuttiAlbum().stream().anyMatch(a -> a.getTitolo().equalsIgnoreCase(albumBrano)))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE INSERIMENTO");
            alert.setContentText("Album non esistente nella libreria");
            alert.showAndWait();
            return;
        } else {
            album = albumService.getTuttiAlbum().stream().filter(a -> a.getTitolo().equalsIgnoreCase(albumBrano)).findFirst().get();
        }

        GenereMusicale genere;
        try {
            genere = GenereMusicale.valueOf(genereBrano.toUpperCase());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE INSERIMENTO");
            alert.setContentText("Genere non valido");
            alert.showAndWait();
            return;
        }

        URL risorsa = getClass().getResource(percorsoBrano.trim());
        if (risorsa == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE INSERIMENTO");
            alert.setContentText("File audio non trovato nella cartella dell'applicazione: " + percorsoBrano);
            alert.showAndWait();
            return;
        }
        Media media = new Media(risorsa.toExternalForm());
        MediaPlayer mp = new MediaPlayer(media);

        mp.setOnReady(()->{
          int durata = (int) media.getDuration().toSeconds();  
       //creo il brano da aggiungere se tutti i controlli sono oik
        Brano brano = new Brano(titoloBrano, durata, genere, album);
        brano.setPercorsoFile(percorsoBrano.trim());

        branoService.aggiungiBrano(brano);
        album.getBrani().add(brano);

        try {
            persis.salvaStato("stato_app.bin");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("SALVATAGGIO");
            alert.setContentText("ERRORE NEL SALVATAGGIO: ");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESSO");
        alert.setContentText("Brano \"" + titoloBrano + "\" aggiunto con successo!");
        alert.showAndWait();
        });

        
        
    }

    /**
     * aggiunge un nuovo album alla libreria controllando se esiste l'artista associato, il genere e il percorso file della copertina.
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void aggiungiAlbum(ActionEvent event) {
       String artistaAlbum = campoAlbumArtista.getText();
       String titoloAlbum = campoAlbumTitolo.getText();
       String annoAlbum = campoAlbumAnno.getText();
       String genereAlbum = campoAlbumGenere.getText().trim();
       String percorsoCop = "/audio/" + percorsoCopertina.getText().trim();

        GenereMusicale genere;
        try {
            genere = GenereMusicale.valueOf(genereAlbum.toUpperCase());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE INSERIMENTO");
            alert.setContentText("Genere non valido");
            alert.showAndWait();
            return;
        }
        if(!(artistaService.getTuttiArtisti().stream().anyMatch(a-> a.getNomeArte().equalsIgnoreCase(artistaAlbum)))){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE INSERIMENTO");
            alert.setContentText("L'artista inserito non è presente nella libreria");
            alert.showAndWait();
            return;
        }
        Artista artista = artistaService.getTuttiArtisti().stream().filter(a -> a.getNomeArte().equalsIgnoreCase(artistaAlbum)).findFirst().get();
       

        URL risorsa = getClass().getResource(percorsoCop);
        if (risorsa == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE INSERIMENTO");
            alert.setContentText("File copertina non trovato");
            alert.showAndWait();
            return;
        }

        Album albumDaInserire = new Album(titoloAlbum, Integer.parseInt(annoAlbum), genere, percorsoCop, artista);
        albumService.aggiungiAlbum(albumDaInserire);
        artista.getDiscografia().add(albumDaInserire);

        try {
            persis.salvaStato("stato_app.bin");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("SALVATAGGIO");
            alert.setContentText("ERRORE NEL SALVATAGGIO: ");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESSO");
        alert.setContentText("Album \"" + titoloAlbum + "\" aggiunto con successo!");
        alert.showAndWait();
    }

    /**
     * aggiunge un nuovo artista, solista o gruppo, alla libreria controllando se esiste il genere dell'artista.
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void aggiungiArtista(ActionEvent event) {
        String nomeArtista = campoArtistaNome.getText();
        String biografia = campoArtistaBiografia.getText();
        String genereArtista = campoArtistaGenere.getText().trim();
        
        GenereMusicale genere;
        try {
            genere = GenereMusicale.valueOf(genereArtista.toUpperCase());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE INSERIMENTO");
            alert.setContentText("Genere non valido");
            alert.showAndWait();
            return;
        }
       
        if(!tipoArtista.getText().equalsIgnoreCase("Solista") && !tipoArtista.getText().equalsIgnoreCase("Gruppo")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("ERRORE INSERIMENTO");
           alert.setContentText("Specificare il tipo di artista SOLISTA o GRUPPO");
           alert.showAndWait();
           return;
        }
        Artista artista;
        if(tipoArtista.getText().equalsIgnoreCase("Solista")) {
           artista = new ArtistaSolista(nomeArtista, biografia, genere); 
        }else {
            artista=new ArtistaGruppo(nomeArtista, biografia, genere);
        }
        
            
        artistaService.aggiungiArtista(artista);
        
        
       
        try {
            persis.salvaStato("stato_app.bin");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("SALVATAGGIO");
            alert.setContentText("ERRORE NEL SALVATAGGIO: ");
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESSO");
        alert.setContentText("Artista \"" + nomeArtista + "\" aggiunto con successo!");
        alert.showAndWait();
    }

    /**
     * elimina un brano dalla libreria e dal suo album
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void eliminaBrano(ActionEvent event) {
        String titolo = branoDaEliminare.getText() ;

        Brano brano = branoService.getTuttiBrani().stream().filter(b -> b.getTitolo().equalsIgnoreCase(titolo)).findFirst().orElse(null);
        //se nnon lo trovo    
        if (brano == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setContentText("Brano non trovato nella libreria!");
            alert.showAndWait();
            return;
        }

        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.setTitle("Conferma eliminazione");
        conferma.setHeaderText(null);
        conferma.setContentText("Confermi l'eliminazione del brano " + brano.getTitolo() + "?");
        Optional<ButtonType> res = conferma.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            branoService.rimuoviBrano(brano);
            if (brano.getAlbumBrano() != null) {
                brano.getAlbumBrano().getBrani().remove(brano);
            }
            try {
                persis.salvaStato("stato_app.bin");
            } catch (IOException e) {
               System.out.println("errore nell' aggiornamento della libreria");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESSO");
            alert.setContentText("Brano eliminato con successo!");
            alert.showAndWait();
            branoDaEliminare.clear();
        }
    }

    /**
     * elimina un album dalla libreria e dalla discografia dell'artista
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void eliminaAlbum(ActionEvent event) {
        String titolo = albumDaEliminare.getText();
        Album album = albumService.getTuttiAlbum().stream().filter(a -> a.getTitolo().equalsIgnoreCase(titolo)).findFirst().orElse(null);
       if (album == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setContentText("Album non trovato nella libreria!");
            alert.showAndWait();
            return;
        }

        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.setTitle("Conferma eliminazione");
        conferma.setHeaderText(null);
        conferma.setContentText("Confermi l'eliminazione dell'album " + album.getTitolo() + "?");
        Optional<ButtonType> res = conferma.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            albumService.rimuoviAlbum(album);
            if (album.getArtistaAlbum() != null) {
                album.getArtistaAlbum().getDiscografia().remove(album); 
            }
            try {
                persis.salvaStato("stato_app.bin");
            } catch (IOException e) {
                System.out.println("errore nell' aggiornamento della libreria");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESSO");
            alert.setContentText("Album eliminato con successo!");
            alert.showAndWait();
            albumDaEliminare.clear();
        }
    }

    /**
     * elimina un artista dalla libreria
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void eliminaArtista(ActionEvent event) {
        String nome = artistaDaEliminare.getText() ;

        Artista artista = artistaService.getTuttiArtisti().stream().filter(a -> a.getNomeArte().equalsIgnoreCase(nome)).findFirst().orElse(null);

        if (artista == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setContentText("Artista non trovato nella libreria!");
            alert.showAndWait();
            return;
        }

        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.setTitle("Conferma eliminazione");
        conferma.setHeaderText(null);
        conferma.setContentText("Confermi l'eliminazione dell'artista " + artista.getNomeArte() + " ?");
        Optional<ButtonType> res = conferma.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            artistaService.rimuoviArtista(artista);
            try {
                persis.salvaStato("stato_app.bin");
            } catch (IOException e) {
                System.out.println("errore nell' aggiornamento della libreria");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESSO");
            alert.setContentText("Artista eliminato con successo!");
            alert.showAndWait();
            artistaDaEliminare.clear();
        }
    }
}
