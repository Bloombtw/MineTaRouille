package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue.AnimationJoueur;

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

        lierPositionContainer();
        lierListeners();

        joueur.getVie().ajouterCallbackDegatsSubis(this::afficherDegats);
        animationJoueur.mettreAJourAnimation(joueur); // Mettre à jour l'animation initiale du joueur
    }

    /**
     * Crée un rectangle semi-transparent pour afficher les dégâts subis par le joueur.
     *
     * @return Un rectangle représentant l'overlay des dégâts.
     */
    private Rectangle creerOverlayDegats() {
        Rectangle rect = new Rectangle(Constantes.TAILLE_PERSO, Constantes.TAILLE_PERSO);
        rect.setFill(Color.RED);
        rect.setOpacity(0.5);
        rect.setVisible(false);
        return rect;
    }


    /**
     * Lie la position du conteneur graphique à la position du joueur dans le modèle.
     */
    private void lierPositionContainer() {
        container.translateXProperty().bind(joueur.xProperty());
        container.translateYProperty().bind(joueur.yProperty());
    }
    /**
     * Ajoute des écouteurs pour mettre à jour l'animation du joueur lorsque sa position change.
     */
    private void lierListeners() {
        joueur.xProperty().addListener((obs, oldX, newX) -> animationJoueur.mettreAJourAnimation(joueur));
        joueur.yProperty().addListener((obs, oldY, newY) -> animationJoueur.mettreAJourAnimation(joueur));
    }

    /**
     * Retourne le noeud graphique principal représentant le joueur.
     *
     * @return Le noeud graphique principal.
     */
    public Group getNode() {
        return container;
    }

    /**
     * Affiche visuellement les dégâts subis par le joueur pendant une courte durée.
     */
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

    /**
     * Met à jour l'affichage de l'objet tenu par le joueur.
     *
     * @param item L'objet tenu par le joueur, ou null si aucun objet n'est tenu.
     */

    public void mettreAJourObjetTenu(Item item) {
        if (item == null) {
            objetTenu.setImage(null);
            return;
        }
        String chemin = GestionnaireImage.getCheminImage(item);
        objetTenu.setImage(Loader.loadImage(chemin));
    }
}
