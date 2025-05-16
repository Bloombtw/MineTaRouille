package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class AccueilApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Charger l'image de fond
        Image backgroundImage = Loader.loadImage("/img/fond/fond.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(Constantes.LARGEUR_FENETRE);
        backgroundView.setFitHeight(Constantes.HAUTEUR_FENETRE);
        backgroundView.setPreserveRatio(false);

        // Créer les boutons
        Button nouvellePartie = new Button("Nouvelle Partie");
        Button quitter = new Button("Quitter");

        nouvellePartie.setPrefWidth(Constantes.TAILLE_BOUTON);
        quitter.setPrefWidth(Constantes.TAILLE_BOUTON);

        // Actions des boutons
        nouvellePartie.setOnAction(e -> System.out.println("Nouvelle partie lancée !"));
        quitter.setOnAction(e -> System.exit(0));

        // Layout des boutons
        VBox buttonBox = new VBox(20, nouvellePartie, quitter);
        buttonBox.setAlignment(Pos.CENTER);

        // Superposei mage et les boutons
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, buttonBox);

        // Scène
        Scene scene = new Scene(root, Constantes.LARGEUR_FENETRE, Constantes.HAUTEUR_FENETRE);

        primaryStage.setTitle("Mine Ta Rouille");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

