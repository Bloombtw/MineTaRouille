package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Carte {
    public static final String[][] PROTOTYPE_CARTE = {
            {"ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel"},
            {"ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel"},
            {"ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel"},
            {"ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel"},
            {"ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel"},
            {"ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel", "ciel"},
            {"terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre"},
            {"terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre"},
            {"terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre"},
            {"terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre", "terre"}
    };
    public static void afficherCarte() {
        for (String[] ligne : PROTOTYPE_CARTE) {
            for (String cellule : ligne) {
                System.out.print(cellule + " ");
            }
            System.out.println();
        }
    }

    public static String[][] genererCarte() {
        String[][] carte = new String[5][5]; // Choisir la taille de la carte

        //remplit avec deux motifs choisis
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[i].length; j++) {
                if (i < 3) {
                    carte[i][j] = "."; // Ici le '.' veut dire ciel
                } else {  // Terre dans les 2 dernières lignes
                    carte[i][j] = "#"; // Changer les symboles ci besoin
                }
            }
        }
        return carte;
    }
}

