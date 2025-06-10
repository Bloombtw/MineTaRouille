package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireCraft;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

public class VueCraft {
    private final GestionnaireCraft gestionnaireCraft;
    private final Item[][] grille = new Item[3][3];
    private Label resultatLabel;
    private final AnchorPane rootPane;
    private AnchorPane craftPane;

    public VueCraft(GestionnaireCraft gestionnaireCraft, AnchorPane rootPane) {
        this.gestionnaireCraft = gestionnaireCraft;
        this.rootPane = rootPane;
    }

    public void showCraftWindow() {
        craftPane = new AnchorPane();
        craftPane.setPrefSize(300, 300);

        // Image en arrière-plan
        ImageView background = new ImageView(Loader.loadImage(Chemin.CRAFT_BACKGROUND));
        background.setFitWidth(300);
        background.setFitHeight(300);

        GridPane grillePane = new GridPane();
        grillePane.setHgap(5);
        grillePane.setVgap(5);
        grillePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Image image;
                if (grille[i][j] != null) {
                    image = Loader.loadImage(GestionnaireImage.getCheminImage(grille[i][j]));
                } else {
                    image = Loader.loadImage(Chemin.SLOT);
                }
                ImageView slotImage = new ImageView(image);
                slotImage.setFitWidth(48);
                slotImage.setFitHeight(48);
                slotImage.setOpacity(0.85);
                final int row = i, col = j;
                slotImage.setOnMouseClicked(event -> handleCraftingClick(row, col));
                grillePane.add(slotImage, j, i);
            }
        }

        Button boutonCrafter = new Button("Crafter");
        boutonCrafter.setPrefSize(100, 30);
        boutonCrafter.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-background-color: rgba(100,100,100,0.8); " +
                        "-fx-background-radius: 15; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: #5A5A5A; " +
                        "-fx-border-width: 2;"
        );
        boutonCrafter.setOnAction(event -> handleCraftButton());

        AnchorPane.setTopAnchor(grillePane, 80.0);
        AnchorPane.setLeftAnchor(grillePane, 75.0);
        AnchorPane.setLeftAnchor(boutonCrafter, 100.0);
        AnchorPane.setTopAnchor(boutonCrafter, 250.0);

        resultatLabel = new Label();
        resultatLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        AnchorPane.setLeftAnchor(resultatLabel, 80.0);
        AnchorPane.setTopAnchor(resultatLabel, 20.0);

        craftPane.getChildren().addAll(background, grillePane, boutonCrafter, resultatLabel);

        rootPane.getChildren().add(craftPane);
    }



    private void handleCraftingClick(int row, int col) {
        if (grille[row][col] == null) {
            Item item = new Item(Bloc.TRONC); // exemple
            grille[row][col] = item;
            gestionnaireCraft.placerItem(row, col, item);
        } else {
            grille[row][col] = null;
            gestionnaireCraft.retirerItem(row, col);
        }

    }

    private void handleCraftButton() {
        GestionnaireCraft.ResultatCraft resultat = gestionnaireCraft.tenterCraft();
        if (resultat != null) {
            resultatLabel.setText("Résultat : " + resultat.resultat.getNom() + " x" + resultat.quantite);
        } else {
            resultatLabel.setText("Aucun craft possible");
        }
    }

    public void toggleCraftWindow() {
        if (craftPane == null || !rootPane.getChildren().contains(craftPane)) {
            showCraftWindow();
        } else {
            rootPane.getChildren().remove(craftPane);
        }
    }
}