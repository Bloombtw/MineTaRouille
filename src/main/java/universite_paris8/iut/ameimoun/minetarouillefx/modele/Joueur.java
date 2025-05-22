package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Joueur extends Personnage {
    private Inventaire inventaire;

    public Joueur() {
        super(30, 50, 100, "Joueur");
    }

    public void setInventaire(Inventaire inventaire) { this.inventaire = inventaire; }
    public Inventaire getInventaire() { return inventaire; }
}