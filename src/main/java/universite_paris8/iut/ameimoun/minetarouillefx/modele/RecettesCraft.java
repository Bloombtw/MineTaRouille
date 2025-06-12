package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum RecettesCraft {
    PLANCHE(
            new Item[][]{
                    {null, null, null},
                    {null, new Item(Bloc.TRONC), null},
                    {null, null, null}
            },
            new Item(Bloc.PLANCHE),
            4
    ),
    BATON(
            new Item[][]{
                    {null, null, null},
                    {null, new Item(Bloc.PLANCHE), null},
                    {null, new Item(Bloc.PLANCHE), null}
            },
            new Item(Objet.BATON),
            4
    ),
    PIOCHE(
            new Item[][]{
                    {new Item(Bloc.PIERRE), new Item(Bloc.PIERRE), new Item(Bloc.PIERRE)},
                    {null, new Item(Objet.BATON), null},
                    {null, new Item(Objet.BATON), null}
            },
            new Item(Objet.PIOCHE),
            1
    );

    private final Item[][] pattern;
    private final Item resultat;
    private final int quantiteResultat;

    RecettesCraft(Item[][] pattern, Item resultat, int quantiteResultat) {
        this.pattern = pattern;
        this.resultat = resultat;
        this.quantiteResultat = quantiteResultat;
    }

    /**
     * Vérifie si la grille fournie correspond au modèle de la recette.
     * @param grille La grille à vérifier.
     * @return true si la grille correspond au modèle, false sinon.
     */
    public boolean correspondPattern(Item[][] grille) {
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

    private boolean caseCorrespond(Item attendu, Item present) {
        return java.util.Objects.equals(attendu, present);
    }

    public Item[][] getPattern() { return pattern; }
    public Item getResultat() { return resultat; }
    public int getQuantiteResultat() { return quantiteResultat; }
}