package universite_paris8.iut.ameimoun.minetarouillefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main1 extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main1.class.getResource("vue2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("LoupMouton");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

