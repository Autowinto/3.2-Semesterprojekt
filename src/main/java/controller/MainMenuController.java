package worldofzuul.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuController {
    @FXML
    Pane root;
    private Stage stage;
    private Scene scene;

    @FXML
    private void startGame(ActionEvent event) {
        try {
            root = FXMLLoader.load(MainMenuController.class.getResource("/Lys.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
