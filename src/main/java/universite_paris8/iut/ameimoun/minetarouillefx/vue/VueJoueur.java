// Dans VueJoueur.java
package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.Controller;

public class VueJoueur {


    private final Joueur modele;
    private final ImageView perso;
    private Animation animMarche;
    private Animation animSaut;
    private Animation animIdle;

    public VueJoueur(Joueur modele) {
        this.modele = modele;

        Image img = new Image(getClass().getResource(
                "/universite_paris8/iut/ameimoun/minetarouillefx/sprite/saut/base.png").toExternalForm());
        perso = new ImageView(img);
        perso.setFitWidth(Controller.TAILLE_TUILE);
        perso.setFitHeight(Controller.TAILLE_TUILE);
    }

    public void mettreAJourPosition() {
        perso.setTranslateX(modele.getX());
        perso.setTranslateY(modele.getY());
    }

    public ImageView getPerso() {
        return perso;
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