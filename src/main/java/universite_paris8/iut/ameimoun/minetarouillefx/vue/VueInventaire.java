package universite_paris8.iut.ameimoun.minetarouillefx.vue;


import javafx.beans.Observable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

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
                String cheminImage = "/img/items/" + item.getNom().toLowerCase().replace("é", "e") + ".png";
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(cheminImage)));
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                caseSlot.getChildren().add(imageView);
            }

            getChildren().add(caseSlot);
        }
    }
}