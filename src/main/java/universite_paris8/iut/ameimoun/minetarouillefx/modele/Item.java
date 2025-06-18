package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.SimpleIntegerProperty;


public class Item {
    public enum TypeItem { BLOC, OBJET }
    private final TypeItem typeItem;
    private final Bloc bloc;
    private final Objet objet;
    private final SimpleIntegerProperty quantite;
    private double x, y; // position au sol

    // Constructeur pour Bloc
    public Item(Bloc bloc, int quantite) {
        this.typeItem = TypeItem.BLOC;
        this.bloc = bloc;
        this.objet = null;
        this.quantite = new SimpleIntegerProperty(quantite);
    }
    public Item (Bloc bloc) {
        this(bloc, 1);
    }
    public Item (Objet objet) {
        this(objet, 1);
    }

    // Constructeur pour Objet
    public Item(Objet objet, int quantite) {
        this.typeItem = TypeItem.OBJET;
        this.objet = objet;
        this.bloc = null;
        this.quantite = new SimpleIntegerProperty(quantite);
    }

    // renvoie le StackMax
    public int getStackMax() {
        if (typeItem == TypeItem.BLOC && bloc != null) {
            return bloc.getStackMax();
        } else if (typeItem == TypeItem.OBJET && objet != null) {
            return objet.getStackSize();
        }
        return 64; // Valeur par défaut si jamais
    }

    @Override
    /**
     * Vérifie l'égalité entre deux items.
     * Deux items sont égaux s'ils ont le même type (Bloc ou Objet) et le même ID.
     */
    public boolean equals(Object autreObjet) {
        if (this == autreObjet) return true;
        // Vérifie si l'objet est de la même classe
        if (!(autreObjet instanceof Item autreItem)) return false;
        // Vérifie si les types et IDs sont égaux
        return typeItem == autreItem.typeItem && getId() == autreItem.getId();
    }

    @Override
    public String toString() {
        return "Item{" +
                "type=" + (getTypeItem() == TypeItem.BLOC ? bloc : objet) +
                ", quantite=" + quantite +
                ", id=" + getId() +
                '}';
    }

    public int getQuantite() { return quantite.get(); }
    public void ajouterQuantite(int q) { quantite.set(quantite.get() + q); }
    public String getNom() {
        return typeItem == TypeItem.BLOC ? bloc.getNom() : objet.getNom();
    }
    public int getId() {
        return typeItem == TypeItem.BLOC ? bloc.getId() : objet.getId();
    }
    public Bloc getBloc() { return bloc; }
    public Objet getObjet() { return objet; }
    public TypeItem getTypeItem() { return typeItem; }
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }
}
