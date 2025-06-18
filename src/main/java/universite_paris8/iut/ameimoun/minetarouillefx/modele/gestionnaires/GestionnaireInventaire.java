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

    /**
     * Initialise l'inventaire avec des items par défaut et ajoute la vue de l'inventaire au rootPane.
     * Ajoute des items prédéfinis à l'inventaire et configure la vue de l'inventaire.
     * Écoute les changements dans l'inventaire pour mettre à jour l'objet tenu par le joueur via des listeners.
     * Met à jour l'objet tenu par le joueur en fonction de l'item sélectionné dans l'inventaire.
     */
    public void initialiserInventaire() {
        inventaire.ajouterItem(new Item(Objet.FIL, 6) );
        inventaire.ajouterItem(new Item(Bloc.TABLE_CRAFT, 1));
        inventaire.ajouterItem(new Item(Bloc.TRONC, 32));
        inventaire.ajouterItem(new Item(Bloc.PIERRE, 64));
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
