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
        private GestionnaireMobHostile gestionnaireMobHostile;
        private GestionnaireMob gestionnaireMob;
        private GestionnaireSon gestionnaireSon;
        private Mob mobNormal;
        private VueMob vueMobNormal;


        // Dans l'ordre : Initialise la carte, le joueur, la barre de vie, l'inventaire, les contrôles.
        // Démarre la boucle de jeu et initialise la musique de fond.
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            initialiserCarte();
            initialiserGestionnaireItem();
            initialiserJoueur();
            initialiserBarreDeVie();
            initialiserMobHostile();
            initialiserMobPassif();
            initialiserGestionnaireSon();
            initialiserGestionnaireMort();
            initialiserGestionnaireVie();
            initialiserInventaire();
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
            gestionnaireControles = new GestionnaireControles(joueurModele, vueCarte, gestionnaireInventaire, debugManager, gestionnaireItem, this );
            gestionnaireControles.getSourisListener().setJeuController(this);
            gestionnaireControles.initialiserControles();
        }

        private void initialiserMobHostile() {
            gestionnaireMobHostile = new GestionnaireMobHostile();
            gestionnaireMobHostile.ajouterMobHostile(joueurModele, 200, rootPane);
            gestionnaireMobHostile.ajouterMobHostile(joueurModele, 400, rootPane);
        }

        private void initialiserMobPassif() {
            gestionnaireMob = new GestionnaireMob();
            gestionnaireMob.ajouterMob(joueurModele, 400, rootPane);
        }

        public GestionnaireMob getGestionnaireMob() {
            return gestionnaireMob;
        }

        public GestionnaireMobHostile getGestionnaireMobHostile() {
            return gestionnaireMobHostile;
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
            // 1) Gravité & vie joueur
            joueurModele.gravite();
            gestionnaireVie.mettreAJour(gameLoop);

            // 2) Mettre à jour items
            gestionnaireItem.update(
                    joueurModele,
                    gestionnaireInventaire.getInventaire(),
                    gestionnaireInventaire.getVueInventaire()
            );

            // 3) Mobs hostiles
            gestionnaireMobHostile.mettreAJour();

            // 4) Mob passif
            gestionnaireMob.mettreAJour();

            // 5) Debug si nécessaire
            if (debugManager.isDebugVisible()) {
                debugManager.update();
            }
        }


    }
