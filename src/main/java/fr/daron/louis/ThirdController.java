package fr.daron.louis;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ThirdController extends Application {

    @FXML
    private ImageView doc1;

    String mdp;

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

}
