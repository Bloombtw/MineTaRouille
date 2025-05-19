package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

import java.util.ArrayList;
import java.util.Random;

public class Item {
    private ArrayList<Item> listeDeToutLesItems = new ArrayList<>();
    private int id;
    private String nom;
    private int durabilite;
    private int stackSize;
    private String description;
    private Type type;
    private Rarete rarete;
    private double x, y;

    public Item(int id, String nom, int stackSize, String description, Type type, Rarete rarete) {
        this.id = id;
        this.nom = nom;
        this.stackSize = stackSize;
        this.description = description;
        this.type = type;
        this.rarete = rarete;
        this.x = x;
        this.y = y;
        listeDeToutLesItems.add(this);
    }

    public void ajouterItem(Carte c, Item i) {
        Random random = new Random();
        int largeur = c.getLargeur();
        int hauteur = c.getHauteur();

        int x, y;

        // position aléeatoire
        do {
            x = random.nextInt(largeur);
            y = random.nextInt(hauteur);
        } while (c.estBlocSolide(x, y));

        i.setX(x * Constantes.TAILLE_PERSO);
        i.setY(y * Constantes.TAILLE_PERSO);

        this.listeDeToutLesItems.add(i);

        System.out.println("Item ajouté à la position : " + x + ", " + y);
    }//à finir


    public void retirerItem(Item i){
        this.listeDeToutLesItems.remove(i);
    }

    public String getNom() {return this.nom;}
    public ArrayList<Item> getListeDeToutLesItems() {
        return listeDeToutLesItems;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}