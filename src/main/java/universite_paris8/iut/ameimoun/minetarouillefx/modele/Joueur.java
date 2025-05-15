package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.Controller;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.Animation;

public class Joueur extends Personnage {

    public static final int TAILLE_PERSO = 32; // ou Controller.TAILLE_TUILE si nécessaire

    public Joueur(Carte carte) {
        super(10, 10, 100, "Joueur", 5, carte);
    }

    @Override
    public void gravite() {
        super.gravite();
    }
}
