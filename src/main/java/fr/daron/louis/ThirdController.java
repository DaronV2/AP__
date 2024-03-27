package fr.daron.louis;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.checkerframework.checker.units.qual.m;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ThirdController extends Application {

    @FXML
    private MenuItem aouItem;

    @FXML
    private MenuItem avrItem;

    @FXML
    private MenuItem decItem;

    @FXML
    private MenuItem fevItem;

    @FXML
    private MenuItem janvItem;

    @FXML
    private MenuItem juilItem;

    @FXML
    private MenuItem juinItem;

    @FXML
    private MenuItem maiItem;

    @FXML
    private MenuItem marsItem;

    @FXML
    private MenuButton menuMois;

    @FXML
    private TextField montantKm;

    @FXML
    private MenuItem novItem;

    @FXML
    private TextField nuitee;

    @FXML
    private MenuItem octItem;

    @FXML
    private TextField qteKm;

    @FXML
    private TextField repasMid;

    @FXML
    private MenuItem septItem;

    @FXML
    private TextField totalKm;

    @FXML
    private TextField totalNuitee;

    @FXML
    private TextField totalRepasMid;


    String mdp;


    MenuItem[] tabItems = {};
    
    @FXML
    public void initialize() throws SQLException{
        selectMois(janvItem);
        selectMois(fevItem);
        selectMois(marsItem);
        selectMois(avrItem);
        selectMois(maiItem);
        selectMois(juinItem);
        selectMois(juilItem);
        selectMois(aouItem);
        selectMois(septItem);
        selectMois(octItem);
        selectMois(novItem);
        selectMois(decItem);
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
        Integer pxKm = Integer.valueOf(montantKm.getText());
        totalKm.setText(String.valueOf(kmNb*pxKm));
    }
    
    private void selectMois(MenuItem item){
        item.setOnAction(event -> {
            System.out.println("Option "+item.getText()+" sélectionnée");
            String selectFevr = "SELECT ff_qte_nuitees, ff_qte_repas, ff_qte_km, prix_km FROM fiche_frais WHERE ff_mois <= '2024-03-31' AND ff_mois >= '2024-03-01' AND vi_matricule = '"+utilisateur.matricule+"'; ";
            try {
                ResultSet res = Sqldb.executionRequete(selectFevr);
                if (res.next()){
                    remplirFiche(res);
                }else{
                    System.out.println("Pas de fiche pour ce mois");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        });
    }

    void remplirDeroulant(){
        
    }

    @Override
    public void start(Stage arg0) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'start'");

    }
}
