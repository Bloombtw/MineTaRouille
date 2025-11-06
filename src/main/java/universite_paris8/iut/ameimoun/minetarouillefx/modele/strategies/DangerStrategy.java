package universite_paris8.iut.ameimoun.minetarouillefx.modele.strategies;


import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie;

public interface DangerStrategy {
    boolean estDangereux(Bloc bloc);
    void appliquerEffet(Vie vie);
}