package universite_paris8.iut.ameimoun.minetarouillefx.controller;
import javafx.beans.binding.BooleanBinding;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Livre;

public class LivreController {
    private final Livre model;

    public LivreController(Livre model) {
        this.model = model;
    }
    public BooleanBinding peutAllerSuivantProperty() {
        return model.peutAllerSuivantProperty();
    }

    public BooleanBinding peutAllerPrecedentProperty() {
        return model.peutAllerPrecedentProperty();
    }

    public void pageSuivante(Runnable callback) {
        model.pageSuivante();
        callback.run();
    }

    public void pagePrecedente(Runnable callback) {
        model.pagePrecedente();
        callback.run();
    }

    public String getCheminPageCourante() {
        return model.getPageActuelle();
    }
}