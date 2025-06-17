package universite_paris8.iut.ameimoun.minetarouillefx.controller.souris;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController; // Peut être retiré si non utilisé ailleurs
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireBloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireMob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;

public class SourisListener {
    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private GestionnaireItem gestionnaireItem;
    private VueCarte vueCarte;
    private VueJoueur vueJoueur;
    private final GestionnaireMobHostile gestionnaireMobHostile;
    private final GestionnaireMob gestionnaireMobPassif;

    public SourisListener(Joueur joueur, Inventaire inventaire, VueCarte vueCarte, VueInventaire vueInventaire, GestionnaireItem gestionnaireItem, GestionnaireMobHostile gestionnaireMobHostile, GestionnaireMob gestionnaireMobPassif) {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueCarte = vueCarte;
        this.vueInventaire = vueInventaire;
        this.gestionnaireItem = gestionnaireItem;
        this.gestionnaireMobHostile = gestionnaireMobHostile;
        this.gestionnaireMobPassif = gestionnaireMobPassif;
    }

    public void lier(TilePane tilePane) {
        tilePane.setOnMousePressed(this::gererClicSouris);
        tilePane.setOnMouseClicked(this::gererPlacementBloc);
    }

    public void desactiver(TilePane tilePane) {
        tilePane.setOnMousePressed(null);
        tilePane.setOnMouseReleased(null);
        tilePane.setOnMouseMoved(null);
    }

    public void lierScrollInventaire(Scene scene) {
        scene.addEventFilter(ScrollEvent.SCROLL, event -> {
            int index = inventaire.getSelectedIndex();
            int max = inventaire.getSlots().size();
            if (event.getDeltaY() < 0) {
                index = (index + 1) % max;
            } else if (event.getDeltaY() > 0) {
                index = (index - 1 + max) % max;
            }
            inventaire.setSelectedIndex(index);
            event.consume();
        });
    }

    private void gererPlacementBloc(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) { // clic droit
            int x = (int) event.getX() / Constantes.TAILLE_TUILE;
            int y = (int) event.getY() / Constantes.TAILLE_TUILE;
            int couche = 1;

            boolean blocPlace = GestionnaireBloc.placerBloc(
                    Carte.getInstance(),
                    inventaire,
                    inventaire.getSelectedIndex(),
                    couche,
                    x,
                    y,
                    joueur
            );

            if (blocPlace) {
                vueCarte.mettreAJourAffichage(x, y);
                vueInventaire.mettreAJourAffichage();
                if (vueJoueur != null) {
                    vueJoueur.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
                }
            }
        }
    }

    private void casserBloc(int couche, double clickX, double clickY) {
        int tx = (int) (clickX / Constantes.TAILLE_TUILE);
        int ty = (int) (clickY / Constantes.TAILLE_TUILE);
        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());

        if (Objet.PIOCHE.getNom().equals(objetSelectionne.getNom())) {
            Item itemBloc = GestionnaireBloc.casserBlocEtDonnerItem(couche, tx, ty, joueur);
            if (itemBloc != null) {
                dropItemEtMettreAJour(itemBloc, tx, ty, couche);
                vueInventaire.mettreAJourAffichage();
            }
        }
    }

    public void attaquerMobs() {
        double playerCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
        double playerCenterY = joueur.getY() + (Constantes.TAILLE_PERSO / 2.0);

        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());
        if (Objet.EPEE.getNom().equals(objetSelectionne.getNom())) {
            if (gestionnaireMobHostile != null) {
                gestionnaireMobHostile.tuerMobSiProximite(playerCenterX, playerCenterY);
            }

            if (gestionnaireMobPassif != null) {
                gestionnaireMobPassif.tuerMobSiProximite(playerCenterX, playerCenterY);
            }
        }
    }

    public void gererAttaqueDistance(double playerCenterX, double playerCenterY) {
        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());

        // Vérifier si l'objet sélectionné est un arc
        if (Objet.ARC.getNom().equals(objetSelectionne.getNom())) {
            gestionnaireMobPassif.attaquerMobsDistance(playerCenterX, playerCenterY);
        }
    }

    private void gererClicSouris(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) {
            return;
        }
        double clickX = event.getX();
        double clickY = event.getY();
        casserBloc(1, clickX, clickY);
        casserBloc(2, clickX, clickY);

        attaquerMobs();
    }

    private void dropItemEtMettreAJour(Item item, int x, int y, int couche) {
        if (item != null) {
            vueCarte.mettreAJourAffichage(x, y); // le bloc cassé
            if (couche == 1 && y - 1 >= 0) {
                vueCarte.mettreAJourAffichage(x, y - 1); // décor au-dessus si sol cassé
            }
            gestionnaireItem.spawnItemAuSol(item, x, y);
        }
    }
}