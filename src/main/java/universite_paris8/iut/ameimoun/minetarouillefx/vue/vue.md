# Dossier `vue`

Ce dossier contient les classes responsables de l’affichage graphique du jeu : elles traduisent l’état du modèle en éléments visuels, sans contenir de logique métier.  
Chaque classe de la vue gère la représentation d’un ou plusieurs objets du jeu à l’écran, l’organisation des éléments graphiques, et l’actualisation de l’interface en fonction des changements du modèle.

---

## VueCarte

Classe de la vue gérant l’affichage graphique de la carte du jeu sous forme de grille de tuiles.

Gère :
- La création et l’organisation des cellules graphiques (`TilePane`) représentant chaque tuile de la carte.
- L’affichage des différents blocs (décors, animations, entités) selon leur type.
- L’ajout d’animations ou d’images statiques pour chaque bloc, en fonction de sa nature.
- La mise à jour dynamique de l’affichage d’une tuile lors d’un changement dans le modèle.

**Principaux attributs :**
- `private final TilePane tileMap` : conteneur graphique de la carte.
- `private final Carte carte` : référence au modèle de la carte.

**Méthodes principales :**
- `public VueCarte(Carte carte)` : construit la vue à partir du modèle de carte.
- `public void mettreAJourAffichage(int x, int y)` : met à jour l’affichage d’une tuile donnée.
- `public TilePane getTileMap()` : retourne le conteneur graphique de la carte.

**Résumé du fonctionnement :**
- Parcourt le modèle de carte pour générer la grille graphique.
- Utilise les gestionnaires d’animation et de ressources pour afficher chaque bloc avec l’image ou l’animation appropriée.
- Permet de synchroniser l’affichage avec l’état du modèle lors des modifications (explosions, déplacement, etc.).

---

## VueInventaire

Classe de la vue gérant l’affichage graphique de l’inventaire du joueur sous forme de barre d’items.

Gère :
- La création et l’organisation des cases d’inventaire (`StackPane`) dans une barre horizontale (`HBox`).
- L’affichage de l’image de chaque item, de sa quantité, et la mise en surbrillance de la case sélectionnée.
- La synchronisation automatique de l’affichage lors des modifications de l’inventaire ou du slot sélectionné.

**Principaux attributs :**
- `private final Inventaire inventaire` : référence au modèle d’inventaire.
- `private final List<StackPane> casesSlots` : liste des cases graphiques de l’inventaire.

**Méthodes principales :**
- `public VueInventaire(Inventaire inventaire)` : construit la vue à partir du modèle d’inventaire.
- `public void mettreAJourAffichage()` : met à jour l’affichage de toutes les cases.
- `private void updateCaseSlot(StackPane caseSlot, int index)` : met à jour une case donnée selon l’item et la sélection.
- `private StackPane creerCaseSlot(int index)` : crée une case graphique pour un slot d’inventaire.

**Résumé du fonctionnement :**
- Génère dynamiquement les cases de l’inventaire à partir du modèle.
- Affiche l’image de l’item, sa quantité (si > 1), et applique un effet visuel à la case sélectionnée.
- Met à jour l’affichage en réponse aux changements du modèle (ajout, retrait, sélection).

---

## VueItem

Classe de la vue gérant l’affichage graphique d’un item posé sur la carte.

Gère :
- La création de l’image graphique (`ImageView`) représentant l’item au sol.
- Le choix dynamique du chemin d’image selon le nom de l’item, avec fallback si l’image n’existe pas.
- L’actualisation de la position graphique de l’item lors de ses déplacements.

**Principaux attributs :**
- `private ImageView itemView` : composant graphique affichant l’image de l’item.

**Méthodes principales :**
- `public VueItem(Item item)` : construit la vue à partir du modèle d’item, choisit l’image appropriée.
- `public ImageView getImageView()` : retourne l’image graphique de l’item.
- `public void updatePosition(Item item)` : met à jour la position graphique selon les coordonnées du modèle.

**Résumé du fonctionnement :**
- Génère dynamiquement l’image de l’item à partir de son nom, avec gestion des accents et espaces.
- Utilise un fallback vers une image par défaut si l’image spécifique n’est pas trouvée.
- Permet d’afficher et de déplacer visuellement les items sur la carte.

---

## VueJoueur

Classe de la vue gérant l’affichage graphique du joueur sur la carte.

Gère :
- L’affichage du sprite du joueur avec animation selon son état (déplacement, saut, etc.).
- La superposition d’un effet visuel lors de la prise de dégâts (overlay rouge temporaire).
- L’affichage dynamique de l’objet actuellement tenu par le joueur.
- La synchronisation automatique de la position graphique avec le modèle.

**Principaux attributs :**
- `private final Joueur joueur` : référence au modèle du joueur.
- `private final ImageView perso` : sprite graphique du joueur.
- `private final Group container` : conteneur regroupant le joueur, l’overlay et l’objet tenu.
- `private final Rectangle overlayDegats` : superposition rouge affichée lors des dégâts.
- `private final ImageView objetTenu` : image de l’objet actuellement tenu.
- `private final AnimationJoueur animationJoueur` : gestionnaire d’animation du sprite.

**Méthodes principales :**
- `public VueJoueur(Joueur joueur)` : construit la vue à partir du modèle, initialise les liaisons et animations.
- `public Group getNode()` : retourne le conteneur graphique du joueur.
- `public void afficherDegats()` : affiche temporairement l’overlay rouge lors de la prise de dégâts.
- `public void mettreAJourObjetTenu(Item item)` : met à jour l’image de l’objet tenu selon l’item sélectionné.

**Résumé du fonctionnement :**
- Lie dynamiquement la position graphique à celle du modèle.
- Met à jour l’animation du sprite selon les déplacements du joueur.
- Affiche un effet visuel lors de la prise de dégâts.
- Met à jour l’objet tenu en fonction de l’inventaire ou des actions du joueur.

---

## VueMob

Classe de la vue gérant l’affichage graphique d’un mob (entité ennemie ou neutre) sur la carte.

Gère :
- L’affichage du sprite du mob avec animation selon son état (déplacement, attaque, etc.).
- La synchronisation automatique de la position graphique avec le modèle du mob.
- La mise à jour de l’animation en fonction de l’état courant du mob.

**Principaux attributs :**
- `private final ImageView mobImage` : sprite graphique du mob.
- `private final Group container` : conteneur regroupant le sprite et autres éléments graphiques éventuels.
- `private final AnimationMob animationMob` : gestionnaire d’animation du mob.

**Méthodes principales :**
- `public VueMob(Mob mob)` : construit la vue à partir du modèle, initialise les liaisons et animations.
- `private void lierPositionContainer(Mob mob)` : lie dynamiquement la position graphique à celle du modèle.
- `public void mettreAJourAnimation(Mob mob)` : met à jour l’animation du sprite selon l’état du mob.
- `public Group getNode()` : retourne le conteneur graphique du mob.

**Résumé du fonctionnement :**
- Lie dynamiquement la position graphique à celle du modèle via les propriétés du mob.
- Met à jour l’animation du sprite selon l’état courant du mob (déplacement, attaque, etc.).
- Permet d’afficher et d’animer visuellement les mobs sur la carte.

---

## VueVie

Classe de la vue gérant l’affichage graphique de la barre de vie du joueur.

Gère :
- L’affichage d’une barre de vie colorée (vert, orange, rouge selon le pourcentage de vie).
- La synchronisation automatique de la largeur et de la couleur de la barre selon la vie actuelle.
- L’affichage d’un overlay rouge global lors de la prise de dégâts (effet de flash).
- L’animation de l’overlay via une transition de fondu.

**Principaux attributs :**
- `private final Vie vieModele` : référence au modèle de vie du joueur.
- `private final Rectangle fond` : fond de la barre de vie.
- `private final Rectangle barre` : barre colorée représentant la vie restante.
- `private final StackPane containerBarreVie` : conteneur graphique de la barre de vie.
- `private final Rectangle overlayDegatsGlobal` : overlay rouge couvrant l’écran lors des dégâts.
- `private final FadeTransition fadeTransition` : animation de fondu pour l’overlay.

**Méthodes principales :**
- `public VueVie(Vie vieModele, Region rootPane)` : construit la vue à partir du modèle de vie et du conteneur principal.
- `public void afficherDegats()` : déclenche l’animation de flash rouge lors de la prise de dégâts.
- `public StackPane getNoeudBarreVie()` : retourne le conteneur graphique de la barre de vie.
- `public Rectangle getOverlayDegatsGlobal()` : retourne l’overlay rouge global.

**Résumé du fonctionnement :**
- Lie dynamiquement la largeur de la barre à la vie actuelle du joueur.
- Change la couleur de la barre selon le pourcentage de vie : vert (>60 %), orange (>30 %), rouge (≤30 %).
- Affiche un effet visuel de flash rouge sur tout l’écran lors de la prise de dégâts, avec animation de disparition.
- Utilise les propriétés observables du modèle pour réagir automatiquement aux changements de vie.

---

## VueParametres

Classe de la vue destinée à gérer l’affichage graphique du menu des paramètres du jeu (menu accessible via la touche Échap).

**Statut :**  
_TODO — tout est à faire._

**Idées de fonctionnalités à implémenter :**
- Affichage d’un menu lors de l’appui sur Échap.
- Possibilité de modifier le volume, les contrôles, la résolution, etc.
- Boutons pour reprendre la partie, accéder au menu principal ou quitter le jeu.
- Prise en charge de la navigation clavier/souris.

Aucune logique ou interface graphique n’est encore implémentée.