package universite_paris8.iut.ameimoun.minetarouillefx.modele;

/**
 * Représente le joueur contrôlé par l'utilisateur.
 * Gère sa position, sa direction de regard et hérite des fonctionnalités
 * de la classe Personnage.
 * Implémenté en Singleton pour garantir une seule instance.
 */
public class Joueur extends Personnage {
    private static Joueur instance;

    private boolean regardADroite = true;

    // Constructeur privé pour empêcher l'instanciation externe
    private Joueur() {
        super(30, 50, 100, "Joueur");
    }

    // Méthode statique pour accéder à l'unique instance
    public static Joueur getInstance() {
        if (instance == null) {
            instance = new Joueur();
        }
        return instance;
    }

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
