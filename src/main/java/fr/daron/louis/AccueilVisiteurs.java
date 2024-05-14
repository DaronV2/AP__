package fr.daron.louis;

//Importation des librairies nécessaires au bon fonctionnement du code 
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class AccueilVisiteurs {

    // Importation de tout les champs FXML de l'application
    @FXML
    private Button btnAccueil;

    @FXML
    private Button btnHistorique;

    @FXML
    private Button btnSuivi;

    @FXML
    private void boutonHistorique(ActionEvent event) throws IOException {
        App.setRoot("third");
    }

    @FXML
    private void btnSecondary(ActionEvent event) throws IOException {
        App.setRoot("secondary");

    }

    @FXML
    private void changePage(ActionEvent event) throws IOException {
        App.setRoot("primary");

    }

}
