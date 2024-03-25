package fr.daron.louis;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
