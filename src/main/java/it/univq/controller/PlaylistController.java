package it.univq.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import it.univq.datamodel.Brano;
import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;
import it.univq.exception.FileAudioNonTrovatoException;
import it.univq.service.CodaDiRiproduzioneService;
import it.univq.service.PlayerService;
import it.univq.service.PlaylistService;
import it.univq.service.impl.CodaDiRiproduzioneServiceImpl;
import it.univq.service.impl.PlayerServiceImpl;
import it.univq.service.impl.PlaylistServiceImpl;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * controller della vista delle playlist,mostra le {@link Playlist} dell'utente loggato e i brani della playlist selezionata.
 */
public class PlaylistController implements Initializable {

    @FXML private Label labelStatoSelezione;
    @FXML private TableView<Playlist> tabellaPlaylist;
    @FXML private TableColumn<Playlist, String> colonnaPlaylist;
    @FXML private TableColumn<Playlist, String> colonnaDurata;
    @FXML private TableColumn<Playlist, Playlist> colonnaPlayPlaylist;
    @FXML private TableColumn<Playlist, Playlist> colonnaEliminaPlaylist;

    @FXML private TableView<Brano> tabellaBraniPlaylist;
    @FXML private TableColumn<Brano, String> colonnaBraniPlaylist;
    @FXML private TableColumn<Brano, Brano> colonnaPlayBrano;
    @FXML private TableColumn<Brano, Brano> colonnaRimuoviBrano;

    @FXML private TextField nomeNuovaPlaylist;
    @FXML private TextField descrizioneNuovaPlaylist;
    @FXML private Button btnCreaPlaylist;
    @FXML private Button btnSpostaSu;
    @FXML private Button btnSpostaGiu;

    private Utente utenteLoggato;
    private final PlaylistService playlistService = new PlaylistServiceImpl();
    private final PlayerService playerService = PlayerServiceImpl.getIstanza();
    private final CodaDiRiproduzioneService coda = new CodaDiRiproduzioneServiceImpl();

    /**
     * salva l'utente che ha effettuato il login.
     * @param utente l'{@link Utente} loggato
     */
    public void salvaUtente(Utente utente) {
        this.utenteLoggato = utente;
        aggiornaListaPlaylist();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        colonnaPlaylist.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colonnaPlayPlaylist.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        colonnaEliminaPlaylist.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        colonnaDurata.setCellValueFactory(cellData -> { int secondiTotali = playlistService.calcolaDurataPlaylist(cellData.getValue()); 
                                                        int min = secondiTotali / 60;
                                                        int sec = secondiTotali % 60; 
                                                        return new SimpleStringProperty(String.format("%d:%02d", min, sec));
                                                       });
        colonnaBraniPlaylist.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitolo()));
        colonnaPlayBrano.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        colonnaRimuoviBrano.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        // Selezione Playlist -> Mostra i suoi brani
        tabellaPlaylist.getSelectionModel().selectedItemProperty().addListener((obs, vecchia, nuova) -> {
            if (nuova != null) {
                tabellaBraniPlaylist.setItems(FXCollections.observableArrayList(nuova.getBrani()));
            } else {
                tabellaBraniPlaylist.getItems().clear();
            }
        });

        // Tasto Play per Playlist
        colonnaPlayPlaylist.setCellFactory(col -> new TableCell<Playlist, Playlist>() {
            private final Label btnPlay = new Label("▶");
            {
                btnPlay.setStyle("-fx-text-fill: #EBB946; -fx-cursor: hand; -fx-font-size: 20px; -fx-font-weight: bold;");
                
                    btnPlay.setOnMouseClicked(e -> {
                        Playlist p = getItem();
                        try{
                            if (p != null && !p.getBrani().isEmpty()) {
                                playlistService.riproduciPlaylist(p);
                        }
                    } catch(FileAudioNonTrovatoException elem){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERRORE DI RIPRODUZIONE");
                        alert.setContentText(elem.getMessage());
                        alert.showAndWait();
                    }
                });     
        }
            @Override
            protected void updateItem(Playlist p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) {
                    setGraphic(null);
                } else {
                    setAlignment(Pos.CENTER);
                    setGraphic(btnPlay);
                }
            }
        });

        // Tasto Elimina per Playlist
        colonnaEliminaPlaylist.setCellFactory(col -> new TableCell<Playlist, Playlist>() {
            private final Label btnDel = new Label("🗑");
            {
                btnDel.setStyle("-fx-text-fill: #E74C3C; -fx-cursor: hand; -fx-font-size: 13px;");
               btnDel.setOnMouseClicked(e -> {
    Playlist p = getItem();
    if (p != null && utenteLoggato != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma eliminazione");
        alert.setHeaderText(null);
        alert.setContentText("confermi l'eliminazione della playlist \"" + p.getNome() + "\"?");
        Optional<ButtonType> risultato = alert.showAndWait();
        if (risultato.isPresent() && risultato.get() == ButtonType.OK) {
            playlistService.eliminaPlaylist(p, utenteLoggato);
            aggiornaListaPlaylist();
            tabellaBraniPlaylist.getItems().clear();
        }
    }
});
            }
            @Override
            protected void updateItem(Playlist p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) {
                    setGraphic(null);
                } else {
                    setAlignment(Pos.CENTER);
                    setGraphic(btnDel);
                }
            }
        });

        // Tasto Play per singolo Brano nella Playlist
        colonnaPlayBrano.setCellFactory(col -> new TableCell<Brano, Brano>() {
            private final Label btnPlay = new Label("▶");
            {
                btnPlay.setStyle("-fx-text-fill: #EBB946; -fx-cursor: hand; -fx-font-size: 20px; -fx-font-weight: bold;");
                
                    btnPlay.setOnMouseClicked(e -> {
                        Brano brano = getItem();
                        try{
                            if (brano != null) {
                                coda.aggiungiAllaCoda(brano);
                                coda.impostaUltimoIndice();
                                playerService.play();
                            }
                        } catch(FileAudioNonTrovatoException elem){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("ERRORE DI RIPRODUZIONE");
                            alert.setContentText(elem.getMessage());
                            alert.showAndWait();
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

        // Tasto Rimuovi 
        colonnaRimuoviBrano.setCellFactory(col -> new TableCell<Brano, Brano>() {
            private final Label btnRem = new Label("🗑");
            {
                btnRem.setStyle("-fx-text-fill: #E74C3C; -fx-cursor: hand; -fx-font-size: 13px;");
                btnRem.setOnMouseClicked(e -> {
                    Brano brano = getItem();
                    Playlist p = tabellaPlaylist.getSelectionModel().getSelectedItem();
                    if (brano != null && p != null) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Conferma eliminazione");
                        alert.setHeaderText(null);
                        alert.setContentText("confermi l'eliminazione del brano \"" + brano.getTitolo() + "\"?");
                        Optional<ButtonType> risultato = alert.showAndWait();
                        if (risultato.isPresent() && risultato.get() == ButtonType.OK){
                     playlistService.rimuoviBrano(p, brano);
                  tabellaBraniPlaylist.setItems(FXCollections.observableArrayList(p.getBrani()));
                        }  
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
                    setGraphic(btnRem);
                }
            }
        });
    }

    /**
     * aggiorna la tabella con le playlist dell'utente loggato.
     */
    private void aggiornaListaPlaylist() {
        if (utenteLoggato != null) {
            tabellaPlaylist.setItems(FXCollections.observableArrayList(playlistService.getPlaylistUtente(utenteLoggato)));
        }
    }

    /**
     * crea una nuova playlist
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void handleCreaPlaylist(ActionEvent event) {
        String nome = nomeNuovaPlaylist.getText();
        String descrizione = descrizioneNuovaPlaylist.getText();
        

        Playlist nuova = new Playlist(nome,descrizione,utenteLoggato);
               
            playlistService.creaPlaylist(nuova, utenteLoggato);
            nomeNuovaPlaylist.clear();
            descrizioneNuovaPlaylist.clear();
            aggiornaListaPlaylist();
    }

    /**
     * sposta il brano selezionato una posizione più in alto nella playlist
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void handleSpostaSu(ActionEvent event) {
        Playlist playlist = tabellaPlaylist.getSelectionModel().getSelectedItem();
        Brano brano = tabellaBraniPlaylist.getSelectionModel().getSelectedItem();
        if (playlist == null || brano == null) {
            return;
        }
        playlistService.spostaSU(playlist, brano);
        tabellaBraniPlaylist.setItems(FXCollections.observableArrayList(playlist.getBrani()));
        tabellaBraniPlaylist.getSelectionModel().select(brano);
    }

    /**
     * sposta il brano selezionato una posizione più in basso nella playlist
     * @param event evento generato dal click sul pulsante
     */
    @FXML
    private void handleSpostaGiu(ActionEvent event) {
        Playlist playlist = tabellaPlaylist.getSelectionModel().getSelectedItem();
        Brano brano = tabellaBraniPlaylist.getSelectionModel().getSelectedItem();
        if (playlist == null || brano == null) {
            return;
        }
        playlistService.spostaGiu(playlist, brano);
        tabellaBraniPlaylist.setItems(FXCollections.observableArrayList(playlist.getBrani()));
        tabellaBraniPlaylist.getSelectionModel().select(brano);
    } 

  
}
