package universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

public class Constantes {

    // Dimensions de la fenêtre
    public static final int LARGEUR_FENETRE = 1680;
    public static final int HAUTEUR_FENETRE = 1050;

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
    public static final double FORCE_SAUT = -8;
    public static final double VITESSE_DEPLACEMENT_MOB = 0.01;
    public static final double DEGATS_MOB_HOSTILE = 0.01;
    public static final double DISTANCE_ATTAQUE = 3 * TAILLE_TUILE;
    // Items
    public static final int TAILLE_ITEM = 32;

    // Interface / UI
    public static final int TAILLE_BOUTON = 200;
    public static final int TAILLE_SLOT = 50;
    public static final int TAILLE_IMAGE_INVENTAIRE = 40;
}
