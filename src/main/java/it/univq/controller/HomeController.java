package it.univq.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.univq.datamodel.Utente;

public class HomeController implements Initializable {
    private Utente utenteLoggato;

    @FXML private Label labelNomeUtente;
    @FXML private Label labelTitoloBrano;
    @FXML private Label labelArtistaAlbum;
    @FXML private Button btnShuffle;
    @FXML private Button btnIndietro;
    @FXML private Button btnPlay;
    @FXML private Button btnPausa;
    @FXML private Button btnAvanti;
    @FXML private Button btnRipeti;
    @FXML private Label labelTempoCorrente;
    @FXML private Slider sliderProgresso;
    @FXML private Label labelTempoTotale;
    @FXML private Slider sliderVolume;
    @FXML private StackPane Centro;

    public void salvaUtente(Utente utente) {
        this.utenteLoggato = utente;
       
        labelNomeUtente.setText(utente.getNome());
        
    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    // tutti metodi della home

    @FXML
    private void handleEsci(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) labelNomeUtente.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("UnivaqSound ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void apriLibreria(ActionEvent event) {
        caricaVista("/viste/libreriaa.fxml");
    }

    @FXML
    private void apriPlaylist(ActionEvent event) {
        caricaVista("/viste/playlist.fxml");
    }

    @FXML
    private void apriRicerca(ActionEvent event) {
        caricaVista("/viste/ricerca.fxml");
    }

    @FXML
    private void apriStatistiche(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/statistiche.fxml"));
            Node vista = loader.load();

            StatisticheController controller = loader.getController();
            if (controller != null && utenteLoggato != null) {
                controller.setUtente(utenteLoggato);
            }

            Centro.getChildren().setAll(vista);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento della vista statistiche: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void play(ActionEvent event) {
        System.out.println("Play premuto");
    }

    @FXML
    private void pausa(ActionEvent event) {
        System.out.println("Pausa premuta");
    }

    @FXML
    private void indietro(ActionEvent event) {
        System.out.println("Indietro premuto");
    }

    @FXML
    private void handleAvanti(ActionEvent event) {
        System.out.println("Avanti premuto");
    }

    @FXML
    private void cambiaShuffle(ActionEvent event) {
        System.out.println("Cambia shuffle");
    }

    @FXML
    private void handleRepeat(ActionEvent event) {
        System.out.println("Cambia repeat");
    }
    
    private void caricaVista(String percorsoFxml) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(percorsoFxml));
        Node vista = loader.load();
        Centro.getChildren().setAll(vista);
    } catch (Exception e) {
        System.out.println("Errore nel caricamento della vista: " + percorsoFxml);
        
    }
}
}
