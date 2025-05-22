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
    import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;
    import javafx.application.Platform;
    import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;

    import javafx.beans.value.ChangeListener;
    import javafx.beans.value.ObservableValue;
    import javafx.scene.input.MouseButton;

    import java.net.URL;
    import java.util.Iterator;
    import java.util.ResourceBundle;
    import java.util.List;
    import java.util.ArrayList;

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
        private final List<Item> itemsAuSol = new ArrayList<>();
        private final List<VueItem> vuesItemsAuSol = new ArrayList<>();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            initialiserCarte();
            initialiserInventaire();
            initialiserJoueur();
            initialiserControles();
            initialiserBarreDeVie();
            initialiserOverlayDegats(); // Add this new initialization method
            initialiserEcouteurDegats();
            demarrerBoucleDeJeu();
        }

        private void initialiserJoueur() {
            joueurModele = new Joueur();
            joueurModele.setInventaire(inventaire);
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
            AnchorPane.setTopAnchor(vueInventaire, 10.0);
            AnchorPane.setRightAnchor(vueInventaire, 10.0);
            rootPane.getChildren().add(vueInventaire);
            inventaire.selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
                joueurVue.mettreAJourItemEnMain();
            });
        }


        private void initialiserControles() {
            initialiserClavier();
            initialiserClicSouris();
        }

        private void initialiserClavier() {
            clavier = new Clavier(joueurModele, inventaire, vueInventaire, debugManager);
            clavier.gestionClavier(tileMap);
            tileMap.setFocusTraversable(true);
            Platform.runLater(() -> tileMap.requestFocus());
        }

        private void initialiserClicSouris() {
            tileMap.setOnMouseClicked(event -> {
                if (event.getButton() != MouseButton.PRIMARY) return;

                int xBlocCible = (int) (event.getX() / Constantes.TAILLE_TUILE);
                int yBlocCible = (int) (event.getY() / Constantes.TAILLE_TUILE);

                int xJoueurBloc = (int) ((joueurModele.getX() + joueurModele.getLargeur() / 2.0) / Constantes.TAILLE_TUILE);
                int yJoueurBloc = (int) ((joueurModele.getY() + joueurModele.getHauteur() / 2.0) / Constantes.TAILLE_TUILE);

                if (distanceEntre(xBlocCible, yBlocCible, xJoueurBloc, yJoueurBloc) > 2.0) return;

                casserBlocEtMettreAJour(xBlocCible, yBlocCible);
            });
        }

        private double distanceEntre(int x1, int y1, int x2, int y2) {
            return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }

        private void casserBlocEtMettreAJour(int x, int y) {
            Carte carte = Carte.getInstance();
            boolean blocCasse = false;
            int coucheCasse = -1;

            for (int couche = carte.getNbCouches() - 1; couche >= 0; couche--) {
                if (!carte.estDansLaMap(x, y)) continue;

                Bloc bloc = carte.getTerrain()[couche][y][x];
                if (carte.casserBloc(couche, x, y)) {
                    blocCasse = true;
                    coucheCasse = couche;

                    if (bloc != null && bloc.estSolide()) {
                        String nom = bloc.name().toLowerCase().replace("_", " ");
                        Item item = new Item(1000 + bloc.ordinal(), nom, 1, "Bloc cassé : " + nom, Type.ARME, Rarete.COMMUN);
                        item.setX(x * Constantes.TAILLE_TUILE + Constantes.TAILLE_TUILE / 2.0);
                        item.setY(y * Constantes.TAILLE_TUILE + Constantes.TAILLE_TUILE / 2.0);

                        itemsAuSol.add(item);

                        VueItem vueItem = new VueItem(item);
                        vuesItemsAuSol.add(vueItem);
                        rootPane.getChildren().add(vueItem.getImageView());
                    }
                }
            }

            if (blocCasse) {
                vueCarte.mettreAJourAffichage(x, y, coucheCasse);
            }
        }

        private void ajouterBlocDansInventaire(Bloc bloc) {
            if (bloc != null && bloc.estSolide()) {
                String nom = bloc.name().toLowerCase().replace("_", " ");
                Item item = new Item(1000 + bloc.ordinal(), nom, 1, "Bloc cassé : " + nom, Type.ARME, Rarete.COMMUN);
                inventaire.ajouterItem(item);
            }
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

            Carte carte = Carte.getInstance();

            // Appliquer la gravité aux items au sol
            for (Item item : itemsAuSol) {
                item.appliquerGravite(carte);
            }

            // Gestion du ramassage des items au sol
            Iterator<Item> itItem = itemsAuSol.iterator();
            Iterator<VueItem> itVue = vuesItemsAuSol.iterator();

            while (itItem.hasNext() && itVue.hasNext()) {
                Item item = itItem.next();
                VueItem vue = itVue.next();

                double distance = Math.hypot(
                        (joueurModele.getX() + Constantes.TAILLE_PERSO / 2.0) - item.getX(),
                        (joueurModele.getY() + Constantes.TAILLE_PERSO / 2.0) - item.getY()
                );

                if (distance < Constantes.TAILLE_TUILE / 2.0) {
                    inventaire.ajouterItem(item);
                    rootPane.getChildren().remove(vue.getImageView());
                    itItem.remove();
                    itVue.remove();
                } else {
                    vue.updatePosition(item);
                }
            }

            if (debugManager.isDebugVisible()) {
                debugManager.update();
            }
        }

        @FXML
        private void handleQuitter() {
            Platform.exit();
        }
    }