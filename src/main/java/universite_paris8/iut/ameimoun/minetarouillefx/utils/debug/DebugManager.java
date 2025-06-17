package universite_paris8.iut.ameimoun.minetarouillefx.utils.debug;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import java.util.List;

public class DebugManager {

    private final AnchorPane rootPane;
    private final Joueur joueur;

    private Canvas debugCanvas;
    private Canvas hitboxCanvas;
    private boolean debugVisible = false;
    private final List<Mob> mobs;

    public DebugManager(AnchorPane rootPane,Joueur joueur,List<Mob> mobs) {
        this.rootPane = rootPane;
        this.joueur = joueur;
        this.mobs = mobs;
    }

    public void toggle() {
        if (debugCanvas == null) {
            debugCanvas = DebugOverlay.genererGrille(Carte.getInstance());
            rootPane.getChildren().add(debugCanvas);

            hitboxCanvas = new Canvas(
                    Constantes.NB_COLONNES * Constantes.TAILLE_TUILE,
                    Constantes.NB_LIGNES * Constantes.TAILLE_TUILE
            );
            rootPane.getChildren().add(hitboxCanvas);
        }

        debugVisible = !debugVisible;
        debugCanvas.setVisible(debugVisible);
        hitboxCanvas.setVisible(debugVisible);
    }

    public void update() {
        if (!debugVisible || hitboxCanvas == null) return;

        GraphicsContext gc = hitboxCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, hitboxCanvas.getWidth(), hitboxCanvas.getHeight());

        // Hitbox du joueur
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2);
        gc.strokeRect(
                joueur.getX(),
                joueur.getY(),
                Constantes.TAILLE_PERSO,
                Constantes.TAILLE_PERSO
        );

        // Hitbox des mobs
        gc.setStroke(Color.RED);
        for (Mob mob : mobs) {
            gc.strokeRect(
                    mob.getX(),
                    mob.getY(),
                    mob.getLargeur(),
                    mob.getHauteur()
            );
        }
    }

    public boolean isDebugVisible() {
        return debugVisible;
    }
}


