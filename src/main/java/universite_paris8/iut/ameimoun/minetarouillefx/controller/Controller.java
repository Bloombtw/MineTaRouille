package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    GridPane tileMap;

    private static final int LARGEUR_FENETRE = 1680;
    private static final int HAUTEUR_FENETRE = 1050;
    private Carte carte;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carte = new Carte();
        int[][] terrain = carte.creerTerrain(32, 58);
//        tileMap.setStyle("-fx-background-image: url('/img/ciel.png'); -fx-background-size: cover;");
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                ImageView tile = new ImageView(getImageAssociee(terrain[i][j]));
                tileMap.setPrefWidth(LARGEUR_FENETRE);
                tileMap.setPrefHeight(HAUTEUR_FENETRE);
                tile.setPreserveRatio(true);
                tileMap.add(tile, j, i);
            }
        }
    }






    private Image getImageAssociee(int id) {
        if (id == 0 ) {
            return new Image(getClass().getResourceAsStream("/img/ciel.png"));
        }
        if (id == 1) {
            return new Image(getClass().getResource("/img/pierre.png").toExternalForm());
        } else if (id == 2) {
            return new Image(getClass().getResource("/img/sable.png").toExternalForm());
        } else {
            return null;
        }
    }


}
