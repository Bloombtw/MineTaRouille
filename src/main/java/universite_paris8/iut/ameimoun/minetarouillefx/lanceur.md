# Explications sur le MainApp

## MainApp
        MainApp est la classe principale JavaFX du jeu MTR. Elle gère l'écran d'accueil, l'accès au jeu et le style des boutons.

### Méthodes :
    start(Stage primaryStage) :
        - Gère le fond d'écran, se charge en appelant le loader et s'adapte à la taille de la fenêtre avec bind().

        - Gère les deux boutons :
                            - Nouvelle partie, démarre le jeu en chargeant Map.fxml et met à jour le stage avec la nouvelle scène et l'autre titre.
                            - Quitter, ferme l'application

        - Gère la structure de la scène :
                            - StackPane en conteneur racine
                            - Vbox centralisé pour les boutons
                            - Scene définie par les constantes LARGEUR_FENETRE et HAUTEUR_FENETRE

    styleBouton(Button bouton) :
        - Personnalise l'apparence des boutons, leur donne :
            - Un fond transparent
            - Un texte blanc
            - Une taille de police augmentée
            - Une animation de zoom léger lorsque la souris les survole

    main(String[] args) :
        - Appelle launch(args) pour démarrer JavaFx.