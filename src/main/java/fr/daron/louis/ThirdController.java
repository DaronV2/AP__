package fr.daron.louis;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


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
    private TextField totalNuitee;

    @FXML
    private TextField totalRepasMid;


    String mdp;

    
    @FXML
    public void initialize() throws SQLException{
        janvItem.setOnAction(event -> {
            System.out.println("Option 1 sélectionnée");
            String selectJanvier = "SELECT ff_qte_nuitees, ff_qte_repas, ff_qte_km, prix_km FROM fiche_frais WHERE ff_mois <= '2024-03-31' AND ff_mois >= '2024-03-01' AND vi_matricule = '"+utilisateur.matricule+"'; ";
            try {
                ResultSet res = Sqldb.executionRequete(selectJanvier);
                if(res.next()){
                    nuitee.setText(res.getNString("ff_qte_nuitees"));
                    
                    totalNuitee.setText();

                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    @FXML
    void accueil(ActionEvent event) throws IOException {

        App.setRoot("primary");
    }

    

    @Override
    public void start(Stage arg0) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'start'");

    }
}
