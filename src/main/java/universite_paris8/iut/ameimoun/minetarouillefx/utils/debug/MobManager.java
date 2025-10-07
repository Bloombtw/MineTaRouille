package universite_paris8.iut.ameimoun.minetarouillefx.utils.debug;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire de la liste des mobs présents dans le monde.
 * Permet d'ajouter un mob et de récupérer la liste.
 */
public class MobManager {
    private final List<Mob> mobs = new ArrayList<>();

    public void ajouterMob(Mob mob) {
        if (mob != null) {
            mobs.add(mob);
        }
    }

    public List<Mob> getMobs() {
        return mobs;
    }
}