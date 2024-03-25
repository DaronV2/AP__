package fr.daron.louis;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccueilComptable {
    @FXML
    private Button btnAccueilC;

    @FXML
    private Button btnHistComp;

    @FXML
    void btnAccueil_2(ActionEvent event) throws IOException {
        App.setRoot("primary");

    }

    @FXML
    void btnHist(ActionEvent event) throws IOException {
        App.setRoot("third");

    }
    // commit
}
