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

    @FXML
    private void startGame(ActionEvent event) {
        try {
            root = FXMLLoader.load(MainMenuController.class.getResource("/Lys.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
