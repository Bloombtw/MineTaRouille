    package universite_paris8.iut.ameimoun.minetarouillefx.controller;

    import javafx.animation.AnimationTimer;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.TilePane;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.Rectangle;
    import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
    import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
    import javafx.application.Platform;
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueVie;
    import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;

    import javafx.beans.value.ChangeListener;
    import javafx.beans.value.ObservableValue;
    import javafx.scene.input.MouseButton;

    import java.net.URL;
    import java.util.ResourceBundle;

    public class JeuController implements Initializable {
        @FXML
        private TilePane tileMap;
        @FXML
        private AnchorPane rootPane;
        @FXML
        private AnchorPane overlayDeMort;

        private Inventaire inventaire;
        private VueInventaire vueInventaire;
        private Clavier clavier;
        private Joueur joueurModele;
        private VueJoueur joueurVue;
        private VueVie vueVie;
        private AnimationTimer gameLoop;
        private VueCarte vueCarte;
        private DebugManager debugManager;
        private Rectangle overlayDegats; // Declared here
        private double derniereVieConnue;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            initialiserCarte();
            initialiserJoueur();
            initialiserInventaire();
            initialiserControles();
            initialiserBarreDeVie();
            initialiserOverlayDegats(); // Add this new initialization method
            initialiserEcouteurDegats();
            demarrerBoucleDeJeu();
        }

        private void initialiserJoueur() {
            joueurModele = new Joueur();
            debugManager = new DebugManager(rootPane, joueurModele);
            joueurVue = new VueJoueur(joueurModele);
            rootPane.getChildren().add(joueurVue.getNode());
            this.derniereVieConnue = joueurModele.getVie().getVieActuelle();
        }

        private void initialiserBarreDeVie() {
            vueVie = new VueVie(joueurModele.getVie());
            rootPane.getChildren().add(vueVie.getNoeud());
        }

        private void initialiserOverlayDegats() {
            overlayDegats = new Rectangle(0, 0, rootPane.getWidth(), rootPane.getHeight());
            overlayDegats.setFill(Color.RED);
            overlayDegats.setOpacity(0.3);
            overlayDegats.setVisible(false);
            rootPane.getChildren().add(overlayDegats);

            overlayDegats.widthProperty().bind(rootPane.widthProperty());
            overlayDegats.heightProperty().bind(rootPane.heightProperty());
        }

        private void initialiserEcouteurDegats() {
            joueurModele.getVie().vieActuelleProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (newValue.doubleValue() < oldValue.doubleValue()) {
                        afficherDegats();
                    }
                    derniereVieConnue = newValue.doubleValue();
                }
            });
        }

        private void initialiserInventaire() {
            inventaire = new Inventaire();
            inventaire.ajouterItem(new Item(1, "Épée", 1, "Une épée basique", Type.ARME, Rarete.COMMUN));
            inventaire.ajouterItem(new Item(2, "Pioche", 1, "Pioche", Type.ARME, Rarete.RARE));
            vueInventaire = new VueInventaire(inventaire);
            vueInventaire.setLayoutX(20);
            vueInventaire.setLayoutY(950);
            rootPane.getChildren().add(vueInventaire);
        }


        private void initialiserControles() {
            clavier = new Clavier(joueurModele, inventaire, vueInventaire, debugManager);
            clavier.gestionClavier(tileMap);
            tileMap.setFocusTraversable(true);
            Platform.runLater(() -> tileMap.requestFocus());

            tileMap.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    double mouseX = event.getX();
                    double mouseY = event.getY();

                    int xBlocCible = (int) (mouseX / Constantes.TAILLE_TUILE);
                    int yBlocCible = (int) (mouseY / Constantes.TAILLE_TUILE);

                    int xJoueurBloc = (int) ((joueurModele.getX() + joueurModele.getLargeur() / 2.0) / Constantes.TAILLE_TUILE);
                    int yJoueurBloc = (int) ((joueurModele.getY() + joueurModele.getHauteur() / 2.0) / Constantes.TAILLE_TUILE);

                    double distance = Math.sqrt(
                            Math.pow(xBlocCible - xJoueurBloc, 2) +
                                    Math.pow(yBlocCible - yJoueurBloc, 2)
                    );

                    if (distance <= 2.0) { // Distance de casse (peut être ajustée)
                        Carte carte = Carte.getInstance();
                        boolean blocCasse = false;
                        int coucheCasse = -1;

                        // On parcourt les couches du haut vers le bas pour casser le bloc le plus "visible"
                        for (int couche = carte.getNbCouches() - 1; couche >= 0; couche--) {
                            if (carte.estDansLaMap(xBlocCible, yBlocCible)) {
                                if (carte.casserBloc(couche, xBlocCible, yBlocCible)) {
                                    blocCasse = true;
                                    coucheCasse = couche; // On stocke la couche du bloc cassé
                                    break; // On a cassé un bloc, on arrête de chercher
                                }
                            }
                        }

                        if (blocCasse) {
                            // Appelle la méthode de mise à jour spécifique de la vue pour la tuile concernée
                            // La couche ici n'est pas utilisée directement par la méthode de mise à jour,
                            // car elle re-scanne toutes les couches pour la tuile.
                            vueCarte.mettreAJourAffichage(xBlocCible, yBlocCible, coucheCasse);
                        }
                    }
                }
            });
        }

        private void initialiserCarte() {
            vueCarte = new VueCarte(Carte.getInstance());
            tileMap.getChildren().add(vueCarte.getTileMap());
        }
        private void demarrerBoucleDeJeu() {
            gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    mettreAJourJeu();
                }
            };
            gameLoop.start();
        }

        public void afficherDegats() {
            Platform.runLater(() -> {
                if (overlayDegats != null) {
                    overlayDegats.setVisible(true);
                    new AnimationTimer() {
                        private long start = -1;

                        @Override
                        public void handle(long now) {
                            if (start < 0) start = now;
                            if (now - start > 100_000_000L) { // ~100ms
                                overlayDegats.setVisible(false);
                                stop();
                            }
                        }
                    }.start();
                }
            });
        }

        private void gererFinDeJeu() {
            if (joueurModele.getVie().estMort()) {
                gameLoop.stop();
                clavier.desactiverClavier(tileMap);
                Platform.runLater(() -> overlayDeMort.setVisible(true));
            }
        }

        private void mettreAJourJeu() {
            joueurModele.gravite();
            joueurModele.getVie().verifierDegats(joueurModele, Carte.getInstance());
            gererFinDeJeu();
            if (debugManager.isDebugVisible()) {
                debugManager.update();
            }
        }

        @FXML
        private void handleQuitter() {
            Platform.exit();
        }
    }