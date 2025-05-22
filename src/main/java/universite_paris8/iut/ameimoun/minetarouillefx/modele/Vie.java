package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class Vie {

    private final DoubleProperty vieMax;
    private DoubleProperty vieActuelle;
    private final List<Runnable> damageTakenCallbacks;
    private final BooleanProperty isTakingDamage;

    public Vie(double vieMaxInitiale) {
        this.vieMax = new SimpleDoubleProperty(vieMaxInitiale);
        this.vieActuelle = new SimpleDoubleProperty(vieMaxInitiale);
        this.damageTakenCallbacks = new ArrayList<>();
        this.isTakingDamage = new SimpleBooleanProperty(false);

        this.vieActuelle.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() < oldValue.doubleValue()) {
                    for (Runnable callback : damageTakenCallbacks) {
                        callback.run();
                    }
                }
            }
        });
    }

    public BooleanProperty isTakingDamageProperty() {
        return isTakingDamage;
    }

    public DoubleProperty vieMaxProperty() {
        return vieMax;
    }

    public DoubleProperty vieActuelleProperty() {
        return vieActuelle;
    }
    public void ajouterCallbackDegatsSubis(Runnable callback) {
        this.damageTakenCallbacks.add(callback);
    }

    public void subirDegats(double montant) {
        double nouvelleVie = vieActuelle.get() - montant;
        if (nouvelleVie < 0) nouvelleVie = 0;
        this.vieActuelle.set(nouvelleVie);
    }

    public void verifierDegats(Joueur joueur, Carte carte) {
        int joueurX = (int) (joueur.getX() / Constantes.TAILLE_TUILE);
        int joueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_TUILE);
        int sousJoueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO) / Constantes.TAILLE_TUILE);

        boolean currentlyOnHazard = false;

        if (carte.estDansLaMap(joueurX, joueurY)) {
            for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
                Bloc bloc = carte.getTerrain()[couche][joueurY][joueurX];
                if (bloc == Bloc.FEU) {
                    this.subirDegats(0.01);
                    currentlyOnHazard = true;
                }
            }
        }
        if (carte.estDansLaMap(joueurX, sousJoueurY)) {
            for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
                Bloc bloc = carte.getTerrain()[couche][sousJoueurY][joueurX];
                if (bloc == Bloc.CACTUS) {
                    this.subirDegats(0.01);
                    currentlyOnHazard = true;
                }
            }
        }
        this.isTakingDamage.set(currentlyOnHazard);
    }

    public boolean estMort() {
        return vieActuelle.get() <= 0;
    }

    public void soigner(double quantite) {
        double nouvelleVie = vieActuelle.get() + quantite;
        if (nouvelleVie > vieMax.get()) nouvelleVie = vieMax.get();
        this.vieActuelle.set(nouvelleVie);
    }

}