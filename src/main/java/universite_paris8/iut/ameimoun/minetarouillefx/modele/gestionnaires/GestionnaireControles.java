package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.application.Platform;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.souris.Souris;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;

public class GestionnaireControles {
    private ClavierListener clavierListener;
    private Souris sourisListener;
    private final Joueur joueurModele;
    private final VueCarte vueCarte;
    private final GestionnaireInventaire gestionnaireInventaire;
    private final DebugManager debugManager;
    private final GestionnaireItem gestionnaireItem;

    public GestionnaireControles(Joueur joueurModele, VueCarte vueCarte, GestionnaireInventaire gestionnaireInventaire, DebugManager debugManager, GestionnaireItem gestionnaireItem) {
        this.joueurModele = joueurModele;
        this.vueCarte = vueCarte;
        this.gestionnaireInventaire = gestionnaireInventaire;
        this.debugManager = debugManager;
        this.gestionnaireItem = gestionnaireItem;
        initialiserControles();
    }

    public void initialiserControles() {
        clavierListener = new ClavierListener(joueurModele, gestionnaireInventaire.getInventaire(), gestionnaireInventaire.getVueInventaire(), debugManager);
        sourisListener = new Souris(joueurModele, gestionnaireInventaire.getInventaire(),vueCarte,gestionnaireInventaire.getVueInventaire(), gestionnaireItem);
        TilePane tileMap = vueCarte.getTileMap();

        clavierListener.lier(tileMap);
        sourisListener.lier(tileMap);


        Platform.runLater(() -> {
            tileMap.setFocusTraversable(true);
            sourisListener.lierScrollInventaire(tileMap.getScene());
            tileMap.requestFocus();
        });
    }

    public Souris getSourisListener() {
        return sourisListener;
    }

    public ClavierListener getClavierListener() {
        return clavierListener;
    }
}
