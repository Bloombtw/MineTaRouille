package universite_paris8.iut.ameimoun.minetarouillefx.modele;

/**
 * Représente le joueur contrôlé par l'utilisateur.
 * Gère sa position, sa direction de regard et hérite des fonctionnalités
 * de la classe Personnage.
 */
public class Joueur extends Personnage {
    public Joueur() {
        super(30, 50, 100, "Joueur");
    }

    @Override
    public void agir() {
        mettreAJourDeplacement();
    }
    private boolean regardADroite = true;
    
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