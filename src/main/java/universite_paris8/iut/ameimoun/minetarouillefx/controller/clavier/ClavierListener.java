package universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;

import java.util.HashMap;
import java.util.Map;

public class ClavierListener {
    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private final DebugManager debugManager;
    private JeuController jeuController;
    private final GestionnaireItem gestionnaireItem;
    private final Map<KeyCode, Runnable> actionsClavier;

    public ClavierListener(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire, DebugManager debugManager,GestionnaireItem gestionnaireItem){
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueInventaire = vueInventaire;
        this.debugManager = debugManager;
        this.gestionnaireItem = gestionnaireItem;
        this.actionsClavier = new HashMap<>();
    }

    /**
     * Initialise les actions associées aux touches du clavier.
     * Méthode utilisée pour alléger le switch de la méthode lier.
     */
    private void initialiserActionsClavier() {
        actionsClavier.put(KeyCode.Z, joueur::sauterUneFois);
        actionsClavier.put(KeyCode.SPACE, joueur::sauterUneFois);
        actionsClavier.put(KeyCode.UP, joueur::sauterUneFois);
        actionsClavier.put(KeyCode.Q, () -> joueur.setEnDeplacementGauche(true));
        actionsClavier.put(KeyCode.LEFT, () -> joueur.setEnDeplacementGauche(true));
        actionsClavier.put(KeyCode.D, () -> joueur.setEnDeplacementDroite(true));
        actionsClavier.put(KeyCode.RIGHT, () -> joueur.setEnDeplacementDroite(true));
        actionsClavier.put(KeyCode.F3, debugManager::toggle);
        actionsClavier.put(KeyCode.A, () -> gestionnaireItem.jeterItemSelectionne(joueur, inventaire, vueInventaire));
        actionsClavier.put(KeyCode.R, () -> gestionnaireItem.consommerMoutonCuitSelectionne(joueur, inventaire, vueInventaire));
    }

    /**
     * Associe les touches du clavier aux actions du jeu.
     *
     * @param tilePane Le TilePane sur lequel les événements de clavier seront écoutés.
     */

    public void lier(TilePane tilePane) {
        tilePane.setOnKeyPressed(event -> {
            Runnable action = actionsClavier.get(event.getCode()); // Récupère l'action associée à la touche pressée
            if (action != null) action.run();
            gererSelectionInventaire(event.getText());
            vueInventaire.mettreAJourAffichageInventaire();
        });

    }

    /**
     * Désactive les actions du clavier.
     *
     * @param tilePane Le TilePane sur lequel les événements de clavier ne seront plus écoutés.
     */
    public void desactiver(TilePane tilePane) {
        tilePane.setOnKeyPressed(null);
        tilePane.setOnKeyReleased(null);
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

