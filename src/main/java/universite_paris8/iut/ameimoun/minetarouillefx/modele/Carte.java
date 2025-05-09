package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Carte {
    public static String[][] generationCarte() {
        String[][] carte = new String[10][10];
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[i].length; j++) {
                carte[i][j] = "-";
            }
        }
        return carte;
    }

    public static void afficherCarte(String[][] carte) {
        for (int i = 0; i < carte.length; i++) {
            System.out.println();
            for (int j = 0; j < carte[i].length; j++) {
                System.out.print(carte[i][j] + " ");
            }
        }
    }
}
