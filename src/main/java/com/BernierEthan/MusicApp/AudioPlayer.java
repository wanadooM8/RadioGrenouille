/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : AudioPlayer.java
 * Last Modified : 2026-10-08, 11:28PM
 */

package com.BernierEthan.MusicApp;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Lecteur audio : joue une musique à la fois et permet de la mettre en pause,
 * de la reprendre, de l'arrêter et de régler le volume.
 */
public class AudioPlayer {
    // Attributs de la classe

    /**
     * Lecteur JavaFX de la musique en cours, ou {@code null} si aucune musique n'a été lancée.
     */
    private MediaPlayer mediaPlayer = null;
    /**
     * Volume appliqué à chaque nouvelle musique, entre 0.0 (muet) et 1.0 (maximum).
     */
    private double volume = 0.5;


    // Méthodes

    /**
     * Joue une musique depuis le début.
     * Si une musique est déjà en cours, elle est arrêtée et son lecteur est libéré
     * avant de lancer la nouvelle.
     *
     * @param track la musique à jouer
     */
    public void play(Track track) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        Media media = new Media(track.file().toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(this.volume);
        mediaPlayer.play();
    }

    /**
     * Arrête la musique en cours et revient à son début.
     * Ne fait rien si aucune musique n'a été lancée.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Met la musique en cours en pause, sans perdre sa position.
     * Ne fait rien si aucune musique n'a été lancée.
     */
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Reprend la lecture de la musique là où elle s'était arrêtée.
     * Ne fait rien si aucune musique n'a été lancée.
     */
    public void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    /**
     * Change le volume utilisé pour les musiques immédiatement.
     *
     * @param volume le nouveau volume, entre 0.0 (muet) et 1.0 (maximum)
     */
    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }
}
