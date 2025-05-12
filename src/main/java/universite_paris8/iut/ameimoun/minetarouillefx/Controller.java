package universite_paris8.iut.ameimoun.minetarouillefx;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private Carte carte;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carte = new Carte();
        int[][] terrain = carte.creerTerrain(32,32);
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                ImageView tile = new ImageView(getImageAssociee(terrain[i][j]));
                tile.setFitWidth(24);
                tile.setFitHeight(24);
                tileMap.add(tile, j, i);
            }
        }

    }

    @FXML
    GridPane tileMap;

    private Image getImageAssociee(int id) {
        if (id == 0 ) {
            return new Image(getClass().getResource("/img/sable.jpg").toExternalForm());
        } else if (id == 1 ) {
            return new Image(getClass().getResource("/img/pierre.jpg").toExternalForm());
        } else return null;
    }
}
