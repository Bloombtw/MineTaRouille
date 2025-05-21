package universite_paris8.iut.ameimoun.minetarouillefx.modele;

// L'import de JeuController est supprimé car Vie ne devrait pas en dépendre.
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes; // Toujours nécessaire pour la vérification des dégâts
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener; // Ajout de l'import
import javafx.beans.value.ObservableValue; // Ajout de l'import

public class Vie {

    private final DoubleProperty vieMax;
    private DoubleProperty vieActuelle;
    // Ajout d'une interface fonctionnelle pour le callback de dégâts
    private Runnable onDamageTakenCallback;

    public Vie(double vieMaxInitiale) {
        this.vieMax = new SimpleDoubleProperty(vieMaxInitiale);
        this.vieActuelle = new SimpleDoubleProperty(vieMaxInitiale);
        this.vieActuelle.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() < oldValue.doubleValue()) {
                    if (onDamageTakenCallback != null) {
                        onDamageTakenCallback.run();
                    }
                }
            }
        });
    }
    public DoubleProperty vieMaxProperty() {
        return vieMax;
    }

    public double getVieMax() {
        return vieMax.get();
    }

    public DoubleProperty vieActuelleProperty() {
        return vieActuelle;
    }

    public double getVieActuelle() {
        return vieActuelle.get();
    }

    // Méthode pour subir des dégâts
    public void subirDegats(double quantite) {
        double nouvelleVie = vieActuelle.get() - quantite;
        if (nouvelleVie < 0) nouvelleVie = 0;
        // La propriété sera mise à jour et l'écouteur se chargera du reste si la vie diminue
        this.vieActuelle.set(nouvelleVie);
    }

    // Déplace la logique de vérification des dégâts de l'environnement ici.
    // Il est important que cette méthode soit appelée régulièrement (par exemple, dans la boucle de jeu du Joueur).
    public void verifierDegats(Joueur joueur, Carte carte) {
        // Obtenez la position du joueur en coordonnées de tuile
        int joueurX = (int) (joueur.getX() / Constantes.TAILLE_TUILE);
        // On prend la base du joueur pour les collisions avec le sol/tuiles
        int joueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_TUILE);
        int sousJoueurY = joueurY + 1; // Tuile juste en dessous du joueur

        // Vérification de la tuile actuelle du joueur pour le feu
        if (carte.estDansLaMap(joueurX, joueurY)) {
            for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
                Bloc bloc = carte.getTerrain()[couche][joueurY][joueurX];
                if (bloc == Bloc.FEU) {
                    this.subirDegats(0.01); // Dégâts de feu
                    return; // Un seul type de dégât à la fois
                }
            }
        }

        // Vérification de la tuile sous le joueur pour les cactus
        if (carte.estDansLaMap(joueurX, sousJoueurY)) {
            for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
                Bloc bloc = carte.getTerrain()[couche][sousJoueurY][joueurX];
                if (bloc == Bloc.CACTUS) {
                    this.subirDegats(0.01); // Dégâts de cactus
                    return; // Un seul type de dégât à la fois
                }
            }
        }
    }

    public boolean estMort() {
        return vieActuelle.get() <= 0;
    }

    public void soigner(double quantite) {
        double nouvelleVie = vieActuelle.get() + quantite;
        if (nouvelleVie > vieMax.get()) nouvelleVie = vieMax.get();
        this.vieActuelle.set(nouvelleVie);
    }

    // Setter pour le callback de dégâts
    public void setOnDamageTakenCallback(Runnable callback) {
        this.onDamageTakenCallback = callback;
    }
}