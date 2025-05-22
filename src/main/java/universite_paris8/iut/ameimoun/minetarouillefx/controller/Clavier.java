package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.musique.MusiqueManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;

public class Clavier {
    private final Joueur joueur;
    private final VueJoueur vueJoueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private DebugManager debugManager;

    private boolean enDeplacementGauche = false;
    private boolean enDeplacementDroite = false;

    private AnimationTimer boucleDeplacement;

    public Clavier(Joueur joueur, VueJoueur vueJoueur, Inventaire inventaire, VueInventaire vueInventaire, DebugManager debugManager) {
        this.vueJoueur = vueJoueur;
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueInventaire = vueInventaire;
        this.debugManager = debugManager;

        initialiserBoucleDeplacement();
    }

    private void initialiserBoucleDeplacement() {
        boucleDeplacement = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (enDeplacementGauche) {
                    joueur.deplacerGauche();
                }
                if (enDeplacementDroite) {
                    joueur.deplacerDroite();
                }
            }
        };
        boucleDeplacement.start();
    }

    // Gestion des événements clavier
    public void gestionClavier(TilePane tilePane) {
        tilePane.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            switch (code) {
                case Z, SPACE, UP:
                    joueur.sauter();
                    MusiqueManager.getInstance().jouerMusiqueSaut();
                    break;

                case Q, LEFT:
                    enDeplacementGauche = true;
                    break;

                case D, RIGHT:
                    enDeplacementDroite = true;
                    break;

                case S, DOWN:
                    // s'accroupir
                    break;

                case F3:
                    debugManager.toggle();
                    break;
            }

            String caractere = event.getText();

            // Inventaire
            switch (caractere) {
                case "&": inventaire.setSelectedIndex(0); break;
                case "é": inventaire.setSelectedIndex(1); break;
                case "\"": inventaire.setSelectedIndex(2); break;
                case "'": inventaire.setSelectedIndex(3); break;
                case "(": inventaire.setSelectedIndex(4); break;
                case "-": inventaire.setSelectedIndex(5); break;
                case "è": inventaire.setSelectedIndex(6); break;
                case "_": inventaire.setSelectedIndex(7); break;
                case "ç": inventaire.setSelectedIndex(8); break;
                default: break;
            }

            vueInventaire.mettreAJourAffichage();
        });

        tilePane.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();

            switch (code) {
                case Q, LEFT:
                    enDeplacementGauche = false;
                    if (!enDeplacementDroite) {
                        joueur.arreterMouvementX();
                    }
                    break;

                case D, RIGHT:
                    enDeplacementDroite = false;
                    if (!enDeplacementGauche) {
                        joueur.arreterMouvementX();
                    }
                    break;

                case Z, SPACE, UP:
                    // rien ici, le saut est instantané
                    break;
            }
        });
    }

    // Méthode pour désactiver les événements clavier
    public void desactiverClavier(TilePane tilePane) {
        tilePane.setOnKeyPressed(null);
        tilePane.setOnKeyReleased(null);
        boucleDeplacement.stop();
    }
}
