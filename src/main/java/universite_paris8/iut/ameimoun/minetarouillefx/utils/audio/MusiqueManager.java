package universite_paris8.iut.ameimoun.minetarouillefx.utils.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class MusiqueManager {


    private MediaPlayer mediaPlayer;
    private double volume = 0.5;
    private double volumeEffet = 0.2;
    private static MusiqueManager instance;

    private MusiqueManager() {}

    public static MusiqueManager getInstance() {
        if (instance == null) {
            instance = new MusiqueManager();
        }
        return instance;
    }

    public void setVolume(double volume) {
        this.volume = volume;
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


    public void arreterMusique() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
    
    
    public void jouerMusiqueFond() {
        jouerMusiqueEnBoucle("/mp3/musique/Zimmer_interstelar.mp3");
    }
    
}
