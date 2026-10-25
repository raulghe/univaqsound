package it.univq;

import java.time.LocalDate;

import it.univq.datamodel.Album;
import it.univq.datamodel.ArtistaGruppo;
import it.univq.datamodel.Brano;
import it.univq.datamodel.GenereMusicale;
import it.univq.datamodel.Libreria;
import it.univq.datamodel.Utente;
import it.univq.datamodel.Utenti;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Runner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            // Inizializzazione utenti di prova
            Utenti.getIstanza().aggiungiUtente(new Utente("Mario", "Rossi", "admin", "admin@univaq.it", LocalDate.of(1990, 1, 1), true));
            Utenti.getIstanza().aggiungiUtente(new Utente("Luigi", "Verdi", "user", "luigi@univaq.it", LocalDate.of(2000, 5, 10), false));

            // Dati di prova per la Libreria (Artisti, Album, Brani)
            inizializzaDatiLibreria();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setTitle("UnivaqSound");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inizializzaDatiLibreria() {
        Libreria lib = Libreria.getIstanza();

        // 1. Artista: Queen
        ArtistaGruppo queen = new ArtistaGruppo("Queen", "Celebre gruppo rock britannico", GenereMusicale.ROCK);
        Album nightAtOpera = new Album("A Night at the Opera", 1975, GenereMusicale.ROCK, "copertina_queen.jpg", queen);
        Brano b1 = new Brano("Bohemian Rhapsody", 355, GenereMusicale.ROCK, nightAtOpera);
        b1.setPercorsoFile("assets/audio/bohemian.mp3");
        Brano b2 = new Brano("Love of My Life", 219, GenereMusicale.ROCK, nightAtOpera);
        b2.setPercorsoFile("assets/audio/love_of_my_life.mp3");
        nightAtOpera.getBrani().add(b1);
        nightAtOpera.getBrani().add(b2);
        queen.getDiscografia().add(nightAtOpera);

        Album theGame = new Album("The Game", 1980, GenereMusicale.ROCK, "copertina_game.jpg", queen);
        Brano b3 = new Brano("Another One Bites the Dust", 215, GenereMusicale.ROCK, theGame);
        b3.setPercorsoFile("assets/audio/dust.mp3");
        theGame.getBrani().add(b3);
        queen.getDiscografia().add(theGame);

        // 2. Artista: Pink Floyd
        ArtistaGruppo pinkFloyd = new ArtistaGruppo("Pink Floyd", "Pionieri della musica psichedelica", GenereMusicale.ROCK);
        Album darkSide = new Album("The Dark Side of the Moon", 1973, GenereMusicale.ROCK, "copertina_floyd.jpg", pinkFloyd);
        Brano b4 = new Brano("Time", 413, GenereMusicale.ROCK, darkSide);
        b4.setPercorsoFile("assets/audio/time.mp3");
        Brano b5 = new Brano("Money", 382, GenereMusicale.ROCK, darkSide);
        b5.setPercorsoFile("assets/audio/money.mp3");
        darkSide.getBrani().add(b4);
        darkSide.getBrani().add(b5);
        pinkFloyd.getDiscografia().add(darkSide);

        // Salvataggio nel catalogo della Libreria
        lib.getArtisti().aggiungiElemento(queen);
        lib.getArtisti().aggiungiElemento(pinkFloyd);

        lib.getAlbum().aggiungiElemento(nightAtOpera);
        lib.getAlbum().aggiungiElemento(theGame);
        lib.getAlbum().aggiungiElemento(darkSide);

        lib.getBrani().aggiungiElemento(b1);
        lib.getBrani().aggiungiElemento(b2);
        lib.getBrani().aggiungiElemento(b3);
        lib.getBrani().aggiungiElemento(b4);
        lib.getBrani().aggiungiElemento(b5);
    }
}