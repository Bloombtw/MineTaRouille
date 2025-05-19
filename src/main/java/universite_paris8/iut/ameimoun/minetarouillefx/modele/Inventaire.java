package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Inventaire {

    private Item[] slots = new Item[9];
    private int selectedIndex = 0;

    public boolean ajouterItem(Item item) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = item;
                return true;
            }
        }
        return false;
    }

    public Item getItem(int index) {
        return (index >= 0 && index < slots.length) ? slots[index] : null;
    }

    public Item[] getSlots() {
        return slots;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < slots.length) {
            selectedIndex = index;
        }
    }
}