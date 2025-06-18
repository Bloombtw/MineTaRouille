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

/**
 * Vue représentant la fenêtre de craft.
 * Gère l'affichage de la grille, du bouton de craft, du label résultat et l'overlay.
 */
public class VueCraft {
    private final GestionnaireCraft gestionnaireCraft;
    private final JeuController jeuController;
    private CraftController craftController;

    /** Pane contenant la grille d'inventaire du jeu. */
    private final TilePane tilePane;
    /** Label affichant le résultat du craft. */
    private Label resultatLabel;
    /** Pane racine de la fenêtre. */
    private final AnchorPane rootPane;
    /** Pane principale de la fenêtre de craft. */
    private AnchorPane craftPane;
    /** Overlay semi-transparent pour le focus et la gestion clavier. */
    private AnchorPane overlayPane;
    /** Grille graphique de la fenêtre de craft. */
    private GridPane grillePane;

    /**
     * Construit la vue de la fenêtre de craft.
     * @param gestionnaireCraft le gestionnaire de craft associé
     * @param rootPane le pane racine de la scène
     * @param jeuController le contrôleur du jeu
     * @param tilePane le pane de l'inventaire principal
     */
    public VueCraft(GestionnaireCraft gestionnaireCraft, AnchorPane rootPane, JeuController jeuController, TilePane tilePane) {
        this.gestionnaireCraft = gestionnaireCraft;
        this.rootPane = rootPane;
        this.jeuController = jeuController;
        this.tilePane = tilePane;

        // Écoute les changements de la grille pour mettre à jour l'affichage
        gestionnaireCraft.getGrille().addListener((ListChangeListener<ObservableList<Item>>) change -> {
            while (change.next()) {
                mettreAJourGrille();
            }
        });

        // Ouvre ou ferme la fenêtre de craft selon la propriété dédiée
        gestionnaireCraft.fenetreCraftOuverteProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                ouvrirCraftWindow();
            } else {
                fermerCraftWindowAffichage();
            }
        });
        for (ObservableList<Item> ligne : gestionnaireCraft.getGrille()) {
            ligne.addListener((ListChangeListener<Item>) change -> mettreAJourGrille());
        }
    }

    /**
     * Définit le contrôleur de la fenêtre de craft.
     * @param craftController le contrôleur à associer
     */
    public void setCraftController(CraftController craftController) {
        this.craftController = craftController;
    }

    /**
     * Ouvre la fenêtre de craft et affiche les composants.
     */
    public void ouvrirCraftWindow() {
        if (craftPane == null || !rootPane.getChildren().contains(craftPane)) {
            ouvrirFenetreCraft();
            overlayPane.requestFocus();
        }
    }

    /**
     * Ferme l'affichage de la fenêtre de craft et reprend le jeu.
     */
    public void fermerCraftWindowAffichage() {
        if (craftPane != null && rootPane.getChildren().contains(craftPane)) {
            rootPane.getChildren().removeAll(craftPane, overlayPane);
            jeuController.reprendreJeu();
            tilePane.requestFocus();
        }
    }

    /**
     * Crée et affiche la fenêtre de craft avec ses composants.
     */
    private void ouvrirFenetreCraft() {
        creerOverlay();
        creerPaneCraft();
        craftPane.setOnMousePressed(event -> {
            if (overlayPane != null) {
                overlayPane.requestFocus();
            }
            event.consume();
        });
        craftPane.getStylesheets().add(Loader.loadCSS(Chemin.CSS_BUTTON_CRAFT));
        ajouterComposantsCraft();
        rootPane.getChildren().addAll(overlayPane, craftPane);
        craftController.initialiserListeners();
        jeuController.mettreEnPauseJeu();
    }

    /**
     * Crée l'overlay semi-transparent pour la gestion du focus et du clavier.
     */
    private void creerOverlay() {
        overlayPane = new AnchorPane();
        overlayPane.setPrefSize(1920, 1080);
        overlayPane.setStyle("-fx-background-color: rgba(0,0,0,0.2);");
        overlayPane.setFocusTraversable(true);
        overlayPane.requestFocus();
    }

    /**
     * Crée le pane principal de la fenêtre de craft.
     */
    private void creerPaneCraft() {
        craftPane = new AnchorPane();
        craftPane.setPrefSize(Constantes.TAILLE_FENETRE_CRAFT, Constantes.TAILLE_FENETRE_CRAFT);
    }

    /**
     * Ajoute les composants (grille, bouton, label) à la fenêtre de craft.
     */
    private void ajouterComposantsCraft() {
        ImageView background = creerArrierePlan();
        grillePane = creerGrilleCraft();
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

    /**
     * Crée l'arrière-plan graphique de la fenêtre de craft.
     * @return l'ImageView de fond
     */
    private ImageView creerArrierePlan() {
        ImageView background = new ImageView(Loader.loadImage(Chemin.CRAFT_BACKGROUND));
        background.setFitWidth(Constantes.TAILLE_FENETRE_CRAFT);
        background.setFitHeight(Constantes.TAILLE_FENETRE_CRAFT);
        return background;
    }

    /**
     * Crée la grille graphique de craft (3x3).
     * @return la GridPane représentant la grille
     */
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

    /**
     * Crée une cellule de la grille de craft.
     * @param row la ligne de la cellule
     * @param col la colonne de la cellule
     * @return l'AnchorPane de la cellule
     */
    private AnchorPane creerCellule(int row, int col) {
        AnchorPane cellule = new AnchorPane();
        cellule.setOnMouseClicked(e -> craftController.clicCaseCraft(row, col));

        ImageView slot = new ImageView(Loader.SLOT_IMAGE);
        slot.setFitWidth(48);
        slot.setFitHeight(48);
        slot.setOpacity(0.85);
        cellule.getChildren().add(slot);

        return cellule;
    }

    /**
     * Crée le bouton pour lancer le craft.
     * @return le bouton "Crafter"
     */
    private Button creerBoutonCrafter() {
        Button bouton = new Button("Crafter");
        bouton.setId("bouton-crafter");
        bouton.setPrefSize(Constantes.LARGEUR_BOUTON_CRAFT, Constantes.HAUTEUR_BOUTON_CRAFT);
        bouton.setOnAction(e -> {
            craftController.clicBoutonCrafter();
            overlayPane.requestFocus();
        });
        return bouton;
    }

    /**
     * Crée le label affichant le résultat du craft ou les messages d'erreur.
     * @return le label de résultat
     * Si quantité > 0, affiche le nom de l'item et sa quantité.
     * Si quantité == -1, affiche "Inventaire plein".
     * Si quantité == -3, affiche rien.
     */
    private Label creerLabelResultat() {
        Label label = new Label();
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        label.textProperty().bind(Bindings.createStringBinding(() -> {
                    int quantite = gestionnaireCraft.quantiteCraftProperty().get();
                    if (quantite > 0 && gestionnaireCraft.resultatCraftProperty().get() != null) {
                        return "Résultat : " + gestionnaireCraft.resultatCraftProperty().get().getNom() + " x" + quantite;
                    } else if (quantite == -1) {
                        return "Inventaire plein";
                    } else if (quantite == -3) {
                        return "";
                    } else {
                        return "Aucun craft possible";
                    }
                },
                gestionnaireCraft.resultatCraftProperty(), gestionnaireCraft.quantiteCraftProperty()));
        return label;
    }

    /**
     * Met à jour l'affichage graphique de la grille de craft.
     * Quand l'affichage de la grille change, l'inventaire est mis à jour immédiatement.
     * Délègue la mise à jour de chaque cellule à une méthode dédiée.
     */
    private void mettreAJourGrille() {
        for (int i = 0; i < Constantes.TAILLE_GRILLE_CRAFT; i++) {
            for (int j = 0; j < Constantes.TAILLE_GRILLE_CRAFT; j++) {
                mettreAJourCellule(i, j);
            }
        }
        jeuController.getGestionnaireInventaire().getVueInventaire().mettreAJourAffichageInventaire();
    }

    /**
     * Met à jour l'affichage d'une cellule de la grille de craft.
     * @param i ligne de la cellule
     * @param j colonne de la cellule
     */
    private void mettreAJourCellule(int i, int j) {
        Item item = gestionnaireCraft.getGrille().get(i).get(j);
        AnchorPane cellule = (AnchorPane) grillePane.getChildren().get(i * Constantes.TAILLE_GRILLE_CRAFT + j);
        cellule.getChildren().removeIf(node -> node instanceof ImageView && ((ImageView) node).getImage() != Loader.SLOT_IMAGE);
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

    public AnchorPane getOverlayPane() {
        return overlayPane;
    }
}