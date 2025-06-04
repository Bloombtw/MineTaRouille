package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Craft {
    private final Objet[][] pattern; // 3x3
    private final Objet resultat;
    private final int quantiteResultat;

    public Craft(Objet resultat, int quantiteResultat) {
        this.pattern = new Objet[3][3];
        this.resultat = resultat;
        this.quantiteResultat = quantiteResultat;
    }

    public Objet[][] getPattern() { return pattern; }
    public Objet getResultat() { return resultat; }
    public int getQuantiteResultat() { return quantiteResultat; }
}