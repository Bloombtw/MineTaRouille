package universite_paris8.iut.ameimoun.minetarouillefx.modele;

/**
 * Fabrique pour la création de blocs en fonction d'un identifiant ou d'un nom.
 * Permet de centraliser la logique de création et d'abstraire les détails d'instanciation.
 */
public class BlocFactory {

    /**
     * Crée un bloc à partir de son identifiant.
     * @param id L'identifiant du bloc.
     * @return Le bloc correspondant, ou `Bloc.CIEL` si non trouvé.
     */
    public static Bloc creerBloc(int id) {
        return Bloc.depuisId(id);
    }

    /**
     * Crée un bloc à partir de son nom.
     * @param nom Le nom du bloc.
     * @return Le bloc correspondant, ou `Bloc.CIEL` si non trouvé.
     */
    public static Bloc creerBloc(String nom) {
        for (Bloc bloc : Bloc.values()) {
            if (bloc.getNom().equalsIgnoreCase(nom)) {
                return bloc;
            }
        }
        return Bloc.CIEL; // Valeur par défaut
    }

    /**
     * Crée un bloc aléatoire parmi un ensemble prédéfini.
     * @return Un bloc aléatoire.
     */
    public static Bloc creerBlocAleatoire() {
        Bloc[] blocs = {Bloc.SABLE, Bloc.SABLE_ROUGE, Bloc.GRES, Bloc.PIERRE};
        return blocs[(int) (Math.random() * blocs.length)];
    }
}
