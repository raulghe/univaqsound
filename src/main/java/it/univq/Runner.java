package it.univq;

import java.time.LocalDate;

import java.io.File;
import java.io.IOException;
import it.univq.service.PersistenzaService;
import it.univq.service.ImportCatalogoService;
import it.univq.service.impl.PersistenzaServiceImpl;
import it.univq.service.impl.ImportCatalogoServiceImpl;
import it.univq.datamodel.GenereMusicale;
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
            Utente mario=new Utente("Mario", "Rossi", "admin", "admin@univaq.it", LocalDate.of(1990, 1, 1), true);
            mario.setPreferenzeMusicali(GenereMusicale.ROCK,GenereMusicale.POP);
            Utente luigi=new Utente("Luigi", "Verdi", "user", "luigi@univaq.it", LocalDate.of(2000, 5, 10), false);
            luigi.setPreferenzeMusicali(GenereMusicale.ELETTRONICA);
            Utenti.getIstanza().aggiungiUtente(mario);
            Utenti.getIstanza().aggiungiUtente(luigi);

            //Import iniziale del catalogo (Artisti, Album, Brani) da file CSV
            ImportCatalogoService importCatalogo = new ImportCatalogoServiceImpl();
            importCatalogo.importaCatalogoDaCsv("/data/catalogo.csv");

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
}