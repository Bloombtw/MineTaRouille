package universite_paris8.iut.ameimoun.minetarouillefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class MineTaRouilleAccueil extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Charger l'image de fond
        Image backgroundImage = new Image("file:fond.png"); // Assure-toi que le chemin est correct
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);  // Taille de la fenêtre
        backgroundView.setFitHeight(600);
        backgroundView.setPreserveRatio(false);

        // Créer les boutons
        Button nouvellePartie = new Button("Nouvelle Partie");
        Button quitter = new Button("Quitter");

        nouvellePartie.setPrefWidth(200);
        quitter.setPrefWidth(200);

        // Actions des boutons
        nouvellePartie.setOnAction(e -> System.out.println("Nouvelle partie lancée !"));
        quitter.setOnAction(e -> System.exit(0));

        // Layout des boutons
        VBox buttonBox = new VBox(20, nouvellePartie, quitter);
        buttonBox.setAlignment(Pos.CENTER);

        // Superposer l'image et les boutons
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, buttonBox);

        // Scène
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("MineTaRouille");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

