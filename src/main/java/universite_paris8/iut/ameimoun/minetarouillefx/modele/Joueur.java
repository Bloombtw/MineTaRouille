package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import java.util.Iterator;
import java.util.List;

/**
 * Représente le joueur contrôlé par l'utilisateur.
 * Gère sa position, sa direction de regard et hérite des fonctionnalités
 * de la classe Personnage.
 * Implémenté en Singleton pour garantir une seule instance.
 */
public class Joueur extends Personnage {
    private static Joueur instance;

    private boolean regardADroite = true;
    private final Inventaire inventaire = new Inventaire();

    private Joueur() {
        super(30, 50, 100, "Joueur");
    }

    public static Joueur getInstance() {
        if (instance == null) {
            instance = new Joueur();
        }
        return instance;
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

    public Inventaire getInventaire() {
        return inventaire;
    }

    public boolean ramasserItem(Item item) {
        if (item == null) return false;
        if (inventaire.aDeLaPlacePour(item)) {
            inventaire.ajouterItem(item);
            return true;
        }
        return false;
    }

    public void ramasserItemsProches(List<Item> itemsDansLeMonde) {
        double rayonRamassage = 30.0;

        Iterator<Item> iterator = itemsDansLeMonde.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            double distance = Math.hypot(getX() - item.getX(), getY() - item.getY());
            if (distance <= rayonRamassage && ramasserItem(item)) {
                iterator.remove();
            }
        }
    }
}
