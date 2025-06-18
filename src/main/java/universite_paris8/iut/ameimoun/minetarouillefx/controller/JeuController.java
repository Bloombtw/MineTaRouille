package universite_paris8.iut.ameimoun.minetarouillefx.controller;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
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


    /**
     * Méthode d'initialisation appelée lors du chargement de la vue.
     * Elle initialise les différents composants du jeu tels que la carte, le joueur, l'inventaire, etc.
     * Démarre également la boucle de jeu qui met à jour l'état du jeu à chaque frame.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserGestionnaireItem();
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserGestionnaireSon();
        initialiserMusique();
        initialiserInventaire();
        initialiserControles();
        initialiserVueCraft();
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

    /**
     * Initialise la vue de craft en créant une instance de VueCraft et en l'associant au gestionnaire de craft.
     * Configure également les contrôleurs de souris et de clavier pour interagir avec la vue de craft.
     */
    private void initialiserVueCraft() {
        GestionnaireCraft gestionnaireCraft = new GestionnaireCraft(gestionnaireInventaire.getInventaire());
        VueCraft vuecraft = new VueCraft(gestionnaireCraft, rootPane, this, tileMap);
        CraftController craftController = new CraftController(gestionnaireCraft, vuecraft);

        vuecraft.setCraftController(craftController);

        if (gestionnaireControles.getSourisListener() != null) {
            gestionnaireControles.getSourisListener().setVueCraft(vuecraft);
            gestionnaireControles.getSourisListener().setcraftController(craftController);
        }

        if (gestionnaireControles.getClavierListener() != null) {
            craftController.setClavierListener(gestionnaireControles.getClavierListener());
        }

        craftController.initialiserListeners();
    }


    /**
     * Initialise le joueur, la vue du joueur et le gestionnaire de débogage.
     * Ajoute la vue du joueur au rootPane et met à jour l'objet tenu par le joueur à l'initialisation.
     */
    private void initialiserJoueur() {
        joueurModele = new Joueur();
        debugManager = new DebugManager(rootPane, joueurModele, mob);
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getNode());
        joueurVue.mettreAJourObjetTenu(null);
    }

    /**
     * Initialise la barre de vie du joueur en créant une instance de VueVie et en l'ajoutant au rootPane.
     * La barre de vie est initialisée avec la vie actuelle du joueur.
     */
    private void initialiserBarreDeVie() {
        vie = joueurModele.getVie();
        VueVie vueVie = new VueVie(vie, rootPane);
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
        gestionnaireControles = new GestionnaireControles(joueurModele, vueCarte, gestionnaireInventaire, debugManager, gestionnaireItem);
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

    /**
     * Met à jour l'état du jeu, y compris la gravité du joueur, la gestion de la vie, et les mises à jour des mobs et des items.
     * Si le jeu est en pause, cette méthode ne fait rien.
     */
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

    public GestionnaireInventaire getGestionnaireInventaire() {
        return gestionnaireInventaire;
    }
}
