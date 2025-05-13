package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Rarete {
    COMMUN("Commun", "#A0A0A0" ),
    RARE("Rare", "#007BFF"),
    EPIQUE("Epique", "#800080"),
    LEGENDAIRE("Legendaire", "#FFD700");




    private final String nomRarete;
    private final String couleurRarete;

    Rarete(String nomRarete, String couleurRarete) {
        this.nomRarete = nomRarete;
        this.couleurRarete = couleurRarete;
    }

    public String getNomLisible() {
        return nomRarete;
    }

    public String getCouleurHex() {
        return couleurRarete;
    }

    @Override
    public String toString() {
        return nomRarete;
    }
}

