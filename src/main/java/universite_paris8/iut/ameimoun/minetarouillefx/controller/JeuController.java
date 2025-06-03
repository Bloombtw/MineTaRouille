package universite_paris8.iut.ameimoun.minetarouillefx.controller;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.souris.Souris;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireControles;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireMort;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.AudioManager;
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
    private boolean sonDegatJoue = false;
    private VueMob vueMob;
    private Mob mob;
    private GestionnaireItem gestionnaireItem;
    private GestionnaireInventaire gestionnaireInventaire;
    private GestionnaireControles gestionnaireControles;
    private GestionnaireMort gestionnaireMort;


    // Dans l'ordre : Initialise la carte, le joueur, la barre de vie, l'inventaire, les contrôles.
    // Démarre la boucle de jeu et initialise la musique de fond.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserGestionnaireItem();
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserInventaire();
        initialiserControles();
        initialiserMob();
        initialiserMusique();
        demarrerBoucleDeJeu();
        initialiserGestionnaireMort();
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

    private void initialiserControles() {
        gestionnaireControles = new GestionnaireControles(joueurModele, vueCarte, gestionnaireInventaire, debugManager);
        gestionnaireControles.getSourisListener().setJeuController(this);
        gestionnaireControles.initialiserControles();
    }

    private void initialiserInventaire() {
        gestionnaireInventaire = new GestionnaireInventaire(rootPane, joueurVue);
        gestionnaireInventaire.initialiserInventaire();
    }

    private void initialiserGestionnaireItem() {
        gestionnaireItem = new GestionnaireItem(rootPane);
    }

    // Démarre la boucle de jeu qui met à jour l'état du jeu à chaque frame.
    private void demarrerBoucleDeJeu() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJourJeu();
                gererVie();
            }
        };
        gameLoop.start();
    }

    // Met à jour l'état du jeu, gère la gravité, les collisions et les alertes de vie.
    private void mettreAJourJeu() {
        joueurModele.gravite();
        gererVie();
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

    private void initialiserMob() {
        mob = new Mob();
        vueMob = new VueMob(mob);
        rootPane.getChildren().add(vueMob.getNode());
    }

    // Gère la vie du joueur, vérifie les dégâts, joue les alertes de vie basse et gère la mort.
    private void gererVie() {
        vie.verifierDegats(joueurModele, Carte.getInstance());
        gererAlerteVieBasse();
        gestionnaireMort.gererMort(gameLoop);
    }



    // Joue une alerte sonore si la vie du joueur est basse, et arrête de jouer si la vie redevient normale.
    private void gererAlerteVieBasse() {
        boolean vieLow = joueurModele.getVie().estLow();
        if (vieLow && !sonDegatJoue) {
            sonDegatJoue = true;
            AudioManager.getInstance().jouerAlerteVieBasse();
        } else if (!vieLow) {
            sonDegatJoue = false;
        }
    }

    private void initialiserMusique() {
        musiqueManager = MusiqueManager.getInstance();
        musiqueManager.jouerMusiqueFond();
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


    private void initialiserCarte() {
        vueCarte = new VueCarte(Carte.getInstance());
        tileMap.getChildren().add(vueCarte.getTileMap());
    }

    public void spawnItemAuSol(Item item, int x, int y) {
        gestionnaireItem.spawnItemAuSol(item, x, y);
    }
}
