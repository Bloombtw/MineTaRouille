package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Bloc {
    DEFAULT(-1, "Default", true, 64),
    CIEL(0, "Ciel", false, 64),
    PIERRE(1, "Pierre", true, 64),
    SABLE(2, "Sable", true, 64),
    TRONC(3, "Tronc", true, 64),
    FEUILLAGE(4, "Feuillage", true, 64),
    TERRE(5, "Terre", true, 64),
    CIEL_CLAIR(6, "Ciel Clair", false, 64),
    GAY_CIEL(7, "Gayciel", true, 64),
    SABLE_ROUGE(8, "Sable Rouge", true, 64),
    TRANSPARENT(11, "Transparent", false, 64),
    CIEL_SOMBRE(12, "Ciel Sombre", false, 64),
    CORBEAU(13, "Corbeau", false, 64),
    LUNE(14, "Lune", true, 64),
    LUNE_ZELDA(15, "Lune Zelda", true, 64),
    ETOILE(16, "Etoile", false, 64),
    ARBUSTE_MORT(17, "Arbuste Mort", false, 64),
    ECHELLE(18, "Echelle", false, 64),
    FLECHE_VERS_LA_DROITE(19, "Flèche vers la droite", false, 64),
    ESCALIER_DROITE(20, "Escalier vers la droite", true, 64),
    GRES_CISELE(21, "Grès Ciselé", true, 64),
    GRES_COUPE(22, "Grès Coupé", true, 64),
    POUSSE_ACACIA(23, "Pousse d'Acacia", false, 64),
    FEUILLAGE_ACACIA(24, "Feuillage d'Acacia", true, 64),
    GRES(25, "Grès", true, 64),
    FEU(26, "Feu", false, 64),
    CACTUS(27, "Cactus", true, 64),
    NUAGE(28, "Nuage", false, 64),
    CIEL_VIOLET(29, "Ciel Violet", false, 64),
    NUAGE_PARTIE1(30, "Nuage Partie 1", false, 64),
    NUAGE_PARTIE2(31, "Nuage Partie 2", false, 64),
    NUAGE_PARTIE3(32, "Nuage Partie 3", false, 64);

    private final int id;
    private final String nom;
    private final boolean estSolide;
    private final int stackMax;

    Bloc(int id, String nom, boolean estSolide, int stackMax) {
        this.id = id;
        this.nom = nom;
        this.estSolide = estSolide;
        this.stackMax = stackMax;
    }

    public boolean estSolide() {
        return estSolide;
    }

    public static Bloc depuisId(int id) {
        for (Bloc bloc : values()) {
            if (bloc.id == id) return bloc;
        }
        return CIEL;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public int getStackMax() {
        return stackMax;
    }
}
