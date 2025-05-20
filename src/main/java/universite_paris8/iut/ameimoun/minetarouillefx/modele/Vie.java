package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

public class Vie {

    private final double vieMax;
    private double vieActuelle;

    private Runnable observateur; // Pour notifier la vue

    public Vie(double vieMax) {
        this.vieMax = vieMax;
        this.vieActuelle = vieMax;
    }

    private JeuController controleur;

    public void setControleur(JeuController controleur) {
        this.controleur = controleur;
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

        if (controleur != null) {
            controleur.afficherDegats(); // 👈 appel de l'effet visuel
        }

        notifier();
    }


    public void soigner(double quantite) {
        vieActuelle += quantite;
        if (vieActuelle > vieMax) vieActuelle = vieMax;
        notifier();
    }


    public void verifierDegats(Joueur joueur, Carte carte) {
        int joueurX = (int) (joueur.getX() / Constantes.TAILLE_TUILE);
        int joueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_TUILE); // au niveau des pieds
        int sousJoueurY = joueurY + 1;

        if (!carte.estDansLaMap(joueurX, joueurY)) return;

        // Verifie pour le feu
        for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
            Bloc bloc = carte.getTerrain()[couche][joueurY][joueurX];
            if (bloc == Bloc.FEU) {
                this.subirDegats(0.01);
                return;
            }
        }

        // Verifie pour les cactus
        if (carte.estDansLaMap(joueurX, sousJoueurY)) {
            for (int couche = Constantes.NB_COUCHES - 1; couche >= 0; couche--) {
                Bloc bloc = carte.getTerrain()[couche][sousJoueurY][joueurX];
                if (bloc == Bloc.CACTUS) {
                    this.subirDegats(0.01);
                    return;
                }
            }
        }
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
