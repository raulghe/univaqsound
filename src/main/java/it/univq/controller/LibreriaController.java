package it.univq.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Utente;
import it.univq.exception.FileAudioNonTrovatoException;
import it.univq.service.ArtistaService;
import it.univq.service.CodaDiRiproduzioneService;
import it.univq.service.impl.ArtistaServiceImpl;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import it.univq.service.PlayerService;
import it.univq.service.Riproducibile;
import it.univq.service.impl.PlayerServiceImpl;
import it.univq.service.impl.CodaDiRiproduzioneServiceImpl;
import javafx.scene.control.TableCell;
import javafx.geometry.Pos;


/**
 * controller della vista della libreria.
 */
public class LibreriaController implements Initializable {
    @FXML
    private Label labelStatoSelezione;
    @FXML
    private TableView<Artista> tabellaArtisti;
    @FXML
    private TableColumn<Artista, String> colonnaArtisti;
    @FXML
    private TableView<Album> tabellaAlbum;
    @FXML
    private TableColumn<Album, String> colonnaAlbum;
    @FXML
    private TableView<Brano> tabellaBrani;
    @FXML
    private TableColumn<Brano, String> colonnaBrani;
    @FXML
    private TableColumn<Album, Album> colonnaPlayAlbum;
    @FXML
    private TableColumn<Brano, Brano> colonnaPlayBrano;

    private final PlayerService playerService = PlayerServiceImpl.getIstanza();
    private final CodaDiRiproduzioneService coda = new CodaDiRiproduzioneServiceImpl();
    private Utente utenteLoggato;
       
    private final ArtistaService artistaService = new ArtistaServiceImpl();
   
    /**
     * salva l'utente che ha effettuato il login.
     * @param utente l'{@link Utente} loggato
     */
    public void salvaUtente(Utente utente) {
        this.utenteLoggato = utente;    
        
        
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // specifico cosa mettere nelle colonne
        colonnaArtisti.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getNomeArte()));
        colonnaAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitolo()));
        colonnaBrani.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getTitolo()));
        colonnaPlayAlbum.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        colonnaPlayBrano.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        //carico gli artisti
        tabellaArtisti.setItems(FXCollections.observableArrayList(artistaService.getTuttiArtisti()));
        tabellaArtisti.getSelectionModel().selectedItemProperty().addListener((obs, vecchioArtista, nuovoArtista) -> {
        if (nuovoArtista != null) {
            
            tabellaAlbum.setItems(FXCollections.observableArrayList(nuovoArtista.getDiscografia()));
            tabellaBrani.getItems().clear();
            
        }
       
    });
     tabellaAlbum.getSelectionModel().selectedItemProperty().addListener((obs,vecchioAlbum,nuovoAlbum)->{
            if(nuovoAlbum!=null){
               
                tabellaBrani.setItems(FXCollections.observableArrayList(nuovoAlbum.getBrani()));
            }
        });
        // Configurazione tasto Play per ciascun Album
        colonnaPlayAlbum.setCellFactory(col -> new TableCell<Album, Album>() {
            private final Label btnPlay = new Label("▶");
            {
                btnPlay.setStyle("-fx-text-fill: #EBB946; -fx-cursor: hand; -fx-font-size: 20px; -fx-font-weight: bold;");
                btnPlay.setOnMouseClicked(e -> {      
                    Album album = getItem();          
                    if (album != null && !album.getBrani().isEmpty()) {
                        riproduci(album);
                    }
                });
            }

            @Override
            protected void updateItem(Album album, boolean empty) {
                super.updateItem(album, empty);
                if (empty || album == null) {
                    setGraphic(null);
                } else {
                    setAlignment(Pos.CENTER);
                    setGraphic(btnPlay);
                }
            }
        });
        // Configurazione tasto Play per ciascun Brano
        colonnaPlayBrano.setCellFactory(col -> new TableCell<Brano, Brano>() {
            private final Label btnPlay = new Label("▶");
            {
                btnPlay.setStyle("-fx-text-fill: #EBB946; -fx-cursor: hand; -fx-font-size: 20px; -fx-font-weight: bold;");
                btnPlay.setOnMouseClicked(e -> {
                    Brano brano = getItem();
                    if (brano != null) {
                        riproduci(brano);
                    }
                });
            }

            @Override
            protected void updateItem(Brano brano, boolean empty) {
                super.updateItem(brano, empty);
                if (empty || brano == null) {
                    setGraphic(null);
                } else {
                    setAlignment(Pos.CENTER);
                    setGraphic(btnPlay);
                }
            }
        });
    
    
    }

    /**
     * aggiunge un elemento alla coda di riproduzione e avvia la riproduzione.
     * @param r l'elemento {@link Riproducibile} da riprodurre (album o brano)
     */
    private void riproduci(Riproducibile r){
        try{
            coda.aggiungiAllaCoda(r);
            if(r instanceof Brano){
                coda.impostaUltimoIndice();
            }
            playerService.play();           
        } catch (FileAudioNonTrovatoException elem){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE DI RIPRODUZIONE");
            alert.setContentText(elem.getMessage());
            alert.showAndWait();
        }
    }
    

}
