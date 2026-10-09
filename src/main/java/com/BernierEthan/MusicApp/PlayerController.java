/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : PlayerController.java
 * Last Modified : 2026-10-09, 12:04AM
 */

package com.BernierEthan.MusicApp;

import javafx.scene.media.MediaPlayer;

public class PlayerController {

    private AudioPlayer audioPlayer;
    private Playlist playlist;

    public PlayerController(Playlist playlist, AudioPlayer audioPlayer) {
        this .playlist = playlist;
        this.audioPlayer = audioPlayer;
    }

    public void play() {
        if (playlist.current() != null) {
            audioPlayer.play(playlist.current());
        }
    }

    public void playNext() {
        if (playlist.next()) {
            audioPlayer.play(playlist.current());
        }
    }

    public void playPrevious() {
        if (playlist.previous()) {
            audioPlayer.play(playlist.current());
        }
    }

    public void setVolume(double volume) {
        audioPlayer.setVolume(volume);
    }

    public void pause() {
        audioPlayer.pause();
    }

    public void resume() {
        audioPlayer.resume();
    }

}
