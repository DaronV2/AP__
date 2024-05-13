package fr.daron.louis;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import java.util.Collections;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import javafx.scene.control.MenuItem;

public class historiqueComptable extends Application {

    @FXML
    private ImageView doc1;

    String mdp;

    @FXML
    private TextField kilo;

    @FXML
    private TextField kiloMontant;

    @FXML
    private Button modifier;

    @FXML
    private TextField nuitee;

    @FXML
    private TextField nuiteeTot;

    @FXML
    private TextField repas;

    @FXML
    private TextField repasTot;

    @FXML
    private Button sauvegarde;

    @FXML
    private MenuButton menuHist;

    @FXML
    private MenuButton menuUser;

    @FXML
    private TextField totalKm;

    private String moisDebut;

    private String moisFin;

    private String matricule;

    private String matriculeUserSelected;

    private String userSelected;

    @FXML
    private Text px_nuit_txt;

    String prixnuit;
    String prixrepas;

    @FXML
    private Text px_repas_txt;

    private Map<String,String> userMatric = new HashMap<>();

    public void setUserMatric(Map<String, String> userMatric) {
        this.userMatric = userMatric;
    }

    public void insertUserMatric(String nomPrenom, String matricule){
        userMatric.put(nomPrenom,matricule);
    }

    @FXML
    void accueil(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void start(Stage arg0) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'start'");

    }
    // faire liste avec tous les ID

    @FXML
    private void modifier() {
        nuitee.setEditable(true);
        nuiteeTot.setEditable(true);
        repas.setEditable(true);
        repasTot.setEditable(true);
        kilo.setEditable(true);
        kiloMontant.setEditable(true);
    }

    @FXML
    private void sauvegarde() {
        nuitee.setEditable(false);
        nuiteeTot.setEditable(false);
        repas.setEditable(false);
        repasTot.setEditable(false);
        kilo.setEditable(false);
        kiloMontant.setEditable(false);
    }

    void remplirFiche(ResultSet res) throws SQLException {
        nuitee.setText(res.getString("ff_qte_nuitees"));
        repas.setText(res.getString("ff_qte_repas"));
        kilo.setText(res.getString("ff_qte_km"));
        kiloMontant.setText(res.getString("prix_km"));
        Integer nuiteeNb = Integer.valueOf(nuitee.getText());
        nuiteeTot.setText(String.valueOf(nuiteeNb * 80));
        Integer repasNb = Integer.valueOf(repas.getText());
        repasTot.setText(String.valueOf(repasNb * 29));
        Integer kmNb = Integer.valueOf(kilo.getText());
        Integer pxKm = Integer.valueOf(kiloMontant.getText());
        totalKm.setText(String.valueOf(kmNb * pxKm));
    }

    @FXML
    public void initialize() throws SQLException {
        test();
        handleAfficherVisiteurs();

        String sql = "SELECT `prix_nuit`,prix_repas FROM `prix`";
        ResultSet res = Sqldb.executionRequete(sql);

        if (res.next()){
            prixnuit = res.getString("prix_nuit");
            px_nuit_txt.setText(prixnuit);

            prixrepas = res.getString("prix_repas");
            px_repas_txt.setText(prixrepas);
        }

        ObservableList<MenuItem> item = menuHist.getItems();
        ObservableList<MenuItem> user = menuUser.getItems();
        item.forEach(menuItem -> {
            selectMois(menuItem);
            
        });
        user.forEach(menuUs ->{
            selectUser(menuUs);
        }
        );
    }

    void test() {
        LocalDate dateActuelle = LocalDate.now();

        // Formatteur pour afficher les noms des mois
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        List<MenuItem> items = new ArrayList<>();

        // Ajout des 12 derniers mois sous forme de MenuItem
        for (int i = 0; i < 12; i++) {
            LocalDate moisPrecedent = dateActuelle.minusMonths(i);
            String nomMois = moisPrecedent.format(formatter);
            MenuItem menuItem = new MenuItem(nomMois);
            System.out.println("la : "+moisPrecedent);
            items.add(menuItem);
        }
        Collections.reverse(items);
        menuHist.getItems().addAll(items);
    }

    // pour faire marcher dateString
    String dateString(String date, Integer jour) {
        String zero = "";
        if (jour < 10) {
            zero = "0";
        } else {
            zero = "";
        }
        LocalDate date1 = LocalDate.parse(zero + jour.toString() + " " + date,
                DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH));
        // Formater la date en chaîne au format "AAAA-MM-JJ"
        String resultat = date1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(resultat);
        return resultat;
    }

    private void selectMois(MenuItem item) {
        item.setOnAction(event -> {
            String dateString = item.getText();
            String debutMois = dateString(dateString, 01);
            this.moisDebut = debutMois;
            String finMois = dateString(dateString, 31);
            this.moisFin = finMois;
            System.out.println("Option " + item.getText() + " sélectionnée");
            String selectFevr = "SELECT ff_qte_nuitees, ff_qte_repas, ff_qte_km, prix_km FROM fiche_frais WHERE ff_mois <= '2024-03-31' AND ff_mois >= '2024-03-01' AND vi_matricule = '"
                    + this.matriculeUserSelected + "'; ";
            String reqEtat = "SELECT ef.ef_libelle FROM fiche_frais AS ff \n" + //
                    "JOIN etat_fiche AS ef ON ff.ef_id = ef.ef_id \n" + //
                    "WHERE ff.vi_matricule = \"" + this.matriculeUserSelected + "\" AND ff.ff_mois <= '" + finMois
                    + "' AND ff.ff_mois >= '" + debutMois + "';";
            try {
                ResultSet res = Sqldb.executionRequete(selectFevr);
                if (res.next()) {
                    remplirFiche(res);
                } else {
                    System.out.println("Pas de fiche pour ce mois");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void selectUser(MenuItem item){
        item.setOnAction(event -> {
            System.out.println("Utilisateur selectionné : "+item.getText());
            userSelected = item.getText();
            for (Map.Entry<String, String> entry : userMatric.entrySet()){
                String nom = entry.getKey();
                String matr = entry.getValue();
                if (nom.equals(userSelected)){
                    matriculeUserSelected = matr;
                    System.out.println(this.matriculeUserSelected);
                } else{
                    System.out.println("testsetstset");
                }
            }
        });
    }

    @FXML
    void handleAfficherVisiteurs() throws SQLException {
        // Création de la requête SQL
        String query = "SELECT vi_nom, vi_prenom, vi_matricule FROM visiteur";
        ResultSet resultSet = Sqldb.executionRequete(query);

        while (resultSet.next()) {
            String nom = resultSet.getString("vi_nom");
            String prenom = resultSet.getString("vi_prenom");
            matricule = resultSet.getString("vi_matricule");
            insertUserMatric(nom + " " +prenom, matricule);
            System.out.println(userMatric);
            MenuItem user = new MenuItem(nom + " " + prenom);
            menuUser.getItems().add(user);
        }
    }
}
