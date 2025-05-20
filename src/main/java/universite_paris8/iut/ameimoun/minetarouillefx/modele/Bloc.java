package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Bloc {
    DEFAULT (-1, "Default", true), // Le bloc si la texture n'est pas trouvée
    CIEL(0, "Ciel", false),
    PIERRE(1, "Pierre", true),
    SABLE(2, "Sable", true),
    TRONC(3, "Tronc", true),
    FEUILLAGE(4, "Feuillage", true),
    TERRE(5, "Terre", true),
    CIEL_CLAIR(6, "Ciel Clair", false),
    GAY_CIEL(7, "Gayciel", true),
    SABLE_ROUGE(8, "Sable Rouge", true),
    TRANSPARENT(11, "Transparent", false),
    CIEL_SOMBRE(12, "Ciel Sombre", false),
    CORBEAU(13, "Corbeau", false),
    LUNE(14, "Lune", true),
    LUNE_ZELDA(15, "Lune Zelda", true),
    ETOILE(16, "Etoile", false),
    ARBUSTE_MORT(17, "Arbuste Mort", false),
    ECHELLE(18, "Echelle", false), // TODO : Ajouter le mouvement de l'échelle
    FLECHE_VERS_LA_DROITE(19, "Flèche vers la droite", false), // Pour guider le joueur
    ESCALIER_DROITE(20, "Escalier vers la droite", true),
    GRES_CISELE(21, "Grès Ciselé", true),
    GRES_COUPE(22, "Grès Coupé", true),
    POUSSE_ACACIA(23, "Pousse d'Acacia", false),
    FEUILLAGE_ACACIA(24, "Feuillage d'Acacia", true),
    GRES(25, "Grès", true),
    FEU(26, "Feu", false),
    CACTUS(27, "Cactus", true),
    NUAGE(28, "Nuage", false),
    CIEL_VIOLET(29, "Ciel Violet", false);

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


    // Méthode pour obtenir un Bloc à partir de son id
    public static Bloc depuisId(int id) {
        for (Bloc bloc : values()) {
            if (bloc.id == id) return bloc;
        }
        return CIEL; // Par défaut
    }
}
