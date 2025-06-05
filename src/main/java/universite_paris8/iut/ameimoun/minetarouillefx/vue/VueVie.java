package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie;

public class VueVie {

    private static final double LARGEUR_BARRE_MAX = 200;
    private static final double HAUTEUR = 20;
    private static final long DUREE_FLASH_MS = 100;

    private final Rectangle fond;
    private final Rectangle barre;
    private final StackPane containerBarreVie;
    private final Vie vieModele;
    private final Rectangle overlayDegatsGlobal;
    private final FadeTransition fadeTransition;

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
        mettreAJourCouleurBarre(vieModele.vieActuelleProperty().get(), vieModele.vieMaxProperty());
    }

    private Rectangle creerFond() {
        Rectangle fond = new Rectangle(LARGEUR_BARRE_MAX, HAUTEUR);
        fond.setFill(Color.DARKGRAY);
        return fond;
    }

    private Rectangle creerBarre() {
        Rectangle barre = new Rectangle(LARGEUR_BARRE_MAX, HAUTEUR);
        barre.setFill(Color.LIMEGREEN);
        return barre;
    }

    private void configurerPositionBarre() {
        containerBarreVie.setTranslateX(20);
        containerBarreVie.setTranslateY(20);
    }

    private void lierLargeurBarre() {
        barre.widthProperty().bind(
                vieModele.vieActuelleProperty()
                        .divide(vieModele.vieMaxProperty())
                        .multiply(LARGEUR_BARRE_MAX)
        );
    }

    private void lierModele() {
        vieModele.vieActuelleProperty().addListener((obs, oldVal, newVal) -> {
            if (!vieModele.isTakingDamageProperty().get()) {
                mettreAJourCouleurBarre(newVal.doubleValue(), vieModele.vieMaxProperty());
            }
        });

        vieModele.isTakingDamageProperty().addListener((obs, oldVal, isTakingDamage) -> {
            if (isTakingDamage) {
                barre.setFill(Color.BLUE);
            } else {
                mettreAJourCouleurBarre(vieModele.vieActuelleProperty().get(), vieModele.vieMaxProperty());
            }
        });
    }

    private Rectangle creerOverlayDegats(Region rootPane) {
        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.RED);
        overlay.setOpacity(0.0);
        overlay.setVisible(false);

        overlay.widthProperty().bind(rootPane.widthProperty());
        overlay.heightProperty().bind(rootPane.heightProperty());

        return overlay;
    }

    private FadeTransition creerFadeTransition() {
        FadeTransition ft = new FadeTransition(Duration.millis(DUREE_FLASH_MS), overlayDegatsGlobal);
        ft.setFromValue(0.3);
        ft.setToValue(0.0);
        ft.setOnFinished(event -> overlayDegatsGlobal.setVisible(false));
        return ft;
    }

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

    public void afficherDegats() {
        Platform.runLater(() -> {
            fadeTransition.stop();
            overlayDegatsGlobal.setOpacity(0.3);
            overlayDegatsGlobal.setVisible(true);
            fadeTransition.playFromStart();
        });
    }

    public StackPane getNoeudBarreVie() {
        return containerBarreVie;
    }

    public Rectangle getOverlayDegatsGlobal() {
        return overlayDegatsGlobal;
    }
}
