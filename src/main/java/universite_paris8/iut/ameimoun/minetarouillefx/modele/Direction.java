package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Direction {
    GAUCHE("Gauche"),
    DROITE("Droite");

    private final String nom;

    Direction(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}