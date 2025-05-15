package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.Controller;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.Animation;

public class Joueur extends Personnage {

    public static final int TAILLE_PERSO = Controller.TAILLE_TUILE;

    private ImageView perso;
    private Animation animMarche;
    private Animation animSaut;
    private Animation animIdle;

    public Joueur(double xInitial, double yInitial) {
        super((int) xInitial, (int) yInitial, 100, "Joueur", 2);
        initialiserPerso();
    }

    private void initialiserPerso() {
        Image img = new Image(getClass().getResource("/universite_paris8/iut/ameimoun/minetarouillefx/sprite/saut/base.png").toExternalForm());
        perso = new ImageView(img);
        perso.setFitWidth(TAILLE_PERSO);
        perso.setFitHeight(TAILLE_PERSO);
    }

    public ImageView getImageView() {
        return perso;
    }

    public ImageView getPerso() {
        return perso;
    }

    @Override
    public void gravite() {
        super.gravite();
        perso.setTranslateX(x);
        perso.setTranslateY(y);
    }

    public Animation getAnimMarche() {
        return animMarche;
    }

    public void setAnimMarche(Animation animMarche) {
        this.animMarche = animMarche;
    }

    public Animation getAnimSaut() {
        return animSaut;
    }

    public void setAnimSaut(Animation animSaut) {
        this.animSaut = animSaut;
    }

    public Animation getAnimIdle() {
        return animIdle;
    }

    public void setAnimIdle(Animation animIdle) {
        this.animIdle = animIdle;
    }
}
