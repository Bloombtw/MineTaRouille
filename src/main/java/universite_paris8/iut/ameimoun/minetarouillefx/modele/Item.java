package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Item {
    public enum TypeItem { BLOC, OBJET }
    private final TypeItem typeItem;
    private final Bloc bloc;
    private final Objet objet;
    private final SimpleIntegerProperty quantite;
    private final DoubleProperty x, y; // position au sol

    // Constructeur pour Bloc
    public Item(Bloc bloc, int quantite) {
        this.typeItem = TypeItem.BLOC;
        this.bloc = bloc;
        this.objet = null;
        this.quantite = new SimpleIntegerProperty(quantite);
        this.x = new SimpleDoubleProperty(0);
        this.y = new SimpleDoubleProperty(0);
    }
    public Item(Bloc bloc) { this(bloc, 1); }
    public Item(Objet objet) { this(objet, 1); }

    // Constructeur pour Objet
    public Item(Objet objet, int quantite) {
        this.typeItem = TypeItem.OBJET;
        this.objet = objet;
        this.bloc = null;
        this.quantite = new SimpleIntegerProperty(quantite);
        this.x = new SimpleDoubleProperty(0);
        this.y = new SimpleDoubleProperty(0);
    }

    // Accesseurs position
    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }
    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }

    // Accesseurs communs
    public int getQuantite() { return quantite.get(); }
    public void ajouterQuantite(int q) { quantite.set(quantite.get() + q); }
    public SimpleIntegerProperty quantiteProperty() { return quantite; }

    public String getNom() {
        return typeItem == TypeItem.BLOC ? bloc.getNom() : objet.getNom();
    }
    public int getId() {
        return typeItem == TypeItem.BLOC ? bloc.getId() : objet.getId();
    }
    public Bloc getBloc() { return bloc; }
    public Objet getObjet() { return objet; }
    public TypeItem getTypeItem() { return typeItem; }

    public Item getItem(int id) {
        if (typeItem == TypeItem.BLOC && bloc != null && bloc.getId() == id) {
            return this;
        } else if (typeItem == TypeItem.OBJET && objet != null && objet.getId() == id) {
            return this;
        }
        return null;
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }

    public int getStackMax() {
        if (typeItem == TypeItem.BLOC && bloc != null) {
            return bloc.getStackMax();
        } else if (typeItem == TypeItem.OBJET && objet != null) {
            return objet.getStackSize();
        }
        return 64;
    }

    @Override
    public boolean equals(Object autreObjet) {
        if (this == autreObjet) return true;
        if (!(autreObjet instanceof Item)) return false;
        Item autreItem = (Item) autreObjet;
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
}