package fr.daron.louis;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ThirdController extends Application {

    @FXML
    private Button btnClot;

    @FXML
    private Button btnModif;

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker dateHf1;

    @FXML
    private DatePicker dateHf2;

    @FXML
    private Text etatFiche;

    @FXML
    private TextField libHf1;

    @FXML
    private TextField libHf2;

    @FXML
    private MenuButton menuMois;

    @FXML
    private TextField montHf1;

    @FXML
    private TextField montHf2;

    @FXML
    private TextField montantKm;

    @FXML
    private TextField nuitee;

    @FXML
    private TextField qteKm;

    @FXML
    private TextField repasMid;

    @FXML
    private TextField totalKm;

    @FXML
    private TextField totalNuitee;

    @FXML
    private TextField totalRepasMid;

    public String moisDebut;

    public String moisFin;

    public String matricule;

    String mdp;


    String idFiche;




    MenuItem[] tabItems = {};
    
    @FXML
    public void initialize() throws SQLException{
        utilisateur ut = new utilisateur();
        matricule = utilisateur.matricule;
        modifRien();
        test();
        ObservableList<MenuItem> item = menuMois.getItems();
        item.forEach(menuItem ->{
            selectMois(menuItem);
        });
        }

    @FXML
    void accueil(ActionEvent event) throws IOException {

        App.setRoot("primary");
    }

    void remplirFiche(ResultSet res) throws SQLException{
        nuitee.setText(res.getString("ff_qte_nuitees"));
        repasMid.setText(res.getString("ff_qte_repas"));
        qteKm.setText(res.getString("ff_qte_km"));
        montantKm.setText(res.getString("prix_km"));
        Integer nuiteeNb = Integer.valueOf(nuitee.getText());
        totalNuitee.setText(String.valueOf(nuiteeNb*80));
        Integer repasNb = Integer.valueOf(repasMid.getText());
        totalRepasMid.setText(String.valueOf(repasNb*29));
        Integer kmNb = Integer.valueOf(qteKm.getText());
        double pxKm = Double.valueOf(montantKm.getText());
        totalKm.setText(String.valueOf(kmNb*pxKm));
        String reqHf = "SELECT hf_date,hf_libelle,hf_montant FROM gsb_etudiants.hors_forfait WHERE ff_id = '"+this.idFiche+"';";
        ResultSet resHf = Sqldb.executionRequete(reqHf);
        if (resHf.next()){
            dateHf1.setValue(LocalDate.parse(resHf.getString("hf_date").toString(),DateTimeFormatter.ISO_DATE));
            libHf1.setText(resHf.getString("hf_libelle"));
            montHf1.setText(resHf.getString("hf_montant"));
            if(resHf.next()){
                dateHf2.setValue(LocalDate.parse(resHf.getString("hf_date").toString(),DateTimeFormatter.ISO_DATE));
                libHf2.setText(resHf.getString("hf_libelle"));
                montHf2.setText(resHf.getString("hf_montant"));
            }
        }
        
        
    
    }
    
    private void selectMois(MenuItem item){
        item.setOnAction(event -> {
            String dateString = item.getText();
            String debutMois = dateString(dateString,01);
            this.moisDebut = debutMois;
            String finMois = dateString(dateString, 31);
            this.moisFin = finMois;
            System.out.println("Option "+item.getText()+" sélectionnée");
            String selectFevr = "SELECT ff_qte_nuitees, ff_qte_repas, ff_qte_km, prix_km,ff_id,ef_id FROM fiche_frais WHERE ff_mois BETWEEN '"+moisDebut+"' AND '"+moisFin+"' AND vi_matricule = '"+utilisateur.matricule+"'; ";
            System.out.println(selectFevr);
            try {
                ResultSet res = Sqldb.executionRequete(selectFevr);
                if (res.next()){
                    remplirFiche(res);
                    this.idFiche =  res.getString("ff_id");
                    System.out.println(selectFevr);
                    String etat = res.getString("ef_id");
                    System.out.println(etat);
                }else{
                    modifRien();
                    System.out.println("Pas de fiche pour ce mois");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try{
                String reqEtat = "SELECT ef.ef_libelle FROM fiche_frais AS ff \n" + //
                                "JOIN etat_fiche AS ef ON ff.ef_id = ef.ef_id \n" + //
                                "WHERE ff.ff_id = '"+this.idFiche+"';";

                    ResultSet resEtat = Sqldb.executionRequete(reqEtat);
                    if(resEtat.next()){
                        if(!resEtat.getString("ef_libelle").equals("créée")){
                            modifNon();
                        }else{
                            modifOk();
                        }
                        etatFiche.setText("Etat de la fiche : "+resEtat.getString("ef_libelle"));
                    }else{
                        System.out.println("bug");
                    }
                
                
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }


    String dateString(String date,Integer jour){
        String zero = "";
        if (jour<10){
            zero ="0";
        }else{
            zero = "";
        }
        LocalDate date1 = LocalDate.parse( zero +jour.toString()+ " " + date, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH));
        // Formater la date en chaîne au format "AAAA-MM-JJ"
        String resultat = date1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(resultat);
        return resultat;
    }


    void test() throws SQLException{
        LocalDate dateActuelle = LocalDate.now();

        // Formatteur pour afficher les noms des mois
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        List<MenuItem> items = new ArrayList<>();

        // Ajout des 12 derniers mois sous forme de MenuItem
        for (int i = 0; i < 12; i++) {
            LocalDate moisPrecedent = dateActuelle.minusMonths(i);
            String nomMois = moisPrecedent.format(formatter);
            MenuItem menuItem = new MenuItem(nomMois);
            items.add(menuItem);
             
        }
        Collections.reverse(items);
        menuMois.getItems().addAll(items);
    }
    
    @FXML
    void cloturer(ActionEvent event) throws SQLException {  
        LocalDate dateajd =  LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateformat = dateajd.format(format);

        String clot = "UPDATE fiche_frais SET ff_date_cloture = '"+ dateformat +"' AND ef_id = 2 WHERE ff_id = '"+idFiche+"'; ";
        System.out.println(clot);
        Sqldb.executionUpdate(clot);
        System.out.println("Cloturée ");
        modifNon();
    }

    @FXML
    void sauvegarder(ActionEvent event) throws SQLException {
        Integer nbNuitee = Integer.valueOf(nuitee.getText());
        Integer nbRepas = Integer.valueOf(repasMid.getText());
        Integer quanteKm = Integer.valueOf(qteKm.getText());
        Integer cleEtrangPx = 1;
        String update = "UPDATE fiche_frais SET ff_qte_nuitees = "+nbNuitee+" ,ff_qte_repas = "+nbRepas+" ,ff_qte_km = "+quanteKm+" ,prix_km = "+cleEtrangPx+"\n" + //
                        "WHERE ff_id = '"+this.idFiche+"'; ";
        System.out.println(update);
        System.out.println(Sqldb.executionUpdate(update));
    }

    @FXML
    void modif(ActionEvent event) {
        List<TextField> liste = new ArrayList<>(List.of(montantKm,nuitee,qteKm,repasMid,totalKm,totalNuitee,totalRepasMid));
        for (TextField element : liste){
            element.setEditable(true);
        }
    }

    void modifNon() {
        List<TextField> liste = new ArrayList<>(List.of(montantKm,nuitee,qteKm,repasMid,totalKm,totalNuitee,totalRepasMid));
        for (TextField element : liste){
            element.setEditable(false);
        }
        btnModif.setDisable(true);
        btnClot.setDisable(true);
        btnSave.setDisable(true);
    }

    
    void modifRien() {
        List<TextField> liste = new ArrayList<>(List.of(montantKm,nuitee,qteKm,repasMid,totalKm,totalNuitee,totalRepasMid,montHf1,montHf2,libHf1,libHf2));
        for (TextField element : liste){
            element.setText("");
        }
        dateHf1.setValue(null);
        dateHf2.setValue(null);
        etatFiche.setText("Etat de la fiche : n'existe pas ");
    }

    void modifOk() {
        List<TextField> liste = new ArrayList<>(List.of(montantKm,nuitee,qteKm,repasMid,totalKm,totalNuitee,totalRepasMid));
        for (TextField element : liste){
            element.setEditable(true);
        }
        System.out.println("modification ok");
        btnModif.setDisable(false);
        btnClot.setDisable(false);
        btnSave.setDisable(false);
    }

    @Override
    public void start(Stage arg0) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'start'");

    }
}
