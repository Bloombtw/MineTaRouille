package universite_paris8.iut.ameimoun.minetarouillefx.environnement;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.CraftController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.MobManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;

/**
 * La classe Environnement gère l'ensemble des éléments du jeu,
 * y compris la carte, le joueur, les mobs, les items et les gestionnaires associés.
 * Elle est responsable de l'initialisation et de la mise à jour de ces éléments.
 */
public class Environnement {
    private final Carte carte;
    private final VueCarte vueCarte;
    private final Joueur joueur;
    private final VueVie vueVie;
    private final VueJoueur vueJoueur;
    private final Group worldGroup;
    private final Region rootPane;

    // Gestionnaires
    private final GestionnaireItem gestionnaireItem;
    private final GestionnaireInventaire gestionnaireInventaire;
    private final GestionnaireMobPassif gestionnaireMobPassif;
    private final GestionnaireMobHostile gestionnaireMobHostile;
    private final GestionnaireFleche gestionnaireFleche;
    private final GestionnaireSon gestionnaireSon;
    private final GestionnaireVie gestionnaireVie;
    private final GestionnaireMort gestionnaireMort;

    // Debug / Mobs
    private final MobManager mobManager;
    private final DebugManager debugManager;

    public Environnement(Group worldGroup, Region rootPane) {
        this.worldGroup = worldGroup;
        this.rootPane = rootPane;

        // Sous-méthodes d'initialisation
        this.carte = initialiserCarte();
        this.vueCarte = initialiserVueCarte();

        this.joueur = initialiserJoueur();
        this.vueVie = initialiserVueVie();
        this.vueJoueur = initialiserVueJoueur();

        this.gestionnaireItem = initialiserGestionnaireItem();
        this.gestionnaireInventaire = initialiserGestionnaireInventaire();
        this.gestionnaireMobPassif = initialiserGestionnaireMobPassif();
        this.gestionnaireMobHostile = initialiserGestionnaireMobHostile();
        this.gestionnaireFleche = initialiserGestionnaireFleche();
        this.gestionnaireSon = initialiserGestionnaireSon();
        this.gestionnaireMort = initialiserGestionnaireMort();
        this.gestionnaireVie = initialiserGestionnaireVie();

        this.mobManager = new MobManager();
        this.debugManager = initialiserDebugManager();

        // Assemblage final
        assemblerElements();
    }

    /* ------------------------------------------------------------------------
     * SOUS-MÉTHODES D’INITIALISATION
     * ------------------------------------------------------------------------ */

    private Carte initialiserCarte() {
        return Carte.getInstance();
    }

    private VueCarte initialiserVueCarte() {
        return new VueCarte(carte);
    }

    private Joueur initialiserJoueur() {
        Joueur joueur = new Joueur();
        int colonneDepart = 50;
        int ligneSol = trouverHauteurSol(colonneDepart);
        joueur.setX(colonneDepart * Constantes.TAILLE_TUILE);
        joueur.setY((ligneSol - 2) * Constantes.TAILLE_TUILE);
        return joueur;
    }

    private VueVie initialiserVueVie() {
        return new VueVie(joueur.getVie(), rootPane);
    }

    private VueJoueur initialiserVueJoueur() {
        return new VueJoueur(joueur);
    }

    private GestionnaireItem initialiserGestionnaireItem() {
        return new GestionnaireItem(worldGroup);
    }

    private GestionnaireInventaire initialiserGestionnaireInventaire() {
        GestionnaireInventaire inv = new GestionnaireInventaire((AnchorPane) rootPane, vueJoueur);
        inv.initialiserInventaire();
        return inv;
    }

    private GestionnaireMobPassif initialiserGestionnaireMobPassif() {
        return new GestionnaireMobPassif(gestionnaireItem);
    }

    private GestionnaireMobHostile initialiserGestionnaireMobHostile() {
        return new GestionnaireMobHostile(gestionnaireItem);
    }

    private GestionnaireFleche initialiserGestionnaireFleche() {
        return new GestionnaireFleche(worldGroup, gestionnaireMobPassif, gestionnaireMobHostile);
    }

    private GestionnaireSon initialiserGestionnaireSon() {
        return new GestionnaireSon(joueur);
    }

    private GestionnaireMort initialiserGestionnaireMort() {
        return new GestionnaireMort(joueur, joueur.getVie(), null, null, null, vueCarte);
    }

    private GestionnaireVie initialiserGestionnaireVie() {
        return new GestionnaireVie(joueur, gestionnaireSon, gestionnaireMort, joueur.getVie());
    }

    private DebugManager initialiserDebugManager() {
        // Création de quelques mobs de test
        Mob mob1 = gestionnaireMobPassif.ajouterMob(null, 200, worldGroup);
        Mob mob2 = gestionnaireMobPassif.ajouterMob(null, 400, worldGroup);
        mobManager.ajouterMob(mob1);
        mobManager.ajouterMob(mob2);

        MobHostile mobH1 = gestionnaireMobHostile.ajouterMob(joueur, 600, worldGroup);
        mobManager.ajouterMob(mobH1);

        return new DebugManager(worldGroup, joueur, mobManager.getMobs());
    }

    private void assemblerElements() {
        worldGroup.getChildren().addAll(
                vueCarte.getTileMap(),
                vueJoueur.getNode(),
                vueVie.getNode()
        );
    }

    /* ------------------------------------------------------------------------
     * AUTRES MÉTHODES
     * ------------------------------------------------------------------------ */

    private int trouverHauteurSol(int x) {
        for (int y = 0; y < Constantes.NB_LIGNES; y++) {
            Bloc bloc = carte.getBloc(x, y, 1);
            if (bloc != null && bloc.estSolide()) return y;
        }
        return Constantes.BASE_SOL;
    }

    public void update() {
        joueur.mettreAJourDeplacement();
        joueur.gravite();

        gestionnaireMobPassif.mettreAJour();
        gestionnaireMobHostile.mettreAJour();
        gestionnaireItem.update(joueur, gestionnaireInventaire.getInventaire(), gestionnaireInventaire.getVueInventaire());
        gestionnaireFleche.mettreAJour();
        gestionnaireVie.mettreAJour();

        if (debugManager.isDebugVisible()) debugManager.update();
    }

    /* ------------------------------------------------------------------------
     * GETTERS
     * ------------------------------------------------------------------------ */
    public Joueur getJoueur() { return joueur; }
    public Group getWorldGroup() { return worldGroup; }
    public GestionnaireInventaire getGestionnaireInventaire() { return gestionnaireInventaire; }
    public GestionnaireItem getGestionnaireItem() { return gestionnaireItem; }
    public GestionnaireMobHostile getGestionnaireMobHostile() { return gestionnaireMobHostile; }
    public GestionnaireMobPassif getGestionnaireMobPassif() { return gestionnaireMobPassif; }
    public GestionnaireFleche getGestionnaireFleche() { return gestionnaireFleche; }
    public VueInventaire getVueInventaire() { return gestionnaireInventaire.getVueInventaire(); }
    public VueCarte getVueCarte() { return vueCarte; }
    public DebugManager getDebugManager() { return debugManager; }
    public VueVie getVueVie() { return vueVie; }
    public Environnement getEnvironnement() { return this; }
}
