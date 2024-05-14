package fr.daron.louis;

//Importation des librairies nécessaires au bon fonctionnement du code 
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SecondaryController {

    // Importation de tout les champs FXML de l'application

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
        matricule.setText(utilisateur.getMatricule());
    }

    @FXML
    private DatePicker barreMois;

    @FXML
    private TextField montantUnitaireKm;

    @FXML
    private TextField nom;

    private void setNom(TextField nom) {
        nom.setText("noms");
        nom.setText(utilisateur.getNom());
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
    private Text px_nuit_txt;

    @FXML
    private Text px_repas_txt;

    @FXML
    private DatePicker barreMois1;

    @FXML
    private DatePicker barreMois11;

    @FXML
    private Text resultatRequete1;

    // Initialisation d'attributs de la classe SecondaryController 

    String idFicheSiExist;

    String prixnuit;
    String prixrepas;


    /* Méthode Initialize
        nom : initialize
        resultat : rien 
        objet de la méthode : La méthode initialize est une méthode de JavaFX qui se lance automatiquement au changement vers notre page,

    */
    @FXML
    void initialize() throws SQLException {
        setMatricule(matricule);
        setNom(nom);

        ficheExistante();

        String sql = "SELECT `prix_nuit`,prix_repas FROM `prix`";
        ResultSet res = Sqldb.executionRequete(sql);

        if (res.next()){
            prixnuit = res.getString("prix_nuit");
            px_nuit_txt.setText(prixnuit);

            prixrepas = res.getString("prix_repas");
            px_repas_txt.setText(prixrepas);
        }

        matricule.setEditable(false);
        nom.setEditable(false);
        barreMois.setEditable(false);
        nuitee.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int pxNuit = Integer.valueOf(prixnuit);
                double value = Double.parseDouble(newValue);
                totalNuitee.setText(String.valueOf(value * pxNuit));
                
            } catch (NumberFormatException e) {
                totalNuitee.setText("execp");
            }
        });

        repasMidi.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                System.out.println("test");
                int pxRepas = Integer.valueOf(prixrepas);
                double value = Double.parseDouble(newValue);
                totalRepasMidi.setText(String.valueOf(value * pxRepas));
            } catch (NumberFormatException e) {
                totalRepasMidi.setText("");
            } 
        });
    }

    boolean ficheExistante() throws SQLException{
        LocalDate premierJourDuMois = LocalDate.now().withDayOfMonth(1);
        LocalDate dernierJourDuMois = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String premierJourDuMoisStr = premierJourDuMois.format(formatter);
        String dernierJourDuMoisStr = dernierJourDuMois.format(formatter);
        String req = "SELECT ff_id FROM fiche_frais WHERE vi_matricule = '"+utilisateur.getMatricule()+"' AND ff_mois BETWEEN '"+ premierJourDuMoisStr +"' AND '"+dernierJourDuMoisStr +"';";
        try{
            ResultSet res = Sqldb.executionRequete(req);
            if (res.next()){
                idFicheSiExist = res.getString("ff_id");
                String req2 = "SELECT ff_mois, ff_qte_nuitees, ff_qte_repas, ff_qte_km, id_prix,prix_km FROM fiche_frais WHERE ff_id = '"+ idFicheSiExist +"';";
                ResultSet res2 = Sqldb.executionRequete(req2);
                if(res2.next()){
                    remplirfiche(res2);
                }
                return true;
            } else{
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    void remplirfiche(ResultSet res) throws SQLException{
        String moisff = res.getString("ff_mois");
        LocalDate moisffParse = LocalDate.parse(moisff);
        barreMois.setValue(moisffParse);

        nuitee.setText(res.getString("ff_qte_nuitees"));
        repasMidi.setText(res.getString("ff_qte_repas"));
        km.setText(res.getString("ff_qte_km"));
        montantUnitaireKm.setText(res.getString("prix_km"));
        Integer fkPrix = Integer.valueOf(res.getString("id_prix"));

        String reqPrix = "SELECT prix_nuit, prix_repas FROM prix WHERE prix_id = "+fkPrix+";";
        ResultSet resPrix = Sqldb.executionRequete(reqPrix);
        if (resPrix.next()){
            px_nuit_txt.setText(resPrix.getString("prix_nuit"));
            px_repas_txt.setText(resPrix.getString("prix_repas"));
        }

        String reqHf = "SELECT hf_date, hf_libelle, hf_montant FROM hors_forfait WHERE ff_id = '"+idFicheSiExist+"';";
        System.out.println(reqHf);
        ResultSet resHf = Sqldb.executionRequete(reqHf);
        if (resHf.next()){
            String moisHf1 = resHf.getString("hf_date");
            LocalDate moisHf1Parse = LocalDate.parse(moisHf1);
            barreMois1.setValue(moisHf1Parse);

            afLibelle1.setText(resHf.getString("hf_libelle"));
            afMontant1.setText(resHf.getString("hf_montant"));

            if (resHf.next()){
                resHf.next();

                String moisHf2 = resHf.getString("hf_date");
                LocalDate moisHf2Parse = LocalDate.parse(moisHf2);
                barreMois11.setValue(moisHf2Parse);

                afLibelle2.setText(resHf.getString("hf_libelle"));
                afMontant2.setText(resHf.getString("hf_montant"));
            }
        }
    }

    @FXML
    void envoyer(ActionEvent event) throws SQLException, InterruptedException {
        String nuit = nuitee.getText();
        String repasMid = repasMidi.getText();
        String km1 = km.getText();
        String afL1 = afLibelle1.getText();
        String afM1 = afMontant1.getText();
        String afL2 = afLibelle2.getText();
        String afM2 = afMontant2.getText();

        LocalDate moisff = barreMois.getValue();

        String matriculeString = matricule.getText();
        String px_km = montantUnitaireKm.getText();

        java.util.UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();


        String moisFfString = moisff.toString().replace("-", ",");
        String date = "STR_TO_DATE(\"" + moisFfString + "\", \"%Y,%m,%d %h,%i,%s\")";


        Integer pxId = 0 ;
        String reqPrixId = "SELECT prix_id FROM prix WHERE prix_date >= '2024-01-01 00:00:00'";
        System.out.println(reqPrixId);
        ResultSet resPrixId = Sqldb.executionRequete(reqPrixId);
        while(resPrixId.next()){
            pxId = Integer.valueOf(resPrixId.getString("prix_id"));
        }

        String sql = "INSERT INTO fiche_frais (ff_mois,ff_qte_nuitees,  ff_qte_repas, ff_qte_km,vi_matricule,prix_km,ff_id,id_prix) VALUES "+
        "("+date+", "+nuit+","+repasMid+","+km1+", '"+matriculeString+"',"+px_km+",'"+uuidString+"',"+pxId+")";
        System.out.println(sql);
        Sqldb.executionUpdate(sql);

        String afD1 = "";
        if (afD1 == null & afL1 == null & afM1 == null) {

            String sql1 = String.format("INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant) VALUES (%s,%s,%s)",
                    afD1, afL1, afM1);
            Sqldb.executionUpdate(sql1);
        } else {
            System.out.println("yes2");
        }

        String afD2 = "";
        if (afD2 == null & afL2 == null & afM2 == null) {
            String ffid = "";
            String sql1 = String.format(
                    "INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES (%s,%s,%s,%s)", afD1,
                    afL1, afM1, ffid);
            Sqldb.executionUpdate(sql1);
        } else {
            System.out.println("yes2");
        }

    }

    @FXML
    void switchAccueil(ActionEvent event) throws IOException, SQLException {

        App.setRoot("accueilVisiteurs");
    }
}
