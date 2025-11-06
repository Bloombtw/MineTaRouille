package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.strategies.CactusStrategy;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.strategies.DangerStrategy;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.strategies.FeuStrategy;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import java.util.ArrayList;
import java.util.List;

public class Vie {
    private final BooleanProperty estEnVieProperty;
    private final double vieMax;
    private DoubleProperty vieActuelle;
    private final List<Runnable> actionsSurDegats;
    private final BooleanProperty subitDegats;
    private Runnable callbackDegatsSubis;

    private final List<DangerStrategy> strategies = List.of(
            new FeuStrategy(),
            new CactusStrategy()
    );

    public Vie(double vieMaxInitiale) {
        this.vieMax = vieMaxInitiale;
        this.vieActuelle = new SimpleDoubleProperty(vieMaxInitiale);
        this.actionsSurDegats = new ArrayList<>();
        this.subitDegats = new SimpleBooleanProperty(false);
        this.estEnVieProperty = new SimpleBooleanProperty(true);
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
        double nouvelleVie = Math.max(0, ancienneVie - quantite);
        if (nouvelleVie < ancienneVie && callbackDegatsSubis != null) {
            callbackDegatsSubis.run();
        }
        this.vieActuelle.set(nouvelleVie);
    }

    public void verifierDegats(Joueur joueur, Carte carte) {
        int x = (int) (joueur.getX() / Constantes.TAILLE_TUILE);
        int y1 = (int) ((joueur.getY() + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_TUILE);
        int y2 = (int) ((joueur.getY() + Constantes.TAILLE_PERSO) / Constantes.TAILLE_TUILE);

        boolean danger = false;

        for (int y : List.of(y1, y2)) {
            if (!carte.estDansLaMap(x, y)) continue;

            for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
                Bloc bloc = carte.getBloc(x, y, couche);
                for (DangerStrategy strategy : strategies) {
                    if (strategy.estDangereux(bloc)) {
                        strategy.appliquerEffet(this);
                        danger = true;
                    }
                }
            }
        }

        subitDegats.set(danger);
    }

    public boolean estMort() {
        return vieActuelle.get() <= 0;
    }

    public void soigner(double quantite) {
        double nouvelleVie = Math.min(vieMax, vieActuelle.get() + quantite);
        this.vieActuelle.set(nouvelleVie);
    }

    public void setEstEnVie(boolean estEnVie) {
        this.estEnVieProperty.set(estEnVie);
    }

    public boolean estLow() {
        return vieActuelle.get() < vieMax * 0.3;
    }
}
