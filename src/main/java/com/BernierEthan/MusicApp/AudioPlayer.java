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
import javafx.util.Duration;

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

    /**
     * Action à exécuter quand la musique en cours se termine
     * (par exemple passer à la suivante), ou {@code null} si aucune action n'est prévue.
     */
    private Runnable onEnd;
    /**
     * Indique si une musique est en train de jouer (lancée et ni en pause, ni arrêtée).
     */
    private boolean playing = false;


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
        if (onEnd != null){
            mediaPlayer.setOnEndOfMedia(onEnd);
        }

        mediaPlayer.play();
        playing = true;
    }

    /**
     * Arrête la musique en cours et revient à son début.
     * Ne fait rien si aucune musique n'a été lancée.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        playing = false;
    }

    /**
     * Met la musique en cours en pause, sans perdre sa position.
     * Ne fait rien si aucune musique n'a été lancée.
     */
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        playing = false;
    }

    /**
     * Reprend la lecture de la musique là où elle s'était arrêtée.
     * Ne fait rien si aucune musique n'a été lancée.
     */
    public void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            playing = true;
        }
    }

    /**
     * Indique si une musique est en train de jouer.
     *
     * @return {@code true} si une musique joue, {@code false} si elle est en pause,
     *         arrêtée ou si aucune musique n'a été lancée
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Renvoie le volume actuel.
     *
     * @return le volume, entre 0.0 (muet) et 1.0 (maximum)
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Change le volume, immédiatement pour la musique en cours et pour les suivantes.
     *
     * @param volume le nouveau volume, entre 0.0 (muet) et 1.0 (maximum)
     */
    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Définit l'action à exécuter quand une musique se termine.
     * Elle s'applique aux musiques lancées après cet appel.
     *
     * @param onEnd l'action à exécuter, ou {@code null} pour n'en exécuter aucune
     */
    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }

    /**
     * Donne la position de lecture dans la musique en cours.
     *
     * @return le temps écoulé en secondes, ou 0 si aucune musique n'a été lancée
     */
    public double getCurrentSeconds(){
        if (mediaPlayer == null){
            return 0;
        }
        return mediaPlayer.getCurrentTime().toSeconds();
    }

    /**
     * Donne la durée totale de la musique en cours.
     * La durée reste inconnue tant que JavaFX n'a pas fini de lire le fichier.
     *
     * @return la durée en secondes, ou 0 si aucune musique n'a été lancée
     *         ou si sa durée n'est pas encore connue
     */
    public double getDurationSeconds(){
        if (mediaPlayer == null){
            return 0;
        }
        Duration total = mediaPlayer.getTotalDuration();
        if (total.isUnknown() || total.isIndefinite()){
            return 0;
        }
        return total.toSeconds();
    }

    /**
     * Déplace la lecture de la musique en cours à la position donnée.
     * Ne fait rien si aucune musique n'a été lancée.
     *
     * @param seconds la position visée, en secondes depuis le début de la musique
     */
    public void seek(double seconds){
        if (mediaPlayer == null){
            return;
        }
        mediaPlayer.seek(Duration.seconds(seconds));
    }
}

