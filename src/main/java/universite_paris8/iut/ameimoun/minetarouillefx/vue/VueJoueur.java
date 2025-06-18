    package universite_paris8.iut.ameimoun.minetarouillefx.vue;

    import javafx.animation.AnimationTimer;
    import javafx.application.Platform;
    import javafx.scene.Group;
    import javafx.scene.image.ImageView;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.Rectangle;
    import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
    import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
    import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
    import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
    import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue.AnimationJoueur;

    /**
     * Représente la vue graphique d’un joueur dans le jeu.
     * Elle affiche le personnage via une imageview, applique une animation de blessure lors
     * des dégâts et affiche l’objet actuellement tenu par le joueur.
     * Elle lie aussi dynamiquement la position de la vue avec le modèle joueur.
     */
    public class VueJoueur {

        /** Modèle représentant le joueur. */
        private final Joueur joueur;

        /** Image du joueur affichée à l’écran. */
        private final ImageView perso;

        /** Conteneur principal de la vue du joueur. */
        private final Group container;

        /** Overlay rouge affiché temporairement lors de dégâts. */
        private final Rectangle overlayDegats;

        /** Image de l’objet actuellement tenu par le joueur. */
        private final ImageView objetTenu;

        /** Gestionnaire d’animation pour le joueur. */
        private final AnimationJoueur animationJoueur;

        /**
         * Crée une vue graphique pour un joueur donné.
         *
         * @param joueur Le joueur à représenter.
         */
        public VueJoueur(Joueur joueur) {
            this.joueur = joueur;

            perso = new ImageView();
            perso.setFitWidth(Constantes.TAILLE_PERSO);
            perso.setFitHeight(Constantes.TAILLE_PERSO);
            animationJoueur = new AnimationJoueur(perso);

            overlayDegats = creerOverlayDegats();

            objetTenu = new ImageView();
            objetTenu.setFitWidth(16);
            objetTenu.setFitHeight(16);
            objetTenu.setTranslateX(10);
            objetTenu.setTranslateY(10);

            container = new Group(perso, overlayDegats, objetTenu);

            lierPositionContainer();
            lierListeners();

            joueur.getVie().ajouterCallbackDegatsSubis(this::afficherDegats);
            animationJoueur.mettreAJourAnimation(joueur);
        }

        /**
         * Crée un rectangle rouge semi-transparent pour signaler les dégâts.
         *
         * @return Un rectangle utilisé comme overlay.
         */
        private Rectangle creerOverlayDegats() {
            Rectangle rect = new Rectangle(Constantes.TAILLE_PERSO, Constantes.TAILLE_PERSO);
            rect.setFill(Color.RED);
            rect.setOpacity(0.5);
            rect.setVisible(false);
            return rect;
        }

        /**
         * Lie la position du conteneur à la position du joueur.
         */
        private void lierPositionContainer() {
            container.translateXProperty().bind(joueur.xProperty());
            container.translateYProperty().bind(joueur.yProperty());
        }

        /**
         * Met à jour l’animation lorsque la position du joueur change.
         */
        private void lierListeners() {
            joueur.xProperty().addListener((obs, oldX, newX) -> animationJoueur.mettreAJourAnimation(joueur));
            joueur.yProperty().addListener((obs, oldY, newY) -> animationJoueur.mettreAJourAnimation(joueur));
        }

        /**
         * Retourne le noeud graphique représentant le joueur.
         *
         * @return Le group contenant l’image du joueur, l’overlay et l’objet tenu.
         */
        public Group getNode() {
            return container;
        }

        /**
         * Affiche un clignotement rouge lorsque le joueur subit des dégâts.
         * Cet effet est temporaire (200 ms).
         */
        public void afficherDegats() {
            Platform.runLater(() -> {
                if (overlayDegats != null) {
                    overlayDegats.setVisible(true);
                    new AnimationTimer() {
                        private long start = -1;
                        @Override
                        public void handle(long now) {
                            if (start < 0) start = now;
                            if (now - start > 200_000_000L) { // 200 ms
                                overlayDegats.setVisible(false);
                                stop();
                            }
                        }
                    }.start();
                }
            });
        }

        /**
         * Met à jour l’image de l’objet tenu par le joueur.
         *
         * @param item L’objet à afficher, ou null pour ne rien afficher.
         */
        public void mettreAJourObjetTenu(Item item) {
            if (item == null) {
                objetTenu.setImage(null);
                return;
            }
            String chemin = GestionnaireImage.getCheminImage(item);
            objetTenu.setImage(Loader.loadImage(chemin));
        }
    }
