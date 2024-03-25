package fr.daron.louis;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class AccueilVisiteurs {

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
