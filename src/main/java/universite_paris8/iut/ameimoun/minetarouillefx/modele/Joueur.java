package universite_paris8.iut.ameimoun.minetarouillefx.modele;

/**
 * Représente le joueur contrôlé par l'utilisateur.
 * Implémente le pattern Singleton pour garantir une seule instance.
 */
public class Joueur extends Personnage {

    // Instance unique de Joueur
    static Joueur instance;

    boolean regardADroite = true;

    // Constructeur privé pour empêcher toute instanciation extérieure
    public Joueur() {
        super(30, 50, 100, "Joueur");
    }

    /**
     * Retourne l'instance unique du joueur.
     * Crée l'instance si elle n'existe pas encore.
     */
    public static Joueur getInstance() {
        if (instance == null) {
            instance = new Joueur();
        }
        return instance;
    }

    //TODO ajt  la méthode ramasser un Item

    public boolean estRegardADroite() {
        return regardADroite;
    }

    public void regarderADroite() {
        this.regardADroite = true;
    }

    public void regarderAGauche() {
        this.regardADroite = false;
    }
}
