package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.scene.layout.GridPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class Clavier {
    private Joueur joueur;
    private boolean enDeplacementGauche = false;
    private boolean enDeplacementDroite = false;

    public Clavier(Joueur joueur) {
        this.joueur = joueur;
    }

    public void gestionClavier(GridPane gridPane) {
        // Gestion des appuis sur les touches
        gridPane.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case Z:
                    joueur.sauter();
                    break;
                case Q:
                    enDeplacementGauche = true;
                    joueur.deplacerGauche();
                    break;
                case D:
                    enDeplacementDroite = true;
                    joueur.deplacerDroite();
                    break;
                case S:
                    // Optionnel: s'accroupir ou tomber plus vite
                    break;
            }
        });

        // Gestion du relâchement des touches
        gridPane.setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case Q:
                    enDeplacementGauche = false;
                    if (!enDeplacementDroite) {
                        joueur.arreterMouvementX();
                    }
                    break;
                case D:
                    enDeplacementDroite = false;
                    if (!enDeplacementGauche) {
                        joueur.arreterMouvementX();
                    }
                    break;
            }
        });

        // Focus sur la GridPane pour capter les événements clavier
        gridPane.setFocusTraversable(true);
        gridPane.requestFocus();
    }
}