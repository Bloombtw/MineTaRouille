package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;

public class Personnage {

    protected double x;
    protected double y;

    private double pointsDeVieMax;
    private double pointsDeVie;
    private String nom;
    private double vitesseDeplacement;
    private int satiete;

    private Item[] inventaire;
    private Item equipedItem;
    private int selectedSlot;
    private boolean isMining;
    private boolean isAttacking;
    private boolean isAlive;
    protected Direction direction;

    // Déplacement / physique
    protected double vitesseX = 0;
    protected double vitesseY = 0;
    protected final double GRAVITE = 0.2;
    protected double VITESSE_DEPLACEMENT = 20;
    protected final double FORCE_SAUT = -5;
    protected boolean peutSauter = true;

    protected Carte carte;

    public static final int TAILLE_PERSO = JeuController.TAILLE_TUILE;

    // ✅ Nouveau constructeur
    public Personnage(double x, double y, double pointsDeVieMax, String nom, double vitesseDeplacement, Carte carte) {
        this.x = x;
        this.y = y;
        this.pointsDeVieMax = pointsDeVieMax;
        this.pointsDeVie = pointsDeVieMax;
        this.nom = nom;
        this.vitesseDeplacement = vitesseDeplacement;
        this.satiete = 100;
        this.isAlive = true;
        this.isMining = false;
        this.isAttacking = false;
        this.inventaire = new Item[10];
        this.selectedSlot = 0;
        this.direction = Direction.DROITE;
        this.carte = carte;
    }

    // === Mouvements avec collisions ===

    public void sauter() {
        if (peutSauter) {
            vitesseY = FORCE_SAUT;
            peutSauter = false;
        }
    }
    public void deplacerGauche() {
        double nouvelleX = getX() - vitesseDeplacement;

        if (!collision(nouvelleX, getY())) {
            this.setX(nouvelleX);
        }
    }

    public void deplacerDroite() {
        double nouvelleX = getX() + vitesseDeplacement;
        if (!collision(nouvelleX, getY())) {
            this.setX(nouvelleX);
        }
    }

    public void gravite() {
        double nouvelleY = getY() + vitesseY;

        if (!collision(getX(), nouvelleY)) {
            this.setY(nouvelleY);
            this.vitesseY += GRAVITE;
        } else {
            this.vitesseY = 0;
            this.setY(Math.floor(getY() / TAILLE_PERSO) * TAILLE_PERSO);
            this.peutSauter = true;
        }
    }

    public void arreterMouvementX() {
        vitesseX = 0;
    }


    // === Collision avec la carte ===

    public boolean collision(double x, double y) {
        int left = (int) (x / TAILLE_PERSO);
        int right = (int) ((x + TAILLE_PERSO - 1) / TAILLE_PERSO);
        int top = (int) (y / TAILLE_PERSO);
        int bottom = (int) ((y + TAILLE_PERSO - 1) / TAILLE_PERSO);

        for (int tx = left; tx <= right; tx++) {
            for (int ty = top; ty <= bottom; ty++) {
                if (carte.estBlocSolide(tx, ty)) return true;
            }
        }
        return false;
    }

    public void ajouterAuTilePane(TilePane tilePane) {
        tilePane.getChildren().add(getPerso());
        getPerso().setTranslateX(x);
        getPerso().setTranslateY(y);
    }

    public void setVitesseDeplacement(double vitesseDeplacement) {
        this.vitesseDeplacement = vitesseDeplacement;
    }

    public ImageView getPerso() {
        return null;
    }


    public int getX() { return (int) x; }
    public void setX(double x) { this.x = x; }

    public int getY() { return (int) y; }
    public void setY(double y) { this.y = y; }

//J'ai retirer toutes les méthodes non utilisé pour l'instant
}
