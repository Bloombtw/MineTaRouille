package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class GestionnaireBloc {

    // Tente de casser un bloc à la position (x, y) dans la couche spécifiée,
    // et retourne un Item représentant le bloc cassé si celui-ci est solide. Sinon, retourne null.
    public static Item casserBlocEtDonnerItem(int couche, int x, int y) {
        Bloc blocCasse = Carte.getInstance().casserBloc(couche, x, y);
        if (blocCasse != null && blocCasse.estSolide()) {
             return new Item(
                    blocCasse.getId(),
                    blocCasse.getNom(),
                    blocCasse.getStackMax(),
                    "Bloc cassé",
                    Type.BLOC,
                    null
            );
        }
        return null;
    }

}
