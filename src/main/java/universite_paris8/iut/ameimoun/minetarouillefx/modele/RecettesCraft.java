package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum RecettesCraft {
    // 1 bûche n'importe où → 4 planches
    PLANCHE(
            null, // pattern ignoré pour ce cas spécial
            new Item(Bloc.PLANCHE),
            4
    );

    private final Bloc[][] pattern;
    private final Item resultat;
    private final int quantiteResultat;

    RecettesCraft(Bloc[][] pattern, Item resultat, int quantiteResultat) {
        this.pattern = pattern;
        this.resultat = resultat;
        this.quantiteResultat = quantiteResultat;
    }

    public boolean correspond(Item[][] grille) {
        return switch (this) {
            case PLANCHE -> correspondPlanches(grille);
            default      -> correspondPattern(grille);
        };
    }

    private boolean correspondPlanches(Item[][] grille) {
        if (!tailleGrilleEstCorrecte(grille)) return false;
        int nbBuches = 0;
        for (Item[] ligne : grille) {
            for (Item item : ligne) {
                if (item != null) {
                    if (item.getBloc() == Bloc.TRONC) nbBuches++;
                    else return false;
                }
            }
        }
        return nbBuches == 1;
    }

    private boolean correspondPattern(Item[][] grille) {
        if (!tailleGrilleEstCorrecte(grille)) return false;
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[i].length; j++) {
                if (!caseCorrespond(pattern[i][j], grille[i][j])) return false;
            }
        }
        return true;
    }

    private boolean tailleGrilleEstCorrecte(Item[][] grille) {
        return pattern != null
                && grille.length == pattern.length
                && grille[0].length == pattern[0].length;
    }

    private boolean caseCorrespond(Bloc attendu, Item present) {
        if (attendu == null) {
            return present == null;
        } else {
            return present != null && present.getBloc() == attendu;
        }
    }

    public Bloc[][] getPattern() { return pattern; }
    public Item getResultat() { return resultat; }
    public int getQuantiteResultat() { return quantiteResultat; }
}