package universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;

public class ClavierListener {
    private final DeplacementManager deplacementManager;
    private final Joueur joueur;
    private final GestionnaireInventaire gestionnaireInventaire;
    private final DebugManager debugManager;

    public ClavierListener(Joueur joueur, DebugManager debugManager, GestionnaireInventaire gestionnaireInventaire) {
        this.joueur = joueur;
        this.gestionnaireInventaire = gestionnaireInventaire;
        this.debugManager = debugManager;
        this.deplacementManager = new DeplacementManager(joueur);
    }

    public void lier(TilePane tilePane) {
        tilePane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z, SPACE, UP -> {
                    joueur.sauter();
                    MusiqueManager.getInstance();
                }
                case Q, LEFT -> deplacementManager.setEnDeplacementGauche(true);
                case D, RIGHT -> deplacementManager.setEnDeplacementDroite(true);
                case A -> gestionnaireInventaire.jeterItemSelectionne(joueur); // Appel pour jeter un item
                case F3 -> debugManager.toggle();
            }
            gererSelectionInventaire(event.getText());
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
        // Accès à l'inventaire via GestionnaireInventaire
        var inventaire = gestionnaireInventaire.getInventaire();
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