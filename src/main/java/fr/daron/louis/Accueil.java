package fr.daron.louis;

//Importation des librairies nécessaires au bon fonctionnement du code 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class Accueil {

    // Importation de tout les champs FXML de l'application
    @FXML
    private Button ajterListView;

    @FXML
    private ListView<?> lsView;

    @FXML
    void addList(ActionEvent event) {
        lsView.getItems();
    }

}
