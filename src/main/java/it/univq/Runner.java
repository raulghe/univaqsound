package it.univq;

import java.time.LocalDate;

import java.io.File;
import java.io.IOException;
import it.univq.service.PersistenzaService;
import it.univq.service.impl.PersistenzaServiceImpl;

import it.univq.datamodel.Album;
import it.univq.datamodel.ArtistaGruppo;
import it.univq.datamodel.ArtistaSolista;
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

            //Avvio con conseguente salvataggio
            PersistenzaService persis = new PersistenzaServiceImpl();
            File stato = new File("stato_app.bin");

            if(stato.exists()){
                persis.caricaStato("stato_app.bin");       
            }else{
            
            //Inizializzazione utenti di prova
            Utenti.getIstanza().aggiungiUtente(new Utente("Mario", "Rossi", "admin", "admin@univaq.it", LocalDate.of(1990, 1, 1), true));
            Utenti.getIstanza().aggiungiUtente(new Utente("Luigi", "Verdi", "user", "luigi@univaq.it", LocalDate.of(2000, 5, 10), false));
            
            //Dati di prova per la Libreria (Artisti, Album, Brani)
            inizializzaDatiLibreria();

            persis.salvaStato("stato_app.bin");
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setTitle("UnivaqSound");
            stage.setScene(scene);
            stage.setResizable(false);

            //Salvataggio stato aggiornato prima della chiusura
            stage.setOnCloseRequest(event ->{
                try{
                    persis.salvaStato("stato_app.bin");
                }catch(IOException elem){
                    elem.printStackTrace();
                }
            });

            stage.show();
        } catch (Exception elem) {
            elem.printStackTrace();
        }
    }


    private void inizializzaDatiLibreria() {
        Libreria lib = Libreria.getIstanza();
        if(!lib.getBrani().getElementi().isEmpty()) return;

        //Artisti
        ArtistaSolista luminewave = new ArtistaSolista("Luminewave", "Produced musica elettronica", GenereMusicale.ELETTRONICA);
        ArtistaGruppo queen = new ArtistaGruppo("Queen", "Celebre gruppo rock britannico", GenereMusicale.ROCK);
        ArtistaGruppo pinkFloyd = new ArtistaGruppo("Pink Floyd", "Pionieri della musica psichedelica", GenereMusicale.ROCK);


        //Album
        Album nightSignals = new Album("Night signals", 2024, GenereMusicale.ELETTRONICA, "/audio/luminewave.jpg", luminewave);
       Album nightAtOpera = new Album("A Night at the Opera", 1975, GenereMusicale.ROCK, "/audio/queen.jpg", queen);
       Album darkSide = new Album("The Dark Side of the Moon", 1973, GenereMusicale.ROCK, "/audio/pink-floyd.png", pinkFloyd);


        //Brani.nightSignals.luminewave
        Brano b1 = new Brano("Trailer intro", 26, GenereMusicale.ELETTRONICA, nightSignals);
        b1.setPercorsoFile("/audio/luminewave-audioknap-trailer-intro-620276.mp3");
        Brano b2 = new Brano("Devil time", 73, GenereMusicale.ELETTRONICA, nightSignals);
        b2.setPercorsoFile("/audio/luminewave-devil-time-621140.mp3");
        Brano b3 = new Brano("Spooky Halloween", 102, GenereMusicale.ELETTRONICA, nightSignals);
        b3.setPercorsoFile("/audio/luminewave-sound4stock-halloween-spooky-music-620458.mp3");

        //Brani.queen.nightAtOpera
        Brano b4 = new Brano("Bohemian rhapsody", 356, GenereMusicale.ROCK, nightAtOpera);
        b4.setPercorsoFile("/audio/queen-bohemian-rhapsody.mp3");
        Brano b5 = new Brano("Love of my life", 223, GenereMusicale.ROCK, nightAtOpera);
        b5.setPercorsoFile("/audio/queen-love-of-my-life.mp3");
        Brano b6 = new Brano("You're my best friend", 175, GenereMusicale.ROCK, nightAtOpera);
        b6.setPercorsoFile("/audio/queen-youre-my-best-friend.mp3");

        //Brani.pinkFloyd.darkSide
        Brano b7 = new Brano("Time", 424, GenereMusicale.ROCK, darkSide);
        b7.setPercorsoFile("/audio/pink-floyd-time.mp3");
        Brano b8 = new Brano("Money", 392, GenereMusicale.ROCK, darkSide);
        b8.setPercorsoFile("/audio/pink-floyd-money.mp3");
        Brano b9 = new Brano("Breathe (In the air)", 173, GenereMusicale.ROCK, darkSide);
        b9.setPercorsoFile("/audio/pink-floyd-breathe-in-the-air.mp3");
        


        

        //Album con i propri brani
        nightSignals.getBrani().add(b1);
        nightSignals.getBrani().add(b2);
        nightSignals.getBrani().add(b3);

        nightAtOpera.getBrani().add(b4);
        nightAtOpera.getBrani().add(b5);
        nightAtOpera.getBrani().add(b6);

        darkSide.getBrani().add(b7);
        darkSide.getBrani().add(b8);
        darkSide.getBrani().add(b9);

        //Artista con la propria discografia
        luminewave.getDiscografia().add(nightSignals);
        queen.getDiscografia().add(nightAtOpera);
        pinkFloyd.getDiscografia().add(darkSide);

        //Libreria per ogni artista
        lib.getArtisti().aggiungiElemento(luminewave);
        lib.getArtisti().aggiungiElemento(queen);
        lib.getArtisti().aggiungiElemento(pinkFloyd);

        //Libreria per ogni album
        lib.getAlbum().aggiungiElemento(nightSignals);
        lib.getAlbum().aggiungiElemento(nightAtOpera);
        lib.getAlbum().aggiungiElemento(darkSide);

        //Libreria per ogni brano
        lib.getBrani().aggiungiElemento(b1);
        lib.getBrani().aggiungiElemento(b2);
        lib.getBrani().aggiungiElemento(b3);
        lib.getBrani().aggiungiElemento(b4);
        lib.getBrani().aggiungiElemento(b5);
        lib.getBrani().aggiungiElemento(b6);
        lib.getBrani().aggiungiElemento(b7);
        lib.getBrani().aggiungiElemento(b8);
        lib.getBrani().aggiungiElemento(b9);
    }
}