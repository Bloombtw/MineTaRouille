package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Direction;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class VueJoueur {
    private final ImageView perso;
    private final ImageView itemEnMain;
    private final Group groupeJoueurComplet;
    private final Group container;
    private Animation animDroite;
    private Animation animGauche;
    private Animation animIdle;
    private Animation animSaut;
    private Animation animActuelle;
    private final Joueur joueur;

    public VueJoueur(Joueur joueur) {
        this.joueur = joueur;

        perso = new ImageView();
        perso.setFitWidth(Constantes.TAILLE_PERSO);
        perso.setFitHeight(Constantes.TAILLE_PERSO);

        itemEnMain = new ImageView();
        itemEnMain.setFitWidth(20);
        itemEnMain.setFitHeight(20);

        groupeJoueurComplet = new Group(perso, itemEnMain);
        container = new Group(groupeJoueurComplet);

        container.translateXProperty().bind(joueur.xProperty());
        container.translateYProperty().bind(joueur.yProperty());

        joueur.xProperty().addListener((obs, oldX, newX) -> mettreAJourAnimation(joueur));
        joueur.yProperty().addListener((obs, oldY, newY) -> mettreAJourAnimation(joueur));

        // Chargement des sprite sheets
        Image spriteIdle = Loader.loadImage("/img/joueur/idle.png");
        Image spriteGauche = Loader.loadImage("/img/joueur/gauche.png");
        Image spriteDroite = Loader.loadImage("/img/joueur/droite.png");
        Image spriteSaut = Loader.loadImage("/img/joueur/saut.png");

        // Découpage en frames
        Image[] framesIdle = Animation.decouperSpriteSheet(spriteIdle, 32, 32, 4);
        Image[] framesGauche = Animation.decouperSpriteSheet(spriteGauche, 32, 32, 6);
        Image[] frameDroite = Animation.decouperSpriteSheet(spriteDroite, 32, 32, 6);
        Image[] framesSaut = Animation.decouperSpriteSheet(spriteSaut, 32, 32, 8);

        // Création des animations
        animIdle = new Animation(perso, framesIdle, 150);
        animGauche = new Animation(perso, framesGauche, 100);
        animDroite = new Animation(perso, frameDroite, 100);
        animSaut = new Animation(perso, framesSaut, 120);

        animIdle.start();

        mettreAJourItemEnMain();
    }

    public Group getNode() {
        return container;
    }

    public void mettreAJourAnimation(Joueur joueur) {
        boolean enSaut = joueur.getVitesseY() != 0;

        if (enSaut) {
            jouerAnimation(animSaut);
        } else {
            switch (joueur.direction) {
                case GAUCHE -> jouerAnimation(animGauche);
                case DROITE -> jouerAnimation(animDroite);
                default -> jouerAnimation(animIdle);
            }
        }
    }

    private void jouerAnimation(Animation animation) {
        if (animActuelle != animation) {
            if (animActuelle != null) animActuelle.stop();
            animActuelle = animation;
            animActuelle.start();
        }
    }

    public void mettreAJourItemEnMain() {
        if (joueur.getInventaire() == null) return;

        Item item = joueur.getInventaire().getItem(joueur.getInventaire().getSelectedIndex());
        if (item != null) {
            String cheminImage = "/img/items/" + item.getNom().toLowerCase().replace("é", "e") + ".png";
            try {
                itemEnMain.setImage(new Image(getClass().getResourceAsStream(cheminImage)));
                itemEnMain.setVisible(true);

                // Position relative à perso (main droite ou gauche)
                double offsetX = joueur.direction == Direction.DROITE ? 18 : -10;
                itemEnMain.setTranslateX(offsetX);
                itemEnMain.setTranslateY(10);
                itemEnMain.setScaleX(joueur.direction == Direction.DROITE ? 1 : -1);

            } catch (Exception e) {
                itemEnMain.setVisible(false);
            }
        } else {
            itemEnMain.setVisible(false);
        }
    }
}
