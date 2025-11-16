package universite_paris8.iut.ameimoun.minetarouillefx.modele.loot;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;

public interface LootStrategy {
    Item genererLoot(Mob mob);
}