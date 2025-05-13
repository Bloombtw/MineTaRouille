package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import java.util.ArrayList;

public class Item {
    private static ArrayList<Item> listeDeToutLesItems = new ArrayList<>(); // Pour avoir tout les items ajoutés
    private int id; // Chaque item à un id unique
    private String nom;
    private int durabilité; // Seulement selon le type
    private int stackSize; // Nombre max dans inventaire
    private String description; // Desc rapide
    private Type type; // Aller voir le type Type
    private Rarete rarete; // Aller voir le type rareté

    public Item(int id, String nom, int stackSize, String description, Type type, Rarete rarete) {
        this.id = id;
        this.nom = nom;
        this.stackSize = stackSize;
        this.description = description;
        this.type = type;
        this.rarete = rarete;
        listeDeToutLesItems.add(this);
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public int getStackSize() { return stackSize; }
    public String getDescription() { return description; }
    public Type getType() { return type; }
    public Rarete getRarete() { return rarete; }
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setStackSize(int stackSize) { this.stackSize = stackSize; }
    public void setDescription(String description) { this.description = description;}
    public void setType(Type type) { this.type = type; }
    public void setRarete(Rarete rarete) { this.rarete = rarete; }

    public ArrayList<Item> getListeDeToutLesItems() {
        return listeDeToutLesItems;
    }


    public static void main(String[] args) {
        Item item1 = new Item(0, "Zizi", 1, "C'est un zizi", Type.ARME, Rarete.LEGENDAIRE);
    }
}
