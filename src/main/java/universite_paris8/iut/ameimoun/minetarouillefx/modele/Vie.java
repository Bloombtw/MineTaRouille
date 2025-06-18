package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la vie d'un joueur ou d'une entité dans le jeu.
 * Elle gère la vie maximale, la vie actuelle, les dégâts subis et les actions associées.
 *
 * @author Ahmed
 */
public class Vie {
    private final BooleanProperty estEnVieProperty;
    private final DoubleProperty vieMax;// en double
    private DoubleProperty vieActuelle;
    private final List<Runnable> actionsSurDegats;
    private final BooleanProperty subitDegats;
    private Runnable callbackDegatsSubis; //

    public Vie(double vieMaxInitiale) {
        this.vieMax = new SimpleDoubleProperty(vieMaxInitiale);
        this.vieActuelle = new SimpleDoubleProperty(vieMaxInitiale);
        this.actionsSurDegats = new ArrayList<>();
        this.subitDegats = new SimpleBooleanProperty(false);
        this.estEnVieProperty = new SimpleBooleanProperty(true); // Initialisé à true
    }

    /**
     * Fait subir les dégats
     * @param quantite la quantité de dégats à infliger
     */
    public void subirDegats(double quantite) {
        double ancienneVie = vieActuelle.get();
        double nouvelleVie = ancienneVie - quantite;
        if (nouvelleVie < 0) nouvelleVie = 0;
        if (nouvelleVie < ancienneVie && callbackDegatsSubis != null) {
            callbackDegatsSubis.run(); // On déclenche ici
        }
        this.vieActuelle.set(nouvelleVie);
    }

    /**
     * Vérifie si le joueur se trouve sur un bloc dangereux (comme le feu ou un cactus)
     * et applique des dégâts s'il y en a. Cette méthode met également à jour
     * l'état {@code subitDegats} pour indiquer si le joueur est actuellement en train de subir des dégâts.
     *
     * @param joueur Le joueur dont la position est vérifiée.
     * @param carte  La carte sur laquelle se trouve le joueur, utilisée pour identifier les blocs autour de lui.
     */
    public void verifierDegats(Joueur joueur, Carte carte) {
        int joueurX = (int) (joueur.getX() / Constantes.TAILLE_TUILE);
        int joueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_TUILE);
        int sousJoueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO) / Constantes.TAILLE_TUILE);

        boolean estSurBlocDeDegat = false;

        // Pour le feu
        if (carte.estDansLaMap(joueurX, joueurY)) {
            for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
                Bloc bloc = carte.getTerrain()[couche][joueurY][joueurX];
                if (bloc == Bloc.FEU) {
                    this.subirDegats(0.01);
                    estSurBlocDeDegat = true;
                }
            }
        }
        // Pour le cactus
        if (carte.estDansLaMap(joueurX, sousJoueurY)) {
            for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
                Bloc bloc = carte.getTerrain()[couche][sousJoueurY][joueurX];
                if (bloc == Bloc.CACTUS) {
                    this.subirDegats(0.01);
                    estSurBlocDeDegat = true;
                }
            }
        }
        this.subitDegats.set(estSurBlocDeDegat);
    }

    public boolean estMort() {
        return vieActuelle.get() <= 0;
    }

    // TODO : Utiliser si ajout de potion
    public void soigner(double quantite) {
        double nouvelleVie = vieActuelle.get() + quantite;
        if (nouvelleVie > vieMax.get()) nouvelleVie = vieMax.get();
        this.vieActuelle.set(nouvelleVie);
    }

    public boolean estLow() {
        return vieActuelle.get() < vieMax.get() * 0.3; // 30% de la vie max
    }


    public BooleanProperty isTakingDamageProperty() { return subitDegats; }
    public DoubleProperty vieMaxProperty() { return vieMax; }
    public DoubleProperty vieActuelleProperty() { return vieActuelle; }
    public void ajouterCallbackDegatsSubis(Runnable callback) { this.actionsSurDegats.add(callback); }
    public void setEstEnVie(boolean estEnVie) {this.estEnVieProperty.set(estEnVie);}

}