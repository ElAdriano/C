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
import javafx.stage.Stage;

public class MapChooserController implements Initializable {

    @FXML
    private Button grid1;

    private Button selectedButton;

    @FXML
    private void handlePlayButton(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Templates/" + selectedButton.getId() +".fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) grid1.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleMapButtons(ActionEvent event) {
        selectedButton.getStyleClass().remove("selected");
        selectedButton = (Button) event.getSource();
        selectedButton.getStyleClass().add("selected");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedButton = grid1;
    }
}
