package universite_paris8.iut.ameimoun.minetarouillefx.vue;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.LivreController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Livre;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class VueLivre {
    private static VueLivre instance;
    private Stage stage;
    private ImageView imageView;

    private LivreController controller;
    private Livre modele;

    private static final double SCALE = 0.65;

    private VueLivre() {
        modele = new Livre();
        this.controller = new LivreController(modele);
    }

    public static VueLivre getInstance() {
        if (instance == null) instance = new VueLivre();
        return instance;
    }

    public void ouvrir() {
        if (stage != null && stage.isShowing()) {
            stage.toFront();
            return;
        }

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Livre d'instructions");

        double scaledWidth = Constantes.LARGEUR_LIVRE * SCALE;
        double scaledHeight = Constantes.HAUTEUR_LIVRE * SCALE;

        imageView = new ImageView();
        imageView.setPreserveRatio(false);
        imageView.setFitWidth(scaledWidth);
        imageView.setFitHeight(scaledHeight);

        // Boutons navigation
        Button btnPrecedent = new Button("←");
        Button btnSuivant = new Button("→");
        Button btnQuitter = new Button("✖");

        btnPrecedent.disableProperty().bind(controller.peutAllerPrecedentProperty().not());
        btnSuivant.disableProperty().bind(controller.peutAllerSuivantProperty().not());
        btnPrecedent.setOnAction(e -> controller.pagePrecedente(this::afficherPage));
        btnSuivant.setOnAction(e -> controller.pageSuivante(this::afficherPage));
        btnQuitter.setOnAction(e -> fermer());

        HBox navigation = new HBox(20, btnPrecedent, btnSuivant);
        navigation.setAlignment(Pos.CENTER);

        HBox quitter = new HBox(btnQuitter);
        quitter.setAlignment(Pos.CENTER_RIGHT);
        quitter.setStyle("-fx-padding: 5px 20px 10px 0;");

        VBox bas = new VBox(10, navigation, quitter);
        bas.setAlignment(Pos.CENTER);

        VBox container = new VBox(imageView, bas);
        container.setAlignment(Pos.TOP_CENTER);
        container.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(container, scaledWidth, scaledHeight + 80);
        stage.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scaledWidth) / 2);
        stage.setY((screenBounds.getHeight() - (scaledHeight + 80)) / 2);

        stage.show();
        afficherPage();
    }

    private void afficherPage() {
        String cheminImage = controller.getCheminPageCourante();
        Image image = Loader.loadImage(cheminImage);
        imageView.setImage(image);
    }

    public void fermer() {
        if (stage != null) {
            stage.close();
        }
    }
}
