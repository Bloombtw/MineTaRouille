package universite_paris8.iut.ameimoun.minetarouillefx.modele;
/**
 * Enum représentant les types d'objets dans le jeu.
 * Chaque type a un nom et une indication de s'il permet la rareté.
 *
 * @author Anton
 */
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



    @Override
    public String toString() {
        return nom;
    }
}