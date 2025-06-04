# Gestionnaires utilitaires (`utils`)

Ce dossier contient les classes utilitaires transverses, indépendantes du modèle métier, qui fournissent des services génériques : gestion de l’audio, chargement de ressources, constantes, etc.

---

## AudioManager

Singleton utilitaire centralisant la gestion des effets sonores et musiques du jeu.

Gère :
- Le chargement et la lecture des fichiers audio (effets et musiques).
- Le réglage du volume des effets sonores.
- La lecture d’un son ponctuel ou en boucle.
- L’arrêt de tous les sons en cours.
- La gestion des sons spécifiques (casse de bloc, alerte vie basse, mort).

**Principaux attributs :**
- `static AudioManager instance` : instance unique du singleton.
- `double volumeEffet` : volume global des effets sonores.
- `Map<Integer, String> sonsCasseBloc` : mapping des IDs de blocs vers les chemins des sons de casse.
- `List<MediaPlayer> mediaPlayersEnCours` : liste des lecteurs audio actifs.

**Méthodes principales :**
- `static AudioManager getInstance()` : retourne l’instance unique.
- `void setVolumeEffet(double volumeEffet)` : modifie le volume des effets.
- `void jouerSon(String chemin)` : joue un son ponctuel.
- `void jouerSonEnBoucle(String chemin)` : joue un son en boucle.
- `void jouerSonCasseBloc(int blocId)` : joue le son associé à la casse d’un bloc.
- `void jouerAlerteVieBasse()` : joue l’alerte sonore de vie basse en boucle.
- `void jouerAlerteMort()` : joue le son de mort.
- `void arreterTousLesSons()` : arrête et libère tous les sons en cours.

**Résumé du fonctionnement :**
- Centralise la gestion de l’audio pour tout le jeu.
- Permet de jouer, boucler ou arrêter des sons facilement.
- Utilisé par les gestionnaires de vie, mort, et le contrôleur principal pour tous les retours audio.

---

## MusiqueManager

Singleton utilitaire centralisant la gestion de la musique de fond du jeu.

Gère :
- Le chargement et la lecture des musiques de fond.
- Le réglage du volume de la musique.
- La lecture en boucle ou ponctuelle d’une musique.
- La pause, la reprise et l’arrêt de la musique en cours.

**Principaux attributs :**
- `static MusiqueManager instance` : instance unique du singleton.
- `MediaPlayer mediaPlayer` : lecteur de la musique en cours.
- `double volume` : volume global de la musique.
- `double volumeEffet` : volume des effets (non utilisé ici, mais présent).
  
**Méthodes principales :**
- `static MusiqueManager getInstance()` : retourne l’instance unique.
- `void setVolume(double volume)` : modifie le volume de la musique.
- `void jouerMusiqueEnBoucle(String chemin)` : joue une musique en boucle.
- `void jouerMusique(String chemin, int repetitions)` : joue une musique un certain nombre de fois.
- `void pauseMusique()` : met la musique en pause.
- `void reprendreMusique()` : reprend la lecture si la musique est en pause.
- `void arreterMusique()` : arrête la musique en cours.
- `void jouerMusiqueFond()` : joue la musique de fond principale en boucle.

**Résumé du fonctionnement :**
- Centralise la gestion de la musique de fond pour tout le jeu.
- Permet de lancer, mettre en pause, reprendre ou arrêter la musique facilement.
- Utilisé par les gestionnaires de mort, de vie et le contrôleur principal pour piloter l’ambiance sonore.

---

## Chemin

Classe utilitaire centralisant tous les chemins d’accès aux ressources du jeu (images, sons, musiques, animations, FXML).

Gère :
- Les chemins relatifs des images de blocs, décors, items, UI, fonds, boutons, etc.
- Les chemins des fichiers audio (sons d’effets, musiques).
- Les chemins des fichiers FXML et animations.

**Principaux attributs :**
- Un ensemble de constantes `public static final String` pour chaque ressource : images, sons, musiques, animations, FXML.

**Résumé du fonctionnement :**
- Fournit un point unique pour référencer toutes les ressources du projet.
- Permet de modifier facilement les chemins sans impacter le reste du code.
- Utilisé par les gestionnaires d’audio, de ressources, et l’UI pour charger dynamiquement les fichiers nécessaires.

---

## Constantes

Classe utilitaire centralisant toutes les constantes globales du jeu (dimensions, tuiles, probabilités, gameplay, interface).

Gère :
- Les dimensions de la fenêtre et des éléments graphiques.
- Les paramètres de la carte (nombre de lignes, colonnes, couches, taille des tuiles).
- Les probabilités et paramètres de génération de la carte.
- Les constantes liées au joueur (gravité, vitesse, force de saut).
- Les tailles d’items et d’éléments d’interface.

**Résumé du fonctionnement :**
- Fournit un point unique pour toutes les constantes utilisées dans le projet.
- Permet de modifier facilement les paramètres globaux sans impacter la logique métier.
- Utilisé par la génération de carte, l’UI, le modèle et les gestionnaires pour garantir la cohérence des valeurs.

---

## DebugManager

Gestionnaire utilitaire facilitant l’affichage d’informations de débogage et de superpositions graphiques pour le développement.

Gère :
- L’affichage d’une grille de débogage sur la carte.
- L’affichage des hitbox du joueur (et potentiellement des mobs).
- Le basculement de la visibilité des éléments de debug.
- La mise à jour dynamique des superpositions de debug à chaque frame.

**Principaux attributs :**
- `AnchorPane rootPane` : conteneur principal de la scène.
- `Joueur joueur` : référence au joueur pour afficher sa hitbox.
- `Mob mob` : référence à un mob pour afficher sa hitbox (optionnel).
- `Canvas debugCanvas` : canevas pour la grille de debug.
- `Canvas hitboxCanvas` : canevas pour les hitbox.
- `boolean debugVisible` : état de visibilité du debug.

**Méthodes principales :**
- `void toggle()` : affiche ou masque les superpositions de debug.
- `void update()` : met à jour l’affichage des hitbox si le debug est actif.
- `boolean isDebugVisible()` : indique si le mode debug est actif.

**Résumé du fonctionnement :**
- Permet d’activer ou désactiver dynamiquement l’affichage d’éléments de debug (grille, hitbox).
- Facilite le développement et le test en visualisant les zones de collision et la structure de la carte.
- Utilisé uniquement en phase de développement ou pour le diagnostic.

---

## DebugOverlay

Classe utilitaire générant des superpositions graphiques de débogage pour la carte du jeu.

Gère :
- La génération d’une grille de debug superposée à la carte.
- La coloration des tuiles contenant des blocs solides pour visualiser leur emplacement.

**Méthodes principales :**
- `static Canvas genererGrille(Carte carte)` : génère un canevas affichant la grille et surlignant les blocs solides.

**Résumé du fonctionnement :**
- Permet de visualiser la structure de la carte et l’emplacement des blocs solides pendant le développement.
- Utilisé par le `DebugManager` pour afficher dynamiquement la grille de debug.

---

## GestionnaireAnimation

Gestionnaire utilitaire centralisant l’ajout et la gestion d’animations sur les éléments graphiques du jeu.

Gère :
- L’ajout d’animations à une cellule graphique (`Pane`) à partir d’une séquence d’images ou d’une planche de sprites.
- Le découpage d’une planche de sprites en plusieurs frames pour créer une animation.
- Le rafraîchissement automatique de l’image affichée selon la durée de chaque frame.

**Méthodes principales :**
- `static void ajouterAnimation(Pane cellule, String cheminAnimation, int nbFrames, int frameDurationMillis)` : ajoute une animation à un conteneur graphique, à partir d’un dossier ou d’un préfixe de fichiers.
- `static Image[] decouperSpriteSheet(Image plancheSprite, int largeurFrame, int hauteurFrame, int nbFrames)` : découpe une planche de sprites en un tableau d’images (frames).

**Résumé du fonctionnement :**
- Permet d’animer dynamiquement des éléments du jeu (explosions, personnages, blocs) en affichant successivement différentes images.
- Utilisé pour les animations d’explosion, de personnages ou de blocs, en complément des classes spécialisées pour les cas particuliers.

---

## GestionnaireImage

Gestionnaire utilitaire centralisant l’association entre les objets du jeu (blocs, items) et leurs chemins d’images.

Gère :
- Le mapping entre le nom d’un bloc ou d’un item et le chemin de son image correspondante.
- La récupération dynamique du chemin d’image à partir d’un objet `Item`.

**Principaux attributs :**
- `private static final Map<String, String> itemToImage` : dictionnaire associant le nom d’un bloc/item à son chemin d’image.

**Méthodes principales :**
- `static String getCheminImage(Item item)` : retourne le chemin d’image correspondant à l’item donné, ou une image par défaut si non trouvé.

**Résumé du fonctionnement :**
- Permet de centraliser et de modifier facilement l’association entre objets et images.
- Utilisé par l’UI et les gestionnaires de ressources pour afficher dynamiquement les images des blocs et items.

---

## Loader

Gestionnaire utilitaire centralisant le chargement des ressources du jeu (images, sons, FXML, animations).

Gère :
- Le chargement sécurisé des images (`Image`), sons (`Media`), fichiers FXML et vidéos MP4.
- Le fallback automatique vers une ressource par défaut en cas d’erreur de chargement.
- Le chargement d’animations à partir d’une séquence d’images nommées de façon incrémentale.

**Méthodes principales :**
- `static URL getResource(String path)` : retourne l’URL d’une ressource, lève une exception si non trouvée.
- `static Image loadImage(String path)` : charge une image, retourne une image par défaut si non trouvée.
- `static Media loadMP3(String path)` : charge un fichier audio MP3, retourne un son d’erreur si non trouvé.
- `static FXMLLoader loadFXML(String path)` : charge un fichier FXML.
- `static <T> T load(String path)` : charge et retourne la racine d’un FXML, affiche une alerte en cas d’échec.
- `static Media getMP4(String path)` : charge une vidéo MP4, lève une exception si non trouvée.
- `static Image[] loadAnimation(String cheminDeBase, int nbFrames)` : charge une séquence d’images pour une animation.

**Résumé du fonctionnement :**
- Centralise le chargement de toutes les ressources nécessaires à l’UI et à l’audio.
- Gère les erreurs de chargement de façon robuste (fallback ou alerte).
- Utilisé par les gestionnaires d’images, d’animations, d’audio et les contrôleurs d’interface.