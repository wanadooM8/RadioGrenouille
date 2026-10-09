/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : RepeatMode.java
 * Last Modified : 2026-10-08, 02:55AM
 */

package com.BernierEthan.MusicApp;

/**
 * Mode de répétition de la playlist, qui correspond aux états du bouton « Répéter ».
 */
public enum RepeatMode {
    /**
     * Pas de répétition : la lecture s'arrête après la dernière musique.
     */
    OFF,
    /**
     * Répète toute la playlist : après la dernière musique, on revient à la première.
     */
    ALL,
    /**
     * Répète la musique en cours en boucle.
     */
    ONE
}
