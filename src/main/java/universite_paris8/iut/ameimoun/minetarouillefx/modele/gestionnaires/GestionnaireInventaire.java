package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Objet;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;

public class GestionnaireInventaire {
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private final VueJoueur joueurVue;
    private final AnchorPane rootPane;

    public GestionnaireInventaire(AnchorPane rootPane, VueJoueur joueurVue) {
        this.rootPane = rootPane;
        this.joueurVue = joueurVue;
        this.inventaire = new Inventaire();
        this.vueInventaire = new VueInventaire(inventaire);
    }

    public void initialiserInventaire() {
        inventaire.ajouterItem(new Item(Objet.EPEE));
        inventaire.ajouterItem(new Item(Bloc.PIERRE, 32));
        AnchorPane.setTopAnchor(vueInventaire, 10.0);
        AnchorPane.setRightAnchor(vueInventaire, 10.0);
        rootPane.getChildren().add(vueInventaire);
        joueurVue.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
        inventaire.selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            joueurVue.mettreAJourObjetTenu(inventaire.getItem(newVal.intValue()));
        });
        inventaire.getSlots().addListener((ListChangeListener.Change<? extends Item> change) -> {
            joueurVue.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
        });
    }

    public Inventaire getInventaire() {
        return inventaire;
    }
    public VueInventaire getVueInventaire() {
        return vueInventaire;
    }
}
