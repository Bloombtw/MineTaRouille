# MineTaRouille

MineTaRouille est un jeu JavaFX structuré autour d’une architecture MVC claire et modulaire.  
Ce dépôt contient le code source, les ressources et la documentation technique du projet.

---

## Structure du projet

- `src/main/java/`  
  - **lanceur** : point d’entrée de l’application (`MainApp`).
  - **modele** : classes du modèle (logique métier, entités du jeu).
  - **gestionnaires** : gestionnaires de logique (blocs, inventaire, vie, etc.).
  - **vue** : affichage graphique (vues du joueur, mobs, barre de vie, etc.).
  - **utils** : utilitaires (gestion d’images, animations, chargement, etc.).
  - **animationVue** : animations spécifiques à la vue (joueur, mobs, blocs).

- `src/main/resources/`  
  - **fxml** : interfaces graphiques.
  - **img** : images (animations, blocs, boutons, décors, fonds, items, UI).
  - **mp3** : musiques et sons.
  - **mp4** : vidéos.

Chaque package contient un fichier `.md` décrivant ses classes et responsabilités.

---

## Principaux composants

- **MainApp** : lance l’application, configure la fenêtre principale et charge l’écran d’accueil.
- **Modèle** : gère les entités du jeu (joueur, mobs, inventaire, vie…).
- **Gestionnaires** : centralisent la logique métier (blocs, inventaire, vie…).
- **Vues** : synchronisent l’affichage graphique avec le modèle (joueur, mobs, barre de vie…).
- **Utilitaires** : fournissent des outils communs (chargement d’images, gestion d’animations…).

---

## Documentation

Chaque dossier principal possède un fichier `.md` détaillant :
- Les rôles des classes.
- Les principaux attributs et méthodes.
- Les interactions entre composants.

La documentation locale facilite la maintenance et l’évolution du projet.

---

## Ressources

Les ressources sont organisées par type :
- **fxml** : interfaces graphiques.
- **img** : images (animations, blocs, UI…).
- **mp3** : sons et musiques.
- **mp4** : vidéos.

Voir `resources.md` pour le détail de l’organisation.

---

## Lancement

Le projet utilise Maven.  


## Contribution

Clonez le dépôt, modifiez les fichiers source et soumettez des pull requests pour contribuer.