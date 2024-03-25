package fr.daron.louis;

import java.io.IOException;
import java.sql.Connection;
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
    void initialize() {
        setMatricule(matricule);
        setNom(nom);

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
        LocalDate moisHf2 = barreMois11.getValue();
        LocalDate moisff = barreMois.getValue();

        String matriculeString = matricule.getText();
        String px_km = montantUnitaireKm.getText();

        Sqldb sql2 = new Sqldb();
        Connection c = sql2.connexionDb();
        Statement stmnt = c.createStatement();

        java.util.UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();


        if(moisHf1!= null & afL1 != null & afM1 != null){
            String moisHf1String = moisHf1.toString();
            moisHf1String = moisHf1String.replace("-", ",");
            moisHf1String = "STR_TO_DATE(\""+moisHf1String+"\", \"%Y,%m,%d\")";
            System.out.println(moisHf1String);
            String sql1 = "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES ("+moisHf1String+",'"+afL1+"',"+afM1+",'"+uuidString+"')";
            stmnt.executeUpdate(sql1);

        }else{
            System.out.println("yes");
        }

        if(moisHf2!= null & afL2 != null & afM2 != null){
            String moisHf2String = moisHf2.toString();
            moisHf2String = moisHf2String.replace("-", ",");
            moisHf2String = "STR_TO_DATE(\""+moisHf2String+"\", \"%Y,%m,%d\")";
            System.out.println(moisHf2String);
            String sql1 = "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES ("+moisHf2String+",'"+afL2+"',"+afM2+",'"+uuidString+"')";
            stmnt.executeUpdate(sql1);
        }else{
            System.out.println("yes");
        }
       
        String moisFfString = moisff.toString().replace("-", ",");
        String date = "STR_TO_DATE(\""+moisFfString+"\", \"%Y,%m,%d %h,%i,%s\")";

        String sql = "INSERT INTO fiche_frais (ff_mois,ff_qte_nuitees, ff_total_nuitees, ff_qte_repas, ff_total_repas, ff_qte_km,vi_matricule,prix_km,ff_id) VALUES ("+date+", "+nuit+","+totalNuit+","+repasMid+","+totalRepas+","+km1+", '"+matriculeString+"',"+px_km+",'"+uuidString+"')";
        stmnt.executeUpdate(sql);





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
