package universite_paris8.iut.ameimoun.minetarouillefx.modele.loot;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Objet;

public class LootHostileStrategy implements LootStrategy {
    @Override
    public Item genererLoot(Mob mob) {
        return new Item(Objet.FIL, 1);
    }
}
