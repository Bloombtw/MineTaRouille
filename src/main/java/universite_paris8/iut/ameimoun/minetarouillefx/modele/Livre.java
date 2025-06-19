package universite_paris8.iut.ameimoun.minetarouillefx.modele;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class Livre {
    private final String[] pages;
    private final IntegerProperty pageCourante = new SimpleIntegerProperty(0);

    private final BooleanBinding peutAllerSuivant;
    private final BooleanBinding peutAllerPrecedent;

    public Livre() {
        this.pages = Constantes.LIVRE_INTRO;
        this.peutAllerSuivant = pageCourante.lessThan(pages.length - 1);
        this.peutAllerPrecedent = pageCourante.greaterThan(0);
    }

    public BooleanBinding peutAllerSuivantProperty() { return peutAllerSuivant; }
    public BooleanBinding peutAllerPrecedentProperty() { return peutAllerPrecedent; }

    public void pageSuivante() {
        if (pageCourante.get() < pages.length - 1) pageCourante.set(pageCourante.get() + 1);
    }
    public void pagePrecedente() {
        if (pageCourante.get() > 0) pageCourante.set(pageCourante.get() - 1);
    }
    public String getPageActuelle() { return pages[pageCourante.get()]; }
}
