package fr.daron.louis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class Accueil {

    @FXML
    private Button ajterListView;

    @FXML
    private ListView<?> lsView;

    @FXML
    void addList(ActionEvent event) {
        lsView.getItems();
    }

}

// kk,gkf,fg
