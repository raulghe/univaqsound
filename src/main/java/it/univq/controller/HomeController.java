package it.univq.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import it.univq.datamodel.Brano;
import it.univq.datamodel.CodaDiRiproduzione;
import it.univq.datamodel.Utente;
import it.univq.service.PlayerService;
import it.univq.service.impl.PlayerServiceImpl;
import javafx.util.Duration;

public class HomeController implements Initializable {
    private Utente utenteLoggato;
    private PlayerService player=PlayerServiceImpl.getIstanza();
    private CodaDiRiproduzione coda= CodaDiRiproduzione.getIstanza();
    @FXML private Label labelNomeUtente;
    @FXML private ImageView immagineCopertina;
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
    @FXML private Button btnGestione;

    
   

    public void salvaUtente(Utente utente) {
        this.utenteLoggato = utente;
       
        labelNomeUtente.setText(utente.getNome());
        player.setUtente(utenteLoggato);
        boolean administrator = utente.isAdministrator();
        btnGestione.setVisible(administrator);
        btnGestione.setManaged(administrator);
        
    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Timer che controlla lo stato del player ogni mezzo secondo per aggiornare la barra sotto
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> aggiornaBarraPlayer()));
    timeline.setCycleCount(Timeline.INDEFINITE);
    
    timeline.play();
        
    }

    // tutti metodi della home

    @FXML
    private void handleEsci(ActionEvent event) {
        try {
           player.pausa();
           
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
        
        LibreriaController libc=(LibreriaController)caricaVista("/viste/libreria.fxml");
        libc.salvaUtente(utenteLoggato);
    }

    @FXML
    private void apriPlaylist(ActionEvent event) {
        PlaylistController pc = (PlaylistController) caricaVista("/viste/playlist.fxml");
        if (pc != null && utenteLoggato != null) {
            pc.salvaUtente(utenteLoggato);
        }
    }

    @FXML
    private void apriRicerca(ActionEvent event) {
         RicercaController rc = (RicercaController) caricaVista("/viste/ricerca.fxml");
        if (rc != null && utenteLoggato != null) {
            rc.salvaUtente(utenteLoggato);
        }
    }

    @FXML
    private void apriStatistiche(ActionEvent event) {
        StatisticheController sc=(StatisticheController)caricaVista("/viste/statistiche.fxml");
        sc.setUtente(utenteLoggato);
    }


    @FXML
    private void play(ActionEvent event) {
        
        player.play();
       
       
    }

    @FXML
    private void pausa(ActionEvent event) {
        player.pausa();
    }

    @FXML
    private void indietro(ActionEvent event) {
        player.indietro();
    }

    @FXML
    private void handleAvanti(ActionEvent event) {
        player.avanti();
    }

   
           @FXML
    private void cambiaShuffle(ActionEvent event) {
        player.cambiaShuffle();
        if (coda.isShuffle()) {
            btnShuffle.setStyle("-fx-background-color: transparent; -fx-text-fill: #EBB946; -fx-cursor: hand;");
        } else {
            btnShuffle.setStyle("-fx-background-color: transparent; -fx-text-fill: #888888; -fx-cursor: hand;");
        }
    }
    

    @FXML
    private void handleRepeat(ActionEvent event) {
       player.cambiaModalitaRipetizione();
          if (coda.isRepeat()) {
            btnRipeti.setStyle("-fx-background-color: transparent; -fx-text-fill: #EBB946; -fx-cursor: hand;");
        } else {
            btnRipeti.setStyle("-fx-background-color: transparent; -fx-text-fill: #888888; -fx-cursor: hand;");
        }
    }
    
    private Object caricaVista(String percorsoFxml) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(percorsoFxml));
        Node vista = loader.load();
        Centro.getChildren().setAll(vista);
        return (Object)loader.getController();
        
    } catch (Exception e) {
        System.out.println("Errore nel caricamento della vista: " + percorsoFxml);
        return null;
    }
}
private void aggiornaBarraPlayer() {
        Brano brano = player.getBranoCorrente();
        if (brano != null) {
            
            labelTitoloBrano.setText(brano.getTitolo());
            
            if (brano.getAlbumBrano() != null) {
                String nomeArtista = (brano.getAlbumBrano().getArtistaAlbum() != null)? brano.getAlbumBrano().getArtistaAlbum().getNomeArte()  : "";
                labelArtistaAlbum.setText(nomeArtista + " - " + brano.getAlbumBrano().getTitolo());

                //caricamento copertina
                String percorsoCop = brano.getAlbumBrano().getPercorsoCopertina();
                if (percorsoCop != null && !percorsoCop.isBlank() && immagineCopertina != null) {
                    URL risorsa = getClass().getResource(percorsoCop.trim());
                    if (risorsa != null) {
                        immagineCopertina.setImage(new Image(risorsa.toExternalForm()));
                    } else {
                        immagineCopertina.setImage(null);
                    }
                } else if (immagineCopertina != null) {
                    immagineCopertina.setImage(null);
                }
            } else if (immagineCopertina != null) {
                immagineCopertina.setImage(null);
            }
            double tempoAttuale = player.getTempoCorrenteSecondi();
            int minAttuali = (int) (tempoAttuale / 60);
            int secAttuali = (int) (tempoAttuale % 60);
            //aggiorna il tempo corrente
            labelTempoCorrente.setText(String.format("%d:%02d", minAttuali, secAttuali));
            double durataTotale = player.getDurataTotaleSecondi();
            if (durataTotale > 0) {
                int minTotali = (int) (durataTotale / 60);
                int secTotali = (int) (durataTotale % 60);
                labelTempoTotale.setText(String.format("%d:%02d", minTotali, secTotali));
                sliderProgresso.setMax(durataTotale);
                if (!sliderProgresso.isValueChanging()) {
                    sliderProgresso.setValue(tempoAttuale);
                }
            }
        } else {
            labelTitoloBrano.setText("Nessun brano in riproduzione");
            labelArtistaAlbum.setText("");
            if (immagineCopertina != null) {
                immagineCopertina.setImage(null);
            }
        }
    }
    @FXML
    private void apriGestione(ActionEvent event){
        if(utenteLoggato == null || !utenteLoggato.isAdministrator()){
            return;
        }
        caricaVista("/viste/gestione.fxml");
    }
}
