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

    private void setMatricule() {
        matricule.setText(utilisateur.matricule);
    }

    @FXML
    private TextField mois;

    @FXML
    private TextField montantUnitaireKm;

    @FXML
    private TextField nom;

    private void setNom() {
        nom.setText(utilisateur.nom);
    }

    @FXML
    private TextField nuitee;

    @FXML
    private TextField repasMidi;

    @FXML
    private TextField totalNuitee;

    @FXML
    private TextField totalRepasMidi;

    @FXML
    private Button btntest;

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

    @SuppressWarnings("unused")
    @FXML
    void switchAccueil(ActionEvent event) throws IOException {
        App.setRoot("primary");
        String nuit = nuitee.getText();
        String totalNuit = totalNuitee.getText();
        String repasMid = repasMidi.getText();
        String totalRepas = totalRepasMidi.getText();
        String km1 = km.getText();
        String afD1 = afDate1.getText();
        String afL1 = afLibelle1.getText();
        String afM1 = afMontant1.getText();
        String afD2 = afDate2.getText();
        String afL2 = afLibelle2.getText();
        String afM2 = afMontant2.getText();

        Sqldb sql2 = new Sqldb();
        //Connection c = sql2.connexionDb();
        //Statement stmnt = c.createStatement();

        String sql = "INSERT INTO fiche_frais (ff_qte_nuitees, ff_total_nuitees, ff_qte_repas, ff_total_repas, ff_qte_km) VALUES ( nuit, totalNuit, repasMidi, totalRepas, km1)";
        String sql1 = "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant) VALUES ( afD1, afL1, afM1)";
        // resultats = sql2.exeRequete(stmnt, sql);
    }

    @FXML
    public void matric() {
        setMatricule();
        setNom();
    }
}
