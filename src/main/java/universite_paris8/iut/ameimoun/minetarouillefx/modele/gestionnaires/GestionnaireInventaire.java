package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class GestionnaireInventaire {
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private final VueJoueur joueurVue;
    private final AnchorPane rootPane;
    private GestionnaireItem gestionnaireItem;

    public GestionnaireInventaire(AnchorPane rootPane, VueJoueur joueurVue) {
        this.rootPane = rootPane;
        this.joueurVue = joueurVue;
        this.inventaire = new Inventaire();
        this.vueInventaire = new VueInventaire(inventaire);
    }

    public void initialiserInventaire() {
        // Initialize GestionnaireItem
        this.gestionnaireItem = new GestionnaireItem(rootPane);

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

    public void jeterItemSelectionne(Joueur joueur) {
        if (gestionnaireItem == null) {
            throw new IllegalStateException("GestionnaireItem is not initialized.");
        }

        int idx = inventaire.getSelectedIndex();
        Item item = inventaire.getItem(idx);

        if (item == null) {
            // No item in the selected slot → do nothing
            return;
        }

        // Create a new single-unit item instance
        Item dropItem = creerItemUnite(item);

        // Remove/decrement the item from the inventory slot
        inventaire.retirerItem(idx);

        // Update the inventory view
        vueInventaire.mettreAJourAffichage();

        // Calculate the spawn tile coordinates
        int[] spawnTile = calculerTuileSpawn(joueur);
        int xTuileSpawn = spawnTile[0];
        int yTuileSpawn = spawnTile[1];

        System.out.println("[DEBUG] playerTile = (" + (int) (joueur.getX() / Constantes.TAILLE_TUILE) + "," +
                (int) ((joueur.getY() + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_TUILE) + "), " +
                "spawnTile = (" + xTuileSpawn + "," + yTuileSpawn + ")");
        gestionnaireItem.spawnItemAuSol(dropItem, xTuileSpawn, yTuileSpawn);
    }

    private Item creerItemUnite(Item item) {
        return (item.getTypeItem() == Item.TypeItem.BLOC) ? new Item(item.getBloc()) : new Item(item.getObjet());
    }

    private int[] calculerTuileSpawn(Joueur joueur) {
        int playerTileX = (int) (joueur.getX() / Constantes.TAILLE_TUILE);
        double yPixelPieds = joueur.getY() + Constantes.TAILLE_PERSO - 1;
        int playerTileY = (int) (yPixelPieds / Constantes.TAILLE_TUILE);

        int direction = joueur.estRegardADroite() ? +1 : -1;
        int xTuileSpawn = playerTileX + direction;
        int yTuileSpawn = playerTileY - 1; // Just above the ground

        return new int[]{xTuileSpawn, yTuileSpawn};
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public VueInventaire getVueInventaire() {
        return vueInventaire;
    }
}