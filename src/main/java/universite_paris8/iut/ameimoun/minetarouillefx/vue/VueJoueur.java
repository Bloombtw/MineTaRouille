// Dans VueJoueur.java
package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.AnimationTimer;
import javafx.application.Platform; // Rétablir l'importation de Platform
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class VueJoueur {
    private final ImageView perso;
    private final Group container;
    private Animation animDroite;
    private Animation animGauche;
    private Animation animIdle;
    private Animation animSaut;
    private Animation animActuelle;
    private Rectangle overlayDegats;

    public VueJoueur(Joueur joueur) {
        perso = new ImageView();
        perso.setFitWidth(Constantes.TAILLE_PERSO);
        perso.setFitHeight(Constantes.TAILLE_PERSO);

        overlayDegats = new Rectangle(Constantes.TAILLE_PERSO, Constantes.TAILLE_PERSO);
        overlayDegats.setFill(Color.RED);
        overlayDegats.setOpacity(0.5);
        overlayDegats.setVisible(false);

        container = new Group(perso, overlayDegats);
        container.translateXProperty().bind(joueur.xProperty());
        container.translateYProperty().bind(joueur.yProperty());

        overlayDegats.xProperty().bind(perso.xProperty().add(perso.getFitWidth() / 2).subtract(overlayDegats.getWidth() / 2));
        overlayDegats.yProperty().bind(perso.yProperty().add(perso.getFitHeight() / 2).subtract(overlayDegats.getHeight() / 2));

        joueur.xProperty().addListener((obs, oldX, newX) -> {
            mettreAJourAnimation(joueur);
        });

        joueur.yProperty().addListener((obs, oldY, newY) -> {
            mettreAJourAnimation(joueur);
        });

        Image spriteIdle = Loader.loadImage("/img/joueur/idle.png");
        Image spriteGauche = Loader.loadImage("/img/joueur/gauche.png");
        Image spriteDroite = Loader.loadImage("/img/joueur/droite.png");
        Image spriteSaut = Loader.loadImage("/img/joueur/saut.png");

        Image[] framesIdle = Animation.decouperSpriteSheet(spriteIdle, 32, 32, 4);
        Image[] framesGauche = Animation.decouperSpriteSheet(spriteGauche, 32, 32, 6);
        Image[] frameDroite = Animation.decouperSpriteSheet(spriteDroite, 32, 32, 6);
        Image[] framesSaut = Animation.decouperSpriteSheet(spriteSaut, 32, 32, 8);

        animIdle = new Animation(perso, framesIdle, 150);
        animGauche = new Animation(perso, framesGauche, 100);
        animDroite = new Animation(perso, frameDroite, 100);
        animSaut = new Animation(perso, framesSaut, 120);

        animIdle.start();

        joueur.getVie().ajouterCallbackDegatsSubis(this::afficherDegats);
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

    public void afficherDegats() {
        Platform.runLater(() -> {
            if (overlayDegats != null) {
                overlayDegats.setVisible(true);
                new AnimationTimer() {
                    private long start = -1;

                    @Override
                    public void handle(long now) {
                        if (start < 0) start = now;
                        if (now - start > 200_000_000L) {
                            overlayDegats.setVisible(false);
                            stop();
                        }
                    }
                }.start();
            }
        });
    }
}