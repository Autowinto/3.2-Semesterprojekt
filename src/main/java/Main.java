package worldofzuul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import worldofzuul.model.Game;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            window.setScene(scene);
            window.setTitle("World of Zuul Projektspil");
            window.show();
        } catch (Exception e) {
            System.out.println(e);
        }

        Game game = new Game();
        game.play();
    }
}
