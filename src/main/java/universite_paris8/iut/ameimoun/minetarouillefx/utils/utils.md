# Explications utils :

## debug :

        En appuyant sur F3, un menu de débug se lance. Il permet d'afficher hit-box + grille de la carte.    

    DebugOverlay :
        DebugOverlay affiche un Canvas de la taille de la map. Il quadrille selon la taille d'une tuile (30px). Il parcourt tout et regarde si le bloc est solide
        avec la fonction estSolide() (voir dans Bloc). Si est solide, il colorie en rouge.
           - La méthode genererGrille() renvoie un Canvas de la taille de la carte en pixels.

    DebugManager :
        DebugManager gère l'état du mode, c'est lui qui choisit le on/off. Il initialise les deux Canvas,
        la grille et la hit-box du personnage en jaune. La hit-box est mise à jour à chaque frame dans la gameLoop.
           - La méthode toggle() permet le on/off
           - La méthode update() redessine la hit-box avec les bonnes coordonnées.
           - La méthode isDebugVisible() retourne l'état de debugVisible.


## Constantes :
       Constantes est la classe rassemblant toutes les constantes (valeurs fixes) du programme MTR. Elles sont centralisées pour faciliter
    leur modification.    


## Loader :
       Loader est la classe permettant de centraliser le chargement des ressources du jeu. Elle gère les images et le fxml.    
        - LoadImage permet de passer de : Image img = new Image(getClass().getResourceAsStream("/exemple/exemple/exemple.png"));

                                    à : Image img = Loader.loadImage("/exemple/exemple/exemple.png");


        - LoadFxml permet de passer de : FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/exemple/exemple.fxml"));
                                       Exemple exemple = fxmlLoader.load();

                                   à : Exemple exemple = Loader.loadFxml("/exemple/exemple.fxml");

    Ces deux fonctions permettent d'uniformiser le chargement des ressources.