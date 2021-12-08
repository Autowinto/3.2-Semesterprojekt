package worldofzuul.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import worldofzuul.model.Power;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    @FXML
    private Label endGameLabel;
    @FXML
    Pane root;
    private Stage stage;
    private Scene scene;

    public void changeEndGameLabel(){
        endGameLabel.setText("Du fik opnået " + Power.getPower()+ "% ud af 100%");
    }

    public void restartGame(ActionEvent event){
        try {
            root = FXMLLoader.load(MainMenuController.class.getResource("/Lys.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            Power.resetPower();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void exitGame(){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeEndGameLabel();
    }
}
