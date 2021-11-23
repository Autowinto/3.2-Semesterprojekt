package worldofzuul.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import worldofzuul.Main;

import java.io.IOException;

public class MainMenuController {
    @FXML
    StackPane root;
    private Stage stage;
    private Scene scene;

    @FXML
    private void startGame(ActionEvent event) {
        try {
            root = FXMLLoader.load(MainMenuController.class.getResource("/Game.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
