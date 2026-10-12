# 🐸 Radio Grenouille — Petite Mare

Un petit lecteur de musique en **pixel art**, fait avec **Java 27** et **JavaFX**.

C'est un projet personnel pour apprendre Java : la programmation orientée objet,
une interface graphique, la lecture audio et la création d'un `.exe`.

## Fonctionnalités

- Lecture des musiques du dossier `musique` (`.mp3`, `.wav`, `.m4a`, `.aac`), triées par nom
- Lecture / pause, morceau précédent / suivant
- Lecture aléatoire et répétition (désactivée, toute la playlist, un seul morceau)
- Barre de progression et barre de volume cliquables
- Statut « EN LECTURE » / « EN PAUSE »
- Fenêtre sans bordure, déplaçable à la souris, agrandie d'un facteur entier pour garder le pixel art net

## Lancer le projet

Il faut un **JDK 27** et **Maven**.

```bash
mvn javafx:run
```

Les musiques sont lues dans le dossier `musique/` à la racine du projet.

## Créer l'exécutable Windows

```bash
mvn clean package
jpackage --type app-image --name PetiteMare --app-version 1.0 \
  --input target/libs --main-jar MusicApp-1.0-SNAPSHOT.jar \
  --main-class com.BernierEthan.MusicApp.Launcher \
  --java-options -Dfile.encoding=UTF-8 --dest dist
```

L'application est créée dans `dist/PetiteMare/` (supprimer ce dossier avant de relancer `jpackage`).
Pour avoir de la musique, mettre un dossier `musique` à côté de `PetiteMare.exe`.

## Organisation du code

Le code est dans `src/main/java/com/BernierEthan/MusicApp/` et suit un découpage
modèle / vue / contrôleur :

| Classe | Rôle |
|---|---|
| `App` | Démarre JavaFX, charge la playlist et ouvre la fenêtre |
| `Launcher` | Point d'entrée de l'exe (lance `App`) |
| `Track` | Une musique (titre, artiste, album, fichier) |
| `Playlist` | La liste des musiques, l'ordre aléatoire et la répétition |
| `RepeatMode` | Les modes de répétition |
| `AudioPlayer` | Joue le son avec le `MediaPlayer` de JavaFX |
| `PlayerController` | Fait le lien entre l'interface, la playlist et le lecteur |
| `PlayerView` | Construit l'interface et la met à jour toutes les 100 ms |
| `PixelButton` / `PixelBar` | Boutons et barres en pixel art |
| `UiConstants` | Positions, couleurs, polices et noms des images |

Les images de l'interface sont dans `src/main/resources/images/`.

## Auteur

Ethan Bernier — 2026
