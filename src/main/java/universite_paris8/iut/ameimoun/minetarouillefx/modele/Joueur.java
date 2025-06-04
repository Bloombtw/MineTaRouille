package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Joueur extends Personnage {
    public Joueur() {
        super(30, 50, 50, "Joueur");
    }

    private boolean regardADroite = true;// initialisation par défaut

    public void creuser(){
        //s'il a une pelle seulement
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