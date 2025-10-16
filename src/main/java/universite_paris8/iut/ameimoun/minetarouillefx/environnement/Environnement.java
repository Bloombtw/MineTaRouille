package universite_paris8.iut.ameimoun.minetarouillefx.environnement;

import javafx.scene.Group;
import javafx.scene.layout.Region;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.MobManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueVie;

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

    private final MobManager mobManager;
    private final DebugManager debugManager;

    public Environnement(Group worldGroup, Region rootPane) {
        this.worldGroup = worldGroup;
        this.rootPane = rootPane;
        // --- Initialisation du monde ---
        carte = Carte.getInstance();
        vueCarte = new VueCarte(carte);

        // --- Joueur ---
        joueur = new Joueur();
        vueVie = new VueVie(joueur.getVie(), rootPane);
        int colonneDepart = 50;
        int ligneSol = trouverHauteurSol(colonneDepart);
        joueur.setX(colonneDepart * Constantes.TAILLE_TUILE);
        joueur.setY((ligneSol - 2) * Constantes.TAILLE_TUILE);
        vueJoueur = new VueJoueur(joueur);

        // --- Gestionnaires ---
        gestionnaireItem = new GestionnaireItem(worldGroup);
        gestionnaireInventaire = new GestionnaireInventaire(null, vueJoueur);
        gestionnaireMobPassif = new GestionnaireMobPassif(gestionnaireItem);
        gestionnaireMobHostile = new GestionnaireMobHostile(gestionnaireItem);
        gestionnaireFleche = new GestionnaireFleche(worldGroup, gestionnaireMobPassif, gestionnaireMobHostile);
        gestionnaireSon = new GestionnaireSon(joueur);
        gestionnaireMort = new GestionnaireMort(joueur, joueur.getVie(), null, null, null, vueCarte);
        gestionnaireVie = new GestionnaireVie(joueur, gestionnaireSon, gestionnaireMort, joueur.getVie());

        // --- Mobs et debug ---
        mobManager = new MobManager();
        Mob mob1 = gestionnaireMobPassif.ajouterMob(null, 200, worldGroup);
        Mob mob2 = gestionnaireMobPassif.ajouterMob(null, 400, worldGroup);
        mobManager.ajouterMob(mob1);
        mobManager.ajouterMob(mob2);

        MobHostile mobH1 = gestionnaireMobHostile.ajouterMob(joueur, 600, worldGroup);
        mobManager.ajouterMob(mobH1);

        debugManager = new DebugManager(worldGroup, joueur, mobManager.getMobs());

        // --- Assemblage ---
        worldGroup.getChildren().addAll(vueCarte.getTileMap(), vueJoueur.getNode());
    }

    private int trouverHauteurSol(int x) {
        for (int y = 0; y < Constantes.NB_LIGNES; y++) {
            Bloc bloc = carte.getBloc(x, y, 1);
            if (bloc != null && bloc.estSolide()) return y;
        }
        return Constantes.BASE_SOL;
    }

    // --- Mise à jour globale ---
    public void update() {
        joueur.mettreAJourDeplacement();
        joueur.gravite();

        gestionnaireMobPassif.mettreAJour();
        gestionnaireMobHostile.mettreAJour();
        gestionnaireItem.update(joueur, gestionnaireInventaire.getInventaire(), gestionnaireInventaire.getVueInventaire());
        gestionnaireFleche.mettreAJour();

        if (debugManager.isDebugVisible()) debugManager.update();
    }

    // --- Getters utiles ---
    public Joueur getJoueur() { return joueur; }
    public Group getWorldGroup() { return worldGroup; }
    public GestionnaireInventaire getGestionnaireInventaire() { return gestionnaireInventaire; }

    public GestionnaireItem getGestionnaireItem() {
        return gestionnaireItem;
    }

    public GestionnaireMobHostile getGestionnaireMobHostile() {
        return gestionnaireMobHostile;
    }

    public GestionnaireMobPassif getGestionnaireMobPassif() {
        return gestionnaireMobPassif;
    }

    public GestionnaireFleche getGestionnaireFleche() {
        return gestionnaireFleche;
    }

    public VueCarte getVueCarte() { return vueCarte; }
    public DebugManager getDebugManager() { return debugManager; }
    public VueVie getVueVie(){return vueVie;}
    public Environnement getEnvironnement() { return this;}


}