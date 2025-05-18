module universite_paris.iut.ameimoun.minetarouillefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires javafx.graphics;

    opens universite_paris8.iut.ameimoun.minetarouillefx to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx;
    exports universite_paris8.iut.ameimoun.minetarouillefx.controller;
    opens universite_paris8.iut.ameimoun.minetarouillefx.controller to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.vue;
    opens universite_paris8.iut.ameimoun.minetarouillefx.vue to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.modele;
    opens universite_paris8.iut.ameimoun.minetarouillefx.modele to javafx.fxml;

}