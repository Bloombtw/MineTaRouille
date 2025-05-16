package universite_paris8.iut.ameimoun.minetarouillefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

import java.util.Objects;

public class MainApp extends Application {


    @Override
    public void start(Stage primaryStage) {
        // Image de fond
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/img/fond/fond.png")).toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false);

        // Conteneur principal
        StackPane root = new StackPane();

        // Lier l'image aux dimensions de la fenêtre
        backgroundView.fitWidthProperty().bind(root.widthProperty());
        backgroundView.fitHeightProperty().bind(root.heightProperty());

        // Boutons
        Button nouvellePartie = new Button("Nouvelle Partie");
        Button quitter = new Button("Quitter");
        nouvellePartie.setPrefWidth(200);
        quitter.setPrefWidth(200);

        styleBouton(nouvellePartie);
        styleBouton(quitter);

        VBox buttonBox = new VBox(20, nouvellePartie, quitter);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(backgroundView, buttonBox);

        // Scène d’accueil
        Scene accueilScene = new Scene(root, Constantes.LARGEUR_FENETRE, Constantes.HAUTEUR_FENETRE);
        primaryStage.setTitle("Mine Ta Rouille - Accueil");
        primaryStage.setScene(accueilScene);
        primaryStage.show();

        // Action Quitter
        quitter.setOnAction(e -> System.exit(0));

        // Action Nouvelle Partie
        nouvellePartie.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/ameimoun/fxml/Map.fxml"));
                Scene jeuScene = new Scene(fxmlLoader.load(), Constantes.LARGEUR_FENETRE, Constantes.HAUTEUR_FENETRE);
                primaryStage.setScene(jeuScene);
                primaryStage.setTitle("Mine Ta Rouille - Jeu");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Erreur lors du chargement de Map.fxml");
            }
        });
    }

    private void styleBouton(Button bouton) {
        bouton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        bouton.setOnMouseEntered(e -> {
            bouton.setScaleX(1.1);
            bouton.setScaleY(1.1);
            bouton.setStyle("-fx-background-color: transparent; -fx-text-fill: #cccccc; -fx-font-size: 18px;");
        });
        bouton.setOnMouseExited(e -> {
            bouton.setScaleX(1.0);
            bouton.setScaleY(1.0);
            bouton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
