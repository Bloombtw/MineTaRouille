package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import universite_paris8.iut.ameimoun.minetarouillefx.MainApp;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EcranAccueilController implements Initializable {

    @FXML
    private ImageView backgroundView;

    @FXML
    private VBox buttonBox;

    @FXML
    private ImageView nouvellePartieImage;

    @FXML
    private ImageView quitterImage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backgroundView.setImage(Loader.loadImage("/img/fond/fond.png"));
        backgroundView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                backgroundView.fitWidthProperty().bind(newScene.widthProperty());
                backgroundView.fitHeightProperty().bind(newScene.heightProperty());
            }
        });
        nouvellePartieImage.setImage(Loader.loadImage("/img/boutons/nouvellePartie.png"));
        quitterImage.setImage(Loader.loadImage("/img/boutons/quitter.png"));
        nouvellePartieImage.setOnMouseClicked(e -> lancerNouvellePartie());
        quitterImage.setOnMouseClicked(e -> Platform.exit());

    }


    @FXML
    private void lancerNouvellePartie() {
        try {
            FXMLLoader fxmlLoader = Loader.loadFXML("/fxml/Map.fxml");
            Parent jeuRoot = fxmlLoader.load();
            Scene jeuScene = new Scene(jeuRoot, Constantes.LARGEUR_FENETRE, Constantes.HAUTEUR_FENETRE);
            MainApp.primaryStageGlobal.setScene(jeuScene);
            MainApp.primaryStageGlobal.setTitle("Mine Ta Rouille - Jeu");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Erreur lors du chargement de Map.fxml");
        }
    }

    @FXML
    private void quitter() {
        Platform.exit();
    }
}
