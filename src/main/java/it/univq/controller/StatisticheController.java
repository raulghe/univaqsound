package it.univq.controller;

import it.univq.datamodel.Artista;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Utente;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

    private Utente utenteCorrente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configuraTabelle();
        configuraGrafici();
        configuraListaSuggeriti();
    }


    public void setUtente(Utente utente) {
        this.utenteCorrente = utente;
        caricaDatiUtente();
    }

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

    private void configuraListaSuggeriti() {

        listaSuggeriti.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Brano item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    String artista = (item.getAlbumBrano() != null && item.getAlbumBrano().getArtistaAlbum() != null)
                            ? item.getAlbumBrano().getArtistaAlbum().getNomeArte() : "-";
                    setText(item.getTitolo() + " • " + artista + " [" + item.getGenereBrano() + "]");
                    setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
                }
            }
        });
    }

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
