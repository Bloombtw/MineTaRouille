package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Bloc {
    CIEL(0, "Ciel", false),
    PIERRE(1, "Pierre", true),
    SABLE(2, "Sable", true),
    TRONC(3, "Tronc", true),
    FEUILLAGE(4, "Feuillage", true),
    TERRE(5, "Terre", true),
    CIEL_CLAIR(6, "Ciel Clair", false),
    GAY_CIEL(7, "Gayciel", true),
    SABLE_ROUGE(8, "Sable Rouge", true),
    TERRE_STYLEE(9, "Terre Stylee", true),
    TERRE_STYLEE_SOMBRE(10, "Terre Stylee Sombre", true),
    TRANSPARENT(11, "Transparent", false),
    CIEL_SOMBRE(12, "Ciel Sombre", false),
    CORBEAU(13, "Corbeau", false),
    LUNE(14, "Lune", true),
    LUNE_ZELDA(15, "Lune Zelda", true),
    ETOILE(16, "Etoile", false),
    ARBUSTE_MORT(17, "Arbuste Mort", false);


    private final int id;
    private final String nom;
    private final boolean estSolide;

    Bloc(int id, String nom, boolean estSolide) {
        this.id = id;
        this.nom = nom;
        this.estSolide = estSolide;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public boolean estSolide() {
        return estSolide;
    }

    public static Bloc depuisId(int id) {
        for (Bloc bloc : values()) {
            if (bloc.id == id) return bloc;
        }
        return CIEL; // Par défaut
    }
}
