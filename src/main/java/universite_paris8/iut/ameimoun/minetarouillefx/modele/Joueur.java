package universite_paris8.iut.ameimoun.minetarouillefx.modele;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.Controller;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.Animation;

public class Joueur {
    public static final int TAILLE_PERSO = Controller.TAILLE_TUILE;
    private double x;
    private double y;
    private ImageView perso;
    private Animation animMarche;
    private Animation animSaut;
    private Animation animIdle;


    private double vitesseX = 0;
    private double vitesseY = 0;
    private final double GRAVITE = 0.2;
    private final double VITESSE_DEPLACEMENT =2;
    private final double FORCE_SAUT = -5;
    private boolean peutSauter = true;

    public Joueur(double xInitial, double yInitial) {
        this.x = xInitial;
        this.y = yInitial;
        initialiserPerso();
    }

    private void initialiserPerso() {
        perso = new ImageView();
        perso.setFitWidth(TAILLE_PERSO);
        perso.setFitHeight(TAILLE_PERSO);
        perso.setSmooth(true);

        Image[] framesMarche = new Image[] {
                new Image(getClass().getResourceAsStream("/universite_paris8/iut/ameimoun/minetarouillefx/sprite/saut/base.png")),
        };

        Image[] framesSaut = new Image[] {
                new Image(getClass().getResourceAsStream("/universite_paris8/iut/ameimoun/minetarouillefx/sprite/saut/base.png")),
                new Image(getClass().getResourceAsStream("/universite_paris8/iut/ameimoun/minetarouillefx/sprite/saut/base.png")),
                new Image(getClass().getResourceAsStream("/universite_paris8/iut/ameimoun/minetarouillefx/sprite/saut/base.png"))
        };

        Image[] framesIdle = new Image[] {
                new Image(getClass().getResourceAsStream("/universite_paris8/iut/ameimoun/minetarouillefx/sprite/saut/base.png")),
        };

        animMarche = new Animation(perso, framesMarche, 100);
        animSaut = new Animation(perso, framesSaut, 100);
        animIdle = new Animation(perso, framesIdle, 300);

        animIdle.start();

    }


    public void ajouterAGrille(GridPane gridPane) {
        gridPane.getChildren().add(perso);
        updatePosition();
    }

    public void updatePosition() {
        int colonne = (int) (x / TAILLE_PERSO);
        int ligne = (int) (y / TAILLE_PERSO);

        GridPane.setColumnIndex(perso, colonne);
        GridPane.setRowIndex(perso, ligne);

        // Ajuster le décalage entre la grille et la position réelle
        perso.setTranslateX(x - colonne * TAILLE_PERSO);
        perso.setTranslateY(y - ligne * TAILLE_PERSO);
    }
    private void lancerAnim(Animation anim) {
        if (animMarche.isRunning()) animMarche.stop();
        if (animSaut.isRunning()) animSaut.stop();
        if (animIdle.isRunning()) animIdle.stop();
        anim.start();
    }

    public void deplacerGauche() {
        vitesseX = -VITESSE_DEPLACEMENT;
        lancerAnim(animMarche);
    }

    public void deplacerDroite() {
        vitesseX = VITESSE_DEPLACEMENT;
        lancerAnim(animMarche);
    }

    public void sauter() {
        if (peutSauter) {
            vitesseY = FORCE_SAUT;
            peutSauter = false;
            lancerAnim(animSaut);
        }
    }

    public void arreterMouvementX() {
        vitesseX = 0;
        lancerAnim(animMarche);
    }

    public void gravite() {
        // Appliquer la gravité
        vitesseY += GRAVITE;

        // Mettre à jour la position
        x += vitesseX;
        y += vitesseY;

        /*Collision*/
        if (y >= 400) {
            y = 400;
            vitesseY = 0;
            peutSauter = true;
        }
        updatePosition();
        perso.setTranslateX(x);
        perso.setTranslateY(y);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public ImageView getPerso() { return perso; }

    public void setVitesseY(double vitesseY) {
        this.vitesseY = vitesseY;
    }
    public void setPeutSauter(boolean peutSauter) {
        this.peutSauter = peutSauter;
    }
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
}