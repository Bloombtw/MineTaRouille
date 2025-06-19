# Organisation des ressources

Ce dossier contient toutes les ressources nécessaires au fonctionnement graphique et sonore du jeu.  
Les ressources sont classées par type pour faciliter la maintenance et l’accès rapide.

---

## Structure des dossiers

- `fxml` : fichiers d’interface graphique (scènes, menus, etc.).
- `img` : toutes les images du jeu, organisées en sous-dossiers :
  - `animations` : spritesheets et images d’animation.
  - `Blocs` : images des blocs, séparées en :
    - `solide` : blocs non traversables.
    - `traversable` : blocs traversables par le joueur.
  - `boutons` : images des boutons de l’interface.
  - `decors` : éléments de décor du jeu.
  - `fond` : images de fond.
  - `item` : icônes ou sprites des objets.
  - `ui` : éléments graphiques de l’interface utilisateur.
- `mp3` : fichiers audio (musiques, effets sonores).
- `mp4` : vidéos utilisées dans le jeu.

---

## Résumé

Cette organisation permet de séparer clairement chaque type de ressource selon son usage (graphique, audio, vidéo, interface), ce qui simplifie le développement et la gestion du projet.