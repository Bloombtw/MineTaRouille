# Explications modèle :

---

## Personnage

Classe principale représentant un personnage du jeu (joueur ou PNJ).

Gère :
- Les coordonnées en pixels (x, y) via des propriétés observables.
- L’état du personnage (points de vie, direction, vitesse).
- La physique : gravité, sauts, collisions avec la carte.
- Les méthodes de déplacement (droite, gauche, saut).
- La gestion de la carte associée.

**Principaux attributs :**
- `DoubleProperty x, y` : position du personnage.
- `Vie vie` : gestion des points de vie.
- `String nom` : nom du personnage.
- `Direction direction` : direction actuelle (GAUCHE/DROITE).
- `double vitesseX, vitesseY` : vitesses horizontale et verticale.
- `boolean peutSauter` : indique si le personnage peut sauter.
- `Carte carte` : référence à la carte du jeu.

**Méthodes principales :**
- `sauter()` : Fait sauter le personnage si possible.
- `deplacerGauche()` : Déplace le personnage vers la gauche (avec gestion collision).
- `deplacerDroite()` : Déplace le personnage vers la droite (avec gestion collision).
- `arreterMouvementX()` : Arrête le déplacement horizontal.
- `gravite()` : Applique la gravité et gère les collisions verticales.
- `collision(x, y)` : Teste la collision aux coordonnées données.
- Accesseurs pour les propriétés, positions, vitesse verticale, vie.

**Résumé du fonctionnement :**
- À chaque déplacement ou saut, la classe vérifie les collisions avec la carte.
- La gravité est appliquée à chaque tick de jeu, modifiant la position verticale.
- Le personnage ne peut sauter que s’il est au sol (détecté via collision).
- La direction est mise à jour selon le dernier déplacement.

---

## Inventaire

Classe représentant l’inventaire du joueur.

Gère :
- Les emplacements d’objets (slots) sous forme de liste observable.
- L’ajout d’objets (empilement si même id, sinon dans un slot vide).
- La sélection de l’emplacement courant (barre d’accès rapide).

**Principaux attributs :**
- `ObservableList<Item> slots` : liste des objets de l’inventaire (9 emplacements par défaut).
- `IntegerProperty selectedIndex` : index de l’emplacement sélectionné.

**Méthodes principales :**
- `ajouterItem(Item nouvelItem)` : Ajoute un objet à l’inventaire (empile si possible, sinon place dans un slot vide).
- `getSlots()` : Retourne la liste observable des slots.
- `getItem(int index)` : Retourne l’objet à l’index donné.
- `getSelectedIndex()` / `setSelectedIndex(int)` : Accesseurs pour l’index sélectionné.
- `selectedIndexProperty()` : Pour lier l’index sélectionné à la vue.

**Résumé du fonctionnement :**
- L’inventaire permet de stocker jusqu’à 9 objets (ou piles d’objets).
- Si un objet ajouté existe déjà (même id), il est empilé.
- Sinon, il est placé dans le premier slot vide.
- L’index sélectionné permet de savoir quel objet est actif (ex : barre d’outils).

---

## Vie

Classe qui gère les points de vie d’un personnage.

Gère :
- Les points de vie maximums et actuels (propriétés observables).
- La prise de dégâts et la guérison via des méthodes dédiées.
- Les callbacks lors de la prise de dégâts (pour déclencher des actions dans la vue ou ailleurs).
- La détection des dangers sur la carte (ex : feu, cactus) et l’application automatique de dégâts.
- L’état vivant/mort du personnage (propriété observable).

**Principaux attributs :**
- `DoubleProperty vieMax` : points de vie maximum.
- `DoubleProperty vieActuelle` : points de vie actuels.
- `BooleanProperty estEnVieProperty` : indique si le personnage est vivant.
- `BooleanProperty subitDegats` : indique si le personnage subit actuellement des dégâts (ex : sur un bloc dangereux).
- `List<Runnable> actionsSurDegats` : liste de callbacks à exécuter lors de la prise de dégâts.

**Méthodes principales :**
- `subirDegats(double quantite)` : retire des points de vie et déclenche les callbacks.
- `soigner(double quantite)` : soigne le personnage sans dépasser le maximum.
- `estMort()` : retourne vrai si les points de vie sont à zéro.
- `verifierDegats(Joueur joueur, Carte carte)` : applique des dégâts si le joueur est sur un bloc dangereux (feu, cactus).
- `ajouterCallbackDegatsSubis(Runnable callback)` : ajoute une action à exécuter lors de la prise de dégâts.
- Accesseurs pour les propriétés observables.

**Résumé du fonctionnement :**
- Les points de vie ne doivent pas être modifiés directement : toujours passer par les méthodes prévues.
- La classe gère la notification de la vue via des propriétés observables et des callbacks.
- La détection des dangers de la carte est centralisée ici, pour éviter la duplication de logique.

---

## Type

Enumération représentant le type d’un objet ou d’un item dans le jeu.

Gère :
- Le nom lisible du type (ex : Arme, Outil, Bloc, Ressource, Consommable).
- Si le type permet d’avoir une rareté (attribut booléen).

**Valeurs possibles :**
- ARME : Arme, permet la rareté.
- OUTIL : Outil, permet la rareté.
- BLOC : Bloc, ne permet pas la rareté.
- RESSOURCE : Ressource, ne permet pas la rareté.
- CONSOMMABLE : Consommable, ne permet pas la rareté.

**Principaux attributs :**
- `String nom` : nom lisible du type.
- `boolean permetRarete` : indique si ce type peut avoir une rareté.

**Méthodes principales :**
- `permetRarete()` : retourne si le type accepte la rareté.
- `toString()` : retourne le nom lisible du type.

**Résumé du fonctionnement :**
- Utilisé pour catégoriser les items et déterminer s’ils peuvent avoir une rareté.

---

## Rarete

Enumération représentant la rareté d’un objet ou d’un item dans le jeu.

Gère :
- Le nom lisible de la rareté (ex : Commun, Rare, Epique, Legendaire).
- La couleur associée à la rareté (pour affichage dans la vue).

**Valeurs possibles :**
- COMMUN : Commun, couleur verte.
- RARE : Rare, couleur bleue.
- EPIQUE : Epique, couleur violette.
- LEGENDAIRE : Legendaire, couleur dorée.

**Principaux attributs :**
- `String nomRarete` : nom lisible de la rareté.
- `Color couleurRarete` : couleur associée à la rareté.

**Méthodes principales :**
- `getNomLisible()` : retourne le nom lisible de la rareté.
- `getCouleurHex()` : retourne la couleur associée.
- `toString()` : retourne le nom lisible de la rareté.

**Résumé du fonctionnement :**
- Utilisé pour indiquer la rareté d’un item, avec un affichage visuel spécifique selon la couleur.

---

## Objet

Enumération représentant les objets du jeu (armes, outils, consommables, etc).

Gère :
- L’identifiant unique de l’objet.
- Le nom lisible de l’objet.
- La taille maximale de pile (stack).
- La description de l’objet.
- Le type de l’objet (`Type`).
- La rareté de l’objet (`Rarete`).

**Exemples de valeurs :**
- EPEE : id 101, "Épée", stack 1, type ARME, rareté COMMUN.
- ARC : id 102, "Arc", stack 1, type ARME, rareté COMMUN.
- PIOCHE : id 103, "Pioche", stack 1, type OUTIL, rareté COMMUN.
- PELLE : id 104, "Pelle", stack 1, type OUTIL, rareté COMMUN.
- MOUTON_CUIT : id 105, "Mouton cuit", stack 64, type CONSOMMABLE, rareté COMMUN.

**Principaux attributs :**
- `int id` : identifiant unique de l’objet.
- `String nom` : nom lisible.
- `int stackSize` : taille maximale de pile.
- `String description` : description de l’objet.
- `Type type` : type de l’objet (arme, outil, etc).
- `Rarete rarete` : rareté de l’objet.

**Méthodes principales :**
- `getId()` : retourne l’identifiant.
- `getNom()` : retourne le nom.
- `getStackSize()` : retourne la taille de pile.
- `getDescription()` : retourne la description.
- `getType()` : retourne le type.
- `getRarete()` : retourne la rareté.

**Résumé du fonctionnement :**
- Utilisé pour référencer tous les objets manipulables dans le jeu.
- Permet d’associer à chaque objet ses propriétés, son type et sa rareté.

---

## Mob

Classe représentant un monstre ou PNJ contrôlé par l’IA, héritant de `Personnage`.

Gère :
- Le déplacement automatique (droite/gauche) selon une direction.
- Le changement de direction et le saut en cas de collision.
- L’application de la gravité et la gestion des collisions comme un personnage classique.

**Principaux attributs :**
- `Direction mouvementDirection` : direction actuelle du déplacement automatique (GAUCHE/DROITE).

**Méthodes principales :**
- `mettreAJour()` : met à jour la position du mob, applique la gravité, gère les collisions et change de direction si besoin.
- `getVitesseX()` : retourne la vitesse horizontale selon la direction.
- Hérite des méthodes de `Personnage` (déplacement, gravité, collision, etc).

**Résumé du fonctionnement :**
- Le mob se déplace automatiquement dans une direction.
- En cas de collision avec un obstacle, il saute et peut changer de direction.
- La gravité et les collisions sont gérées comme pour un personnage joueur.

---

## Joueur

Classe représentant le joueur contrôlé par l’utilisateur, héritant de `Personnage`.

Gère :
- L’initialisation du joueur avec des coordonnées et des points de vie spécifiques.
- Les actions spécifiques au joueur (ex : creuser, utiliser des objets de l’inventaire).

**Principaux attributs :**
- Hérite de tous les attributs de `Personnage`.

**Méthodes principales :**
- Hérite de toutes les méthodes de `Personnage` (déplacement, saut, gravité, etc).

**Résumé du fonctionnement :**
- Le joueur est le personnage principal contrôlé par l’utilisateur.
- //TODO : Peut effectuer des actions spécifiques selon les objets possédés (ex : creuser avec une pelle).
- Dispose d’un inventaire et de points de vie gérés comme pour tout personnage.

---

## Item

Classe représentant un item manipulable dans le jeu (bloc ou objet).

Gère :
- Le type d’item (bloc ou objet).
- La référence vers le bloc ou l’objet associé.
- La quantité de l’item (empilable).
- La position au sol (pour les items déposés dans le monde).

**Principaux attributs :**
- `TypeItem typeItem` : type de l’item (`BLOC` ou `OBJET`).
- `Bloc bloc` : référence vers le bloc (si type `BLOC`).
- `Objet objet` : référence vers l’objet (si type `OBJET`).
- `SimpleIntegerProperty quantite` : quantité de l’item (empilable).
- `double x, y` : position de l’item au sol (si déposé dans le monde).

**Méthodes principales :**
- `getQuantite()` : retourne la quantité.
- `ajouterQuantite(int q)` : ajoute une quantité à l’item.
- `getNom()` : retourne le nom lisible de l’item.
- `getId()` : retourne l’identifiant unique de l’item.
- `getBloc()` / `getObjet()` : accesseurs vers le bloc ou l’objet associé.
- `getTypeItem()` : retourne le type (`BLOC` ou `OBJET`).
- Accesseurs pour la position au sol (`getX()`, `getY()`, `setX()`, `setY()`).

**Résumé du fonctionnement :**
- Un `Item` représente soit un bloc, soit un objet, avec une quantité.
- Utilisé dans l’inventaire, dans le monde (items au sol), ou lors de la manipulation d’objets/blocs.
- Permet d’unifier la gestion des objets et blocs dans l’inventaire et le jeu.

---

## Inventaire

Classe représentant l’inventaire du joueur.

Gère :
- Les emplacements d’objets (slots) sous forme de liste observable.
- L’ajout d’objets (empilement si même id, sinon dans un slot vide).
- La sélection de l’emplacement courant (barre d’accès rapide).

**Principaux attributs :**
- `ObservableList<Item> slots` : liste des objets de l’inventaire (9 emplacements par défaut).
- `IntegerProperty selectedIndex` : index de l’emplacement sélectionné.

**Méthodes principales :**
- `ajouterItem(Item nouvelItem)` : Ajoute un objet à l’inventaire (empile si possible, sinon place dans un slot vide).
- `getSlots()` : Retourne la liste observable des slots.
- `getItem(int index)` : Retourne l’objet à l’index donné.
- `getSelectedIndex()` / `setSelectedIndex(int)` : Accesseurs pour l’index sélectionné.
- `selectedIndexProperty()` : Pour lier l’index sélectionné à la vue.

**Résumé du fonctionnement :**
- L’inventaire permet de stocker jusqu’à 9 objets (ou piles d’objets).
- Si un objet ajouté existe déjà (même id), il est empilé.
- Sinon, il est placé dans le premier slot vide.
- L’index sélectionné permet de savoir quel objet est actif (ex : barre d’outils).

---

## GenerateurCarte

Classe utilitaire responsable de la génération procédurale de la carte du jeu.

Gère :
- La création du terrain sous forme de tableau 3D de blocs (`Bloc[][][]`).
- La génération de la hauteur du sol avec variations aléatoires.
- Le placement des différents types de blocs (surface, profondeur, décors, cactus, nuages, arbres, étoiles, objets spéciaux).

**Principaux attributs :**
- Aucun attribut d’instance : toutes les méthodes sont statiques.

**Méthodes principales :**
- `genererCarte()` : génère et retourne une nouvelle instance de `Carte` avec un terrain aléatoire.
- Méthodes privées utilitaires :
  - `genererHauteurSol(Random, Bloc[][][])` : calcule la hauteur du sol pour chaque colonne.
  - `remplirTerrain(int[], Random, Bloc[][][])` : remplit le terrain avec les blocs de surface et de profondeur.
  - `placerBlocSurface(...)`, `placerBlocProfondeur(...)` : placent les blocs selon la couche.
  - `ajouterNuages(Random, Bloc[][][])` : ajoute des nuages en haut de la carte.
  - `ajouterArbre(int[], Bloc[][][])` : place un arbre à un emplacement fixe.
  - `ajouterEtoiles(Random, Bloc[][][])` : ajoute des étoiles dans le ciel.
  - `ajouterObjetsSpeciaux(Bloc[][][])` : place des éléments spéciaux (ex : corbeau, lune).

**Résumé du fonctionnement :**
- Utilisée pour créer une carte de jeu variée à chaque partie.
- Les éléments du décor et du terrain sont placés de façon pseudo-aléatoire pour garantir la rejouabilité.
- La carte générée est ensuite utilisée par la classe `Carte` pour le jeu.

---

## Direction

Enumération représentant la direction horizontale d’un personnage ou d’un mob.

Gère :
- Le nom lisible de la direction (ex : Gauche, Droite).

**Valeurs possibles :**
- GAUCHE : "Gauche"
- DROITE : "Droite"

**Principaux attributs :**
- `String nom` : nom lisible de la direction.

**Méthodes principales :**
- `getNom()` : retourne le nom lisible de la direction.

**Résumé du fonctionnement :**
- Utilisée pour indiquer la direction actuelle d’un personnage ou d’un mob (gauche ou droite).
- Permet d’afficher ou de gérer le sens du déplacement dans le jeu.

---

## Carte

Classe représentant la carte du jeu, composée d’un terrain en 3D de blocs.

Gère :
- Le stockage du terrain sous forme de tableau 3D (`Bloc[][][]`) : couches, lignes, colonnes.
- Les méthodes pour casser un bloc, vérifier la solidité d’un bloc, et accéder aux dimensions de la carte.
- Le singleton de la carte (une seule instance partagée dans le jeu).

**Principaux attributs :**
- `Bloc[][][] terrain` : tableau 3D représentant les blocs de la carte.
- `static Carte instance` : instance unique de la carte (singleton).

**Méthodes principales :**
- `casserBloc(int couche, int x, int y)` : casse un bloc à la position donnée et retourne le bloc cassé.
- `estDansLaMap(int x, int y)` : vérifie si les coordonnées sont dans les limites de la carte.
- `getInstance()` : retourne l’instance unique de la carte (singleton, générée par `GenerateurCarte`).
- `getTerrain()` : retourne le tableau 3D du terrain.
- `getLargeur()` : retourne la largeur de la carte.
- `getHauteur()` : retourne la hauteur de la carte.
- `getNbCouches()` : retourne le nombre de couches de la carte.
- `estBlocSolide(int x, int y)` : indique si un bloc à la position donnée est solide (pour la collision).

**Résumé du fonctionnement :**
- La carte gère l’ensemble du terrain du jeu, avec plusieurs couches (sol, décor, etc).
- Permet de manipuler les blocs (casser, vérifier la solidité) et d’accéder aux dimensions.
- Utilisée par les personnages pour la gestion des collisions et des interactions avec le monde.

---

## Bloc

Enumération représentant les différents types de blocs du jeu.

Gère :
- L’identifiant unique du bloc.
- Le nom lisible du bloc.
- Si le bloc est solide (collision).
- Si le bloc nécessite un support pour être posé.
- La taille maximale de pile (stack).
- Si le bloc est animé (ex : feu, corbeau).

**Exemples de valeurs :**
- PIERRE : id 1, "Pierre", solide, stack 64, non animé.
- SABLE : id 2, "Sable", solide, stack 64, non animé.
- FEU : id 26, "Feu", non solide, nécessite support, stack 64, animé.
- CACTUS : id 27, "Cactus", solide, nécessite support, stack 64, non animé.

**Principaux attributs :**
- `int id` : identifiant unique du bloc.
- `String nom` : nom lisible du bloc.
- `boolean estSolide` : indique si le bloc est solide (collision).
- `boolean necessiteSupport` : indique si le bloc doit être posé sur un autre bloc.
- `int stackMax` : taille maximale de pile.
- `boolean estAnime` : indique si le bloc est animé.

**Méthodes principales :**
- `estSolide()` : retourne si le bloc est solide.
- `estAnime()` : retourne si le bloc est animé.
- `necessiteSupport()` : retourne si le bloc nécessite un support.
- `depuisId(int id)` : retourne le bloc correspondant à l’id.
- `getNom()` : retourne le nom lisible du bloc.
- `getId()` : retourne l’identifiant du bloc.
- `getStackMax()` : retourne la taille de pile maximale.

**Résumé du fonctionnement :**
- Utilisé pour référencer tous les blocs du jeu, avec leurs propriétés (collision, support, stack, animation).
- Permet de gérer la logique de pose, de casse, d’empilement et d’affichage des blocs dans le monde.

