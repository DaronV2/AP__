package fr.daron.louis;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

}
