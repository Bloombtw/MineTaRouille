package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Type {
    ARME("Arme"),
    OUTIL("Outil"),
    BLOC("Bloc"),
    RESSOURCE("Ressource"),
    CONSOMMABLE("Consommable");

    private final String nom;

    Type(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}