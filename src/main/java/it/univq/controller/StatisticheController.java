package it.univq.controller;

import it.univq.datamodel.Artista;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Utente;
import it.univq.exception.FileAudioNonTrovatoException;
import it.univq.service.ArtistaService;
import it.univq.service.StatisticheService;
import it.univq.service.impl.ArtistaServiceImpl;
import it.univq.service.impl.StatisticheServiceImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import it.univq.service.CodaDiRiproduzioneService;
import it.univq.service.impl.CodaDiRiproduzioneServiceImpl;
import it.univq.service.PlayerService;
import it.univq.service.impl.PlayerServiceImpl;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * controller della vista delle statistiche.mostra le classifiche dei brani e degli artisti più ascoltati (tabelle e grafici),
 * il tempo totale di ascolto dell'utente loggato e i brani che gli vengono suggeriti.
 */
public class StatisticheController implements Initializable {

    @FXML private Label labelTempoAscolto;

    @FXML private TableView<Brano> tabellaBrani;
    @FXML private TableColumn<Brano, String> colTitolo;
    @FXML private TableColumn<Brano, String> colArtista;
    @FXML private TableColumn<Brano, Integer> colAscoltiBrano;

    @FXML private TableView<Artista> tabellaArtisti;
    @FXML private TableColumn<Artista, String> colNomeArtista;
    @FXML private TableColumn<Artista, Integer> colAscoltiArtista;

    @FXML private BarChart<String, Number> barChartBrani;
    @FXML private PieChart pieChartArtisti;

    @FXML private ListView<Brano> listaSuggeriti;

    private final StatisticheService statisticheService = new StatisticheServiceImpl();
    private final ArtistaService artistaService = new ArtistaServiceImpl();
    private final PlayerService playerService = PlayerServiceImpl.getIstanza();
    private final CodaDiRiproduzioneService coda = new CodaDiRiproduzioneServiceImpl();


    private Utente utenteCorrente;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configuraTabelle();
        configuraGrafici();
        configuraListaSuggeriti();
    }


    /**
     * imposta l'utente corrente
     * @param utente l'{@link Utente} loggato
     */
    public void setUtente(Utente utente) {
        this.utenteCorrente = utente;
        caricaDatiUtente();
    }

    /**
     * configura le colonne delle tabelle e le riempie con i brani e gli artisti più ascoltati.
     */
    private void configuraTabelle() {
        colTitolo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitolo()));
        colArtista.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getAlbumBrano() != null && c.getValue().getAlbumBrano().getArtistaAlbum() != null
                        ? c.getValue().getAlbumBrano().getArtistaAlbum().getNomeArte() : "-"
        ));
        colAscoltiBrano.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getNumeroAscolti()).asObject());

        List<Brano> topBrani = statisticheService.braniPiuAscoltati();
        tabellaBrani.setItems(FXCollections.observableArrayList(topBrani));

        colNomeArtista.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNomeArte()));
        colAscoltiArtista.setCellValueFactory(c -> new SimpleIntegerProperty(artistaService.getAscoltiTotali(c.getValue())).asObject());

        List<Artista> topArtisti = statisticheService.artistiPiuAscoltati();
        tabellaArtisti.setItems(FXCollections.observableArrayList(topArtisti));
    }

    /**
     * riempie il grafico a barre dei brani più ascoltati e il grafico a torta degli artisti più ascoltati.
     */
    private void configuraGrafici() {

        List<Brano> topBrani = statisticheService.braniPiuAscoltati();
        XYChart.Series<String, Number> serieBrani = new XYChart.Series<>();
        serieBrani.setName("Numero ascolti");

        for (Brano b : topBrani) {
            serieBrani.getData().add(new XYChart.Data<>(b.getTitolo(), b.getNumeroAscolti()));
        }
        barChartBrani.getData().clear();
        barChartBrani.getData().add(serieBrani);
        barChartBrani.setLegendVisible(false);

        List<Artista> topArtisti = statisticheService.artistiPiuAscoltati();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for (Artista a : topArtisti) {
            int ascolti = artistaService.getAscoltiTotali(a);
            if (ascolti > 0) {
                pieData.add(new PieChart.Data(a.getNomeArte(), ascolti));
            }
        }
        pieChartArtisti.setData(pieData);
    }

    /**
     * configura le celle della lista dei brani suggeriti.
     */
    private void configuraListaSuggeriti() {
        listaSuggeriti.setCellFactory(param -> new ListCell<>() {
            private final Label btnPlay = new Label("▶");
            private final Label lblTesto = new Label();
            private final HBox riga = new HBox(12, btnPlay, lblTesto);
            {
                riga.setAlignment(Pos.CENTER_LEFT);
                btnPlay.setStyle("-fx-text-fill: #EBB946; -fx-cursor: hand; -fx-font-size: 20px; -fx-font-weight: bold;");
                lblTesto.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
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
            protected void updateItem(Brano item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    String artista = (item.getAlbumBrano() != null && item.getAlbumBrano().getArtistaAlbum() != null)
                            ? item.getAlbumBrano().getArtistaAlbum().getNomeArte() : "-";
                    lblTesto.setText(item.getTitolo() + " • " + artista + " [" + item.getGenereBrano() + "]");
                    setText(null);
                    setGraphic(riga);
                    setStyle("-fx-background-color: transparent;");
                }
            }
        });
    }

    /**
     * mostra il tempo totale di ascolto dell'utente
     */
    private void caricaDatiUtente() {
        if (utenteCorrente == null) {
            labelTempoAscolto.setText("00:00:00");
            return;
        }

        int totalSeconds = statisticheService.tempoTotaleAscoltato(utenteCorrente);
        int ore = totalSeconds / 3600;
        int minuti = (totalSeconds % 3600) / 60;
        int secondi = totalSeconds % 60;
        labelTempoAscolto.setText(String.format("%02d:%02d:%02d", ore, minuti, secondi));

        List<Brano> proposti = statisticheService.braniProposti(utenteCorrente);
        listaSuggeriti.setItems(FXCollections.observableArrayList(proposti));
    }



}
