# Documentation du package `controller`

---

## JeuController

Contrôle principal de la scène de jeu.  
Initialise la carte, le joueur, la barre de vie, l’inventaire, les contrôles, la musique et les entités (mobs).  
Gère la boucle de jeu (`AnimationTimer`) : gravité, mise à jour des entités, gestion de la vie, inventaire, debug.

**Principales méthodes :**
- `initialize()`  
  Appelle toutes les méthodes d’initialisation : carte, joueur, barre de vie, inventaire, contrôles, mob, musique, gestionnaires.
- `demarrerBoucleDeJeu()`  
  Lance la boucle principale du jeu.
- `mettreAJourJeu()`  
  Met à jour la gravité, la vie, les mobs, l’inventaire et le debug.
- 
---

## EcranAccueilController

Contrôle l’écran d’accueil.  
Gère l’affichage du fond et des boutons (nouvelle partie, quitter).  
Réagit aux clics sur les boutons pour lancer une partie ou quitter l’application.

**Principales méthodes :**
- `initialize()`  
  Charge les images des boutons et du fond. Lie la taille du fond à la fenêtre.
- `lancerNouvellePartie()`  
  Charge la scène de jeu (`Map.fxml`) et bascule la fenêtre.
- `quitter()`  
  Ferme l’application.

---

## EcranDeMortController

Contrôle l’écran de mort.  
Affiche le message de mort, les boutons rejouer/quitter.  
Peut gérer une vidéo de mort (commenté).  
Gère les actions des boutons.

**Principales méthodes :**
- `initialize()`  
  Charge les images des boutons et du message de mort.
- `handleQuitter()`  
  Arrête la musique et ferme l’application.
- `handleRejouer()`  
  Recharge la scène de jeu pour recommencer une partie.

---

## GestionnaireControles

Gère l’initialisation et la liaison des contrôles clavier et souris.  
Crée et configure les écouteurs (**ClavierListener**, **Souris**).  
Lie les événements d’entrée utilisateur à la carte du jeu.

**Principales méthodes :**
- `initialiserControles()`  
  Instancie et configure les listeners clavier/souris.
- **Accesseurs**  
  Permet de récupérer les listeners clavier et souris.

---

## clavier/ClavierListener

Gère les événements clavier pour le joueur.  
Détecte les touches pressées/relâchées pour déplacer le joueur, sauter, etc.

---

## souris/Souris

Gère les événements souris (clics, déplacements) sur la carte.  
Permet d’interagir avec les éléments du jeu via la souris.

---

## Dernières modifications

- ✅ Ajout de `initialiserCarte()` → Organisation plus claire de la logique de la carte.
- ✅ Ajout de `initialiserGestionnaireItem()` → Génération et affichage des objets dans JeuController.
- ✅ Ajout de `initialiserGestionnaireSon()` → Gestion des sons liés au joueur.
- ✅ Ajout de `initialiserGestionnaireMort()` → Gestion de la mort du joueur.
- ✅ Ajout de `initialiserGestionnaireVie()` → Gestion de la vie du joueur.
- ✅ Ajout de `initialiserMob()` → Ajout et gestion des entités ennemies.
- ✅ Optimisation de `demarrerBoucleDeJeu()` → Intégration des mises à jour des objets (`updateItem()`).
- ✅ Ajout de la documentation des écrans d’accueil et de mort.
- ✅ Ajout des sous-packages clavier et souris.

