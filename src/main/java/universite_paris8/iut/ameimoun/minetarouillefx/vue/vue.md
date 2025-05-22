# Explication globale de la vue

La **vue** est utilisée pour tous les affichages du jeu.  
Actuellement, ce package contient **6 classes**, dont **4 actives sans bug**.

## VueCarte

### Méthodes :
- **`getImageAssociee(Bloc bloc)`**  
  → Stocke toutes les tuiles en effectuant une conversion du type **Bloc** en **image**.
  
- **`initialiserCarte()`**  
  → Initialise la map pour l'affichage en convertissant un bloc en **image**,  
    puis l’intègre dans une case de la map.  
    Chaque couche récupère son bloc correspondant.  
    Ne prend **pas** en compte les blocs de type `CIEL`.

- **`ajouterBloc(Bloc b, StackPane cellule)`**  
  → Méthode appelée dans `initialiserCarte()`.  
    Utilise **StackPane** pour créer un conteneur pour chaque case.

---

## VueJoueur

### Méthodes :
- **`miseAJourPosition()`**  
  → Modifie la position du **Joueur** via `setLayoutX()` et `setLayoutY()`.
-ajt d'animation (gérer le bug côté gauche)
- Système de découpage de frames appelé de la classe Animation.
---

## Dernières modifications :
✅ **Création de `VueItem`** → Inspiré de **VueJoueur**, gère l'affichage visuel des objets (`ImageView`).  
✅ **Modification de `VueJoueur`** → Optimisation pour un affichage plus propre.  
✅ **Ajout de `updatePosition()`** → Mise à jour de la position des objets dans la scène.


## VueVie

  Attributs principaux :
  fond : fond gris de la barre.
  barre : barre verte/rouge/orange qui diminue quand la vie baisse.
  overlayDegatsGlobal : écran rouge transparent qui clignote en cas de dégâts.
  fadeTransition : animation de disparition progressive de l'écran rouge.

### Méthodes :
- **`afficherDegats()`**  
  → Joue une animation rouge clignotante quand des dégâts sont subis.
  -ajt d'animation (gérer le bug côté gauche)
- Système de découpage de frames appelé de la classe Animation.
---

>Modification : allégement du constructeur en séparant la créa de la barre de vie sous plusieurs methodes.