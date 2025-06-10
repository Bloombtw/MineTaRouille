package universite_paris8.iut.ameimoun.minetarouillefx.controller;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.*;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import java.net.URL;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML
    private TilePane tileMap;
    @FXML
    private AnchorPane rootPane;
    private Vie vie;
    private VueVie vueVie;
    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private AnimationTimer gameLoop;
    private VueCarte vueCarte;
    private DebugManager debugManager;
    private MusiqueManager musiqueManager;
    private VueMob vueMob;
    private Mob mob;
    private GestionnaireItem gestionnaireItem;
    private GestionnaireInventaire gestionnaireInventaire;
    private GestionnaireControles gestionnaireControles;
    private GestionnaireVie gestionnaireVie;
    private GestionnaireMort gestionnaireMort;
    private GestionnaireSon gestionnaireSon;
    private boolean jeuEstEnPause = false;
    private VueCraft vuecraft;

    // Dans l'ordre : Initialise la carte, le joueur, la barre de vie, l'inventaire, les contrôles.
    // Démarre la boucle de jeu et initialise la musique de fond.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserGestionnaireItem();
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserGestionnaireSon();
        initialiserMusique();
        initialiserInventaire();
        initialiserVueCraft();
        initialiserControles();
        initialiserGestionnaireMort();
        initialiserGestionnaireVie();
        initialiserMob();
        demarrerBoucleDeJeu();
    }

    /*
        INITIALISATIONS
     */


    private void initialiserCarte() {
        vueCarte = new VueCarte(Carte.getInstance());
        tileMap.getChildren().add(vueCarte.getTileMap());
    }

    private void initialiserVueCraft() {
        GestionnaireCraft gestionnaireCraft = new GestionnaireCraft(gestionnaireInventaire.getInventaire());
        vuecraft = new VueCraft(gestionnaireCraft, rootPane);
    }

    private void initialiserJoueur() {
        joueurModele = new Joueur();
        debugManager = new DebugManager(rootPane, joueurModele, mob);
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getNode());
        joueurVue.mettreAJourObjetTenu(null);
    }

    private void initialiserBarreDeVie() {
        vie = joueurModele.getVie();
        vueVie = new VueVie(vie, rootPane);
        rootPane.getChildren().add(vueVie.getNoeudBarreVie());
        rootPane.getChildren().add(0, vueVie.getOverlayDegatsGlobal());
    }

    private void initialiserInventaire() {
        gestionnaireInventaire = new GestionnaireInventaire(rootPane, joueurVue);
        gestionnaireInventaire.initialiserInventaire();
    }

    private void initialiserGestionnaireItem() {
        gestionnaireItem = new GestionnaireItem(rootPane);
    }

    private void initialiserGestionnaireSon() {
        gestionnaireSon = new GestionnaireSon(joueurModele);
    }

    private void initialiserControles() {
        gestionnaireControles = new GestionnaireControles(joueurModele, vueCarte, gestionnaireInventaire, debugManager, gestionnaireItem, vuecraft);
        gestionnaireControles.getSourisListener().setJeuController(this);
        gestionnaireControles.getClavierListener().setJeuController(this);
        gestionnaireControles.getClavierListener().lier(tileMap);
        gestionnaireControles.initialiserControles();
    }

    private void initialiserMob() {
        mob = new Mob();
        vueMob = new VueMob(mob);
        rootPane.getChildren().add(vueMob.getNode());
    }

    private void initialiserMusique() {
        musiqueManager = MusiqueManager.getInstance();
        musiqueManager.jouerMusiqueFond();
    }

    private void initialiserGestionnaireMort() {
        gestionnaireMort = new GestionnaireMort(
                joueurModele,
                vie,
                musiqueManager,
                rootPane,
                gestionnaireControles,
                vueCarte
        );
    }

    private void  initialiserGestionnaireVie() {
        gestionnaireVie = new GestionnaireVie(
                joueurModele,
                gestionnaireSon,
                gestionnaireMort,
                vie
        );
    }

    /*
        BOUCLES DE JEU
     */

    // Démarre la boucle de jeu qui met à jour l'état du jeu à chaque frame.
    private void demarrerBoucleDeJeu() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJourJeu();
            }
        };
        gameLoop.start();
    }

    // Met à jour l'état du jeu, gère la gravité, les collisions et les alertes de vie.
    private void mettreAJourJeu() {
        if (jeuEstEnPause) {
            return; // Si le jeu est en pause, on ne met pas à jour l'état du jeu.
        }
        joueurModele.gravite();
        gestionnaireVie.mettreAJour(gameLoop);
        if (mob != null && vueMob != null) {
            mob.mettreAJour();
        }
        gestionnaireItem.update(
                joueurModele,
                gestionnaireInventaire.getInventaire(),
                gestionnaireInventaire.getVueInventaire()
        );
        if (debugManager.isDebugVisible()) {
            debugManager.update();
        }
    }

    public void mettreEnPauseJeu() {
        jeuEstEnPause = true;
    }

    public void reprendreJeu() {
        jeuEstEnPause = false;
    }


    public boolean isEnPause() {
        return jeuEstEnPause;
    }
}
