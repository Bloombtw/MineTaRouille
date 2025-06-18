package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import universite_paris8.iut.ameimoun.minetarouillefx.MainApp;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

import java.net.URL;
import java.util.ResourceBundle;

public class EcranAccueilController implements Initializable {

    @FXML
    private ImageView backgroundView;

    @FXML
    private ImageView nouvellePartieImage;

    @FXML
    private ImageView quitterImage;


    /**
     * Initialise l'écran d'accueil en chargeant les images de fond et des boutons,
     * et en liant les actions des boutons aux méthodes correspondantes.
     **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backgroundView.setImage(Loader.loadImage(Chemin.FOND));
        backgroundView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                backgroundView.fitWidthProperty().bind(newScene.widthProperty());
                backgroundView.fitHeightProperty().bind(newScene.heightProperty());
            }
        });
        nouvellePartieImage.setImage(Loader.loadImage(Chemin.BOUTON_NOUVELLE_PARTIE));
        quitterImage.setImage(Loader.loadImage(Chemin.BOUTON_QUITTER));
        nouvellePartieImage.setOnMouseClicked(e -> {
                        System.out.println("Lancement d'une nouvelle partie depuis le bouton nouvelle Partie.");
                lancerNouvellePartie();
                });
        quitterImage.setOnMouseClicked(e -> {
            System.out.println("Fermeture de l'application depuis le bouton quitter.");
            Platform.exit();
        });

    }


    /**
     * Lance une nouvelle partie en chargeant la scène du jeu.
     * Si le chargement échoue, affiche une erreur dans la console.
     */
    @FXML
    private void lancerNouvellePartie() {
        try {
            FXMLLoader fxmlLoader = Loader.loadFXML(Chemin.FXML_MAP);
            Parent jeuRoot = fxmlLoader.load();
            Scene jeuScene = new Scene(jeuRoot, Constantes.LARGEUR_FENETRE, Constantes.HAUTEUR_FENETRE);
            MainApp.primaryStageGlobal.setScene(jeuScene);
            MainApp.primaryStageGlobal.setTitle("Mine Ta Rouille - Jeu");
            System.out.println("Chargement de Map.fxml réussi, nouvelle partie lancée.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Erreur lors du chargement de Map.fxml");
        }
    }

    @FXML
    private void quitter() {
        Platform.exit();
    }
}
