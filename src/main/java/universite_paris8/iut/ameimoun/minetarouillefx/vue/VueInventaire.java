package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la vue de l'inventaire dans l'interface graphique.
 * Chaque élément de l'inventaire est affiché sous forme de slot contenant une image et une quantité.
 * Cette classe observe l'inventaire et met à jour l'affichage automatiquement en cas de modification.
 */
public class VueInventaire extends HBox {

    /** Inventaire à afficher. */
    private final Inventaire inventaire;

    /** Liste des conteneurs visuels pour chaque slot de l'inventaire. */
    private final List<StackPane> casesSlots = new ArrayList<>();

    /**
     * Construit la vue de l'inventaire à partir du modèle
     *
     * @param inventaire L'inventaire à représenter.
     */
    public VueInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        setSpacing(3);
        initCasesSlots();
        inventaire.getSlots().addListener((Observable o) -> mettreAJourAffichageInventaire());
        inventaire.selectedIndexProperty().addListener((obs, oldVal, newVal) -> mettreAJourAffichageInventaire());
    }

    /**
     * Met à jour tous les slots visuellement en fonction des données de l'inventaire.
     */
    public void mettreAJourAffichageInventaire() {
        for (int i = 0; i < casesSlots.size(); i++) {
            updateCaseSlot(casesSlots.get(i), i);
        }
    }

    /**
     * Met à jour un slot individuel (image, quantité, effet visuel).
     *
     * @param caseSlot Le conteneur graphique du slot.
     * @param index    L'index du slot dans l'inventaire.
     */
    private void updateCaseSlot(StackPane caseSlot, int index) {
        caseSlot.getChildren().clear();
        caseSlot.getChildren().add(creerFondSlot(index));

        Item item = inventaire.getItem(index);
        if (item != null) {
            caseSlot.getChildren().add(creerImageItem(item));
            if (item.getQuantite() > 1) {
                caseSlot.getChildren().add(creerQuantiteText(item));
            }
        }
    }

    /**
     * Initialise tous les slots graphiques à partir de l'inventaire.
     */
    private void initCasesSlots() {
        for (int i = 0; i < inventaire.getSlots().size(); i++) {
            StackPane caseSlot = creerCaseSlot(i);
            casesSlots.add(caseSlot);
            getChildren().add(caseSlot);
        }
    }

    /**
     * Crée un slot (StackPane) contenant le fond et éventuellement l'image et la quantité.
     *
     * @param index L'index du slot.
     * @return Le StackPane représentant un slot.
     */
    private StackPane creerCaseSlot(int index) {
        StackPane caseSlot = new StackPane();
        caseSlot.getChildren().add(creerFondSlot(index));
        Item item = inventaire.getItem(index);
        if (item != null) {
            caseSlot.getChildren().add(creerImageItem(item));
            if (item.getQuantite() > 1) {
                caseSlot.getChildren().add(creerQuantiteText(item));
            }
        }
        return caseSlot;
    }

    /**
     * Crée le fond visuel d’un slot. Met en surbrillance s’il est sélectionné.
     *
     * @param index L'index du slot.
     * @return L’image de fond du slot.
     */
    private ImageView creerFondSlot(int index) {
        Image imageSlot = Loader.loadImage(Chemin.SLOT);
        ImageView imageViewSlot = new ImageView(imageSlot);
        imageViewSlot.setFitWidth(Constantes.TAILLE_SLOT);
        imageViewSlot.setFitHeight(Constantes.TAILLE_SLOT);

        if (index == inventaire.getSelectedIndex()) {
            imageViewSlot.setStyle("-fx-effect: dropshadow(one-pass-box, gold, 10, 0.5, 0, 0);");
        }
        return imageViewSlot;
    }

    /**
     * Crée l’image de l’objet affiché dans le slot.
     *
     * @param item L’objet à afficher.
     * @return Une ImageView de l’objet.
     */
    private ImageView creerImageItem(Item item) {
        String chemin = GestionnaireImage.getCheminImage(item);
        Image image = Loader.loadImage(chemin);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(Constantes.TAILLE_IMAGE_INVENTAIRE);
        imageView.setFitHeight(Constantes.TAILLE_IMAGE_INVENTAIRE);
        return imageView;
    }

    /**
     * Crée le texte indiquant la quantité de l’objet dans un slot.
     *
     * @param item L’objet dont on veut afficher la quantité.
     * @return Un texte JavaFX positionné.
     */
    private Text creerQuantiteText(Item item) {
        Text qteText = new Text("x" + item.getQuantite());
        qteText.setFill(Color.WHITE);
        qteText.setTranslateX(15);
        qteText.setTranslateY(15);
        qteText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return qteText;
    }
}
