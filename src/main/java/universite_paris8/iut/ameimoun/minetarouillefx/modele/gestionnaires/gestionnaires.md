# Explications gestionnaires :

Ce dossier regroupe les classes utilitaires responsables de la gestion de certaines mécaniques du jeu (blocs, entités, etc).  
Chaque gestionnaire centralise la logique liée à un aspect précis pour éviter la duplication de code dans le modèle principal.
Il est pensé pour réduire la taille du controller principal et éviter la "God Class".

---

## GestionnaireBloc

Classe utilitaire centralisant la gestion des interactions avec les blocs (casser, poser).

Gère :
- La casse d’un bloc et la création de l’objet correspondant (Item).
- Le placement d’un bloc sur la carte, avec toutes les vérifications nécessaires (distance, collision, inventaire).
- Les vérifications de distance entre le joueur et le bloc ciblé.
- La détection de collision entre la hitbox du joueur et un bloc à poser.

**Principaux attributs :**
- Aucun attribut d’instance : toutes les méthodes sont statiques.

**Méthodes principales :**
- `casserBlocEtDonnerItem(int couche, int x, int y, Joueur joueur)` : casse un bloc à la position donnée (si autorisé) et retourne l’Item correspondant.
- `placerBloc(Carte carte, Inventaire inventaire, int indexItem, int couche, int x, int y, Joueur joueur)` : place un bloc depuis l’inventaire à la position donnée (si toutes les conditions sont réunies).
- `estADistanceAutorisee(Joueur joueur, int x, int y, int distanceMax)` : vérifie si le joueur est à distance suffisante pour interagir avec le bloc.
- `hitboxSurBloc(Joueur joueur, int x, int y)` : teste si la hitbox du joueur recouvre le bloc ciblé.

**Résumé du fonctionnement :**
- Toutes les méthodes sont statiques : la classe n’est jamais instanciée.
- Permet de centraliser la logique de casse/pose de blocs, en tenant compte des collisions, de la distance et de l’état de l’inventaire.
- Utilisée par le contrôleur pour gérer les interactions du joueur avec le monde.

---

## GestionnaireDeplacement

Classe utilitaire gérant le déplacement horizontal continu du joueur via une boucle d’animation.

Gère :
- L’état de déplacement vers la gauche et la droite.
- L’appel répété aux méthodes de déplacement du joueur (`deplacerGauche()`, `deplacerDroite()`) tant que la touche est maintenue.
- L’arrêt du mouvement horizontal lorsque plus aucune direction n’est active.

**Principaux attributs :**
- `boolean enDeplacementGauche` : indique si le joueur se déplace vers la gauche.
- `boolean enDeplacementDroite` : indique si le joueur se déplace vers la droite.
- `Joueur joueur` : référence vers le joueur à déplacer.
- `AnimationTimer boucleDeplacement` : boucle d’animation qui gère le déplacement continu.

**Méthodes principales :**
- `GestionnaireDeplacement(Joueur joueur)` : constructeur, initialise la boucle d’animation.
- `setEnDeplacementGauche(boolean actif)` : active/désactive le déplacement à gauche.
- `setEnDeplacementDroite(boolean actif)` : active/désactive le déplacement à droite.
- `stop()` : arrête la boucle d’animation.
- `isEnDeplacementGauche()` / `isEnDeplacementDroite()` : accesseurs des états de déplacement.

**Résumé du fonctionnement :**
- La classe gère le déplacement fluide du joueur en fonction des entrées clavier.
- Utilise une boucle d’animation (`AnimationTimer`) pour appeler en continu les méthodes de déplacement tant que la direction est active.
- Arrête le mouvement horizontal du joueur si aucune direction n’est maintenue.
- Permet de séparer la logique de déplacement du reste du contrôleur.

---

## GestionnaireInventaire

Classe utilitaire gérant l’initialisation et la synchronisation de l’inventaire du joueur avec l’interface graphique.

Gère :
- La création et l’initialisation de l’inventaire du joueur avec des objets de départ.
- L’affichage de l’inventaire dans l’interface (`VueInventaire`), positionné dans la fenêtre de jeu.
- La synchronisation entre l’inventaire et l’objet tenu par le joueur dans la vue (`VueJoueur`).
- L’écoute des changements de sélection ou de contenu de l’inventaire pour mettre à jour la vue du joueur.

**Principaux attributs :**
- `Inventaire inventaire` : l’inventaire du joueur.
- `VueInventaire vueInventaire` : la vue graphique de l’inventaire.
- `VueJoueur joueurVue` : la vue graphique du joueur.
- `AnchorPane rootPane` : le conteneur principal de la scène.

**Méthodes principales :**
- `GestionnaireInventaire(AnchorPane rootPane, VueJoueur joueurVue)` : constructeur, crée l’inventaire et la vue.
- `initialiserInventaire()` : ajoute les objets de départ, place la vue dans l’interface, synchronise la sélection et les changements d’inventaire avec la vue du joueur.
- `getInventaire()` : retourne l’inventaire.
- `getVueInventaire()` : retourne la vue de l’inventaire.

**Résumé du fonctionnement :**
- Permet de centraliser la gestion de l’inventaire et son affichage, ainsi que la synchronisation avec l’objet actuellement tenu par le joueur.
- Utilisé par le contrôleur pour initialiser et mettre à jour l’inventaire et la vue associée lors des interactions du joueur.

---

## GestionnaireItem

Classe utilitaire gérant la gestion, l’affichage et la physique des items au sol (objets lâchés ou générés dans le monde).

Gère :
- Le suivi de tous les items présents au sol et leur représentation graphique (`VueItem`).
- L’application de la gravité sur les items pour qu’ils tombent naturellement.
- La détection de collision avec le sol pour arrêter la chute des items.
- La détection du ramassage d’un item par le joueur (collision avec la hitbox du joueur).
- L’ajout de l’item ramassé à l’inventaire du joueur et la mise à jour de l’interface.
- Le spawn d’un nouvel item au sol à une position donnée.

**Principaux attributs :**
- `List<Item> itemsAuSol` : liste des items actuellement au sol.
- `List<VueItem> vuesItemsAuSol` : liste des vues graphiques associées aux items au sol.
- `AnchorPane rootPane` : conteneur principal de la scène pour l’affichage des items.

**Méthodes principales :**
- `GestionnaireItem(AnchorPane rootPane)` : constructeur, initialise le gestionnaire avec le conteneur graphique.
- `update(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire)` : met à jour la position des items, applique la gravité, gère les collisions et le ramassage.
- `spawnItemAuSol(Item item, int x, int y)` : fait apparaître un item au sol à la position spécifiée.
- (privées) `appliquerGravite(Item item)` : applique la gravité à l’item.
- (privées) `gererCollisionSol(Item item)` : gère la collision de l’item avec le sol.
- (privées) `detecterRamassage(Item item, Joueur joueur)` : détecte si le joueur ramasse l’item.

**Résumé du fonctionnement :**
- Centralise la gestion de la physique et de l’affichage des items au sol.
- Permet de faire tomber les items, de les afficher, et de gérer leur ramassage par le joueur.
- Utilisé par le contrôleur pour gérer les objets lâchés, les récompenses de casse de bloc, etc.

---

## GestionnaireMort

Classe utilitaire gérant la détection de la mort du joueur et l’affichage de l’écran de mort.

Gère :
- La vérification de l’état de vie du joueur.
- L’arrêt de la musique et des sons lors de la mort.
- L’arrêt de la boucle de jeu (`AnimationTimer`).
- La désactivation des contrôles clavier et souris.
- L’affichage de l’écran de mort via un overlay FXML.

**Principaux attributs :**
- `Joueur joueurModele` : le modèle du joueur.
- `Vie vie` : l’objet représentant la vie du joueur.
- `MusiqueManager musiqueManager` : gestionnaire de la musique de fond.
- `AnchorPane rootPane` : conteneur principal de la scène.
- `GestionnaireControles gestionnaireControles` : gestionnaire des contrôles clavier/souris.
- `VueCarte vueCarte` : vue graphique de la carte.

**Méthodes principales :**
- `GestionnaireMort(Joueur joueurModele, Vie vie, MusiqueManager musiqueManager, AnchorPane rootPane, GestionnaireControles gestionnaireControles, VueCarte vueCarte)` : constructeur, initialise les références nécessaires.
- `gererMort(AnimationTimer gameLoop)` : vérifie si le joueur est mort, arrête la musique, les sons, la boucle de jeu, désactive les contrôles et affiche l’écran de mort.
- (privée) `afficherEcranDeMort()` : charge et affiche l’overlay de mort.

**Résumé du fonctionnement :**
- Centralise la gestion de la mort du joueur : arrêt du jeu, sons, contrôles et affichage de l’écran de mort.
- Utilisée par le contrôleur principal pour détecter la mort et afficher l’interface correspondante.

---

## GestionnaireSon

Classe utilitaire gérant les alertes sonores liées à l’état de vie du joueur.

Gère :
- La détection d’un état de vie basse du joueur.
- Le déclenchement d’une alerte sonore lorsque la vie du joueur est faible.
- L’arrêt de l’alerte sonore lorsque la vie redevient normale.

**Principaux attributs :**
- `Joueur joueurModele` : référence vers le modèle du joueur.
- `boolean sonDegatJoue` : indique si l’alerte sonore est en cours de lecture.

**Méthodes principales :**
- `GestionnaireSon(Joueur joueurModele)` : constructeur, initialise la référence au joueur.
- `gererAlerteVieBasse()` : joue ou arrête l’alerte sonore selon l’état de vie du joueur.

**Résumé du fonctionnement :**
- Surveille en continu la vie du joueur.
- Joue une alerte sonore si la vie est basse, et arrête l’alerte si la vie remonte.
- Utilisé par le contrôleur pour fournir un retour audio en cas de danger.

---

## GestionnaireVie

Classe utilitaire centralisant la gestion de la vie du joueur, des dégâts, des alertes sonores et de la mort.

Gère :
- La vérification et l’application des dégâts subis par le joueur.
- Le déclenchement d’une alerte sonore lorsque la vie du joueur est basse.
- La détection de la mort du joueur et l’arrêt du jeu.

**Principaux attributs :**
- `Joueur joueurModele` : référence vers le modèle du joueur.
- `GestionnaireSon gestionnaireSon` : gestionnaire des alertes sonores liées à la vie.
- `GestionnaireMort gestionnaireMort` : gestionnaire de la mort du joueur.
- `Vie vie` : objet représentant la vie du joueur.

**Méthodes principales :**
- `GestionnaireVie(Joueur joueurModele, GestionnaireSon gestionnaireSon, GestionnaireMort gestionnaireMort, Vie vie)` : constructeur, initialise les références nécessaires.
- `mettreAJour(AnimationTimer gameLoop)` : vérifie les dégâts, joue l’alerte sonore si besoin, gère la mort du joueur.

**Résumé du fonctionnement :**
- Centralise la gestion de la vie du joueur : dégâts, alertes et mort.
- Appelée à chaque boucle de jeu pour mettre à jour l’état de la vie, jouer les sons d’alerte et arrêter le jeu si le joueur meurt.
- Permet de séparer la logique de gestion de la vie du reste du contrôleur.