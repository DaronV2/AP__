package fr.daron.louis;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccueilVisiteurs {

    //fffff

    @FXML
    private Button btnHistorique;

    @FXML
    private Button btnSuivi;

    @FXML
    private void btnHistorique(ActionEvent event) throws IOException {
        App.setRoot("third");
    }

    @FXML
    private void btnSuivi(ActionEvent event) throws IOException {
        App.setRoot("secondary");
    }

}
