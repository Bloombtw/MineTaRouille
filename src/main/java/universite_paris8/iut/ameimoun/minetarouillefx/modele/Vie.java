package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import java.util.ArrayList;
import java.util.List;

public class Vie {
    private final BooleanProperty estEnVieProperty;
    private final double vieMax;// en double
    private DoubleProperty vieActuelle;
    private final List<Runnable> actionsSurDegats;
    private final BooleanProperty subitDegats;
    private Runnable callbackDegatsSubis;

    public Vie(double vieMaxInitiale) {
        this.vieMax = vieMaxInitiale;
        this.vieActuelle = new SimpleDoubleProperty(vieMaxInitiale);
        this.actionsSurDegats = new ArrayList<>();
        this.subitDegats = new SimpleBooleanProperty(false);
        this.estEnVieProperty = new SimpleBooleanProperty(true); // Initialisé à true
    }

    public BooleanProperty isTakingDamageProperty() {
        return subitDegats;
    }

    public double getVieMax() {
        return vieMax;
    }

    public DoubleProperty vieActuelleProperty() {
        return vieActuelle;
    }

    public void ajouterCallbackDegatsSubis(Runnable callback) {
        this.actionsSurDegats.add(callback);
    }

    public void subirDegats(double quantite) {
        double ancienneVie = vieActuelle.get();
        double nouvelleVie = ancienneVie - quantite;
        if (nouvelleVie < 0) nouvelleVie = 0;
        if (nouvelleVie < ancienneVie && callbackDegatsSubis != null) {
            callbackDegatsSubis.run(); // On déclenche ici
        }
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
        this.subitDegats.set(currentlyOnHazard);
    }

    public boolean estMort() {
        return vieActuelle.get() <= 0;
    }

    public void soigner(double quantite) {
        double nouvelleVie = vieActuelle.get() + quantite;
        if (nouvelleVie > vieMax) nouvelleVie = vieMax;
        this.vieActuelle.set(nouvelleVie);
    }


    public void setEstEnVie(boolean estEnVie) {
        this.estEnVieProperty.set(estEnVie);
    }

    public boolean estLow() {
        return vieActuelle.get() < vieMax * 0.3; // 30% de la vie max
    }

}