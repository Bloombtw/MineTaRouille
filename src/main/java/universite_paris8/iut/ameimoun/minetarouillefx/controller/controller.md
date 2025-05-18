
# Package controller :
Dans le controller on gère pour l'instant via deux classes
la gestion du clavier ainsi que le déroulement du jeu appelé Clavier et JeuController.

## Clavier :

Ses variables locales : des booleans enDeplacment gauche et droite qui nous permet de []

>La classe clavier prend en paramètre le Joueur et utilise des setOnKeyPressed et setOnKeyReleased
pour pouvoir par la suite précisé des types d'animations, on fera l'appel de la méthode gestionDeClavier pour changer
d'image quand on relâche le clavier par exemple.

> note : j'ajoute la vue du joueur pour que la maj du perso au niveau de l'affichage s'actualise à chaque déplacement.
> (j'ai hésiter à apl la méthode dans Joueur directement mais ça serait mélanger vue et modele ...?)

Le clavier appel la methode peutSauter() de joueur, car il y a une gestion de gravité ajt.


## JeuController :

J'appelle dans l'initializable trois sous classe : 

### initialiserControles() : 
Qui créer un nouveau Clavier qui cible un joueur et cile les déplacements 
du joueur sur la TileMap ciblée.

### initialiserJoueur() : 
Qui va créer un joueur qui cible la carte cotée modele et creer une vueJoueur qui cible
la rootPane pour l'affichage. 

### demarrerBoucleDeJeu() :
La gestion de la gameLoop() en utilisant la méthode miseAJourJeu(), c'est plus pratique de séparer dans le
cas où il faudra ajouter/adapter du contenu dans la game loop à l'avenir. C'est plus simple de s'y retrouver et de debugger.

### mettreAJourJeu() : 
La mise à jour du jeu se fait par la gestion de la gravité du perso ainsi que changement de position
visuel.


Note : la map est actuellement créé directement dans le controller : il doit être créé dans
Carte et vueCarte(à créer).
-->Pour avoir un initialiserMap() et l'ajouter dans l'initializable.

Note : déplacer la methode getImageAssociee dans vueCarte.

Note : vérifier si le problème du déplacement du joueur n'est pas au vu du fait que la vue du joueur et ciblée
sur l'AncorPane et non la TilePane.


