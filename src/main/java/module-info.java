module universite_paris8.iut.ameimoun.minetarouillefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens universite_paris8.iut.ameimoun.minetarouillefx to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx;
}