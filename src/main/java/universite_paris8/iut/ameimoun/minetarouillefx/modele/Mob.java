package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Mob extends Personnage {
    private Direction mouvementDirection = Direction.DROITE;

    public Mob() {
        super(200, 10, 10, "poulet");
    }

    public void mettreAJour() {
        gravite();
        if (mouvementDirection == Direction.DROITE) {
            deplacerDroite();
        } else {
            deplacerGauche();
        }
    }

}