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

public class VueApplicaton extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Charger l'image de fond
        Image backgroundImage = new Image(getClass().getResource("/img/fond.png").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false); // Permet l'étirement complet

        // Créer la racine
        StackPane root = new StackPane();

        // Lier les dimensions de l'image à celles de la fenêtre
        backgroundView.fitWidthProperty().bind(root.widthProperty());
        backgroundView.fitHeightProperty().bind(root.heightProperty());

        // Créer les boutons
        Button nouvellePartie = new Button("Nouvelle Partie");
        Button quitter = new Button("Quitter");

        nouvellePartie.setPrefWidth(200);
        quitter.setPrefWidth(200);

        //Design des boutons
        nouvellePartie.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        nouvellePartie.setOnMouseEntered(e -> {
            nouvellePartie.setScaleX(1.1);
            nouvellePartie.setScaleY(1.1);
            nouvellePartie.setStyle("-fx-background-color: transparent; -fx-text-fill: #cccccc; -fx-font-size: 18px;");
        });
        nouvellePartie.setOnMouseExited(e -> {
            nouvellePartie.setScaleX(1.0);
            nouvellePartie.setScaleY(1.0);
            nouvellePartie.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        });

        quitter.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        quitter.setOnMouseEntered(e -> {
            quitter.setScaleX(1.1);
            quitter.setScaleY(1.1);
            quitter.setStyle("-fx-background-color: transparent; -fx-text-fill: #cccccc; -fx-font-size: 18px;");
        });
        quitter.setOnMouseExited(e -> {
            quitter.setScaleX(1.0);
            quitter.setScaleY(1.0);
            quitter.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        });



        // Action bouton "Nouvelle Partie"
        nouvellePartie.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vue1.fxml"));
                if (fxmlLoader.getLocation() == null) {
                    System.out.println("Fichier FXML non trouvé");
                    return;
                }
                Scene gameScene = new Scene(fxmlLoader.load(), 1680, 1050);
                primaryStage.setScene(gameScene);
                primaryStage.setTitle("Mine Ta Rouille - Jeu");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Erreur lors du chargement de vue1.fxml");
            }
        });

        // Action bouton "Quitter"
        quitter.setOnAction(e -> System.exit(0));

        // Layout des boutons
        VBox buttonBox = new VBox(20, nouvellePartie, quitter);
        buttonBox.setAlignment(Pos.CENTER);

        // Superposer l'image et les boutons
        root.getChildren().addAll(backgroundView, buttonBox);

        // Scène d'accueil
        Scene accueilScene = new Scene(root, 1680, 1050);
        primaryStage.setTitle("Mine Ta Rouille - Accueil");
        primaryStage.setScene(accueilScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

