package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Bloc {
    CIEL(0, "Ciel", false),
    PIERRE(1, "Pierre", true),
    SABLE(2, "Sable", true),
    TRONC(3, "Tronc", true),
    FEUILLAGE(4, "Feuillage", true),
    TERRE(5, "Terre", true),
    CIEL_CLAIR(6, "Ciel Clair", false);

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