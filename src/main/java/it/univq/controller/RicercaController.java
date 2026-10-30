package it.univq.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univq.datamodel.Album;
import it.univq.datamodel.Artista;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Playlist;
import it.univq.datamodel.Utente;

import it.univq.exception.DuplicatoException;
import it.univq.exception.FileAudioNonTrovatoException;
import it.univq.service.CodaDiRiproduzioneService;
import it.univq.service.CriterioOrdinamento;
import it.univq.service.Filtro;
import it.univq.service.PlayerService;
import it.univq.service.PlaylistService;
import it.univq.service.Ricercabile;
import it.univq.service.Riproducibile;
import it.univq.service.RicercaService;
import it.univq.service.impl.CodaDiRiproduzioneServiceImpl;
import it.univq.service.impl.PlayerServiceImpl;
import it.univq.service.impl.PlaylistServiceImpl;
import it.univq.service.impl.RicercaServiceImpl;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

/**
 * controller della vista di ricerca,permette di cercare artisti, album e brani per testo, con filtro (genere, anno, durata massima)
 * e criterio di ordinamento (nome, data, durata, ascolti) per i brani.
 */
public class RicercaController implements Initializable {

    @FXML private TextField testoRicerca;
    @FXML private Button cerca;

    // Ordinamento e Filtro
    @FXML private ToggleGroup ordine;
    @FXML private RadioButton ordinaNome;
    @FXML private RadioButton ordinaData;
    @FXML private RadioButton ordinaDurata;
    @FXML private RadioButton ordinaAscolti;
    @FXML private ComboBox<String> filtro;
    @FXML private TextField valoreFiltro;

    // Tabella Artisti
    @FXML private TableView<Artista> tabellaArtistiCercati;
    @FXML private TableColumn<Artista, String> colonnaArtisti;

    // Tabella Album
    @FXML private TableView<Album> tabellaAlbumCercati;
    @FXML private TableColumn<Album, String> colonnaAlbum;
    @FXML private TableColumn<Album, Album> colonnaPlayAlbum;

    // Tabella Brani
    @FXML private TableView<Brano> tabellaBraniCercati;
    @FXML private TableColumn<Brano, String> colonnaBrani;
    @FXML private TableColumn<Brano, Brano> colonnaPlayBrano;
    @FXML private TableColumn<Brano, String> colonnaAlbumBrano;
    @FXML private TableColumn<Brano, String> colonnaDurataBrano;
    @FXML private TableColumn<Brano, Brano> colonnaAggiungiPlaylist;

    private final RicercaService ricercaService = new RicercaServiceImpl();
    private final PlayerService playerService = PlayerServiceImpl.getIstanza();
    private final CodaDiRiproduzioneService coda = new CodaDiRiproduzioneServiceImpl();
    private Utente utenteLoggato;

    /**
     * salva l'utente che ha effettuato il login.
     *
     * @param utente l'{@link Utente} loggato
     */
    public void salvaUtente(Utente utente) {
        this.utenteLoggato = utente;        
        
    }    
    
    
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurazione visualizzazione testi colonne
        colonnaArtisti.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getNomeArte()));

        colonnaAlbum.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getTitolo()));
        
        colonnaBrani.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getTitolo()));
        colonnaAlbumBrano.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getAlbumBrano().getTitolo()));
        colonnaDurataBrano.setCellValueFactory(cellData -> {int durata = cellData.getValue().getDurata();
    return new SimpleStringProperty(String.format("%d:%02d", durata / 60, durata % 60));});
        colonnaPlayAlbum.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        colonnaPlayBrano.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        colonnaAggiungiPlaylist.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

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
        // Configurazione tasto aggiungi a playlisy
        colonnaAggiungiPlaylist.setCellFactory(col -> new TableCell<Brano, Brano>() {
            private final Label btnAggiungi = new Label("➕");
            {
                btnAggiungi.setStyle("-fx-text-fill: #EBB946; -fx-cursor: hand; -fx-font-size: 20px; -fx-font-weight: bold;");
                btnAggiungi.setOnMouseClicked(e -> {
                    Brano brano = getItem();
                    if (brano != null) {
                        RicercaController.this.aggiungiAllaPlaylist(brano);
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
                    setGraphic(btnAggiungi);
                }
            }
        });
        
        //imposto i filtri
        if (filtro != null) {
            filtro.getItems().addAll("Nessuno", "Genere", "Anno", "Durata Massima");
            filtro.setValue("Nessuno");
        }
        
        // cerca con invio
        testoRicerca.setOnAction(e -> eseguiRicerca(e));
        // Permette di deselezionare il RadioButton ricliccandoci sopra
        for (Toggle toggle : ordine.getToggles()) {
       if (toggle instanceof RadioButton rb) {
        rb.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (rb.isSelected()) {
                ordine.selectToggle(null); // deseleziona
                event.consume();  
            }
        });
    }
}

    }

    /**
     * esegue la ricerca in base al testo, al filtro e al criterio di ordinamento selezionati.
     * se è attivo un filtro o un ordinamento mostra solo i brani, altrimenti mostra artisti, album e brani trovati.
     * @param event evento generato dal click sul pulsante o dal tasto invio
     */
    @FXML
    private void eseguiRicerca(ActionEvent event) {
        tabellaArtistiCercati.getItems().clear();
        tabellaAlbumCercati.getItems().clear();
        tabellaBraniCercati.getItems().clear();
        String testo = testoRicerca.getText();
         Filtro<Brano> filtroobj = null;
         CriterioOrdinamento<Brano> criterio = null;
        //imposto il criterio di ordinamento
        if (ordinaNome.isSelected()) {
            criterio = (b1, b2) -> b1.getTitolo().compareToIgnoreCase(b2.getTitolo());
        } else if (ordinaData.isSelected()) {
            criterio = (b1, b2) -> b2.getDataAggiunta().compareTo(b1.getDataAggiunta()); // dal più recente
        } else if (ordinaDurata.isSelected()) {
            criterio = (b1, b2) -> Integer.compare(b1.getDurata(), b2.getDurata());
        } else if (ordinaAscolti.isSelected()) {
            criterio = (b1, b2) -> Integer.compare(b2.getNumeroAscolti(), b1.getNumeroAscolti()); // dal più ascoltato
        }
        //qui imposto il filtro se attivo
        if(!(filtro.getValue().equals("Nessuno"))){
            
            if(filtro.getValue().equals("Genere")){
                filtroobj = b -> b.getGenereBrano().name().equalsIgnoreCase(valoreFiltro.getText());
            }else if(filtro.getValue().equals("Anno")){filtroobj = b -> b.getDataAggiunta().getYear() == Integer.parseInt(valoreFiltro.getText());
            }else if(filtro.getValue().equals("Durata Massima")){filtroobj = b -> b.getDurata() <= Integer.parseInt(valoreFiltro.getText());
            }
        }
        

        //se attivo criterio di ordinamento o un filtro
        if (criterio != null || !filtro.getValue().equals("Nessuno")) {
            //qui ho i brani con il filtro 
            List<Brano> risultati = ricercaService.filtraBrani(filtroobj, criterio);     
            List<Brano> risultatiFinali = risultati.stream().filter(b->b.trovato(testoRicerca.getText())).toList();
            tabellaBraniCercati.setItems(FXCollections.observableArrayList(risultatiFinali));
        } else {
            // Nessun ordinamento o filtro
            List<Ricercabile> risultati = ricercaService.cerca(testo);
            List<Artista> artistiTrovati = new ArrayList<>();
            List<Album> albumTrovati = new ArrayList<>();
            List<Brano> braniTrovati = new ArrayList<>();

            for (Ricercabile r : risultati) {
                if (r instanceof Artista artista) {
                    artistiTrovati.add(artista);
                } else if (r instanceof Album album) {
                    albumTrovati.add(album);
                } else if (r instanceof Brano brano) {
                    braniTrovati.add(brano);
                }
            }

            tabellaArtistiCercati.setItems(FXCollections.observableArrayList(artistiTrovati));
            tabellaAlbumCercati.setItems(FXCollections.observableArrayList(albumTrovati));
            tabellaBraniCercati.setItems(FXCollections.observableArrayList(braniTrovati));
        }
    }

    /**
     * aggiunge un brano a una playlist scelta dall'utente.
     * @param brano il {@link Brano} da aggiungere
     */
    public void aggiungiAllaPlaylist(Brano brano) {
          if ( utenteLoggato.getPlaylist().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Non hai ancora nessuna playlist. Creane prima una nella sezione Playlist!");
        alert.showAndWait();
        return;
    } 

    
    List<Playlist> miePlaylist = utenteLoggato.getPlaylist();
    ChoiceDialog<Playlist> dialog = new ChoiceDialog<>(miePlaylist.get(0), miePlaylist);
    dialog.setTitle("Aggiungi a Playlist");
    dialog.setHeaderText("Aggiungi \"" + brano.getTitolo() + "\" a una playlist");
    dialog.setContentText("Scegli la playlist:");
    
    try {
        dialog.showAndWait().ifPresent(playlistScelta -> {
          PlaylistService playlistService = new PlaylistServiceImpl();
            playlistService.aggiungiBrano(playlistScelta, brano);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);  
            alert.setContentText("Brano aggiunto con successo a: " + playlistScelta.getNome());
            alert.show();
        
        });
    } catch(DuplicatoException elem){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE DUPLICATO");
            alert.setContentText(elem.getMessage());
            alert.showAndWait();
    }
    
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
