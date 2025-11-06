package universite_paris8.iut.ameimoun.minetarouillefx.modele.strategies;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie;

public class CactusStrategy implements DangerStrategy {
    @Override
    public boolean estDangereux(Bloc bloc) {
        return bloc == Bloc.CACTUS;
    }

    @Override
    public void appliquerEffet(Vie vie) {
        vie.subirDegats(0.01);
    }
}
