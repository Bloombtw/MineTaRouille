package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
// import javafx.scene.image.ImageView; // Pas utilisé ici, peut-être dans VuePersonnage
// import javafx.scene.layout.TilePane; // Pas utilisé ici, peut-être dans VueCarte
// import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController; // Pas pertinent ici
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

// import java.util.Properties; // Non utilisé

public class Personnage {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();

    public DoubleProperty xProperty() { return x; }
    public DoubleProperty yProperty() { return y; }

    // --- Ajout de la vie du personnage ---
    private Vie vie; // Le Personnage possède une Vie
    // private double pointsDeVieMax; // Plus besoin, la Vie gère son propre max
    // --- Fin de l'ajout ---

    private String nom;
    private int satiete;

    private Item[] inventaire;
    private Item equipedItem;
    private int selectedSlot;
    private boolean isMining;
    private boolean isAttacking;
    private boolean isAlive;
    public Direction direction;

    // Déplacement / physique
    private double vitesseX = 0;
    private double vitesseY = 0;
    private boolean peutSauter = true;
    private Carte carte; // Référence à la carte pour les collisions

    public Personnage(double x, double y, double pointsDeVieMax, String nom) {
        this.x.set(x);
        this.y.set(y);
        // Initialise l'objet Vie du personnage avec les points de vie max
        this.vie = new Vie(pointsDeVieMax);
        this.nom = nom;
        this.satiete = 100;
        this.isAlive = true; // Dépendra de la vie.estMort()
        this.isMining = false;
        this.isAttacking = false;
        this.inventaire = new Item[10];
        this.selectedSlot = 0;
        this.direction = Direction.DROITE;
        this.carte = Carte.getInstance(); // Assurez-vous que Carte.getInstance() est géré correctement
    }

    // Nouveau : Obtenir l'objet Vie du personnage
    public Vie getVie() {
        return vie;
    }

    // Méthodes de mouvement et de collision (inchangées)

    public void sauter() {
        if (peutSauter) {
            vitesseY = Constantes.FORCE_SAUT;
            peutSauter = false;
        }
    }

    public void deplacerGauche() {
        double futurX = getX() - Constantes.VITESSE_DEPLACEMENT;
        if (!collision(futurX, getY())) {
            setX(futurX);
        }
        direction = Direction.GAUCHE;
    }

    public void deplacerDroite() {
        double futurX = getX() + Constantes.VITESSE_DEPLACEMENT;
        if (!collision(futurX, getY())) {
            setX(futurX);
        }
        direction = Direction.DROITE;
    }

    public void arreterMouvementX() {
        vitesseX = 0;
    }

    public void gravite() {
        vitesseY += Constantes.GRAVITE;
        double futurY = getY() + vitesseY;

        if (!collision(getX(), futurY)) {
            setY(futurY);
        } else {
            vitesseY = 0;
            peutSauter = true;
        }
        // C'est ici que vous pourriez appeler la vérification des dégâts liés aux blocs
        // vie.verifierDegats(this, carte); // À appeler dans la boucle de jeu principale plutôt
    }

    private boolean collision(double x, double y) {
        int left = (int) (x / Constantes.TAILLE_PERSO);
        int right = (int) ((x + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_PERSO);
        int top = (int) (y / Constantes.TAILLE_PERSO);
        int bottom = (int) ((y + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_PERSO);

        for (int tx = left; tx <= right; tx++) {
            for (int ty = top; ty <= bottom; ty++) {
                if (carte.estBlocSolide(tx, ty)) return true;
            }
        }
        return false;
    }


    public double getX() { return x.get(); }
    public void setX(double val) { x.set(val); }

    public double getY() { return y.get(); }
    public void setY(double val) { y.set(val); }


    public boolean onGround() {
        return getY() >= carte.getHauteur() - Constantes.TAILLE_PERSO;
    }
    public double getVitesseY() { return vitesseY; }

    // Vous pouvez ajouter une méthode isAlive() qui se base sur la vie du personnage
    public boolean isAlive() {
        return !vie.estMort();
    }
}