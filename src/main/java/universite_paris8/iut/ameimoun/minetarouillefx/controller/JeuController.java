package universite_paris8.iut.ameimoun.minetarouillefx.controller;
import javafx.animation.AnimationTimer;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.souris.Souris;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.AudioManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import javafx.application.Platform;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML
    private TilePane tileMap;
    @FXML
    private AnchorPane rootPane;
    private Inventaire inventaire;
    private VueInventaire vueInventaire;
    private ClavierListener clavierListener;
    private Vie vie;
    private VueVie vueVie;
    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private AnimationTimer gameLoop;
    private VueCarte vueCarte;
    private DebugManager debugManager;
    private MusiqueManager musiqueManager;
    private Souris sourisListener;
    private final List<Item> itemsAuSol = new ArrayList<>();
    private final List<VueItem> vuesItemsAuSol = new ArrayList<>();
    private boolean sonDegatJoue = false;
    private VueMob vueMob;
    private Mob mob;


    // Dans l'ordre : Initialise la carte, le joueur, la barre de vie, l'inventaire, les contrôles.
    // Démarre la boucle de jeu et initialise la musique de fond.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserInventaire();
        initialiserControles();
        initialiserMob();
        demarrerBoucleDeJeu();
        initialiserMusique();
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
        mettreAJourItemsAuSol();
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
        gererMort();
    }

    // Vérifie si le joueur est mort, arrête la musique et affiche l'écran de mort.
    private void gererMort() {
        if (joueurModele.getVie().estMort() && vie.estMort()) {
            joueurModele.getVie().setEstEnVie(false);
            musiqueManager.arreterMusique();
            AudioManager.getInstance().arreterTousLesSons();
            sonDegatJoue = false;
            AudioManager.getInstance().jouerAlerteMort();
            gameLoop.stop();
            clavierListener.desactiver(tileMap);
            sourisListener.desactiver(tileMap);
            afficherEcranDeMort();
        }
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

    // Affiche l'écran de mort en chargeant le FXML.
    private void afficherEcranDeMort() {
        Parent overlayDeMort = Loader.load(Chemin.FXML_ECRAN_MORT);
        if (overlayDeMort != null) {
            rootPane.getChildren().add(overlayDeMort);
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

    private void initialiserInventaire() {
        inventaire = new Inventaire();
        inventaire.ajouterItem(new Item(Objet.EPEE));
        inventaire.ajouterItem(new Item(Bloc.PIERRE, 32));
        vueInventaire = new VueInventaire(inventaire);
        AnchorPane.setTopAnchor(vueInventaire, 10.0);
        AnchorPane.setRightAnchor(vueInventaire, 10.0);
        rootPane.getChildren().add(vueInventaire);
        joueurVue.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
        inventaire.selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            joueurVue.mettreAJourObjetTenu(inventaire.getItem(newVal.intValue()));
        });
        inventaire.getSlots().addListener((ListChangeListener.Change<? extends Item> change) -> {
            // Met à jour l'objet tenu dans la main à chaque changement de la liste
            joueurVue.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
        });
    }

    private void initialiserControles() {
        clavierListener = new ClavierListener(joueurModele, inventaire, vueInventaire, debugManager);
        sourisListener = new Souris(joueurModele, inventaire,vueCarte,vueInventaire);

        clavierListener.lier(tileMap);
        sourisListener.lier(tileMap);
        sourisListener.setJeuController(this);


        Platform.runLater(() -> {
            tileMap.setFocusTraversable(true);
            sourisListener.lierScrollInventaire(tileMap.getScene());
            tileMap.requestFocus();
        });
    }

    private void initialiserCarte() {
        vueCarte = new VueCarte(Carte.getInstance());
        tileMap.getChildren().add(vueCarte.getTileMap());
    }

    // Met à jour la position des items au sol, applique la gravité et gère les collisions.
    // Utilise les méthodes appliquerGravite, gererCollisionSol et detecterRamassage.
    private void mettreAJourItemsAuSol() {
        Iterator<Item> itemIterator = itemsAuSol.iterator();
        Iterator<VueItem> vueIterator = vuesItemsAuSol.iterator();

        while (itemIterator.hasNext() && vueIterator.hasNext()) {
            Item item = itemIterator.next();
            VueItem vue = vueIterator.next();

            appliquerGravite(item);
            gererCollisionSol(item);
            vue.updatePosition(item);

            if (detecterRamassage(item, joueurModele)) {

                inventaire.ajouterItem(item);
                vueInventaire.mettreAJourAffichage();

                rootPane.getChildren().remove(vue.getImageView());
                itemIterator.remove();
                vueIterator.remove();
            }
        }


    }


    // Gère la collision d'un item avec le sol
    private void gererCollisionSol(Item item) {
        int x = (int)(item.getX() / Constantes.TAILLE_TUILE);
        int y = (int)((item.getY() + Constantes.TAILLE_ITEM) / Constantes.TAILLE_TUILE);
        if (Carte.getInstance().estBlocSolide(x, y)) {
            item.setY((y * Constantes.TAILLE_TUILE) - (Constantes.TAILLE_ITEM / 2.0));
        }
    }

    // Détecte si un item est ramassé par le joueur
    private boolean detecterRamassage(Item item, Joueur joueur) {
        double itemGauche = item.getX();
        double itemDroite = item.getX() + Constantes.TAILLE_ITEM;
        double itemHaut = item.getY();
        double itemBas = item.getY() + Constantes.TAILLE_ITEM;

        double joueurGauche = joueur.getX();
        double joueurDroite = joueur.getX() + Constantes.TAILLE_PERSO;
        double joueurHaut = joueur.getY();
        double joueurBas = joueur.getY() + Constantes.TAILLE_PERSO;

        boolean collisionX = itemDroite > joueurGauche && itemGauche < joueurDroite;
        boolean collisionY = itemBas > joueurHaut && itemHaut < joueurBas;

        return collisionX && collisionY;
    }

    // Applique la gravité à un item, le fait tomber vers le bas
    private void appliquerGravite(Item item) {
        item.setY(item.getY() + Constantes.GRAVITE * 5);
    }

    // fait spawn un item au sol à la position (x, y)
    public void spawnItemAuSol(Item item, int x, int y) {

        item.setX(x * Constantes.TAILLE_TUILE);
        item.setY(y * Constantes.TAILLE_TUILE);

        VueItem vue = new VueItem(item);
        vue.updatePosition(item);

        itemsAuSol.add(item);
        vuesItemsAuSol.add(vue);
        rootPane.getChildren().add(vue.getImageView());
    }
}
