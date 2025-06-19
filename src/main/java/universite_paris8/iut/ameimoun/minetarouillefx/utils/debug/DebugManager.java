package universite_paris8.iut.ameimoun.minetarouillefx.utils.debug;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import java.util.List;

public class DebugManager {

    private final Group worldGroup;
    private final Joueur joueur;
    private final List<Mob> mobs;

    private Canvas grilleCanvas;
    private Canvas hitboxCanvas;
    private boolean debugVisible = false;

    public DebugManager(Group worldGroup, Joueur joueur, List<Mob> mobs) {
        this.worldGroup = worldGroup;
        this.joueur = joueur;
        this.mobs = mobs;
        this.grilleCanvas = DebugOverlay.genererGrille(Carte.getInstance());

        this.hitboxCanvas = new Canvas(
                Constantes.NB_COLONNES * Constantes.TAILLE_TUILE,
                Constantes.NB_LIGNES * Constantes.TAILLE_TUILE
        );

        // Initialisation mais pas encore visible
        grilleCanvas.setVisible(false);
        hitboxCanvas.setVisible(false);

        worldGroup.getChildren().addAll(grilleCanvas, hitboxCanvas);
    }

    public void toggle() {
        debugVisible = !debugVisible;
        grilleCanvas.setVisible(debugVisible);
        hitboxCanvas.setVisible(debugVisible);
    }

    public void update() {
        if (!debugVisible) return;

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
        gc.setStroke(Color.GREEN);
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
