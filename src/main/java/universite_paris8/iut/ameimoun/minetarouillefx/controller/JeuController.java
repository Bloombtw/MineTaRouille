package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.souris.SourisListener;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobPassif;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.MobManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;

import java.net.URL;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML private AnchorPane rootPane;

    private TilePane tileMap;
    private Pane cameraPane;
    private Group worldGroup;
    private VueCarte vueCarte;
    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private Vie vie;
    private AnimationTimer gameLoop;
    private DebugManager debugManager;
    private MobManager mobManager;
    private MusiqueManager musiqueManager;
    private GestionnaireItem gestionnaireItem;
    private GestionnaireInventaire gestionnaireInventaire;
    private GestionnaireControles gestionnaireControles;
    private GestionnaireMobHostile gestionnaireMobHostile;
    private GestionnaireMobPassif gestionnaireMobPassif;
    private GestionnaireVie gestionnaireVie;
    private GestionnaireMort gestionnaireMort;
    private GestionnaireSon gestionnaireSon;
    private GestionnaireFleche gestionnaireFleche;
    private boolean jeuEstEnPause = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserGestionnaireItem();
        initialiserJoueur();
        initialiserCamera();
        initialiserBarreDeVie();
        initialiserGestionnaireSon();
        initialiserMusique();
        initialiserInventaire();
        initialiserMob();
        initialiserMobHostile();
        initialiserGestionnaireFleche();
        initialiserControles();
        initialiserVueCraft();
        initialiserGestionnaireMort();
        initialiserGestionnaireVie();
        demarrerBoucleDeJeu();
    }

    private void initialiserCarte() {
        vueCarte = new VueCarte(Carte.getInstance());
        tileMap = vueCarte.getTileMap();
    }

    private void initialiserJoueur() {
        joueurModele = new Joueur();
        joueurModele.setX(Constantes.NB_COLONNES / 2 * 30);
        joueurModele.setY(20 * 30);
        joueurVue = new VueJoueur(joueurModele);
        joueurVue.mettreAJourObjetTenu(null);
    }

    private void initialiserCamera() {
        worldGroup = new Group(tileMap, joueurVue.getNode());
        cameraPane = new Pane(worldGroup);
        cameraPane.setPrefSize(1280, 720);
        rootPane.getChildren().add(cameraPane);
    }

    private void mettreAJourCamera() {
        double centreEcranX = cameraPane.getPrefWidth() / 2;
        double centreEcranY = cameraPane.getPrefHeight() / 2;

        double cibleX = joueurModele.getX();
        double cibleY = joueurModele.getY();

        worldGroup.setTranslateX(centreEcranX - cibleX);
        worldGroup.setTranslateY(centreEcranY - cibleY);
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
        Inventaire inventaire = gestionnaireInventaire.getInventaire();
        VueInventaire vueInventaire = gestionnaireInventaire.getVueInventaire();

        SourisListener sourisListener = new SourisListener(
                joueurModele, inventaire, vueCarte, gestionnaireItem,
                gestionnaireMobHostile, gestionnaireMobPassif,
                gestionnaireFleche, vueInventaire
        );

        gestionnaireControles = new GestionnaireControles(
                joueurModele, vueCarte, gestionnaireInventaire,
                debugManager, gestionnaireItem, sourisListener
        );

        gestionnaireControles.getClavierListener().setJeuController(this);
        gestionnaireControles.getClavierListener().lier(tileMap);
        gestionnaireControles.initialiserControles();
    }

    private void initialiserMob() {
        gestionnaireMobPassif = new GestionnaireMobPassif(gestionnaireItem);
        Mob mob1 = gestionnaireMobPassif.ajouterMob(null, 200, rootPane);
        Mob mob2 = gestionnaireMobPassif.ajouterMob(null, 400, rootPane);
        mobManager = new MobManager();
        mobManager.ajouterMob(mob1);
        mobManager.ajouterMob(mob2);
        debugManager = new DebugManager(rootPane, joueurModele, mobManager.getMobs());
    }

    private void initialiserMobHostile() {
        gestionnaireMobHostile = new GestionnaireMobHostile();
        gestionnaireMobHostile.ajouterMob(joueurModele, 200, rootPane);
        gestionnaireMobHostile.ajouterMob(joueurModele, 400, rootPane);
    }

    private void initialiserMusique() {
        musiqueManager = MusiqueManager.getInstance();
        musiqueManager.jouerMusiqueFond();
    }

    private void initialiserGestionnaireMort() {
        gestionnaireMort = new GestionnaireMort(
                joueurModele, vie, musiqueManager,
                rootPane, gestionnaireControles, vueCarte
        );
    }

    private void initialiserGestionnaireVie() {
        gestionnaireVie = new GestionnaireVie(
                joueurModele, gestionnaireSon,
                gestionnaireMort, vie
        );
    }

    private void initialiserGestionnaireFleche() {
        gestionnaireFleche = new GestionnaireFleche(
                rootPane, gestionnaireMobPassif, gestionnaireMobHostile
        );
    }

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
        if (jeuEstEnPause) return;

        joueurModele.gravite();
        gestionnaireVie.mettreAJour(gameLoop);

        if (gestionnaireMobPassif != null) {
            gestionnaireMobPassif.mettreAJour();
        }

        gestionnaireMobHostile.mettreAJour();

        gestionnaireItem.update(
                joueurModele,
                gestionnaireInventaire.getInventaire(),
                gestionnaireInventaire.getVueInventaire()
        );

        if (gestionnaireFleche != null) {
            gestionnaireFleche.mettreAJour();
        }

        if (debugManager.isDebugVisible()) {
            debugManager.update();
        }

        mettreAJourCamera();
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
