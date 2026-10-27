package it.univq.controller;

import java.io.File;
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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    // Servizi
    private final BranoService branoService = new BranoServiceImpl();
    private final AlbumService albumService = new AlbumServiceImpl();
    private final ArtistaService artistaService = new ArtistaServiceImpl();
    private final PersistenzaService persis = new PersistenzaServiceImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

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
}
