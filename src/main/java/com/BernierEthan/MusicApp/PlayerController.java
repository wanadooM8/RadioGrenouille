/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : PlayerController.java
 * Last Modified : 2026-10-09, 12:04AM
 */

package com.BernierEthan.MusicApp;

/**
 * Contrôleur du lecteur : fait le lien entre la playlist et le lecteur audio.
 * Les boutons de l'interface appellent ses méthodes, qui choisissent la musique
 * dans la {@link Playlist} puis la font jouer par l'{@link AudioPlayer}.
 */
public class PlayerController {

    /**
     * Lecteur audio qui joue réellement les musiques.
     */
    private final AudioPlayer audioPlayer;
    /**
     * Playlist qui fournit la musique courante, la suivante et la précédente.
     */
    private final Playlist playlist;

    /**
     * Crée un contrôleur qui pilote le lecteur audio à partir de la playlist donnée.
     *
     * @param playlist    la playlist des musiques à jouer
     * @param audioPlayer le lecteur audio qui joue les musiques
     */
    public PlayerController(Playlist playlist, AudioPlayer audioPlayer) {
        this.playlist = playlist;
        this.audioPlayer = audioPlayer;
        audioPlayer.setOnEnd(this::onTrackEnded);
    }

    /**
     * Joue la musique courante de la playlist depuis le début.
     * Ne fait rien si la playlist est vide.
     */
    public void play() {
        if (playlist.current() != null) {
            audioPlayer.play(playlist.current());
        }
    }

    /**
     * Passe à la musique suivante de la playlist et la joue.
     * Ne fait rien si la playlist n'a pas pu avancer
     * (dernière musique sans répétition, ou playlist vide).
     */
    public void playNext() {
        if (playlist.next()) {
            audioPlayer.play(playlist.current());
        }
    }

    /**
     * Revient à la musique précédente de la playlist et la joue.
     * Si la musique courante est la première, elle est relancée depuis le début.
     * Ne fait rien si la playlist est vide.
     */
    public void playPrevious() {
        playlist.previous();
        play();
    }

    /**
     * Change le volume du lecteur audio.
     *
     * @param volume le nouveau volume, entre 0.0 (muet) et 1.0 (maximum)
     */
    public void setVolume(double volume) {
        audioPlayer.setVolume(volume);
    }

    /**
     * Met la musique en cours en pause, sans perdre sa position.
     */
    public void pause() {
        audioPlayer.pause();
    }

    /**
     * Reprend la lecture de la musique en cours là où elle s'était arrêtée.
     */
    public void resume() {
        audioPlayer.resume();
    }

    /**
     * Appelée par le lecteur audio quand la musique en cours se termine.
     * En mode {@link RepeatMode#ONE}, relance la même musique ;
     * sinon, passe à la suivante.
     */
    private void onTrackEnded() {
        if (playlist.getRepeatMode() == RepeatMode.ONE) {
            play();
        } else {
            playNext();
        }
    }

}
