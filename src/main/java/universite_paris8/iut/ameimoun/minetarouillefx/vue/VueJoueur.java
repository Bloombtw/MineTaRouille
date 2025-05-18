package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class VueJoueur {
    public static final int TAILLE_PERSO= 30;
    private ImageView perso=null;
    private Animation animMarche;
    private Animation animSaut;
    private Animation animIdle;

    public VueJoueur(Joueur joueur) {
        Image img = new Image(getClass().getResource(
                "/img/joueur/base.png").toExternalForm());
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

    public void updatePosition(Joueur joueur) {
        perso.setLayoutX(joueur.getX());
        perso.setLayoutY(joueur.getY());
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