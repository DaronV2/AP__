package fr.daron.louis;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ThirdController extends Application {

    @FXML
    private ImageView doc1;

    String mdp;

    @FXML
    void accueil(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    

    @Override
    public void start(Stage arg0) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'start'");

    }

}
