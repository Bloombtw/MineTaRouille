package universite_paris8.iut.ameimoun.minetarouillefx.controller;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.MobManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import java.net.URL;
import java.util.ResourceBundle;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.souris.SourisListener;

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
    private MobManager mobManager;
    private MusiqueManager musiqueManager;
    private VueMob vueMob;
    private Mob mob;
    private GestionnaireItem gestionnaireItem;
    private GestionnaireInventaire gestionnaireInventaire;
    private GestionnaireControles gestionnaireControles;
    private GestionnaireMobHostile gestionnaireMobHostile;
    private GestionnaireMob gestionnaireMob;
    private GestionnaireVie gestionnaireVie;
    private GestionnaireMort gestionnaireMort;
    private GestionnaireSon gestionnaireSon;
    private GestionnaireFleche gestionnaireFleche;
    private boolean jeuEstEnPause = false;


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
        initialiserMob();
        initialiserMobHostile();
        initialiserGestionnaireFleche();
        initialiserControles();
        initialiserMusique();
        initialiserControles();
        initialiserVueCraft();
        initialiserGestionnaireMort();
        initialiserGestionnaireVie();
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
        VueCraft vuecraft = new VueCraft(gestionnaireCraft, rootPane, this, tileMap);
        CraftController craftController = new CraftController(gestionnaireCraft, vuecraft);

        vuecraft.setCraftController(craftController);

        if (gestionnaireControles.getSourisListener() != null) {
            gestionnaireControles.getSourisListener().setVueCraft(vuecraft);
            gestionnaireControles.getSourisListener().setCraftController(craftController);
        }

        if (gestionnaireControles.getClavierListener() != null) {
            craftController.setClavierListener(gestionnaireControles.getClavierListener());
        }

        craftController.initialiserListeners();
    }


    private void initialiserJoueur() {
        joueurModele = new Joueur();
        mobManager= new MobManager();
        debugManager = new DebugManager(rootPane, joueurModele, mobManager.getMobs());
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getNode());
        joueurVue.mettreAJourObjetTenu(null);
    }

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
        gestionnaireControles = new GestionnaireControles(
                joueurModele,
                vueCarte,
                gestionnaireInventaire,
                debugManager,
                gestionnaireItem,
                new SourisListener(
                        joueurModele,
                        gestionnaireInventaire.getInventaire(),
                        vueCarte,
                        gestionnaireItem,
                        gestionnaireMobHostile,
                        gestionnaireMob,
                        gestionnaireFleche,
                        gestionnaireInventaire.getVueInventaire()
                )
        );

        gestionnaireControles.getClavierListener().setJeuController(this);
        gestionnaireControles.getClavierListener().lier(tileMap);
    }
    private void initialiserMob() {
        gestionnaireMob = new GestionnaireMob(gestionnaireItem);
        Mob mob1 = gestionnaireMob.ajouterMob(null, 200, rootPane);
        Mob mob2 = gestionnaireMob.ajouterMob(null, 400, rootPane);
        mobManager.ajouterMob(mob1);
        mobManager.ajouterMob(mob2);
    }

    private void initialiserMobHostile() {
        gestionnaireMobHostile = new GestionnaireMobHostile();
        MobHostile mob1 = gestionnaireMobHostile.ajouterMob(joueurModele, 200, rootPane);
        MobHostile mob2 =  gestionnaireMobHostile.ajouterMob(joueurModele, 400, rootPane);
        mobManager.ajouterMob(mob1);
        mobManager.ajouterMob(mob2);
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

    private void initialiserGestionnaireFleche() {
        gestionnaireFleche = new GestionnaireFleche(rootPane, gestionnaireMob, gestionnaireMobHostile);
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

    private void mettreAJourJeu() {
        if (jeuEstEnPause) {
            return; // Si le jeu est en pause, on ne met pas à jour l'état du jeu.
        }

        // Mise à jour de la gravité et des collisions pour le joueur
        joueurModele.gravite();

        // Mise à jour de la vie du joueur
        gestionnaireVie.mettreAJour(gameLoop);

        // Mise à jour des mobs passifs
        if (gestionnaireMob != null) {
            gestionnaireMob.mettreAJour();
        }

        // Mise à jour des mobs hostiles
        gestionnaireMobHostile.mettreAJour();

        // Mise à jour des items au sol
        gestionnaireItem.miseAJourItemAuSol(
                joueurModele,
                gestionnaireInventaire.getInventaire(),
                gestionnaireInventaire.getVueInventaire()
        );

        // Mise à jour des flèches
        if (gestionnaireFleche != null) {
            gestionnaireFleche.mettreAJour();
        }

        // Mise à jour du mode debug
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
