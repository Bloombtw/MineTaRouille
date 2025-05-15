package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.Controller;

public class Personnage {

    protected int x;
    protected int y;

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
    protected final double VITESSE_DEPLACEMENT = 2;
    protected final double FORCE_SAUT = -5;
    protected boolean peutSauter = true;

    public Personnage(int x, int y, double pointsDeVieMax, String nom, double vitesseDeplacement) {
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
    }

    // === Mouvements ===

    public void sauter() {
        if (peutSauter) {
            vitesseY = FORCE_SAUT;
            peutSauter = false;
        }
    }

    public void deplacerGauche() {
        vitesseX = -VITESSE_DEPLACEMENT;
        direction = Direction.GAUCHE;
    }

    public void deplacerDroite() {
        vitesseX = VITESSE_DEPLACEMENT;
        direction = Direction.DROITE;
    }

    public void arreterMouvementX() {
        vitesseX = 0;
    }

    public void gravite() {
        vitesseY += GRAVITE;
        y += vitesseY;
        x += vitesseX;
    }

    public void ajouterAGrille(GridPane gridPane) {
        gridPane.add(getPerso(), x / Controller.TAILLE_TUILE, y / Controller.TAILLE_TUILE);
    }

    // === À surcharger dans Joueur ===
    public ImageView getPerso() {
        return null;
    }

    // === Getters / Setters ===

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public double getPointsDeVie() { return pointsDeVie; }
    public void setPointsDeVie(double pointsDeVie) { this.pointsDeVie = pointsDeVie; }

    public double getPointsDeVieMax() { return pointsDeVieMax; }

    public String getNom() { return nom; }

    public double getVitesseDeplacement() { return VITESSE_DEPLACEMENT; }

    public double getVitesseX() { return vitesseX; }
    public void setVitesseX(double vitesseX) { this.vitesseX = vitesseX; }

    public double getVitesseY() { return vitesseY; }
    public void setVitesseY(double vitesseY) { this.vitesseY = vitesseY; }

    public boolean peutSauter() { return peutSauter; }
    public void setPeutSauter(boolean peutSauter) { this.peutSauter = peutSauter; }

    public double getGravite() { return GRAVITE; }
    public double getForceSaut() { return FORCE_SAUT; }

    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { isAlive = alive; }

    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }

    public Item[] getInventaire() { return inventaire; }
    public void setInventaire(Item[] inventaire) { this.inventaire = inventaire; }

    public Item getEquipedItem() { return equipedItem; }
    public void setEquipedItem(Item equipedItem) { this.equipedItem = equipedItem; }

    public int getSelectedSlot() { return selectedSlot; }
    public void setSelectedSlot(int selectedSlot) { this.selectedSlot = selectedSlot; }

    public boolean isMining() { return isMining; }
    public void setMining(boolean mining) { isMining = mining; }

    public boolean isAttacking() { return isAttacking; }
    public void setAttacking(boolean attacking) { this.isAttacking = attacking; }
}
