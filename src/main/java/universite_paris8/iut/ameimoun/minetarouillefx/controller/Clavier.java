package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
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


    public Clavier(Joueur joueur, VueJoueur vueJoueur, Inventaire inventaire, VueInventaire vueInventaire, DebugManager debugManager) {
        this.vueJoueur = vueJoueur;
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueInventaire = vueInventaire;
        this.debugManager = debugManager;
    }

    public void gestionClavier(TilePane tilePane) {
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

                case F3 : debugManager.toggle();
                break;
            }


            String caractere = event.getText();

            switch (caractere) {
                case "&":
                    inventaire.setSelectedIndex(0);
                    break;
                case "é":
                    inventaire.setSelectedIndex(1);
                    break;
                case "\"":
                    inventaire.setSelectedIndex(2);
                    break;
                case "'":
                    inventaire.setSelectedIndex(3);
                    break;
                case "(":
                    inventaire.setSelectedIndex(4);
                    break;
                case "-":
                    inventaire.setSelectedIndex(5);
                    break;
                case "è":
                    inventaire.setSelectedIndex(6);
                    break;
                case "_":
                    inventaire.setSelectedIndex(7);
                    break;
                case "ç":
                    inventaire.setSelectedIndex(8);
                    break;
                default:
                    break;
            }
            vueInventaire.mettreAJourAffichage();
        });

//relâchement des touches (faire deux méthodes distinctes ?)
        tilePane.setOnKeyReleased(event -> {

            switch(event.getCode()) {
                case Z:
                    if(joueur.getPeutSauter()){
                        joueur.sauter();
                        vueJoueur.miseAJourPosition(joueur);
                    }
                    break;
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

    public void desactiverClavier(TilePane tilePane) {
            tilePane.setOnKeyPressed(null);
            tilePane.setOnKeyReleased(null);
    }
}