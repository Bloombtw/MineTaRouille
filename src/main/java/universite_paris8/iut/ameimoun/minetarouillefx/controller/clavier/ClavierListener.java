package universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireDeplacement;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;

public class ClavierListener {
    private final GestionnaireDeplacement deplacementManager;
    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private final DebugManager debugManager;
    private JeuController jeuController;

    public ClavierListener(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire, DebugManager debugManager) {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueInventaire = vueInventaire;
        this.debugManager = debugManager;
        this.deplacementManager = new GestionnaireDeplacement(joueur);
    }

    /**
     * Associe les touches du clavier aux actions du jeu.
     *
     * @param tilePane Le TilePane sur lequel les événements de clavier seront écoutés.
     */
    public void lier(TilePane tilePane) {
        tilePane.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case Z, SPACE, UP -> {
                    ignorerToucheSiJeuEnPause(event);
                    joueur.sauter();
                    MusiqueManager.getInstance();

                }
                case Q, LEFT -> {
                    ignorerToucheSiJeuEnPause(event);
                    deplacementManager.setEnDeplacementGauche(true);
                }
                case D, RIGHT -> {
                    ignorerToucheSiJeuEnPause(event);
                    deplacementManager.setEnDeplacementDroite(true);
                }
                case F3 -> {
                    ignorerToucheSiJeuEnPause(event);
                    debugManager.toggle();
                }
            }
            gererSelectionInventaire(event.getText());
            vueInventaire.mettreAJourAffichageInventaire();
        });

        tilePane.setOnKeyReleased(event -> arreterMouvement(event.getCode()));
    }

    /**
     * Désactive les actions du clavier.
     *
     * @param tilePane Le TilePane sur lequel les événements de clavier ne seront plus écoutés.
     */
    public void desactiver(TilePane tilePane) {
        tilePane.setOnKeyPressed(null);
        tilePane.setOnKeyReleased(null);
        deplacementManager.stop();
    }

    /**
     * Arrête le mouvement du joueur en fonction de la touche relâchée.
     *
     * @param code La touche relâchée.
     */
    private void arreterMouvement(KeyCode code) {
        switch (code) {
            case Q, LEFT -> {
                deplacementManager.setEnDeplacementGauche(false);
            }
            case D, RIGHT -> {
                deplacementManager.setEnDeplacementDroite(false);
            }
            case Z, SPACE, UP -> {} // RIEN
        }
    }

    /**
     * Gère la sélection de l'inventaire en fonction du caractère entré.
     *
     * @param caractere Le caractère entré par l'utilisateur.
     */
    public void gererSelectionInventaire(String caractere) {
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


    private void ignorerToucheSiJeuEnPause(KeyEvent event) {
        if (jeuController != null && jeuController.isEnPause()) {
            event.consume();
        }
    }

    public void setJeuController(JeuController jeuController) {
        this.jeuController = jeuController;
    }

}

