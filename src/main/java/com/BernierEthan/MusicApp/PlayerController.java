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
     * sinon, passe à la suivante. Sur la dernière musique sans répétition,
     * arrête le lecteur.
     */
    private void onTrackEnded() {
        if (playlist.getRepeatMode() == RepeatMode.ONE) {
            play();
        } else if (playlist.next()) {
            audioPlayer.play(playlist.current());
        } else {
            // Fin de la playlist sans répétition : on s'arrête pour que le bouton repasse sur « lecture »
            audioPlayer.stop();
        }
    }

    /**
     * Met en pause si une musique joue, sinon reprend la lecture.
     * Ne fait rien si la playlist est vide.
     */
    public void togglePlayPause() {
        if (audioPlayer.isPlaying()) {
            pause();
        } else {
            resume();
        }
    }

    /**
     * Indique si une musique est en train de jouer.
     *
     * @return {@code true} si une musique joue, {@code false} sinon
     */
    public boolean isPlaying() {
        return audioPlayer.isPlaying();
    }

    /**
     * Renvoie la musique courante de la playlist.
     *
     * @return la musique courante, ou {@code null} si la playlist est vide
     */
    public Track getCurrentTrack() {
        return playlist.current();
    }

    /**
     * Renvoie le volume actuel du lecteur audio.
     *
     * @return le volume, entre 0.0 (muet) et 1.0 (maximum)
     */
    public double getVolume() {
        return audioPlayer.getVolume();
    }

    /**
     * Indique si la lecture aléatoire est activée.
     *
     * @return {@code true} si l'aléatoire est actif, {@code false} sinon
     */
    public boolean isShuffle() {
        return playlist.isShuffle();
    }

    /**
     * Renvoie le mode de répétition actuel.
     *
     * @return le mode de répétition de la playlist
     */
    public RepeatMode getRepeatMode() {
        return playlist.getRepeatMode();
    }

    /**
     * Active la lecture aléatoire si elle est désactivée, et inversement.
     * La musique en cours ne change pas.
     */
    public void toggleShuffle(){
        playlist.setShuffle(!playlist.isShuffle());
    }

    /**
     * Passe au mode de répétition suivant, dans l'ordre de {@link RepeatMode} :
     * {@link RepeatMode#OFF}, puis {@link RepeatMode#ALL}, puis {@link RepeatMode#ONE},
     * puis de nouveau {@link RepeatMode#OFF}.
     */
    public void cycleRepeatMode(){
        RepeatMode[] modes = RepeatMode.values();
        int next = (playlist.getRepeatMode().ordinal() + 1) % modes.length;
        playlist.setRepeatMode(modes[next]);
    }

    /**
     * Donne la position de lecture dans la musique en cours.
     *
     * @return le temps écoulé en secondes, ou 0 si aucune musique n'a été lancée
     */
    public double getCurrentSeconds(){
        return audioPlayer.getCurrentSeconds();
    }

    /**
     * Donne la durée totale de la musique en cours.
     *
     * @return la durée en secondes, ou 0 si elle n'est pas encore connue
     */
    public double getDurationSeconds(){
        return audioPlayer.getDurationSeconds();
    }

    /**
     * Déplace la lecture de la musique en cours à la position donnée.
     *
     * @param seconds la position visée, en secondes depuis le début de la musique
     */
    public void seek(double seconds){
        audioPlayer.seek(seconds);
    }
}
