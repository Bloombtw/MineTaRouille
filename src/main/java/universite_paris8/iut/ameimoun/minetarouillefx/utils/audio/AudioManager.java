package universite_paris8.iut.ameimoun.minetarouillefx.utils.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioManager {

    private static AudioManager instance;
    private double volumeEffet = 0.5;
    private final Map<Integer, String> sonsCasseBloc;
    private final List<MediaPlayer> mediaPlayersEnCours = new ArrayList<>();

    private AudioManager() {
        sonsCasseBloc = new HashMap<>();
        sonsCasseBloc.put(1, "/mp3/effets/blocCassé/pierre.mp3");
        sonsCasseBloc.put(2, "/mp3/effets/blocCassé/pierre.mp3");
        sonsCasseBloc.put(3, "/mp3/effets/blocCassé/pierre.mp3");
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void setVolumeEffet(double volumeEffet) {
        this.volumeEffet = volumeEffet;
    }

    public void jouerSon(String chemin) {
            Media media = Loader.loadMP3(chemin);
            MediaPlayer player = new MediaPlayer(media);
            mediaPlayersEnCours.add(player);
            player.setVolume(volumeEffet);
            player.play();
            player.setOnEndOfMedia(() ->  {
                player.dispose();
                mediaPlayersEnCours.remove(player);
            });
    }

    public void jouerSonEnBoucle(String chemin) {
        Media media = Loader.loadMP3(chemin);
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(volumeEffet);
        player.play();
        mediaPlayersEnCours.add(player);
    }

    public void jouerSonCasseBloc(int blocId) {
        String cheminSon = sonsCasseBloc.get(blocId);
        if (cheminSon != null) {
            jouerSon(cheminSon);
        }
    }

    public void jouerAlerteVieBasse() {
        jouerSonEnBoucle("/mp3/effets/joueur/degat/vieLow.mp3");
    }


    public void jouerAlerteMort() {
        jouerSon("/mp3/effets/joueur/degat/mort/GTA5_mort.mp3");
    }

    public void arreterTousLesSons() {
        for (MediaPlayer player : new ArrayList<>(mediaPlayersEnCours)) {
            player.stop();
            player.dispose();
        }
        mediaPlayersEnCours.clear();
    }
}
