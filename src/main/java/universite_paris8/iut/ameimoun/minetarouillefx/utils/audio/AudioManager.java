package universite_paris8.iut.ameimoun.minetarouillefx.utils.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton chargé de la gestion des effets sonores du jeu.
 * Permet de jouer des sons uniques, des boucles, ou des effets spécifiques comme la casse de blocs ou les alertes de vie/mort.
 */
public class AudioManager {

    private static AudioManager instance;
    private double volumeEffet = 0.5;
    private final Map<Integer, String> sonsCasseBloc;
    private final List<MediaPlayer> mediaPlayersEnCours = new ArrayList<>();

    /**
     * Constructeur privé du singleton. Initialise la map des sons de casse de blocs.
     */
    private AudioManager() {
        sonsCasseBloc = new HashMap<>();
        sonsCasseBloc.put(1, "/mp3/effets/blocCassé/pierre.mp3");
        sonsCasseBloc.put(2, "/mp3/effets/blocCassé/pierre.mp3");
        sonsCasseBloc.put(3, "/mp3/effets/blocCassé/pierre.mp3");
    }

    /**
     * Retourne l'unique instance d'AudioManager.
     *
     * @return instance d'AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Définit le volume global des effets sonores.
     *
     * @param volumeEffet Valeur du volume entre 0.0 (muet) et 1.0 (maximum).
     */
    public void setVolumeEffet(double volumeEffet) {
        this.volumeEffet = volumeEffet;
    }

    /**
     * Joue un son ponctuel à partir d'un chemin donné.
     *
     * @param chemin Chemin relatif du fichier audio à jouer.
     */
    public void jouerSon(String chemin) {
        Media media = Loader.loadMP3(chemin);
        MediaPlayer player = new MediaPlayer(media);
        mediaPlayersEnCours.add(player);
        player.setVolume(volumeEffet);
        player.play();
        player.setOnEndOfMedia(() -> {
            player.dispose();
            mediaPlayersEnCours.remove(player);
        });
    }

    /**
     * Joue un son en boucle à partir d'un chemin donné.
     *
     * @param chemin Chemin relatif du fichier audio à jouer en boucle.
     */
    public void jouerSonEnBoucle(String chemin) {
        Media media = Loader.loadMP3(chemin);
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(volumeEffet);
        player.play();
        mediaPlayersEnCours.add(player);
    }

    /**
     * Joue un son spécifique lorsqu’un bloc est cassé.
     *
     * @param blocId Identifiant du type de bloc cassé.
     */
    public void jouerSonCasseBloc(int blocId) {
        String cheminSon = sonsCasseBloc.get(blocId);
        if (cheminSon != null) {
            jouerSon(cheminSon);
        }
    }

    /**
     * Joue l’alerte sonore en boucle lorsque la vie du joueur est basse.
     */
    public void jouerAlerteVieBasse() {
        jouerSonEnBoucle(Chemin.SON_VIE_LOW);
    }

    /**
     * Joue le son déclenché lors de la mort du joueur.
     */
    public void jouerAlerteMort() {
        jouerSon(Chemin.SON_MORT);
    }

    /**
     * Arrête et libère tous les sons actuellement en cours de lecture.
     */
    public void arreterTousLesSons() {
        for (MediaPlayer player : new ArrayList<>(mediaPlayersEnCours)) {
            player.stop();
            player.dispose();
        }
        mediaPlayersEnCours.clear();
    }
}
