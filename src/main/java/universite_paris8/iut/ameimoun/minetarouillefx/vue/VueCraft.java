package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.CraftController;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireCraft;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class VueCraft {
    private final GestionnaireCraft gestionnaireCraft;
    private final JeuController jeuController;
    private CraftController craftController;

    private final TilePane tilePane;
    private Label resultatLabel;
    private final AnchorPane rootPane;
    private AnchorPane craftPane;
    private AnchorPane overlayPane;
    private GridPane grillePane;

    public VueCraft(GestionnaireCraft gestionnaireCraft, AnchorPane rootPane, JeuController jeuController, TilePane tilePane) {
        this.gestionnaireCraft = gestionnaireCraft;
        this.rootPane = rootPane;
        this.jeuController = jeuController;
        this.tilePane = tilePane;

        // Écouteur automatique des changements de la grille
        gestionnaireCraft.getGrille().addListener((ListChangeListener<ObservableList<Item>>) change -> {
            while (change.next()) {
                mettreAJourGrille();
            }
        });

        // Écouteur pour la propriété de fenêtre craft ouverte
        gestionnaireCraft.fenetreCraftOuverteProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                ouvrirCraftWindow();
            } else {
                fermerCraftWindowAffichage();
            }
        });
    }

    public void setCraftController(CraftController craftController) {
        this.craftController = craftController;
    }

    public void ouvrirCraftWindow() {
        if (craftPane == null || !rootPane.getChildren().contains(craftPane)) {
            ouvrirFenetreCraft();
            overlayPane.requestFocus();
        }
    }

    public void fermerCraftWindowAffichage() {
        if (craftPane != null && rootPane.getChildren().contains(craftPane)) {
            rootPane.getChildren().removeAll(craftPane, overlayPane);
            jeuController.reprendreJeu();
            tilePane.requestFocus();
        }
    }

    private void ouvrirFenetreCraft() {
        creerOverlay();
        creerPaneCraft();
        craftPane.getStylesheets().add(Loader.loadCSS(Chemin.CSS_BUTTON_CRAFT));
        ajouterComposantsCraft();
        rootPane.getChildren().addAll(overlayPane, craftPane);
        craftController.initialiserListeners();
        jeuController.mettreEnPauseJeu();
    }

    private void creerOverlay() {
        overlayPane = new AnchorPane();
        overlayPane.setPrefSize(1920, 1080);
        overlayPane.setStyle("-fx-background-color: rgba(0,0,0,0.2);");
        overlayPane.setFocusTraversable(true);
        overlayPane.requestFocus();
    }

    private void creerPaneCraft() {
        craftPane = new AnchorPane();
        craftPane.setPrefSize(Constantes.TAILLE_FENETRE_CRAFT, Constantes.TAILLE_FENETRE_CRAFT);
    }

    private void ajouterComposantsCraft() {
        ImageView background = creerArrierePlan();
        grillePane = creerGrilleCraft(); // Grille stockée pour mise à jour ciblée
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
        background.setFitWidth(Constantes.TAILLE_FENETRE_CRAFT);
        background.setFitHeight(Constantes.TAILLE_FENETRE_CRAFT);
        return background;
    }

    private GridPane creerGrilleCraft() {
        GridPane grillePane = new GridPane();
        grillePane.setHgap(5);
        grillePane.setVgap(5);
        grillePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < Constantes.TAILLE_GRILLE_CRAFT; i++) {
            for (int j = 0; j < Constantes.TAILLE_GRILLE_CRAFT; j++) {
                AnchorPane cellule = creerCellule(i, j);
                grillePane.add(cellule, j, i);
            }
        }
        return grillePane;
    }

    private AnchorPane creerCellule(int row, int col) {
        AnchorPane cellule = new AnchorPane();
        cellule.setOnMouseClicked(e -> craftController.clicCaseCraft(row, col));

        ImageView slot = new ImageView(Loader.loadImage(Chemin.SLOT));
        slot.setFitWidth(48);
        slot.setFitHeight(48);
        slot.setOpacity(0.85);
        cellule.getChildren().add(slot);

        return cellule;
    }

    private Button creerBoutonCrafter() {
        Button bouton = new Button("Crafter");
        bouton.setId("bouton-crafter");
        bouton.setPrefSize(Constantes.LARGEUR_BOUTON_CRAFT, Constantes.LARGEUR_BOUTON_CRAFT);
        bouton.setOnAction(e -> craftController.clicBoutonCrafter());
        return bouton;
    }

    private Label creerLabelResultat() {
        Label label = new Label();
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        label.textProperty().bind(Bindings.createStringBinding(() ->
                        (gestionnaireCraft.quantiteCraftProperty().get() > 0 && gestionnaireCraft.resultatCraftProperty().get() != null) ?
                                "Résultat : " + gestionnaireCraft.resultatCraftProperty().get().getNom() + " x" + gestionnaireCraft.quantiteCraftProperty().get() :
                                gestionnaireCraft.quantiteCraftProperty().get() == -1 ? "Inventaire plein" :
                                        "Aucun craft possible",
                gestionnaireCraft.resultatCraftProperty(), gestionnaireCraft.quantiteCraftProperty()));

        return label;
    }

    private void mettreAJourGrille() {
        for (int i = 0; i < Constantes.TAILLE_GRILLE_CRAFT; i++) {
            for (int j = 0; j < Constantes.TAILLE_GRILLE_CRAFT; j++) {
                Item item = gestionnaireCraft.getGrille().get(i).get(j);
                AnchorPane cellule = (AnchorPane) grillePane.getChildren().get(i * Constantes.TAILLE_GRILLE_CRAFT + j);
                cellule.getChildren().removeIf(node -> node instanceof ImageView && !((ImageView) node).getImage().equals(Loader.loadImage(Chemin.SLOT)));

                if (item != null) {
                    ImageView itemImage = new ImageView(Loader.loadImage(GestionnaireImage.getCheminImage(item)));
                    itemImage.setFitWidth(32);
                    itemImage.setFitHeight(32);
                    itemImage.setOpacity(0.85);
                    AnchorPane.setTopAnchor(itemImage, 8.0);
                    AnchorPane.setLeftAnchor(itemImage, 8.0);
                    cellule.getChildren().add(itemImage);
                }
            }
        }
    }

    public AnchorPane getOverlayPane() {
        return overlayPane;
    }
}
