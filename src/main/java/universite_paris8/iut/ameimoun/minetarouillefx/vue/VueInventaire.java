package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

public class VueInventaire extends HBox {
    @FXML
    private Label quantiteLabel;

    private final Inventaire inventaire;

    public VueInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        setSpacing(5);
        mettreAJourAffichage();

        // 🔁 Mise à jour si le contenu change
        inventaire.getSlots().addListener((Observable o) -> mettreAJourAffichage());

        // 🔁 Mise à jour si la sélection change
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
                Text nomItem = new Text(item.getNom());
                nomItem.setFill(Color.WHITE);
                caseSlot.getChildren().add(nomItem);
            }

            getChildren().add(caseSlot);
        }
    }

    private void ajouterItemVisuel(Item item, Pane slot) {//reprendre ici
        if (item != null) {
            Label quantiteLabel = new Label();
            quantiteLabel.textProperty().bind(item.quantiteProperty().asString("x%d")); // Liaison directe

            quantiteLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14; "
                    + "-fx-background-color: rgba(0,0,0,0.5);");


            StackPane itemAffichage = new StackPane(quantiteLabel);
            slot.getChildren().add(itemAffichage);
        }
    }



}