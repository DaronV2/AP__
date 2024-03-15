package fr.daron.louis;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    private Button accueilBtn;

    @FXML
    private TextField afDate1;

    @FXML
    private TextField afDate2;

    @FXML
    private TextField afLibelle1;

    @FXML
    private TextField afLibelle2;

    @FXML
    private TextField afMontant1;

    @FXML
    private TextField afMontant2;

    @FXML
    private TextField km;

    @FXML
    private TextField matricule;

    @FXML
    private TextField mois;

    @FXML
    private TextField montantUnitaireKm;

    @FXML
    private TextField nom;

    @FXML
    private TextField nuitee;

    @FXML
    private TextField repasMidi;

    @FXML
    private TextField totalNuitee;

    @FXML
    private TextField totalRepasMidi;

    @FXML
    void initialize() {
        nuitee.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double value = Double.parseDouble(newValue);
                totalNuitee.setText(String.valueOf(value * 80));
            } catch (NumberFormatException e) {
                totalNuitee.setText("");
            }
        });

        repasMidi.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double value = Double.parseDouble(newValue);
                totalRepasMidi.setText(String.valueOf(value * 29));
            } catch (NumberFormatException e) {
                totalRepasMidi.setText("");
            }
        });
    }

    @FXML
    void switchAccueil(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

}