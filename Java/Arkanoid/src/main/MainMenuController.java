package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuController{

    @FXML
    private Pane mainPane;

    private String grid_name = "grid1";

    @FXML
    private void handlePlayButton(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Templates/MapChooser.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleExitButton(ActionEvent event) throws Exception {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void chooseGridToPlay(ActionEvent event) {
        grid_name = ((Button) event.getSource()).getId();
    }
}
