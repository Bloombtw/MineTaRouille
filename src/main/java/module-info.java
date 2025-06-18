module universite_paris.iut.ameimoun.minetarouillefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.testng;
    requires org.junit.jupiter.api;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires junit;

    opens universite_paris8.iut.ameimoun.minetarouillefx to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx;
    exports universite_paris8.iut.ameimoun.minetarouillefx.controller;
    opens universite_paris8.iut.ameimoun.minetarouillefx.controller to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.vue;
    opens universite_paris8.iut.ameimoun.minetarouillefx.vue to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.modele;
    opens universite_paris8.iut.ameimoun.minetarouillefx.modele to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier;
    opens universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.controller.souris;
    opens universite_paris8.iut.ameimoun.minetarouillefx.controller.souris to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue;
    opens universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;
    opens universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob;
    opens universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob to javafx.fxml;
    exports universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
    exports universite_paris8.iut.ameimoun.minetarouillefx.testJunit to junit;
    opens universite_paris8.iut.ameimoun.minetarouillefx.testJunit to org.testng;
}