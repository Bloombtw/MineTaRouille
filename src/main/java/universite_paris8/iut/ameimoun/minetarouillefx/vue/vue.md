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
- **`jouerAnimation()`**
 Sert à lancer l'animation du joueur et elle est appelée dans majAnimation.
- **`afficherDegats()`**
Affiche un rectangle rouge autour du joueur (avant de mettre l'image du sprite correspondant), l'overlay est crée dans le constructeur
---


## VueVie

### Méthodes :
- **`mettreAJourCouleurBarre()`**  
  → Modifie la couleur de la barre de vie en fonction de s'il subit des dégats ou non.
- **`afficherDegats()`**
  Affiche la barre de vie en bleue
---
## Dernières modifications :
✅ **Ajout de l'affichage de dégat sur la barre de vie dans **`VueVie`**
✅ **Modification de `VueJoueur`** → ajout d'afficher dégat pour que le perso change d'animation quand il subit des dégats