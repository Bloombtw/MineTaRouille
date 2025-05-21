package universite_paris8.iut.ameimoun.minetarouillefx.utils.musique;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

import java.io.File;

public class MusiqueManager {

    private MediaPlayer mediaPlayer;
    private double volume = 0.5;


    public void setVolume(double volume) {
        this.volume = Math.max(0, Math.min(1, volume));
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(this.volume);
        }
    }

    public void jouerMusiqueEnBoucle(String chemin) {
        jouerMusique(chemin, MediaPlayer.INDEFINITE);
    }

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


    public void pauseMusique() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    public void reprendreMusique() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        }
    }

    public void jouerEffetSonore(String chemin) {
        Media media = Loader.loadMP3(chemin);
        MediaPlayer effet = new MediaPlayer(media);
        effet.setVolume(volume); // même volume que la musique
        effet.play();
    }

    public void arreterMusique() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
}
