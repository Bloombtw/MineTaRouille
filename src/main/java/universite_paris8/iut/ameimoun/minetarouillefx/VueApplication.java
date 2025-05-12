package universite_paris8.iut.ameimoun.minetarouillefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VueApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VueApplication.class.getResource("vue1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1680, 1050);
        stage.setTitle("Mine Ta Rouille");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

