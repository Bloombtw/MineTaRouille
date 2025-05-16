package universite_paris8.iut.ameimoun.minetarouillefx.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
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
        return new Image(getResource(path).toExternalForm());
    }

    public static FXMLLoader loadFXML(String path) {
        return new FXMLLoader(getResource(path));
    }
}
