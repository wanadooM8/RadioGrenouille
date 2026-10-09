/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : Track.java
 * Last Modified : 2026-10-08, 02:50AM
 */

package com.BernierEthan.MusicApp;

import java.nio.file.Path;

/**
 * Un morceau de musique : son fichier et ses informations.
 *
 * @param file   le chemin du fichier audio
 * @param title  le titre de la musique
 * @param artist le nom de l'artiste
 * @param album  le nom de l'album
 */
public record Track(Path file, String title, String artist, String album) {
}
