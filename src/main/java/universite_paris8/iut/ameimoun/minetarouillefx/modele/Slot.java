package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Slot {
    private Item item;

    public boolean isEmpty() {
        return item == null;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean peutEmpiler(Item autre) {
        return item != null && item.equals(autre) && item.getQuantite() < item.getStackMax();
    }

    public int empiler(Item autre) {
        if (!peutEmpiler(autre)) return autre.getQuantite();
        int place = item.getStackMax() - item.getQuantite();
        int aAjouter = Math.min(place, autre.getQuantite());
        item.ajouterQuantite(aAjouter);
        return autre.getQuantite() - aAjouter;
    }

    public void retirerUn() {
        if (item == null) return;
        if (item.getQuantite() > 1) {
            item.ajouterQuantite(-1);
        } else {
            item = null;
        }
    }

    public int retirerQuantite(int itemId, int quantite) {
        if (item == null || item.getId() != itemId) return quantite;

        if (item.getQuantite() > quantite) {
            item.setQuantite(item.getQuantite() - quantite);
            return 0;
        } else {
            quantite -= item.getQuantite();
            item = null;
            return quantite;
        }
    }

}
