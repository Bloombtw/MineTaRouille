package universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;

public class ClavierListener {
    private final DeplacementManager deplacementManager;
    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private final DebugManager debugManager;
    private final JeuController jeuController;

    public ClavierListener(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire, DebugManager debugManager,  JeuController jeuController) {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueInventaire = vueInventaire;
        this.debugManager = debugManager;
        this.deplacementManager = new DeplacementManager(joueur);
        this.jeuController = jeuController;
    }

    public void lier(TilePane tilePane) {
        tilePane.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {
                case Z, SPACE, UP -> {
                    joueur.sauter();
                    MusiqueManager.getInstance();

                }

                case Q, LEFT -> deplacementManager.setEnDeplacementGauche(true);
                case D, RIGHT -> deplacementManager.setEnDeplacementDroite(true);
                case F3 -> debugManager.toggle();

            }

            if (code == KeyCode.A) {
                inventaire.jeterItemSelectionne(joueur, vueInventaire, jeuController);
            }

            gererSelectionInventaire(event.getText());
            vueInventaire.mettreAJourAffichage();
        });

        tilePane.setOnKeyReleased(event -> gereTouchePressee(event.getCode()));
    }

    public void desactiver(TilePane tilePane) {
        tilePane.setOnKeyPressed(null);
        tilePane.setOnKeyReleased(null);
        deplacementManager.stop();
    }

    private void gereTouchePressee(KeyCode code) {
        switch (code) {
            case Q, LEFT -> {
                deplacementManager.setEnDeplacementGauche(false);
                if (!deplacementManager.isEnDeplacementDroite()) {
                    joueur.arreterMouvementX();
                }
            }
            case D, RIGHT -> {
                deplacementManager.setEnDeplacementDroite(false);
                if (!deplacementManager.isEnDeplacementGauche()) {
                    joueur.arreterMouvementX();
                }
            }
            case Z, SPACE, UP -> {} // RIEN
        }
    }

    private void gererSelectionInventaire(String caractere) {
        switch (caractere) {
            case "&" -> inventaire.setSelectedIndex(0);
            case "é" -> inventaire.setSelectedIndex(1);
            case "\"" -> inventaire.setSelectedIndex(2);
            case "'" -> inventaire.setSelectedIndex(3);
            case "(" -> inventaire.setSelectedIndex(4);
            case "-" -> inventaire.setSelectedIndex(5);
            case "è" -> inventaire.setSelectedIndex(6);
            case "_" -> inventaire.setSelectedIndex(7);
            case "ç" -> inventaire.setSelectedIndex(8);
        }
    }
}


