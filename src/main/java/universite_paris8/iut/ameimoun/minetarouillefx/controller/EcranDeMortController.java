package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.MainApp;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import java.net.URL;
import java.util.ResourceBundle;



public class EcranDeMortController implements Initializable {

    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageMortImage.setImage(Loader.loadImage(Chemin.BOUTON_MESSAGE_MORT));
        rejouerImage.setImage(Loader.loadImage(Chemin.BOUTON_REJOUER));
        quitterImage.setImage(Loader.loadImage(Chemin.BOUTON_QUITTER));
    }

    @FXML
    private ImageView messageMortImage;

    @FXML
    private ImageView rejouerImage;

    @FXML
    private ImageView quitterImage;

    @FXML
    private void handleQuitter() {
        MusiqueManager.getInstance().arreterMusique();
        Platform.exit();
    }

    @FXML
    /**
     * Gère le clic sur le bouton "Rejouer".
     * Charge la scène de jeu et réinitialise l'état du jeu.
     */
    private void handleRejouer() {
        Parent root = Loader.load(Chemin.FXML_MAP);
        if (root != null) {
            MainApp.primaryStageGlobal.setScene(new Scene(root, Constantes.LARGEUR_FENETRE, Constantes.HAUTEUR_FENETRE));
            MainApp.primaryStageGlobal.setTitle("Mine Ta Rouille - Jeu");
            System.out.println("Nouvelle partie relancée depuis l’écran de mort");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement de la scène");
            alert.show();
        }
    }
}


