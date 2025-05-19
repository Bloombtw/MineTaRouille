package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

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
    protected boolean peutSauter = true;

    protected Carte carte;

    public Personnage(double x, double y, double pointsDeVieMax, String nom, double vitesseDeplacement) {
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
        this.carte = Carte.getInstance();
    }

    // Mvts

    public void placer(double x, double y) {
        this.x = x;
        this.y = y;
        getPerso().setTranslateX(x);
        getPerso().setTranslateY(y);
    }

    public void sauter() {
        if (peutSauter) {
            vitesseY = Constantes.FORCE_SAUT;
            peutSauter = false;
        }
    }

    public void deplacerGauche() {
        double futurX = x - vitesseDeplacement;
        if (!collision(futurX, y)) {
            x = futurX;
        }
        direction = Direction.GAUCHE;
    }

    public void deplacerDroite() {
        double futurX = x + Constantes.VITESSE_DEPLACEMENT;
        if (!collision(futurX, y)) {
            x = futurX;
        }
        direction = Direction.DROITE;
    }

    public void arreterMouvementX() {
        vitesseX = 0;
    }

    public void gravite() {
        vitesseY += Constantes.GRAVITE;
        double futurY = y + vitesseY;

        if (!collision(x, futurY)) {
            y = futurY;
        } else {
            vitesseY = 0;
            peutSauter = true;
        }
    }





    protected boolean collision(double futurX, double futurY) {
        int tileSize = Constantes.TAILLE_TUILE;

        int left = (int)(futurX / tileSize);
        int right = (int)((futurX + tileSize - 1) / tileSize);
        int top = (int)(futurY / tileSize);
        int bottom = (int)((futurY + tileSize - 1) / tileSize);

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

    public boolean onGround() {
        return getY() >= carte.getHauteur() - Constantes.TAILLE_PERSO;
    }

    // === À surcharger dans Joueur ===
    public ImageView getPerso() {
        return null;
    }

    public int getX() { return (int) x; }
    public void setX(double x) { this.x = x; }

    public int getY() { return (int) y; }
    public void setY(double y) { this.y = y; }

    public double getVitesseDeplacement() { return Constantes.VITESSE_DEPLACEMENT; }
    public double getVitesseY() { return vitesseY; }
    public void setVitesseY(double vitesseY) { this.vitesseY = vitesseY; }

    public boolean getPeutSauter() { return peutSauter; }
    public void setPeutSauter(boolean peutSauter) { this.peutSauter = peutSauter; }


}