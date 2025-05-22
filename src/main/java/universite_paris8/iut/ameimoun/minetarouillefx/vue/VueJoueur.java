package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
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

        overlayDegats = creerOverlayDegats();
        container = new Group(perso, overlayDegats);

        lierPositionContainer(joueur);
        lierPositionOverlayDegats();

        chargerAnimations();

        animIdle.start();

        lierListenersDeplacement(joueur);

        joueur.getVie().ajouterCallbackDegatsSubis(this::afficherDegats);
    }

    private Rectangle creerOverlayDegats() {
        Rectangle rect = new Rectangle(Constantes.TAILLE_PERSO, Constantes.TAILLE_PERSO);
        rect.setFill(Color.RED);
        rect.setOpacity(0.5);
        rect.setVisible(false);
        return rect;
    }

    private void lierPositionContainer(Joueur joueur) {
        container.translateXProperty().bind(joueur.xProperty());
        container.translateYProperty().bind(joueur.yProperty());
    }

    private void lierPositionOverlayDegats() {
        overlayDegats.xProperty().bind(perso.xProperty().add(perso.getFitWidth() / 2).subtract(overlayDegats.getWidth() / 2));
        overlayDegats.yProperty().bind(perso.yProperty().add(perso.getFitHeight() / 2).subtract(overlayDegats.getHeight() / 2));
    }

    private void chargerAnimations() {
        animIdle = creerAnimation("/img/joueur/idle.png", 32, 32, 4, 150);
        animGauche = creerAnimation("/img/joueur/gauche.png", 32, 32, 6, 100);
        animDroite = creerAnimation("/img/joueur/droite.png", 32, 32, 6, 100);
        animSaut = creerAnimation("/img/joueur/saut.png", 32, 32, 8, 120);
    }

    private Animation creerAnimation(String path, int width, int height, int frames, int duree) {
        Image sprite = Loader.loadImage(path);
        Image[] framesArray = Animation.decouperSpriteSheet(sprite, width, height, frames);
        return new Animation(perso, framesArray, duree);
    }

    private void lierListenersDeplacement(Joueur joueur) {
        joueur.xProperty().addListener((obs, oldX, newX) -> mettreAJourAnimation(joueur));
        joueur.yProperty().addListener((obs, oldY, newY) -> mettreAJourAnimation(joueur));
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
                        if (now - start > 200_000_000L) { // 200 ms
                            overlayDegats.setVisible(false);
                            stop();
                        }
                    }
                }.start();
            }
        });
    }
}
