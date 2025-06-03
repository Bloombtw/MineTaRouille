package universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;

import java.net.URL;


public class Loader {

    public static URL getResource(String path) {
        URL ressource = Loader.class.getResource(path);
        if (ressource == null) {
            throw new IllegalArgumentException("Ressource pas trouvée : " + path);
        }
        return ressource;
    }

    public static Image loadImage(String path) {
        URL resource = Loader.class.getResource(path);
        if (resource != null) {
            return new Image(resource.toExternalForm());
        } else {
            System.err.println("Image non trouvée : " + path + " → Image par défaut utilisée.");
            return new Image(Loader.class.getResource(Chemin.IMAGE_DEFAULT).toExternalForm());
        }
    }

    public static Media loadMP3(String path) {
        URL resource = Loader.class.getResource(path);
        if (resource != null) {
            return new Media(resource.toExternalForm());
        } else {
            System.err.println("Musique non trouvée : " + path + " → Musique par défaut utilisée. (Son d'erreur)");
            return new Media(Loader.class.getResource(Chemin.SON_DEFAULT).toExternalForm());
        }
    }


    public static FXMLLoader loadFXML(String path) {
        return new FXMLLoader(getResource(path));
    }

    public static <T> T load(String path) {
        try {
            FXMLLoader loader = loadFXML(path);
            return loader.load();
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la vue : " + path + " → Boite d'erreur utilisée.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger l'interface");
            alert.setContentText("Fichier FXML introuvable ou invalide : " + path);
            alert.show();

            return null;
        }
    }

    public static Media getMP4(String path) {
        URL resource = Loader.class.getResource(path);
        if (resource != null) {
            return new Media(resource.toExternalForm());
        } else {
            throw new IllegalArgumentException("mp4 pas trouvé : " + path);
        }
    }


    // Pour que la méthode marche, il faut que les images soient nommées de la forme "cheminDeBase1.png", "cheminDeBase2.png", etc.
    // Il faut que la première image soit nommée "cheminDeBase1.png" avec un 1.
    public static Image[] loadAnimation(String cheminDeBase, int nbFrames) {
        Image[] frames = new Image[nbFrames];
        for (int i = 0; i < nbFrames; i++) {
            String chemin = cheminDeBase + (i+1) + ".png";
            frames[i] = loadImage(chemin);
        }
        return frames;
    }


}
