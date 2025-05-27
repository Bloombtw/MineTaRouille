package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Type {
    ARME("Arme", true),
    OUTIL("Outil", true),
    BLOC("Bloc", false),
    RESSOURCE("Ressource", false),
    CONSOMMABLE("Consommable", false);

    private final boolean permetRarete;
    private final String nom;

    Type(String nom, boolean permetRarete) {
        this.nom = nom;
        this.permetRarete = permetRarete;
    }

    public boolean permetRarete() {
        return permetRarete;
    }

    @Override
    public String toString() {
        return nom;
    }
}