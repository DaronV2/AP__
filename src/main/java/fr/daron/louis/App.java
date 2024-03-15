package fr.daron.louis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stg;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, NoSuchAlgorithmException {
        scene = new Scene(loadFXML("primary"), 1280, 720);
        stage.setTitle("Application Galaxy Swiss Bourdin");
        stage.setResizable(true);
        stage.getIcons().add(new Image("file:src/main/resources/images/logo1.png"));
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(String FXML) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(FXML));
        stg.getScene().setRoot(pane);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}