package worldofzuul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            window.setTitle("FEBEN: Fremtidens Energiløsninger Bør Etableres Nu");
            window.setResizable(false);
            window.centerOnScreen();
            window.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
