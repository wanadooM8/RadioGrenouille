/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : PixelButton.java
 * Last Modified : 2026-10-12, 02:57AM
 */

package com.BernierEthan.MusicApp;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Bouton en pixel art : une simple image cliquable.
 * Pendant l'appui, l'image descend de quelques pixels pour donner un effet d'enfoncement.
 * L'image peut être changée à tout moment (par exemple lecture / pause).
 */
public class PixelButton extends ImageView {

    /**
     * Action exécutée quand on clique sur le bouton.
     */
    private final Runnable action;

    /**
     * Crée un bouton à la position donnée.
     *
     * @param image  l'image du bouton
     * @param x      l'abscisse du coin haut-gauche
     * @param y      l'ordonnée du coin haut-gauche
     * @param action l'action à exécuter au clic
     */
    public PixelButton(Image image, int x, int y, Runnable action) {
        super(image);
        this.action = action;
        setSmooth(false);
        setLayoutX(x);
        setLayoutY(y);
        setCursor(Cursor.HAND);
        // Toute la case du bouton est cliquable, même ses coins transparents
        setPickOnBounds(true);

        setOnMousePressed(event -> {
            setTranslateY(UiConstants.PRESS_OFFSET);
            event.consume(); // empêche le glissement de la fenêtre
        });
        setOnMouseDragged(event -> event.consume());
        setOnMouseReleased(event -> {
            setTranslateY(0);
            // Le clic ne compte que si on relâche au-dessus du bouton
            if (getLayoutBounds().contains(event.getX(), event.getY())) {
                this.action.run();
            }
            event.consume();
        });
    }
}
