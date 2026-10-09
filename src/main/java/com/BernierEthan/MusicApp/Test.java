/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : Test.java
 * Last Modified : 2026-10-08, 03:12AM
 */

package com.BernierEthan.MusicApp;

import java.nio.file.Path;

/**
 * Test manuel de la playlist : vérifie le passage à la musique suivante
 * selon le mode de répétition, en affichant les résultats dans la console.
 */
public class Test {

    /**
     * Crée une playlist de trois musiques et affiche le résultat de {@link Playlist#next()}
     * en mode {@link RepeatMode#OFF}, puis en mode {@link RepeatMode#ALL}.
     *
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        Track track1 = new Track(Path.of("a.mp3"),"Enfer","Stromae","album");
        Track track2 = new Track(Path.of("b.mp3"),"Quoi","Angele","album");
        Track track3 = new Track(Path.of("c.mp3"),"Papaoutai","Stromae","album");
        playlist.addTrack(track1);
        playlist.addTrack(track2);
        playlist.addTrack(track3);

        // Mode OFF : attendu true, true, false, false, et rester sur Papaoutai
        System.out.println("--- Mode OFF ---");
        System.out.println(playlist.current().title());
        for (int i = 0; i < 4; i++) {
            System.out.println(playlist.next() + " -> " + playlist.current().title());
        }

        // Mode ALL : attendu true, et revenir sur Enfer
        System.out.println("--- Mode ALL ---");
        playlist.setRepeatMode(RepeatMode.ALL);
        System.out.println(playlist.next() + " -> " + playlist.current().title());
    }
}
