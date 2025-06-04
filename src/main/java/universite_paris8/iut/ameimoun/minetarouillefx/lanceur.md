# Lanceur de l’application

Ce dossier contient la classe principale permettant de lancer l’application JavaFX.  
Il initialise la fenêtre principale, charge l’écran d’accueil et configure la scène de départ.

---

## MainApp

Classe principale héritant de `Application` (JavaFX), point d’entrée du programme.

Gère :
- L’initialisation de la fenêtre principale (`Stage`).
- Le chargement de la première interface graphique via un fichier FXML.
- L’application des dimensions et du titre de la fenêtre.
- La gestion d’une référence statique au `Stage` principal pour un accès global.

**Principaux attributs :**
- `public static Stage primaryStageGlobal` : référence statique à la fenêtre principale.

**Méthodes principales :**
- `public void start(Stage primaryStage)` : méthode appelée au lancement, configure la scène et affiche la fenêtre.
- `public static void main(String[] args)` : point d’entrée Java, lance l’application JavaFX.

**Résumé du fonctionnement :**
- Charge l’écran d’accueil depuis le FXML.
- Crée la scène avec les dimensions définies dans les constantes.
- Affiche la fenêtre principale avec le titre du jeu.
- Gère les exceptions lors du chargement de l’interface.