package universite_paris8.iut.ameimoun.minetarouillefx.controller;


import javafx.scene.layout.TilePane;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;


public class Clavier {

    private Joueur joueur;
    private VueJoueur vueJoueur;

    private boolean enDeplacementGauche = false;

    private boolean enDeplacementDroite = false;


    public Clavier(Joueur joueur, VueJoueur vueJoueur) {
        this.vueJoueur = vueJoueur;
        this.joueur = joueur;

    }


    public void gestionClavier(TilePane tilePane) {

//appuis sur les touches

        tilePane.setOnKeyPressed(event -> {

            switch(event.getCode()) {

                case Z:
                    joueur.sauter();
                    vueJoueur.miseAJourPosition(joueur);
                    break;

                case Q:
                    enDeplacementGauche = true;
                    joueur.deplacerGauche();
                    vueJoueur.miseAJourPosition(joueur);
                    break;

                case D:
                    enDeplacementDroite = true;
                    joueur.deplacerDroite();
                    vueJoueur.miseAJourPosition(joueur);
                    break;

                case S:

// s'accroupir

                    break;

            }

        });

//relâchement des touches (faire deux méthodes distinctes ?)

        tilePane.setOnKeyReleased(event -> {

            switch(event.getCode()) {
                case Z:
                    if(joueur.getPeutSauter()){
                        joueur.sauter();
                        vueJoueur.miseAJourPosition(joueur);
                    }
                case Q:
                    enDeplacementGauche = false;

                    if (!enDeplacementDroite) {
                        joueur.arreterMouvementX();
                        vueJoueur.miseAJourPosition(joueur);
                    }

                    break;

                case D:
                    enDeplacementDroite = false;
                    if (!enDeplacementGauche) {
                        joueur.arreterMouvementX();
                        vueJoueur.miseAJourPosition(joueur);
                    }
                    break;

            }

        });


    }

}