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

/**
 * Gère l'affichage des éléments de débogage dans l'application, tels que la grille et les hitbox.
 * Permet de visualiser en temps réel la position du joueur et des mobs pour le développement.
 */
public class DebugManager {

    /** Conteneur principal où sont ajoutés les éléments graphiques de debug */
    private final AnchorPane rootPane;

    /** Référence au joueur pour afficher sa hitbox */
    private final Joueur joueur;

    /** Canevas pour la grille de debug (grille de la carte) */
    private Canvas debugCanvas;

    /** Canevas pour dessiner les hitbox */
    private Canvas hitboxCanvas;

    /** État d'affichage du mode debug */
    private boolean debugVisible = false;

    /** Référence à un mob pour dessiner sa hitbox */
    private final Mob mob;

    /**
     * Constructeur du gestionnaire de debug.
     *
     * @param rootPane Pane principal de la scène JavaFX.
     * @param joueur   Joueur à observer dans le mode debug.
     * @param mobs     Mob à observer
     */
    public DebugManager(AnchorPane rootPane, Joueur joueur, Mob mobs) {
        this.rootPane = rootPane;
        this.joueur = joueur;
        this.mob = mobs;
    }

    /**
     * Active ou désactive l'affichage des canevas de debug.
     * Initialise les canevas s'ils ne sont pas encore créés.
     */
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

    /**
     * Met à jour l'affichage des éléments graphiques de debug,
     * notamment la hitbox du joueur TODO : afficher le mob aussi
     */
    public void update() {
        if (!debugVisible || hitboxCanvas == null) return;

        GraphicsContext gc = hitboxCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, hitboxCanvas.getWidth(), hitboxCanvas.getHeight());

        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2);
        gc.strokeRect(
                joueur.getX(),
                joueur.getY(),
                Constantes.TAILLE_PERSO,
                Constantes.TAILLE_PERSO
        );

        /*
        // Affichage de la hitbox des mobs (en vert)
        if (mob != null) {
            gc.setStroke(Color.GREEN);
            gc.strokeRect(
                mob.getX(),
                mob.getY(),
                Constantes.TAILLE_PERSO,
                Constantes.TAILLE_PERSO
            );
        }
        */
    }

    /**
     * Vérifie si le mode debug est activé.
     *
     * @return true si les canevas de debug sont visibles, false sinon.
     */
    public boolean isDebugVisible() {
        return debugVisible;
    }
}
