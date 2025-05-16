package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class Clavier {
    private Joueur joueur;
    private JeuController controller;
    private boolean enDeplacementGauche = false;
    private boolean enDeplacementDroite = false;

    public Clavier(Joueur joueur, JeuController controller) {
        this.joueur = joueur;
        this.controller = controller;
    }

    public void gestionClavier(TilePane gridPane) {
        // Gestion des appuis sur les touches
        gridPane.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case Z, SPACE -> joueur.sauter();
                case Q -> {
                    enDeplacementGauche = true;
                    joueur.deplacerGauche();
                }
                case D -> {
                    enDeplacementDroite = true;
                    joueur.deplacerDroite();
                }
                case S -> { // ?
                }
                case F3 -> controller.toggleDebug();
            }
        });

        // Gestion du relâchement des touches
        gridPane.setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case Q -> {
                    enDeplacementGauche = false;
                    if (!enDeplacementDroite) joueur.arreterMouvementX();
                }
                case D -> {
                    enDeplacementDroite = false;
                    if (!enDeplacementGauche) joueur.arreterMouvementX();
                }
            }
        });
    }
}
