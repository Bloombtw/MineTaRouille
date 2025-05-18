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

---

## Dernières modifications :
✅ **Création de `VueItem`** → Inspiré de **VueJoueur**, gère l'affichage visuel des objets (`ImageView`).  
✅ **Modification de `VueJoueur`** → Optimisation pour un affichage plus propre.  
✅ **Ajout de `updatePosition()`** → Mise à jour de la position des objets dans la scène.
