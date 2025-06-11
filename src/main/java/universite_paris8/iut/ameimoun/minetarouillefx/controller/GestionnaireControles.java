package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.application.Platform;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.souris.SourisListener;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCraft;

public class GestionnaireControles {
    private ClavierListener clavierListener;
    private SourisListener sourisListener;
    private final Joueur joueurModele;
    private final VueCarte vueCarte;
    private final GestionnaireInventaire gestionnaireInventaire;
    private final DebugManager debugManager;
    private final GestionnaireItem gestionnaireItem;
    private VueCraft vuecraft;

    public GestionnaireControles(Joueur joueurModele, VueCarte vueCarte, GestionnaireInventaire gestionnaireInventaire, DebugManager debugManager, GestionnaireItem gestionnaireItem) {
        this.joueurModele = joueurModele;
        this.vueCarte = vueCarte;
        this.gestionnaireInventaire = gestionnaireInventaire;
        this.debugManager = debugManager;
        this.gestionnaireItem = gestionnaireItem;
        clavierListener = new ClavierListener(joueurModele, gestionnaireInventaire.getInventaire(), gestionnaireInventaire.getVueInventaire(), debugManager, vuecraft);
        initialiserControles();
    }

    public void initialiserControles() {
        clavierListener = new ClavierListener(joueurModele, gestionnaireInventaire.getInventaire(), gestionnaireInventaire.getVueInventaire(), debugManager, vuecraft);
        sourisListener = new SourisListener(joueurModele, gestionnaireInventaire.getInventaire(),vueCarte,gestionnaireInventaire.getVueInventaire(), gestionnaireItem, vuecraft);
        TilePane tileMap = vueCarte.getTileMap();

        clavierListener.lier(tileMap);
        sourisListener.lier(tileMap);


        Platform.runLater(() -> {
            tileMap.setFocusTraversable(true);
            sourisListener.lierScrollInventaire(tileMap.getScene());
            tileMap.requestFocus();
        });
    }

    public SourisListener getSourisListener() {
        return sourisListener;
    }

    public ClavierListener getClavierListener() {
        return clavierListener;
    }

    public void setVueCraft(VueCraft vueCraft) {
        this.vuecraft = vueCraft;
    }
}
