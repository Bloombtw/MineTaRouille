package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class VueJoueur {
    private final Joueur joueur;
    private final ImageView perso;
    private final Group container;
    private final Rectangle overlayDegats;
    private final ImageView objetTenu;
    private final AnimationJoueur animationJoueur;



    public VueJoueur(Joueur joueur) {
        this.joueur = joueur;
        perso = new ImageView();
        perso.setFitWidth(Constantes.TAILLE_PERSO);
        perso.setFitHeight(Constantes.TAILLE_PERSO);
        animationJoueur = new AnimationJoueur(perso);

        overlayDegats = creerOverlayDegats();
        objetTenu = new ImageView();
        objetTenu.setFitWidth(16);
        objetTenu.setFitHeight(16);
        objetTenu.setTranslateX(10);
        objetTenu.setTranslateY(10);

        container = new Group(perso, overlayDegats, objetTenu);

        // Chargement des animations et initialisation
        lierPositionContainer();
        lierListeners();

        joueur.getVie().ajouterCallbackDegatsSubis(this::afficherDegats);
        animationJoueur.mettreAJourAnimation(joueur); // Mettre à jour l'animation initiale du joueur
    }



    private Rectangle creerOverlayDegats() {
        Rectangle rect = new Rectangle(Constantes.TAILLE_PERSO, Constantes.TAILLE_PERSO);
        rect.setFill(Color.RED);
        rect.setOpacity(0.5);
        rect.setVisible(false);
        return rect;
    }

    private void lierPositionContainer() {
        container.translateXProperty().bind(joueur.xProperty());
        container.translateYProperty().bind(joueur.yProperty());
    }

    private void lierListeners() {
        joueur.xProperty().addListener((obs, oldX, newX) -> animationJoueur.mettreAJourAnimation(joueur));
        joueur.yProperty().addListener((obs, oldY, newY) -> animationJoueur.mettreAJourAnimation(joueur));
    }

    public Group getNode() {
        return container;
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

    public void mettreAJourObjetTenu(Item item) {
        if (item == null) {
            objetTenu.setImage(null);
            return;
        }
        String chemin = GestionnaireImage.getCheminImage(item);
        objetTenu.setImage(Loader.loadImage(chemin));
    }
}
