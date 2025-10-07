package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class EntitePosition {
    protected final DoubleProperty x = new SimpleDoubleProperty();
    protected final DoubleProperty y = new SimpleDoubleProperty();

    public EntitePosition(double x, double y) {
        this.x.set(x);
        this.y.set(y);
    }

    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }
}
