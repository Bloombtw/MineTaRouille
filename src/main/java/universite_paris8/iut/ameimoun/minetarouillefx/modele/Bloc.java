package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Bloc {
    //Ajouter le bloc dans GestionnaireImage pour l'affichage des blocs.
    DEFAULT(-1, "Default", true, false, 64, false, false),
    CIEL(0, "Ciel", false, false, 64, false, false),
    PIERRE(1, "Pierre", true, false, 64, false, false),
    SABLE(2, "Sable", true, false, 64, false, false),
    TRONC(3, "Tronc", true, false, 64, false, false),
    FEUILLAGE(4, "Feuillage", true, false, 64, false, false),
    TERRE(5, "Terre", true, false, 64, false, false),
    CIEL_CLAIR(6, "Ciel Clair", false, false, 64, false, false),
    GAY_CIEL(7, "Gayciel", true, false, 64, false, false),
    SABLE_ROUGE(8, "Sable Rouge", true, false, 64, false, false),
    TRANSPARENT(11, "Transparent", false, false, 64, false, false),
    CIEL_SOMBRE(12, "Ciel Sombre", false, false, 64, false, false),
    CORBEAU(13, "Corbeau", false, false, 64, true, false),
    LUNE(14, "Lune", true, false, 64, false, false),
    LUNE_ZELDA(15, "Lune Zelda", true, false, 64, false, false),
    ETOILE(16, "Etoile", false, false, 64, false, false),
    ARBUSTE_MORT(17, "Arbuste Mort", false, true, 64, false, false),
    ECHELLE(18, "Echelle", false, true, 64, false, false),
    FLECHE_VERS_LA_DROITE(19, "Flèche vers la droite", false, false, 64, false, false),
    ESCALIER_DROITE(20, "Escalier vers la droite", true, false, 64, false, false),
    GRES_CISELE(21, "Grès Ciselé", true, false, 64, false, false),
    GRES_COUPE(22, "Grès Coupé", true, false, 64, false, false),
    POUSSE_ACACIA(23, "Pousse d'Acacia", false, true, 64, false, false),
    FEUILLAGE_ACACIA(24, "Feuillage d'Acacia", true, false, 64, false, false),
    GRES(25, "Grès", true, false, 64, false, false),
    FEU(26, "Feu", false, true, 64, true, false),
    CACTUS(27, "Cactus", true, true, 64, false, false),
    NUAGE(28, "Nuage", false, false, 64, false, false),
    CIEL_VIOLET(29, "Ciel Violet", false, false, 64, false, false),
    NUAGE_PARTIE1(30, "Nuage Partie 1", false, false, 64, false, false),
    NUAGE_PARTIE2(31, "Nuage Partie 2", false, false, 64, false, false),
    NUAGE_PARTIE3(32, "Nuage Partie 3", false, false, 64, false, false),
    TABLE_CRAFT(33, "Table de Craft", true, false, 64, false, true),
    PLANCHE(34, "Planche", true, false, 64, false, false);

    private final int id;
    private final String nom;
    private final boolean estSolide;
    private final boolean necessiteSupport;
    private final int stackMax;
    private final boolean estAnime;
    private final boolean estBlocAction;

    Bloc(int id, String nom, boolean estSolide, boolean necessiteSupport, int stackMax, boolean estAnime, boolean estBlocAction) {
        this.id = id;
        this.nom = nom;
        this.estSolide = estSolide;
        this.necessiteSupport = necessiteSupport;
        this.stackMax = stackMax;
        this.estAnime = estAnime;
        this.estBlocAction = estBlocAction;
    }

    public boolean estSolide() {
        return estSolide;
    }

    public boolean estAnime() {
        return estAnime;
    }

    public boolean necessiteSupport() {
        return necessiteSupport;
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

    public boolean estBlocAction() {
        return estBlocAction;
    }
}