package universite_paris8.iut.ameimoun.minetarouillefx.controller;


import javafx.scene.layout.TilePane;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;


public class Clavier {

    private Joueur joueur;

    private boolean enDeplacementGauche = false;

    private boolean enDeplacementDroite = false;


    public Clavier(Joueur joueur) {

        this.joueur = joueur;

    }


    public void gestionClavier(TilePane tilePane) {

// Gestion des appuis sur les touches

        tilePane.setOnKeyPressed(event -> {

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

// s'accroupir

                    break;

            }

        });


// Gestion du relâchement des touches

        tilePane.setOnKeyReleased(event -> {

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


    }

}