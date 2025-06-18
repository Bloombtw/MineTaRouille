package universite_paris8.iut.ameimoun.minetarouillefx.utils.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

import java.nio.charset.CharsetEncoder;

/**
 * Singleton chargé de la gestion de la musique de fond du jeu.
 * Permet de jouer, mettre en pause, arrêter et ajuster le volume de la musique.
 */
public class MusiqueManager {

    private MediaPlayer mediaPlayer;
    private double volume = 0.5;
    private double volumeEffet = 0.2; // Non utilisé ici, potentiellement prévu pour les effets
    private static MusiqueManager instance;

    /**
     * Constructeur privé pour implémenter le pattern singleton.
     */
    private MusiqueManager() {}

    /**
     * Retourne l'unique instance de MusiqueManager.
     *
     * @return instance de MusiqueManager.
     */
    public static MusiqueManager getInstance() {
        if (instance == null) {
            instance = new MusiqueManager();
        }
        return instance;
    }

    /**
     * Définit le volume de la musique.
     *
     * @param volume Valeur entre 0.0 (muet) et 1.0 (maximum).
     */
    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(this.volume);
        }
    }

    /**
     * Joue une musique en boucle à partir du chemin spécifié.
     *
     * @param chemin Chemin relatif du fichier audio.
     */
    public void jouerMusiqueEnBoucle(String chemin) {
        jouerMusique(chemin, MediaPlayer.INDEFINITE);
    }

    /**
     * Joue une musique un nombre défini de fois.
     *
     * @param chemin Chemin relatif du fichier audio.
     * @param repetitions Nombre de répétitions (utiliser MediaPlayer.INDEFINITE pour une boucle infinie).
     */
    public void jouerMusique(String chemin, int repetitions) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Media media = Loader.loadMP3(chemin);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(repetitions);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    /**
     * Met en pause la musique si elle est en cours de lecture.
     */
    public void pauseMusique() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    /**
     * Reprend la lecture de la musique si elle est en pause.
     */
    public void reprendreMusique() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        }
    }

    /**
     * Arrête complètement la lecture de la musique.
     */
    public void arreterMusique() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    /**
     * Joue la musique de fond définie dans les constantes.
     */
    public void jouerMusiqueFond() {
        jouerMusiqueEnBoucle(Chemin.MUSIQUE_FOND);
    }
}
