package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.Animation;

public class Joueur extends Personnage {

    private final ImageView perso;
    private Animation animMarche;
    private Animation animSaut;
    private Animation animIdle;

    public Joueur(Carte carte) {
        super(10, 10, 100, "Joueur", 5, carte); // position x, y, vie, nom, vitesse, carte

        // Chargement de l'image du joueur
        Image img = new Image(getClass().getResource(
                "/img/joueur/base.png").toExternalForm());
        perso = new ImageView(img);
        perso.setFitWidth(Constantes.TAILLE_PERSO);
        perso.setFitHeight(Constantes.TAILLE_PERSO);
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
        perso.setTranslateX(getX());
        perso.setTranslateY(getY());
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
