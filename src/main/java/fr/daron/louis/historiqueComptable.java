package fr.daron.louis;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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

    @FXML
    void accueil(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void pdf(MouseEvent event) throws IOException {
        System.out.println("marche");
        File file = new File("C:\\Users\\darke\\OneDrive\\Documents\\cv.pdf");
        HostServices hostServices = getHostServices();
        hostServices.showDocument(file.getAbsolutePath());
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
        ObservableList<MenuItem> item = menuHist.getItems();
        ObservableList<MenuItem> user = menuUser.getItems();
        item.forEach(menuItem -> {
            selectMois(menuItem);
        });
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
                    + utilisateur.getMatricule() + "'; ";
            String reqEtat = "SELECT ef.ef_libelle FROM fiche_frais AS ff \n" + //
                    "JOIN etat_fiche AS ef ON ff.ef_id = ef.ef_id \n" + //
                    "WHERE ff.vi_matricule = \"" + matricule + "\" AND ff.ff_mois <= '" + finMois
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

    @FXML
    void handleAfficherVisiteurs() throws SQLException {
        // Création de la requête SQL
        String query = "SELECT vi_nom, vi_prenom, vi_matricule FROM visiteur";
        ResultSet resultSet = Sqldb.executionRequete(query);

        while (resultSet.next()) {
            String nom = resultSet.getString("vi_nom");
            String prenom = resultSet.getString("vi_prenom");
            matricule = resultSet.getString("vi_matricule");
            System.out.println("Nom: " + nom + ", Prénom: " + prenom);
            System.out.println("matricule : " + matricule);
            MenuItem user = new MenuItem(nom + " " + prenom);
            menuUser.getItems().add(user);
        }
    }
}
