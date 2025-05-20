package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Vie {

    private final double vieMax;
    private double vieActuelle;

    private Runnable observateur; // Pour notifier la vue

    public Vie(double vieMax) {
        this.vieMax = vieMax;
        this.vieActuelle = vieMax;
    }

    public double getVieMax() {
        return vieMax;
    }

    public double getVieActuelle() {
        return vieActuelle;
    }

    public void subirDegats(double quantite) {
        vieActuelle -= quantite;
        if (vieActuelle < 0) vieActuelle = 0;
        notifier();
    }

    public void soigner(double quantite) {
        vieActuelle += quantite;
        if (vieActuelle > vieMax) vieActuelle = vieMax;
        notifier();
    }

    public void setObservateur(Runnable obs) {
        this.observateur = obs;
    }

    private void notifier() {
        if (observateur != null) observateur.run();
    }

    public boolean estMort(){
        if(vieActuelle <=0){
            return true;
        }
        return false;
    }
}
