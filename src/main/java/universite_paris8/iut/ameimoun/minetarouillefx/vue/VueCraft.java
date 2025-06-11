package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.CraftListener;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireCraft;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class VueCraft implements CraftListener {
    private final GestionnaireCraft gestionnaireCraft;
    private final AnchorPane rootPane;
    private final JeuController jeuController;
    private final TilePane tilePane;

    private Label resultatLabel;
    private AnchorPane craftPane;
    private AnchorPane overlayPane;

    public VueCraft(GestionnaireCraft gestionnaireCraft, AnchorPane rootPane, JeuController jeuController, TilePane tilePane) {
        this.gestionnaireCraft = gestionnaireCraft;
        this.rootPane = rootPane;
        this.jeuController = jeuController;
        this.tilePane = tilePane;
        this.gestionnaireCraft.setCraftListener(this);
    }

    public void ouvrirCraftWindow() {
        if (craftPane == null || !rootPane.getChildren().contains(craftPane)) {
            afficherFenetreCraft();
            overlayPane.requestFocus();
        }
    }

    public void fermerCraftWindow() {
        gestionnaireCraft.remettreItemsGrilleDansInventaire();
        if (craftPane != null && rootPane.getChildren().contains(craftPane)) {
            rootPane.getChildren().removeAll(craftPane, overlayPane);
            jeuController.reprendreJeu();
            tilePane.requestFocus();
        }
    }

    private void afficherFenetreCraft() {
        creerOverlay();
        creerPaneCraft();
        ajouterComposantsCraft();
        gestionnaireCraft.ajouterEcouteurs(craftPane, jeuController.getClavierListener());
        overlayPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                fermerCraftWindow();
            }
        });
        rootPane.getChildren().addAll(overlayPane, craftPane);
        jeuController.mettreEnPauseJeu();
    }

    private void creerOverlay() {
        overlayPane = new AnchorPane();
        overlayPane.setPrefSize(1920, 1080);
        overlayPane.setStyle("-fx-background-color: rgba(0,0,0,0.1);");
        overlayPane.setFocusTraversable(true);
        overlayPane.requestFocus();
        gestionnaireCraft.ajouterEcouteurs(overlayPane, jeuController.getClavierListener());
    }

    private void creerPaneCraft() {
        craftPane = new AnchorPane();
        craftPane.setPrefSize(300, 300);
    }

    private void ajouterComposantsCraft() {
        ImageView background = creerArrierePlan();
        GridPane grillePane = creerGrilleCraft();
        Button boutonCrafter = creerBoutonCrafter();
        resultatLabel = creerLabelResultat();

        AnchorPane.setTopAnchor(grillePane, 80.0);
        AnchorPane.setLeftAnchor(grillePane, 75.0);
        AnchorPane.setLeftAnchor(boutonCrafter, 100.0);
        AnchorPane.setTopAnchor(boutonCrafter, 250.0);
        AnchorPane.setLeftAnchor(resultatLabel, 80.0);
        AnchorPane.setTopAnchor(resultatLabel, 20.0);

        craftPane.getChildren().addAll(background, grillePane, boutonCrafter, resultatLabel);
    }

    private ImageView creerArrierePlan() {
        ImageView background = new ImageView(Loader.loadImage(Chemin.CRAFT_BACKGROUND));
        background.setFitWidth(300);
        background.setFitHeight(300);
        return background;
    }

    private GridPane creerGrilleCraft() {
        GridPane grillePane = new GridPane();
        grillePane.setHgap(5);
        grillePane.setVgap(5);
        grillePane.setAlignment(Pos.CENTER);

        Item[][] grille = gestionnaireCraft.getGrille();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ImageView slotImage = new ImageView(Loader.loadImage(Chemin.SLOT));
                slotImage.setFitWidth(48);
                slotImage.setFitHeight(48);
                slotImage.setOpacity(0.85);

                if (grille[i][j] != null) {
                    ImageView itemImage = new ImageView(Loader.loadImage(GestionnaireImage.getCheminImage(grille[i][j])));
                    itemImage.setFitWidth(32);
                    itemImage.setFitHeight(32);
                    itemImage.setOpacity(0.85);
                    AnchorPane stack = new AnchorPane(slotImage, itemImage);
                    AnchorPane.setTopAnchor(itemImage, 8.0);
                    AnchorPane.setLeftAnchor(itemImage, 8.0);
                    final int row = i, col = j;
                    stack.setOnMouseClicked(e -> gestionnaireCraft.gererClicCraft(row, col));
                    grillePane.add(stack, j, i);
                } else {
                    final int row = i, col = j;
                    slotImage.setOnMouseClicked(e -> gestionnaireCraft.gererClicCraft(row, col));
                    grillePane.add(slotImage, j, i);
                }
            }
        }

        return grillePane;
    }

    private Button creerBoutonCrafter() {
        Button bouton = new Button("Crafter");
        bouton.setPrefSize(100, 30);
        bouton.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-background-color: rgba(100,100,100,0.8); " +
                        "-fx-background-radius: 15; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: #5A5A5A; " +
                        "-fx-border-width: 2;"
        );
        bouton.setOnAction(e -> gestionnaireCraft.tenterCraft());
        return bouton;
    }

    private Label creerLabelResultat() {
        Label label = new Label();
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        return label;
    }

    // Méthodes du listener CraftListener

    @Override
    public void onGrilleChange(Item[][] grille) {
        rafraichirCraftWindow();
    }

    @Override
    public void onCraftResult(Item resultat, int quantite) {
        if (quantite == -1) {
            resultatLabel.setText("Plus de place dans l'inventaire");
            return;
        } else if (quantite == -2) {
            resultatLabel.setText("Aucun craft possible");
            return;
        }
        if (resultat != null) {
            resultatLabel.setText("Résultat : " + resultat.getNom() + " x" + quantite);
        }
    }

    public void rafraichirCraftWindow() {
        rootPane.getChildren().removeAll(craftPane, overlayPane);
        afficherFenetreCraft();
    }


}