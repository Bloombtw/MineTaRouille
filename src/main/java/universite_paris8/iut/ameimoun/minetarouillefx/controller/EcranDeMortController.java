package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import universite_paris8.iut.ameimoun.minetarouillefx.MainApp;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import java.net.URL;
import java.util.ResourceBundle;



public class EcranDeMortController implements Initializable {

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // les commentaires en dessous sont à enlever si on veut utiliser une vidéo sur l'écran de mort.

//        var media = Loader.getMP4("/mp4/takeTheL.mp4");
//        var player = new javafx.scene.media.MediaPlayer(media);
//        player.setAutoPlay(true);
//        player.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaView.setMediaPlayer(player);
        messageMortImage.setImage(Loader.loadImage("/img/boutons/messageMort.png"));
        rejouerImage.setImage(Loader.loadImage("/img/boutons/rejouer.png"));
        quitterImage.setImage(Loader.loadImage("/img/boutons/quitter.png"));
    }

    @FXML
    private ImageView messageMortImage;

    @FXML
    private ImageView rejouerImage;

    @FXML
    private ImageView quitterImage;

    @FXML
    private MediaView mediaView;

    @FXML
    private AnchorPane overlayDeMort;

    @FXML
    private void handleQuitter() {
        MusiqueManager.getInstance().arreterMusique();
        System.out.println("Fermeture de l'application depuis l'écran de mort.");
        Platform.exit();
    }

    @FXML
    private void handleRejouer() {
        Parent root = Loader.load("/fxml/Map.fxml");
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


