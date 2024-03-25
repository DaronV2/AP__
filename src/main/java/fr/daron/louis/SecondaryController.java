package fr.daron.louis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;

//import com.mysql.cj.protocol.Resultset;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    private Button accueilBtn;

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

    private void setMatricule(TextField matricule) {
        matricule.setEditable(true);
        matricule.setText(utilisateur.matricule);
    }

    @FXML
    private DatePicker barreMois;

    /*
     * public void setMois(DatePicker mois) {
     * String dateTime =
     * DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
     * mois.setText("00-00-0000");
     * mois.setText(dateTime.toString());
     * }
     */

    @FXML
    private TextField montantUnitaireKm;

    @FXML
    private TextField nom;

    private void setNom(TextField nom) {
        nom.setText("noms");
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
    private DatePicker barreMois1;

    @FXML
    private DatePicker barreMois11;

    @FXML
    void initialize() {
        setMatricule(matricule);
        setNom(nom);
        // setMois(mois);

        matricule.setEditable(false);
        nom.setEditable(false);
        barreMois.setEditable(false);
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
    void envoyer(ActionEvent event) throws SQLException, InterruptedException {
        String nuit = nuitee.getText();
        String totalNuit = totalNuitee.getText();
        String repasMid = repasMidi.getText();
        String totalRepas = totalRepasMidi.getText();
        String km1 = km.getText();
        String afL1 = afLibelle1.getText();
        String afM1 = afMontant1.getText();
        String afL2 = afLibelle2.getText();
        String afM2 = afMontant2.getText();
        LocalDate moisHf1 = barreMois1.getValue();
        String moisHf1String = moisHf1.toString();
        LocalDate moisHf2 = barreMois11.getValue();
        LocalDate moisString = barreMois.getValue();
        String matriculeString = matricule.getText();
        String px_km = montantUnitaireKm.getText();

        Sqldb sql2 = new Sqldb();
        // Connection c = sql2.connexionDb();
        // Statement stmnt = c.createStatement();
        Connection c = sql2.connexionDb();
        Statement stmnt = c.createStatement();

        java.util.UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        moisHf1String = moisHf1String.replace("-", ",");
        moisHf1String = moisHf1String.replace("03", "3");

        if (moisHf1 != null & afL1 != null & afM1 != null) {
            moisHf1String = "STR_TO_DATE(\"" + moisHf1String + ", 10,10,10\", \"%Y,%m,%d %h,%i,%s\")";
            System.out.println(moisHf1String);
            String sql1 = "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES (" + moisHf1String
                    + ",'" + afL1 + "'," + afM1 + ",'" + uuidString + "')";
            stmnt.executeUpdate(sql1);

        } else {
            System.out.println("yes");
        }

        if (moisHf2 != null & afL2 != null & afM2 != null) {
            String sql1 = String.format(
                    "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES (%s,'%s',%s,'%s')",
                    moisHf2, afL1, afM2, uuidString);
            stmnt.executeUpdate(sql1);
        } else {
            System.out.println("yes");
        }

        String jointure = String.format("SELECT vi_matricule FROM visiteur WHERE cr_identifiant = '%s'",
                utilisateur.identfiant);
        ResultSet resultats43 = stmnt.executeQuery(jointure);
        resultats43.next();
        matriculeString = resultats43.getNString("vi_matricule");
        System.out.println(moisString);

        String date = "STR_TO_DATE(\"2017,8,14 10,40,10\", \"%Y,%m,%d %h,%i,%s\")";

        String sql = "INSERT INTO fiche_frais (ff_mois,ff_qte_nuitees, ff_total_nuitees, ff_qte_repas, ff_total_repas, ff_qte_km,vi_matricule,prix_km) VALUES ("
                + date + ", " + nuit + "," + totalNuit + "," + repasMid + "," + totalRepas + "," + km1 + ", '"
                + matriculeString + "'," + px_km + ")";
        System.out.println(sql);
        stmnt.executeUpdate(sql);

        String getId = "SELECT ff_id from fiche_frais WHERE ff_mois = %s AND";
        ResultSet resultatId = stmnt.executeQuery(getId);
        resultatId.next();

        String afD1 = "";
        if (afD1 == null & afL1 == null & afM1 == null) {

            String sql1 = String.format("INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant) VALUES (%s,%s,%s)",
                    afD1, afL1, afM1);
            stmnt.executeUpdate(sql1);
        } else {
            System.out.println("yes");
        }

        String afD2 = "";
        if (afD2 == null & afL2 == null & afM2 == null) {
            String ffid = "";
            String sql1 = String.format(
                    "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES (%s,%s,%s,%s)", afD1,
                    afL1, afM1, ffid);
            stmnt.executeUpdate(sql1);
        } else {
            System.out.println("yes");
        }

        // Faire d'abord les requetes d'hors forfait et ensuite la fiche frais afin
        // dr'avoir l'id

    }

    private String UUID() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UUID'");
    }

    @FXML
    void switchAccueil(ActionEvent event) throws IOException, SQLException {
        App.setRoot("primary");
    }
}
