package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

import java.io.InputStream;

public class VueInventaire extends HBox {

    private final Inventaire inventaire;

    public VueInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        setSpacing(5);
        mettreAJourAffichage();

        inventaire.getSlots().addListener((Observable o) -> mettreAJourAffichage());

        inventaire.selectedIndexProperty().addListener((obs, oldVal, newVal) -> mettreAJourAffichage());
    }

    public void mettreAJourAffichage() {
        getChildren().clear();

        for (int i = 0; i < inventaire.getSlots().size(); i++) {
            Item item = inventaire.getItem(i);

            Rectangle slot = new Rectangle(50, 50);
            slot.setStroke(Color.BLACK);
            slot.setFill(i == inventaire.getSelectedIndex() ? Color.GOLD : Color.DARKGRAY);

            StackPane caseSlot = new StackPane(slot);

            if (item != null) {
                String nomFichier = item.getNom()
                        .toLowerCase()
                        .replace("é", "e")
                        .replace("è", "e")
                        .replace("à", "a")
                        .replace("ù", "u")
                        .replace(" ", "_");

                // 1. Tente dossier des items classiques
                String cheminImage = "/img/items/" + nomFichier + ".png";
                InputStream stream = getClass().getResourceAsStream(cheminImage);

                // 2. Si pas trouvé, tente dossier des blocs solides
                if (stream == null) {
                    cheminImage = "/img/blocs/solide/" + nomFichier + ".png";
                    stream = getClass().getResourceAsStream(cheminImage);
                }

                // 3. Affiche image ou fallback texte
                if (stream != null) {
                    ImageView imageView = new ImageView(new Image(stream));
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);
                    caseSlot.getChildren().add(imageView);
                } else {
                    Text fallback = new Text(item.getNom());
                    fallback.setFill(Color.WHITE);
                    caseSlot.getChildren().add(fallback);
                    System.out.println("Image non trouvée pour : " + nomFichier);
                }

                if (item.getQuantite() > 1) {
                    Text qteText = new Text("x" + item.getQuantite());
                    qteText.setFill(Color.WHITE);
                    qteText.setTranslateX(15); // ajuste selon la position
                    qteText.setTranslateY(15);
                    caseSlot.getChildren().add(qteText);
                }
            }

            getChildren().add(caseSlot);
        }
    }
}