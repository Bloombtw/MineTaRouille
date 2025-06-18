package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Classe représentant la barre de vie d’un personnage ou joueur dans l’interface graphique.
 * Cette vue est liée au modèle et met à jour dynamiquement son apparence en fonction
 * de la vie actuelle, des dégâts subis, et applique un effet de clignotement rouge sur tout l’écran
 * lorsqu’un dégât est pris.
 */
public class VueVie {

    /** Largeur maximale de la barre de vie. */
    double largeurMax = Constantes.LARGEUR_BARRE_MAX_BARRE_DE_VIE;

    /** Hauteur de la barre de vie. */
    double hauteur = Constantes.HAUTEUR_BARRE_DE_VIE;

    /** Durée du clignotement de dégâts en millisecondes. */
    long dureeFlash = Constantes.DUREE_FLASH_MS_VUE_VIE;

    private final Rectangle fond;
    private final Rectangle barre;
    private final StackPane containerBarreVie;
    private final Vie vieModele;
    private final Rectangle overlayDegatsGlobal;
    private final FadeTransition fadeTransition;

    /**
     * Crée la vue de la barre de vie et initialise les bindings avec le modèle {@link Vie}.
     *
     * @param vieModele Le modèle de vie à observer.
     * @param rootPane  Le conteneur principal de la scène, utilisé pour dessiner l’overlay de dégâts.
     */
    public VueVie(Vie vieModele, Region rootPane) {
        this.vieModele = vieModele;

        fond = creerFond();
        barre = creerBarre();
        containerBarreVie = new StackPane(fond, barre);
        configurerPositionBarre();

        lierLargeurBarre();
        lierModele();
        overlayDegatsGlobal = creerOverlayDegats(rootPane);
        fadeTransition = creerFadeTransition();

        vieModele.ajouterCallbackDegatsSubis(this::afficherDegats);
        mettreAJourCouleurBarre(vieModele.vieActuelleProperty().get(), vieModele.vieMaxProperty().get());
    }

    /**
     * Crée le fond gris de la barre de vie.
     *
     * @return Un rectangle de fond.
     */
    private Rectangle creerFond() {
        Rectangle fond = new Rectangle(largeurMax, hauteur);
        fond.setFill(Color.DARKGRAY);
        return fond;
    }

    /**
     * Crée la partie colorée de la barre de vie (remplissage).
     *
     * @return Un rectangle vert représentant la vie restante.
     */
    private Rectangle creerBarre() {
        Rectangle barre = new Rectangle(largeurMax, hauteur);
        barre.setFill(Color.LIMEGREEN);
        return barre;
    }

    /**
     * Positionne la barre de vie dans la fenêtre.
     */
    private void configurerPositionBarre() {
        containerBarreVie.setTranslateX(20);
        containerBarreVie.setTranslateY(20);
    }

    /**
     * Lie dynamiquement la largeur de la barre au ratio vie actuelle / vie max.
     */
    private void lierLargeurBarre() {
        barre.widthProperty().bind(
                vieModele.vieActuelleProperty()
                        .divide(vieModele.vieMaxProperty())
                        .multiply(largeurMax)
        );
    }

    /**
     * Lie la couleur de la barre et le comportement à l’état de dégâts du modèle.
     */
    private void lierModele() {
        vieModele.vieActuelleProperty().addListener((obs, oldVal, newVal) -> {
            if (!vieModele.isTakingDamageProperty().get()) {
                mettreAJourCouleurBarre(newVal.doubleValue(), vieModele.vieMaxProperty().get());
            }
        });

        vieModele.isTakingDamageProperty().addListener((obs, oldVal, isTakingDamage) -> {
            if (isTakingDamage) {
                barre.setFill(Color.BLUE);
            } else {
                mettreAJourCouleurBarre(vieModele.vieActuelleProperty().get(), vieModele.vieMaxProperty().get());
            }
        });
    }

    /**
     * Crée un overlay rouge qui recouvre l'écran brièvement lorsqu’un dégât est pris.
     *
     * @param rootPane Le conteneur principal de la scène.
     * @return Un rectangle transparent utilisé pour le flash rouge.
     */
    private Rectangle creerOverlayDegats(Region rootPane) {
        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.RED);
        overlay.setOpacity(0.0);
        overlay.setVisible(false);

        overlay.widthProperty().bind(rootPane.widthProperty());
        overlay.heightProperty().bind(rootPane.heightProperty());

        return overlay;
    }

    /**
     * Crée la transition de clignotement rouge pour signaler un dégât.
     *
     * @return Une transition d’animation JavaFX.
     */
    private FadeTransition creerFadeTransition() {
        FadeTransition ft = new FadeTransition(Duration.millis(dureeFlash), overlayDegatsGlobal);
        ft.setFromValue(0.3);
        ft.setToValue(0.0);
        ft.setOnFinished(event -> overlayDegatsGlobal.setVisible(false));
        return ft;
    }

    /**
     * Met à jour dynamiquement la couleur de la barre de vie en fonction du ratio de vie.
     *
     * @param vieActuelle La vie actuelle.
     * @param vieMax      La vie maximale.
     */
    private void mettreAJourCouleurBarre(double vieActuelle, double vieMax) {
        double ratio = vieActuelle / vieMax;
        if (ratio > 0.6) {
            barre.setFill(Color.LIMEGREEN);
        } else if (ratio > 0.3) {
            barre.setFill(Color.ORANGE);
        } else {
            barre.setFill(Color.RED);
        }
    }

    /**
     * Affiche un flash rouge à l’écran lorsque le joueur subit des dégâts.
     */
    public void afficherDegats() {
        Platform.runLater(() -> {
            fadeTransition.stop();
            overlayDegatsGlobal.setOpacity(0.3);
            overlayDegatsGlobal.setVisible(true);
            fadeTransition.playFromStart();
        });
    }

    /**
     * Retourne le nœud JavaFX représentant la barre de vie.
     *
     * @return Une stackPane contenant la barre.
     */
    public StackPane getNoeudBarreVie() {
        return containerBarreVie;
    }

    /**
     * Retourne le rectangle utilisé pour l’effet visuel de dégâts globaux.
     *
     * @return L’overlay rouge.
     */
    public Rectangle getOverlayDegatsGlobal() {
        return overlayDegatsGlobal;
    }
}
