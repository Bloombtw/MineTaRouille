# Package Controller

Dans le controller, nous gérons actuellement deux classes :  
la gestion du clavier ainsi que le déroulement du jeu, appelées **Clavier** et **JeuController**.

## Clavier

### Variables locales :
- **enDéplacementGauche** (booléen)
- **enDéplacementDroite** (booléen)

### Fonctionnalités :
La classe **Clavier** prend en paramètre **Joueur** et utilise `setOnKeyPressed` et `setOnKeyReleased`  
pour gérer les animations et mettre à jour l'affichage du personnage.

note : j'ai ajouter une méthode initialiserBoucleDeplacement qui fluidifie les mvts des perso
(rigidité du personnage il ne pouvait pas sauter tout en avancant).

> **Note :** J'ajoute la vue du joueur pour que la mise à jour du personnage s'actualise à chaque déplacement.  
> (J'ai hésité à appeler la méthode dans **Joueur** directement, mais cela mélangerait vue et modèle...)

Le clavier appelle la méthode `peutSauter()` de **Joueur**,  
car une gestion de la gravité a été ajoutée.


---

## JeuController

Dans `initializable`, j'appelle trois sous-classes :

### `initialiserControles()`
- Crée un nouvel **Clavier** ciblant un **Joueur**.
- Permet le déplacement du joueur sur la **TileMap** ciblée.

### `initialiserJoueur()`
- Crée un **Joueur** lié à la carte (côté modèle).
- Instancie une **VueJoueur** pour l'affichage dans la `rootPane`.

### `demarrerBoucleDeJeu()`
- Gestion de la `gameLoop()` via `miseAJourJeu()`.
- Séparation logique qui facilite les ajouts et modifications futures.

### `mettreAJourJeu()`
- Mise à jour du jeu par la gestion de la gravité et des changements de position visuelle.

---

## Dernières modifications :
✅ **Ajout de `initialiserMap()`** → Organisation plus claire de la logique de la carte.  
✅ **Ajout de `initialiserItems()`** → Génération et affichage des objets dans **JeuController**.  
✅ **Modification de `gestionClavier()`** → Utilisation de `getPeutSauter()` au lieu de `peutSauter()`.  
✅ **Optimisation de `demarrerBoucleDeJeu()`** → Intégration des mises à jour des objets (`updateItem()`).
