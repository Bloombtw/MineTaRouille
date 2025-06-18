package universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;

import java.net.URL;

/**
 * Classe utilitaire pour charger les ressources du projet :
 * images, sons, fichiers FXML, etc.
 */
public class Loader {

    /**
     * Image du slot d'inventaire chargée au démarrage.
     */
    // Utilisée uniquement dans vueCraft
    public static final Image SLOT_IMAGE = loadImage(Chemin.SLOT);

    /**
     * Récupère l'URL d'une ressource.
     *
     * @param path chemin relatif de la ressource dans le projet.
     * @return l'URL correspondante.
     * @throws IllegalArgumentException si la ressource est introuvable.
     */
    public static URL getResource(String path) {
        URL ressource = Loader.class.getResource(path);
        if (ressource == null) {
            throw new IllegalArgumentException("Ressource pas trouvée : " + path);
        }
        return ressource;
    }

    /**
     * Charge une image à partir du chemin donné.
     *
     * @param path chemin de l'image.
     * @return l'image chargée, ou une image par défaut si introuvable.
     */
    public static Image loadImage(String path) {
        URL resource = Loader.class.getResource(path);
        if (resource != null) {
            return new Image(resource.toExternalForm());
        } else {
            System.err.println("Image non trouvée : " + path + " → Image par défaut utilisée.");
            return new Image(Loader.class.getResource(Chemin.IMAGE_DEFAULT).toExternalForm());
        }
    }

    /**
     * Charge un fichier MP3.
     *
     * @param path chemin du fichier MP3.
     * @return l'objet Media correspondant.
     */
    public static Media loadMP3(String path) {
        URL resource = Loader.class.getResource(path);
        if (resource != null) {
            return new Media(resource.toExternalForm());
        } else {
            System.err.println("Musique non trouvée : " + path + " → Musique par défaut utilisée. (Son d'erreur)");
            return new Media(Loader.class.getResource(Chemin.SON_DEFAULT).toExternalForm());
        }
    }

    /**
     * Charge un fichier FXML sans l'interpréter.
     *
     * @param path chemin du fichier FXML.
     * @return le FXMLLoader prêt à charger.
     */
    public static FXMLLoader loadFXML(String path) {
        return new FXMLLoader(getResource(path));
    }

    /**
     * Charge et instancie une interface FXML.
     *
     * @param path chemin du fichier FXML.
     * @param <T>  type attendu du contrôleur racine.
     * @return l'objet FXML instancié ou null en cas d'erreur.
     */
    public static <T> T load(String path) {
        try {
            FXMLLoader loader = loadFXML(path);
            return loader.load();
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la vue : " + path + " → Boite d'erreur utilisée.");
            Alert alert = new Alert(Alert.AlertType.ERROR); // Affichage d'une petite boite d'erreur
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger l'interface");
            alert.setContentText("Fichier FXML introuvable ou invalide : " + path);
            alert.show();

            return null;
        }
    }

    /**
     * Charge un fichier vidéo MP4.
     *
     * @param path chemin du fichier MP4.
     * @return l'objet Media correspondant.
     * @throws IllegalArgumentException si le fichier est introuvable.
     */
    public static Media getMP4(String path) {
        URL resource = Loader.class.getResource(path);
        if (resource != null) {
            return new Media(resource.toExternalForm());
        } else {
            throw new IllegalArgumentException("mp4 pas trouvé : " + path);
        }
    }

    /**
     * Charge une animation composée de plusieurs images nommées de façon séquentielle
     * (ex: image1.png, image2.png, ...).
     *
     * @param cheminDeBase chemin commun des images sans le numéro ni l'extension.
     * @param nbFrames     nombre d'images dans l'animation.
     * @return un tableau contenant les images de l'animation.
     */
    public static Image[] loadAnimation(String cheminDeBase, int nbFrames) {
        Image[] frames = new Image[nbFrames];
        for (int i = 0; i < nbFrames; i++) {
            String chemin = cheminDeBase + (i + 1) + ".png";
            frames[i] = loadImage(chemin);
        }
        return frames;
    }

    /**
     * Charge une feuille de style CSS.
     *
     * @param path chemin du fichier CSS.
     * @return l'URL du fichier CSS ou null si introuvable.
     */
    public static String loadCSS(String path) {
        URL resource = Loader.class.getResource(path);
        if (resource != null) {
            return resource.toExternalForm();
        } else {
            System.err.println("CSS non trouvé : " + path);
            return null;
        }
    }
}
