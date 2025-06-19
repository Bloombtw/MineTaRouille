package universite_paris8.iut.ameimoun.minetarouillefx;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class MainApp extends Application {
    public static Stage primaryStageGlobal;
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStageGlobal = primaryStage;
            Parent root = Loader.load(Chemin.FXML_ECRAN_ACCUEIL);
            Scene scene = new Scene(root, Constantes.LARGEUR_FENETRE, Constantes.HAUTEUR_FENETRE);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Mine Ta Rouille - Accueil");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
