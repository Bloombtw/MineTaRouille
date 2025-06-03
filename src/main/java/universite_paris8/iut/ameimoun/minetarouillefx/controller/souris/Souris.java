    package universite_paris8.iut.ameimoun.minetarouillefx.controller.souris;

    import javafx.scene.Scene;
    import javafx.scene.input.MouseButton;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.input.ScrollEvent;
    import javafx.scene.layout.TilePane;
    import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
    import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
    import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireBloc;
    import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;

    public class Souris {
        private final Joueur joueur;
        private final Inventaire inventaire;
        private final VueInventaire vueInventaire;
        private JeuController jeuController;
        private VueCarte vueCarte;
        private VueJoueur vueJoueur;

        public Souris(Joueur joueur, Inventaire inventaire, VueCarte vueCarte, VueInventaire vueInventaire ) {
            this.joueur = joueur;
            this.inventaire = inventaire;
            this.vueCarte = vueCarte;
            this.vueInventaire = vueInventaire;
        }

        public void lier(TilePane tilePane) {
            tilePane.setOnMousePressed(this::gererClicSouris);
            tilePane.setOnMouseClicked(this::gererPlacementBloc);
        }

        public void desactiver(TilePane tilePane) {
            tilePane.setOnMousePressed(null);
            tilePane.setOnMouseReleased(null);
            tilePane.setOnMouseMoved(null);
        }

        public void lierScrollInventaire(Scene scene) {
            scene.addEventFilter(ScrollEvent.SCROLL, event -> {
                int index = inventaire.getSelectedIndex();
                int max = inventaire.getSlots().size();
                if (event.getDeltaY() < 0) {
                    index = (index + 1) % max;
                } else if (event.getDeltaY() > 0) {
                    index = (index - 1 + max) % max;
                }
                inventaire.setSelectedIndex(index);
                event.consume();
            });
        }


        public void setJeuController(JeuController jeuController) {
            this.jeuController = jeuController;
        }







        private void gererPlacementBloc(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) { // clic droit
                int x = (int) event.getX() / Constantes.TAILLE_TUILE;
                int y = (int) event.getY() / Constantes.TAILLE_TUILE;
                int couche = 1;

                boolean blocPlace = GestionnaireBloc.placerBloc(
                        Carte.getInstance(),
                        inventaire,
                        inventaire.getSelectedIndex(),
                        couche,
                        x,
                        y,
                        joueur
                );

                if (blocPlace) {
                    vueCarte.mettreAJourAffichage(x, y);
                    vueInventaire.mettreAJourAffichage();
                    if (vueJoueur != null) {
                        vueJoueur.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
                    }
                }
            }
        }



        private void gererClicSouris(MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY) return; // On ne gère que les clics gauches.
        int x = (int) event.getX() / Constantes.TAILLE_TUILE;
        int y = (int) event.getY() / Constantes.TAILLE_TUILE;
        int couche1 = 1;
        int couche2 = 2;

        Item itemBloc1 = GestionnaireBloc.casserBlocEtDonnerItem(couche1, x, y, joueur);
        Item itemBloc2 = GestionnaireBloc.casserBlocEtDonnerItem(couche2, x, y, joueur);
            if (itemBloc1 != null || itemBloc2 != null) {
                dropItemEtMettreAJour(itemBloc1, x, y, couche1);
                dropItemEtMettreAJour(itemBloc2, x, y, couche2);
                vueInventaire.mettreAJourAffichage();
            }

        }

        private void dropItemEtMettreAJour(Item item, int x, int y, int couche) {
            if (item != null) {
                vueCarte.mettreAJourAffichage(x, y); // le bloc cassé
                if (couche == 1 && y - 1 >= 0) {
                    vueCarte.mettreAJourAffichage(x, y - 1); // décor au-dessus si sol cassé
                }
                jeuController.spawnItemAuSol(item, x, y);
            }
        }

}


