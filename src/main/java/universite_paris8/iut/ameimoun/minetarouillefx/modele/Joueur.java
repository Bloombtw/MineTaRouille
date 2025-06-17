package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Joueur extends Personnage {
    public Joueur() {
        super(30, 50, 100, "Joueur");
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