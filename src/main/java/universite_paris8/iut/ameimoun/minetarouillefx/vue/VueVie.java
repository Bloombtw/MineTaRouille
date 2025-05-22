package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie;
import javafx.util.Duration;

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

        fond = new Rectangle(LARGEUR_BARRE_MAX, HAUTEUR);
        fond.setFill(Color.DARKGRAY);

        barre = new Rectangle(LARGEUR_BARRE_MAX, HAUTEUR);
        barre.setFill(Color.LIMEGREEN);

        containerBarreVie = new StackPane(fond, barre);
        containerBarreVie.setTranslateX(20);
        containerBarreVie.setTranslateY(20);
        barre.widthProperty().bind(
                vieModele.vieActuelleProperty().divide(vieModele.vieMaxProperty()).multiply(LARGEUR_BARRE_MAX)
        );

        vieModele.vieActuelleProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (!vieModele.isTakingDamageProperty().get()) {
                    mettreAJourCouleurBarre(newValue.doubleValue(), vieModele.vieMaxProperty().get());
                }
            }
        });

        vieModele.isTakingDamageProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isCurrentlyTakingDamage) {
                if (isCurrentlyTakingDamage) {
                    barre.setFill(Color.BLUE);
                } else {
                    mettreAJourCouleurBarre(vieModele.vieActuelleProperty().get(), vieModele.vieMaxProperty().get());
                }
            }
        });

        overlayDegatsGlobal = new Rectangle();
        overlayDegatsGlobal.setFill(Color.RED);
        overlayDegatsGlobal.setOpacity(0.0);
        overlayDegatsGlobal.setVisible(false);

        overlayDegatsGlobal.widthProperty().bind(rootPane.widthProperty());
        overlayDegatsGlobal.heightProperty().bind(rootPane.heightProperty());

        fadeTransition = new FadeTransition(Duration.millis(DUREE_FLASH_MS), overlayDegatsGlobal);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> {
            overlayDegatsGlobal.setVisible(false);
        });
        vieModele.ajouterCallbackDegatsSubis(this::afficherDegats);
        mettreAJourCouleurBarre(vieModele.vieActuelleProperty().get(), vieModele.vieMaxProperty().get());
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
            if (overlayDegatsGlobal != null) {
                fadeTransition.stop();
                overlayDegatsGlobal.setOpacity(0.3);
                overlayDegatsGlobal.setVisible(true);
                fadeTransition.playFromStart();
            }
        });
    }

    public StackPane getNoeudBarreVie() {
        return containerBarreVie;
    }

    public Rectangle getOverlayDegatsGlobal() {
        return overlayDegatsGlobal;
    }
}