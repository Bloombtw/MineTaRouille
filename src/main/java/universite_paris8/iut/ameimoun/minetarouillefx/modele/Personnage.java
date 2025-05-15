package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import java.util.ArrayList;
import java.util.Map;

public class Personnage {
    int x;
    int y;
    private double pointsDeVieMax;
    private double pointsDeVie;
    private String nom;
    private double vitesseDeplacement;
    private int satiete; // Satiete est notée sur 100 points
    private Item[] inventaire;
    private Item equipedItem;
    private int selectedSlot;
    private boolean isMining;
    private boolean isAttacking;
    private boolean isAlive;
    private Direction direction;

    public Personnage(int x, int y, double pointsDeVieMax, String nom, double vitesseDeplacement) {
        this.x = x;
        this.y = y;
        this.pointsDeVieMax = pointsDeVieMax;
        this.nom = nom;
        this.vitesseDeplacement = vitesseDeplacement;
        this.pointsDeVie = pointsDeVieMax;
        this.satiete = 100;
        this.isMining = false;
        this.isAttacking = false;
        this.isAlive = true;
        inventaire = new Item[9]; // Taille de l'inventaire du personnage, nombre de "Slots".
        equipedItem = null;
        selectedSlot = 1;
        direction = Direction.DROITE;
    }
}