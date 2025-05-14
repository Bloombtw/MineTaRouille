package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Joueur {
    public static final int TAILLE_PERSO = 10;
    private double x; // Position en pixels
    private double y; // Position en pixels
    private Rectangle perso; // Représentation visuelle
    private double vitesseX = 0;
    private double vitesseY = 0;
    private final double GRAVITE = 0.5;
    private final double VITESSE_DEPLACEMENT =2;
    private final double FORCE_SAUT = -5;
    private boolean peutSauter = true;

    public Joueur(double xInitial, double yInitial) {
        this.x = xInitial;
        this.y = yInitial;
        initialiserPerso();
    }

    private void initialiserPerso() {
        perso = new Rectangle(TAILLE_PERSO, TAILLE_PERSO);
        perso.setFill(Color.BLUE);
        perso.setStroke(Color.BLACK);
    }

    public void ajouterAGrille(GridPane gridPane) {
        gridPane.getChildren().add(perso);
        updatePosition();
    }

    public void updatePosition() {
        GridPane.setColumnIndex(perso, (int)(x / TAILLE_PERSO));
        GridPane.setRowIndex(perso, (int)(y / TAILLE_PERSO));
    }

    public void deplacerGauche() {
        vitesseX = -VITESSE_DEPLACEMENT;
    }

    public void deplacerDroite() {
        vitesseX = VITESSE_DEPLACEMENT;
    }

    public void sauter() {
        if (peutSauter) {
            vitesseY = FORCE_SAUT;
            peutSauter = false;
        }
    }

    public void arreterMouvementX() {
        vitesseX = 0;
    }

    public void appliquerPhysique() {
        // Appliquer la gravité
        vitesseY += GRAVITE;

        // Mettre à jour la position
        x += vitesseX;
        y += vitesseY;

        // Limites simples (à adapter avec votre système de collision)
        if (y >= 400) { // Sol fictif
            y = 400;
            vitesseY = 0;
            peutSauter = true;
        }

        updatePosition();
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public Rectangle getPerso() { return perso; }

    public double getVITESSE_DEPLACEMENT() {
        return VITESSE_DEPLACEMENT;
    }

    public void setVitesseX(double vitesseX) {
        this.vitesseX = vitesseX;
    }

    public void setVitesseY(double vitesseY) {
        this.vitesseY = vitesseY;
    }

    public void setPeutSauter(boolean peutSauter) {
        this.peutSauter = peutSauter;
    }
}