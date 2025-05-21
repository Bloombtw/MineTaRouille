package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class VueJoueur {
    public static final int TAILLE_PERSO = 30;

    private final ImageView perso;
    private final Group container;
    private Animation animDroite;
    private Animation animGauche;
    private Animation animIdle;
    private Animation animSaut;
    private Animation animActuelle;


    public VueJoueur(Joueur joueur) {
        perso = new ImageView();
        container = new Group(perso);
        perso.setFitWidth(TAILLE_PERSO);
        perso.setFitHeight(TAILLE_PERSO);

        container.translateXProperty().bind(joueur.xProperty());
        container.translateYProperty().bind(joueur.yProperty());

        joueur.xProperty().addListener((obs, oldX, newX) -> {
            mettreAJourAnimation(joueur);
        });

        joueur.yProperty().addListener((obs, oldY, newY) -> {
            mettreAJourAnimation(joueur);
        });

        // Chargement des sprite sheets
        Image spriteIdle = Loader.loadImage("/img/joueur/idle.png");
        Image spriteGauche = Loader.loadImage("/img/joueur/gauche.png");
        Image spriteDroite = Loader.loadImage("/img/joueur/droite.png");
        Image spriteSaut = Loader.loadImage("/img/joueur/saut.png");


        // Découpage en frames
        Image[] framesIdle = Animation.decouperSpriteSheet(spriteIdle, 32, 32, 4);  // par exemple 4 frames pour idle
        Image[] framesGauche = Animation.decouperSpriteSheet(spriteGauche, 32, 32, 6); // 6 frames marche
        Image[] frameDroite = Animation.decouperSpriteSheet(spriteDroite, 32, 32, 6);
        Image[] framesSaut = Animation.decouperSpriteSheet(spriteSaut, 32, 32, 8);    // 6 frames saut

        // Création des animations (durée par frame en ms)
        animIdle = new Animation(perso, framesIdle, 150);
        animGauche = new Animation(perso, framesGauche, 100);
        animDroite = new Animation(perso, frameDroite, 100);
        animSaut = new Animation(perso, framesSaut, 120);

        animIdle.start();
    }

    public Group getNode() {
        return container;
    }



    public void mettreAJourAnimation(Joueur joueur) {
        boolean enSaut = joueur.getVitesseY() != 0;

        if (enSaut) {
            jouerAnimation(animSaut);
        } else {
            switch (joueur.direction) {
                case GAUCHE -> jouerAnimation(animGauche);
                case DROITE -> jouerAnimation(animDroite);
                default -> jouerAnimation(animIdle);
            }
        }
    }

    private void jouerAnimation(Animation animation) {
        if (animActuelle != animation) {
            if (animActuelle != null) animActuelle.stop();
            animActuelle = animation;
            animActuelle.start();
        }
    }

}
