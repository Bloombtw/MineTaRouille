package universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

public class Constantes {

    // Dimensions de la fenêtre
    public static final int LARGEUR_FENETRE = 1680;
    public static final int HAUTEUR_FENETRE = 1050;

    // Dimensions fenêtre craft
    public static final int TAILLE_FENETRE_CRAFT = 300;
    public static final int TAILLE_GRILLE_CRAFT = 3; // 3x3

    // Dimensions fenêtre livre
    public static final int HAUTEUR_LIVRE = 1536;
    public static final int LARGEUR_LIVRE = 1024;

    // Dimensions bouton craft
    public static final int HAUTEUR_BOUTON_CRAFT = 10;
    public static final int LARGEUR_BOUTON_CRAFT = 100;

    // Carte / Tuiles
    public static final int TAILLE_TUILE = 30;
    public static final int NB_LIGNES = 35;
    public static final int NB_COLONNES = 62;
    public static final int NB_COUCHES = 3;
    public static final int BASE_SOL = 25;

    // Génération Carte
    public static final double PROBA_CACTUS = 0.05;
    public static final double PROBA_ARBUSTE = 0.2;
    public static final double PROBA_VARIATION = 0.05;
    public static final int NB_ETOILES = 6;

    // Joueur / Personnage
    public static final int TAILLE_PERSO = TAILLE_TUILE; // 30
    public static final double GRAVITE = 0.1;
    public static final double VITESSE_DEPLACEMENT = 0.5;

    public static final double VITESSE_DEPLACEMENT_MOB = 0.01;
    public static final double DEGATS_MOB_HOSTILE = 0.01;
    public static final double DISTANCE_ATTAQUE = 1 * TAILLE_TUILE;
    public static final double DISTANCE_ATTAQUE_ARC = 3 * TAILLE_TUILE;

    public static final double FORCE_SAUT = -4;
    public static final int DISTANCE_MAX_CASSAGE_BLOC = 3; // Distance max pour casser un bloc

    // Items
    public static final int TAILLE_ITEM = 32;

    // Interface / UI
    public static final int TAILLE_BOUTON = 200;
    public static final int TAILLE_SLOT = 50;
    public static final int TAILLE_IMAGE_INVENTAIRE = 40;


    // Livre
    public static final String[] LIVRE_INTRO = { Chemin.LIVRE_DIDAC_PAGE_1, Chemin.LIVRE_DIDAC_PAGE_2, Chemin.LIVRE_DIDAC_PAGE_3, Chemin.LIVRE_DIDAC_PAGE_4 };
}
