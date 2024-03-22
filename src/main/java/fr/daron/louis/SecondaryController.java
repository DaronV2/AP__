package fr.daron.louis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private void setMatricule(TextField matricule) {
        matricule.setEditable(true);
        matricule.setText(utilisateur.matricule);
    }

    @FXML
    private DatePicker barreMois;

    /*public void setMois(DatePicker mois) {
        String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        mois.setText("00-00-0000");
        mois.setText(dateTime.toString());
    }*/

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
    void initialize() {
        setMatricule(matricule);
        setNom(nom);
        //setMois(mois);

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
        String afD1 = afDate1.getText();
        String afL1 = afLibelle1.getText();
        String afM1 = afMontant1.getText();
        String afD2 = afDate2.getText();
        String afL2 = afLibelle2.getText();
        String afM2 = afMontant2.getText();
        LocalDate moisString = barreMois.getValue();
        String matriculeString = matricule.getText();
        String px_km = montantUnitaireKm.getText();

        Sqldb sql2 = new Sqldb();
        // Connection c = sql2.connexionDb();
        // Statement stmnt = c.createStatement();
        Connection c = sql2.connexionDb();
        Statement stmnt = c.createStatement();

        String jointure = String.format("SELECT vi_matricule FROM visiteur WHERE cr_identifiant = '%s'",utilisateur.identfiant);
        ResultSet resultats43 = stmnt.executeQuery(jointure);
        resultats43.next();
        matriculeString = resultats43.getNString("vi_matricule");
        System.out.println(moisString);

        String date = "STR_TO_DATE(\"2017,8,14 10,40,10\", \"%Y,%m,%d %h,%i,%s\")";

        String sql = "INSERT INTO fiche_frais (ff_mois,ff_qte_nuitees, ff_total_nuitees, ff_qte_repas, ff_total_repas, ff_qte_km,vi_matricule,prix_km) VALUES ("+date+", "+nuit+","+totalNuit+","+repasMid+","+totalRepas+","+km1+", '"+matriculeString+"',"+px_km+")";
        System.out.println(sql);
        stmnt.executeUpdate(sql);

        String ffid ;
        String getId =String.format("SELECT ff_id from fiche_frais WHERE ff_mois = %s AND vi_matricule = '%s'",moisString,matriculeString);
        ResultSet resultatId = stmnt.executeQuery(getId);
        resultatId.next();
        ffid = resultatId.getNString("ff_id");
        System.out.println(ffid);



        //Faire d'abord les requetes d'hors forfait et ensuite la fiche frais afin dr'avoir l'id
        if(afD1== null & afL1 == null & afM1 == null){
            
            String sql1 = String.format("INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES (%s,%s,%s)",afD1, afL1, afM1,ffid);
            stmnt.executeUpdate(sql1);
        }else{
            System.out.println("yes");
        }

        if(afD2== null & afL2 == null & afM2 == null){
            String sql1 = String.format("INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES (%s,%s,%s,%s)",afD1, afL1, afM1,ffid);
            stmnt.executeUpdate(sql1);
        }else{
            System.out.println("yes");
        }

    }

    @FXML
    void switchAccueil(ActionEvent event) throws IOException, SQLException {
        App.setRoot("primary");
    }
}
