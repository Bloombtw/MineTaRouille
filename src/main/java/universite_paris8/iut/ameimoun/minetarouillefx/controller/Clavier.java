package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class Clavier {
    private Joueur joueur;
    private boolean enDeplacementGauche = false;
    private boolean enDeplacementDroite = false;

    public Clavier(Joueur joueur) {
        this.joueur = joueur;
        System.out.println("Clavier initialisé avec le joueur : " + joueur.getNom());
    }

    public void gestionClavier(TilePane tilePane) {
        System.out.println("Gestion du clavier activée pour le TilePane.");
        tilePane.setOnKeyPressed(event -> {
            System.out.println("Touche pressée : " + event.getCode());
            switch(event.getCode()) {
                case Z:
                    joueur.setPeutSauter(true);
                    System.out.println("Intention de sauter activée pour le joueur : " + joueur.getNom());
                    break;
                case Q:
                    enDeplacementGauche = true;
                    joueur.setVitesseX(-joueur.getVitesseDeplacement());
                    System.out.println("Déplacement à gauche activé pour le joueur : " + joueur.getNom() + ", vitesseX = " + joueur.getVitesseX());
                    break;
                case D:
                    enDeplacementDroite = true;
                    joueur.setVitesseX(joueur.getVitesseDeplacement());
                    System.out.println("Déplacement à droite activé pour le joueur : " + joueur.getNom() + ", vitesseX = " + joueur.getVitesseX());
                    break;
                case S:
                    System.out.println("Touche S pressée.");
                    break;
            }
        });

        tilePane.setOnKeyReleased(event -> {
            System.out.println("Touche relâchée : " + event.getCode());
            switch(event.getCode()) {
                case Q:
                    enDeplacementGauche = false;
                    if (!enDeplacementDroite) {
                        joueur.setVitesseX(0);
                        System.out.println("Arrêt du déplacement à gauche pour le joueur : " + joueur.getNom() + ", vitesseX = " + joueur.getVitesseX());
                    }
                    break;
                case D:
                    enDeplacementDroite = false;
                    if (!enDeplacementGauche) {
                        joueur.setVitesseX(0);
                        System.out.println("Arrêt du déplacement à droite pour le joueur : " + joueur.getNom() + ", vitesseX = " + joueur.getVitesseX());
                    }
                    break;
            }
        });
        tilePane.setFocusTraversable(true);
        tilePane.requestFocus();
        System.out.println("Focus demandé pour le TilePane.");
    }
}