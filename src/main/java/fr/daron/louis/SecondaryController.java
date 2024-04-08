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
import javafx.scene.text.Text;

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
    private Text resultatRequete1;

    @FXML
    void initialize() throws SQLException {
        setMatricule(matricule);
        setNom(nom);

        matricule.setEditable(false);
        nom.setEditable(false);
        barreMois.setEditable(false);
        nuitee.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String sql = "SELECT `prix_nuit` FROM `prix`";
                ResultSet res = Sqldb.executionRequete(sql);
                if (res.next()) {
                    String prixnuit = res.getString("prix_nuit");
                    int pxNuit = Integer.valueOf(prixnuit);
                    double value = Double.parseDouble(newValue);
                    double calcul = value * pxNuit;
                    totalNuitee.setText(String.valueOf(value * pxNuit));
                }
            } catch (NumberFormatException e) {
                totalNuitee.setText("");
            } catch (SQLException e) {
                totalNuitee.setText("");
            }
        });

        repasMidi.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String sql = "SELECT `prix_repas` FROM `prix`";
                ResultSet res = Sqldb.executionRequete(sql);
                if (res.next()) {
                    String prixrepas = res.getString("prix_repas");
                    int pxRepas = Integer.valueOf(prixrepas);
                    double value = Double.parseDouble(newValue);
                    totalRepasMidi.setText(String.valueOf(value * pxRepas));
                }
            } catch (NumberFormatException e) {
                totalRepasMidi.setText("");
            } catch (SQLException e) {
                totalNuitee.setText("");
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
        LocalDate moisHf2 = barreMois11.getValue();
        LocalDate moisff = barreMois.getValue();

        String matriculeString = matricule.getText();
        String px_km = montantUnitaireKm.getText();

        Sqldb sql2 = new Sqldb();
        Connection c = sql2.connexionDb();
        Statement stmnt = c.createStatement();

        java.util.UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        if (moisHf1 != null & afL1 != null & afM1 != null) {
            String moisHf1String = moisHf1.toString();
            moisHf1String = moisHf1String.replace("-", ",");
            moisHf1String = "STR_TO_DATE(\"" + moisHf1String + "\", \"%Y,%m,%d\")";
            System.out.println(moisHf1String);
            String sql1 = "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES (" + moisHf1String
                    + ",'" + afL1 + "'," + afM1 + ",'" + uuidString + "')";
            stmnt.executeUpdate(sql1);

        } else {
            System.out.println("yes");
        }

        if (moisHf2 != null & afL2 != null & afM2 != null) {
            String moisHf2String = moisHf2.toString();
            moisHf2String = moisHf2String.replace("-", ",");
            moisHf2String = "STR_TO_DATE(\"" + moisHf2String + "\", \"%Y,%m,%d\")";
            System.out.println(moisHf2String);
            String sql1 = "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES (" + moisHf2String
                    + ",'" + afL2 + "'," + afM2 + ",'" + uuidString + "')";

            stmnt.executeUpdate(sql1);
        } else {
            System.out.println("yes");
        }

        String moisFfString = moisff.toString().replace("-", ",");
        String date = "STR_TO_DATE(\"" + moisFfString + "\", \"%Y,%m,%d %h,%i,%s\")";


        Integer pxId = 0 ;
        String reqPrixId = "SELECT prix_id FROM prix WHERE prix_date >= '2024-01-01 00:00:00'";
        System.out.println(reqPrixId);
        ResultSet resPrixId = Sqldb.executionRequete(reqPrixId);
        while(resPrixId.next()){
            pxId = Integer.valueOf(resPrixId.getString("prix_id"));
        }
        System.out.println(pxId);

        System.out.println(date);
        System.out.println(nuit);
        System.out.println(repasMid);
        System.out.println(km1);
        System.out.println(matriculeString);
        System.out.println(px_km);
        System.out.println(uuidString);
        System.out.println(pxId);

        String sql = "INSERT INTO fiche_frais (ff_mois,ff_qte_nuitees,  ff_qte_repas, ff_qte_km,vi_matricule,prix_km,ff_id,id_prix) VALUES "+
        "("+date+", "+nuit+","+repasMid+","+km1+", '"+matriculeString+"',"+px_km+",'"+uuidString+"',"+pxId+")";

        stmnt.executeUpdate(sql);

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
