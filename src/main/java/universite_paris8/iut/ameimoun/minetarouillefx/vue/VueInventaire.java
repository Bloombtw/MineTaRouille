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

public class VueInventaire extends HBox {

    private final Inventaire inventaire;
    private final List<StackPane> casesSlots = new ArrayList<>();

    public VueInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        setSpacing(3);
        initCasesSlots(); // Initialisation des slots
        mettreAJourAffichage(); // Mise à jour initiale de l'affichage
        inventaire.getSlots().addListener((Observable o) -> mettreAJourAffichage());
        inventaire.selectedIndexProperty().addListener((obs, oldVal, newVal) -> mettreAJourAffichage());
    }

    public void mettreAJourAffichage() {
        for (int i = 0; i < casesSlots.size(); i++) {
            updateCaseSlot(casesSlots.get(i), i);
        }
    }

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

    private void initCasesSlots() {
        for (int i = 0; i < inventaire.getSlots().size(); i++) {
            StackPane caseSlot = creerCaseSlot(i);
            casesSlots.add(caseSlot);
            getChildren().add(caseSlot);
        }
    }

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

    private ImageView creerImageItem(Item item) {
        String chemin = GestionnaireImage.getCheminImage(item);
        Image image = Loader.loadImage(chemin);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(Constantes.TAILLE_IMAGE_INVENTAIRE);
        imageView.setFitHeight(Constantes.TAILLE_IMAGE_INVENTAIRE);
        return imageView;
    }

    private Text creerQuantiteText(Item item) {
        Text qteText = new Text("x" + item.getQuantite());
        qteText.setFill(Color.WHITE);
        qteText.setTranslateX(15);
        qteText.setTranslateY(15);
        qteText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return qteText;
    }
}