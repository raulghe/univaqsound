package it.univq.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univq.datamodel.Utente;
import it.univq.datamodel.Utenti;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private ComboBox<Utente> listaUtenti;

    @FXML
    private Button btnAccedi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaUtenti.setItems(FXCollections.observableArrayList(Utenti.getIstanza().getListaUtenti()));

        
    }

    @FXML
    private void handleAccedi(ActionEvent event) {
        Utente utenteSelezionato = listaUtenti.getSelectionModel().getSelectedItem();

        if (utenteSelezionato == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText(null);
            alert.setContentText("Per favore, seleziona un utente prima di accedere.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/home.fxml"));
            Parent root = loader.load();

            HomeController homeController = loader.getController();
            homeController.salvaUtente(utenteSelezionato);



            Stage stage = (Stage) listaUtenti.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("UnivaqSound ");
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception elem) {
            elem.printStackTrace();
        }
    }
}
