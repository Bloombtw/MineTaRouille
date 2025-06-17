package universite_paris8.iut.ameimoun.minetarouillefx.controller;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.*;
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
    private VueVie vueVie;
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


    // Dans l'ordre : Initialise la carte, le joueur, la barre de vie, l'inventaire, les contrôles.
    // Démarre la boucle de jeu et initialise la musique de fond.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserGestionnaireItem();
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserGestionnaireSon();
        initialiserGestionnaireMort();
        initialiserGestionnaireVie();
        initialiserInventaire();
        initialiserMob();
        initialiserMobHostile();
        initialiserGestionnaireFleche();
        initialiserControles();
        initialiserMusique();
        demarrerBoucleDeJeu();
    }

    /*
        INITIALISATIONS
    */

    private void initialiserCarte() {
        vueCarte = new VueCarte(Carte.getInstance());
        tileMap.getChildren().add(vueCarte.getTileMap());
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
        // Obtenez l'inventaire et la vueInventaire du gestionnaire d'inventaire
        Inventaire inventaire = gestionnaireInventaire.getInventaire();
        VueInventaire vueInventaire = gestionnaireInventaire.getVueInventaire();

        // Créer l'instance de SourisListener en passant les gestionnaires de mobs
        SourisListener sourisListener = new SourisListener(
                joueurModele,
                inventaire,
                vueCarte,
                vueInventaire,
                gestionnaireItem,
                gestionnaireMobHostile, // Passe le gestionnaire de mobs hostiles
                gestionnaireMob,
                gestionnaireFleche
        );

        // Initialiser GestionnaireControles avec le SourisListener créé
        gestionnaireControles = new GestionnaireControles(
                joueurModele,
                vueCarte,
                gestionnaireInventaire,
                debugManager,
                gestionnaireItem,
                sourisListener // Passe le SourisListener créé
        );
        // La ligne suivante n'est plus nécessaire car JeuController n'est plus directement lié
        // gestionnaireControles.getSourisListener().setJeuController(this);
        gestionnaireControles.initialiserControles();
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
        gestionnaireMobHostile.ajouterMob(joueurModele, 200, rootPane);
        gestionnaireMobHostile.ajouterMob(joueurModele, 400, rootPane);
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

    // Met à jour l'état du jeu, gère la gravité, les collisions et les alertes de vie.
    private void mettreAJourJeu() {
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
        gestionnaireMobHostile.mettreAJour();
        if (gestionnaireMob != null) {
            gestionnaireMob.mettreAJour();
        }

        if (debugManager.isDebugVisible()) {
            debugManager.update();
        }
        if (gestionnaireFleche != null) {
            gestionnaireFleche.mettreAJour();
        }
    }

}