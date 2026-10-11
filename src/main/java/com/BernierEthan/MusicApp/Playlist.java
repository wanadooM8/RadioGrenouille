/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : Playlist.java
 * Last Modified : 2026-10-08, 03:01AM
 */

package com.BernierEthan.MusicApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Représente une playlist : une liste ordonnée de musiques avec une position
 * de lecture courante, un mode aléatoire et un mode de répétition.
 */
public class Playlist {
    // Attributs de la classe

    /**
     * Liste des musiques de la playlist, dans l'ordre d'ajout.
     */
    private final List<Track> tracks = new ArrayList<Track>();
    /**
     * Ordre dans lequel seront jouées les musiques : chaque case contient un index
     * dans {@link #tracks}.
     */
    private final List<Integer> order = new ArrayList<Integer>();
    /**
     * Position de la musique en cours de lecture dans {@link #order}.
     */
    private int position = 0;
    /**
     * Indique si la lecture aléatoire est activée.
     */
    private boolean shuffle = false;
    /**
     * Mode de répétition de la playlist (désactivé par défaut).
     */
    private RepeatMode repeatMode = RepeatMode.OFF;


    // Méthodes

    /**
     * Ajoute une musique à la fin de la playlist.
     *
     * @param track la musique à ajouter
     */
    public void addTrack(Track track) {
        tracks.add(track);
        order.add(tracks.size()-1);
    }

    /**
     * Vérifie si la playlist est vide.
     *
     * @return {@code true} si la playlist ne contient aucune musique, {@code false} sinon
     */
    public boolean isEmpty() {
        return tracks.isEmpty();
    }

    /**
     * Donne le nombre de musiques dans la playlist.
     *
     * @return le nombre de musiques
     */
    public int size() {
        return tracks.size();
    }

    /**
     * Renvoie la musique à la position de lecture courante.
     *
     * @return la musique courante, ou {@code null} si la playlist est vide
     */
    public Track current() {
        if (isEmpty()) {
            return null;
        }
        return tracks.get(order.get(position));

    }

    /**
     * Passe à la musique suivante en avançant la position d'un cran.
     * Sur la dernière musique, revient à la première si la répétition est activée,
     * sinon ne fait rien. Ne fait rien non plus si la playlist est vide.
     *
     * @return {@code true} si la position a changé, {@code false} sinon
     */
    public boolean next() {
        if (isEmpty()) {
            return false;
        }
        if (position + 1 < tracks.size()) {
            position++;
            return true;
        }

        if (getRepeatMode() != RepeatMode.OFF) {
            position = 0;
            return true;
        }
        return false;
    }

    /**
     * Revient à la musique précédente en reculant la position d'un cran.
     * Ne fait rien si la playlist est vide ou si la musique courante est la première.
     *
     * @return {@code true} si la position a reculé, {@code false} sinon
     */
    public boolean previous() {
        if (isEmpty()) {
            return false;
        }
        if (position > 0) {
            position--;
            return true;
        }
        return false;
    }

    /**
     * Renvoie le mode de répétition actuel de la playlist.
     *
     * @return le mode de répétition ({@link RepeatMode#OFF} par défaut)
     */
    public RepeatMode getRepeatMode() {
        return repeatMode;
    }

    /**
     * Change le mode de répétition de la playlist.
     *
     * @param mode le nouveau mode de répétition : {@link RepeatMode#OFF},
     *             {@link RepeatMode#ALL} ou {@link RepeatMode#ONE}
     */
    public void setRepeatMode(RepeatMode mode) {
        this.repeatMode = mode;
    }

    /**
     * Charge tous les fichiers audio d'un dossier dans la playlist.
     * Remplace le contenu actuel, remet la position à 0 et désactive l'aléatoire.
     * Seuls les fichiers .mp3, .wav, .m4a et .aac sont gardés, triés par nom.
     *
     * @param folder le dossier contenant les musiques
     * @throws IOException si le dossier n'existe pas ou ne peut pas être lu
     */
    public void loadFolder(Path folder) throws IOException {
        // Réinitialisation de la playlist
        tracks.clear();
        position = 0;
        shuffle = false;
        order.clear();

        // Le try-with-resources ferme le flux automatiquement à la sortie du bloc
        try (Stream<Path> files = Files.list(folder)) {
            files
                    // 1. On ne garde que les fichiers audio (extension testée en minuscules)
                    .filter(path -> {
                        String name = path.getFileName().toString().toLowerCase();
                        return name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".m4a") || name.endsWith(".aac");
                    })
                    // 2. Tri par nom pour avoir un ordre stable
                    .sorted()
                    // 3. Un Track par fichier : titre = nom sans extension, album = nom du dossier
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();
                        addTrack(new Track(path, removeExtension(fileName), "Inconnu",
                                folder.getFileName().toString()));
                    });
        }
    }

    /**
     * Retire l'extension d'un nom de fichier ("Enfer.mp3" devient "Enfer").
     *
     * @param fileName le nom du fichier
     * @return le nom sans son extension, ou le nom inchangé s'il n'a pas de point
     */
    private String removeExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        if (dot == -1) {
            return fileName;
        }
        return fileName.substring(0, dot);
    }

    /**
     * Active ou désactive la lecture aléatoire, sans changer la musique en cours.
     * En activant, les autres musiques sont mélangées et la musique en cours passe
     * en tête de l'ordre de lecture. En désactivant, l'ordre d'ajout est rétabli et
     * la lecture reprend à la musique en cours.
     * Ne fait rien si le mode demandé est déjà actif ou si la playlist est vide.
     *
     * @param enable {@code true} pour activer l'aléatoire, {@code false} pour le désactiver
     */
    public void setShuffle(boolean enable){
        if (enable == shuffle || isEmpty()){
            return;
        }

        int currentIndex = order.get(position);
        this.shuffle = enable;

        if (enable){
            Collections.shuffle(order);
            order.remove(Integer.valueOf(currentIndex));
            order.addFirst(currentIndex);
            this.position = 0;
        } else {
            // Fonctionne car order contient exactement 0 à n-1, une fois chacun
            Collections.sort(order);
            this.position = currentIndex;
        }
    }

    /**
     * Indique si la lecture aléatoire est activée.
     *
     * @return {@code true} si l'aléatoire est actif, {@code false} sinon
     */
    public boolean isShuffle(){
        return shuffle;
    }
}

