# Explications modèle :

## Personnage :

        Classe abstraite qui représente un personnage (Joueur ou PNJ).

    Gère :
        - Les coordonnées en px. (X et Y)
        - L'état du personnage. (Points de vie, inventaire, direction, vitesse)
        - | TEMPORAIREMENT | La physique (gravité, sauts, collision)
        - | TEMPORAIREMENT | Les méthodes de déplacement (droite, gauche, saut)

## Joueur :

        Classe héritant de *Personnage*. Elle représente le joueur.

    Gère :
        - | TEMPORAIREMENT | Les animations ( marche, saut) 
        - | TEMPORAIREMENT | La synchro entre modèle et vue ??

## Carte :
        Classe génerant la map du monde.

    La carte est composée de 3 couches superposées :
        - [0] Fond 
        - [1] Sols 
        - [2] Elements de surface/décors 

    Gère :
        - | TEMPORAIREMENT | Le test de solidité de blocs (estSolide(x, y))
        - | EN REFLEXION | Le générateur de carte dans le constructeur.
        - Les dimensions via les constantes dans le utils.

## Items :
        Classe représentant un objet stockable dans l'inventaire.
    
    Contient :
        - Un id
        - Un nom
        - Un stacksize (Nb d'item stackable dans une case d'inventaire)
        - Une description
        - Un type (voir classe Type)
        - Une rareté (voir classe rareté)

## Bloc : 
        Classe Enum représentant tout les blocs de la carte (sols, décors, ciel).

    Contient :
        - Un id
        - Un nom
        - Une propriété estSolide (booléan) pour gérer les collisions

## Type :
        Classe enum représentant le type d'item. 

    Contient 5 types :
        - Arme
        - Outil
        - Bloc
        - Ressource
        - Consommable
    
    N'ont pas bcp d'intêret pour l'instant, pas encore travaillé sur les items.
    Pourront servir pour autoriser ou non la façon dont est utilisé le bloc dans la main du joueur.

## Rareté :
        Classe enum représentant la rareté des items.

    Contient 4 raretés :
        - Commun
        - Rare
        - Epique
        - Légendaire
    
    N'ont pas bcp d'intêret pour l'instant, pas encore travaillé sur les items.
    Pourront influencer les stats/taux d'apparition des items.

## Direction : 
        Classe enum représentant les deux directions.

    Contient 2 directions :
        - Gauche
        - Droite   

    Sont utilisés pour choisir le sprite à afficher.

## Inventaire :

        Classe qui représente les items sous forme de tab[] que possède le Joueur.

    Gère :
        - Les coordonnées en px. (X et Y)
        -l'ajout d'Item à l'inventaire

## Vie :

    La classe Vie représente l'état de santé de ton personnage dans le jeu. C'est le modèle. Elle ne s'occupe PAS de dessiner la barre de vie, ni des couleurs, ni des animations. Son unique rôle est de :

        Attributs principaux :
        vieMax : la vie maximale du joueur.
        vieActuelle : la vie actuelle.
        onDamageTakenCallback : une fonction à appeler quand le joueur subit des dégâts.    

    Gère :

        -Garder la valeur actuelle de la vie (par exemple, 100 points sur 100).
        -Savoir la vie maximale (par exemple, 100).
        -Appliquer des dégâts (subirDegats()).
        -Appliquer des soins (soigner()).
        -Vérifier si le personnage est mort (estMort()).
        -Détecter si le personnage est actuellement sur un danger (comme du feu ou un cactus) via verifierDegats().

>Modification apportée : 
    le listener n'est plus apl dans le constructeur, mais dans subirDegat.
    Faire attention à ne pas modifier vieActuelle directement (on prend soigner et subirDegat).

> note : subirDegats() : Réduit la vie du joueur. Si elle diminue, le callback est déclenché (ex. effet visuel dans la vue).
> AjouterCallbackDegatsSubis() : Permet à une vue de s'abonner pour être notifiée en cas de dégâts (utile pour jouer une animation).
> callbackDegatsSubis : représente la liaison entre Vie et VueVie