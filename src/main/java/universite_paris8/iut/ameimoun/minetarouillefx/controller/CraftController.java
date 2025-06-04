package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class CraftController {
    @FXML private GridPane grille;
    @FXML private Button boutonCrafter;
    private final Button[][] casesCraft = new Button[3][3];
    JeuController jeucontroller;
    Stage stage;

    @FXML
    public void initialize() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button b = new Button();
                b.setPrefSize(50, 50);
                casesCraft[i][j] = b;
                grille.add(b, j, i);
            }
        }
    }

    public void initialiserCraftController() {
        Parent root = Loader.load(Chemin.FXML_MENU_CRAFT);
        if (root != null) {
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Table de craft");
            stage.initModality(Modality.APPLICATION_MODAL);
        } else {
            System.err.println("Erreur lors du chargement de la fenêtre de craft.");
        }
    }

    public void afficherInterfaceCraft() {
        if (stage != null) {
            jeucontroller.mettreEnPauseJeu();
            stage.showAndWait();
            jeucontroller.reprendreJeu();
        } else {
            System.err.println("Erreur, la fenêtre de craft n'a pas été initialisée.");
        }
    }

    public Button[][] getCasesCraft() { return casesCraft; }
    public Button getBoutonCrafter() { return boutonCrafter; }

    public void setJeucontroller(JeuController jeucontroller) {
        this.jeucontroller = jeucontroller;
    }
}