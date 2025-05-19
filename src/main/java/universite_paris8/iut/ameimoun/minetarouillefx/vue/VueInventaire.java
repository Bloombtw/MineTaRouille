package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

public class VueInventaire extends HBox {

    private Inventaire inventaire;

    public VueInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        setSpacing(5);
        mettreAJourAffichage();
    }

    public void mettreAJourAffichage() {
        getChildren().clear();

        for (int i = 0; i < 9; i++) {
            Item item = inventaire.getItem(i);

            Rectangle slot = new Rectangle(50, 50);
            slot.setStroke(Color.BLACK);
            slot.setFill(i == inventaire.getSelectedIndex() ? Color.GOLD : Color.DARKGRAY);

            StackPane caseSlot = new StackPane(slot);

            if (item != null) {
                Text nomItem = new Text(item.getNom());
                nomItem.setFill(Color.WHITE);
                caseSlot.getChildren().add(nomItem);
            }

            getChildren().add(caseSlot);
        }
    }
}

