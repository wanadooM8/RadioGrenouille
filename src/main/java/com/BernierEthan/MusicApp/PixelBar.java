/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : PixelBar.java
 * Last Modified : 2026-10-12, 02:57AM
 */

package com.BernierEthan.MusicApp;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.function.DoubleConsumer;

/**
 * Barre horizontale en pixel art, dessinée avec des rectangles :
 * une bordure, une partie remplie, un curseur et une partie vide.
 * Sa valeur va de 0.0 (vide) à 1.0 (pleine). Un clic ou un glissement
 * change la valeur et prévient l'action donnée au constructeur.
 * Sert pour la barre de progression et pour la barre de volume.
 */
public class PixelBar extends Group {

    /**
     * Épaisseur de la bordure, en pixels.
     */
    private static final int BORDER = 2;

    /**
     * Largeur totale de la barre, bordure comprise.
     */
    private final int width;
    /**
     * Largeur du curseur.
     */
    private final int knobWidth;
    /**
     * Partie remplie, à gauche du curseur.
     */
    private final Rectangle fill;
    /**
     * Partie vide, à droite du curseur.
     */
    private final Rectangle empty;
    /**
     * Curseur (contour foncé).
     */
    private final Rectangle knob;
    /**
     * Centre clair du curseur, ou {@code null} si le curseur est uni.
     */
    private final Rectangle knobLight;
    /**
     * Action appelée avec la nouvelle valeur quand l'utilisateur clique ou glisse.
     */
    private final DoubleConsumer onUserChange;
    /**
     * Valeur actuelle, entre 0.0 et 1.0.
     */
    private double value = 0;
    /**
     * Indique si l'utilisateur est en train de glisser sur la barre.
     */
    private boolean dragging = false;

    /**
     * Crée une barre à la position donnée.
     *
     * @param x            l'abscisse du coin haut-gauche
     * @param y            l'ordonnée du coin haut-gauche
     * @param width        la largeur totale, bordure comprise
     * @param height       la hauteur totale, bordure comprise
     * @param knobWidth    la largeur du curseur
     * @param fillColor    la couleur de la partie remplie
     * @param knobLightColor la couleur du centre du curseur, ou {@code null} pour un curseur uni
     * @param onUserChange l'action appelée avec la nouvelle valeur (0.0 à 1.0) au clic ou au glissement
     */
    public PixelBar(int x, int y, int width, int height, int knobWidth,
                    Color fillColor, Color knobLightColor, DoubleConsumer onUserChange) {
        this.width = width;
        this.knobWidth = knobWidth;
        this.onUserChange = onUserChange;
        setLayoutX(x);
        setLayoutY(y);

        int innerHeight = height - 2 * BORDER;
        Rectangle border = pixelRect(0, 0, width, height, UiConstants.BAR_BORDER_COLOR);
        fill = pixelRect(BORDER, BORDER, 0, innerHeight, fillColor);
        empty = pixelRect(BORDER, BORDER, 0, innerHeight, UiConstants.BAR_EMPTY_COLOR);
        knob = pixelRect(BORDER, BORDER, knobWidth, innerHeight, UiConstants.DARK_COLOR);
        if (knobLightColor != null) {
            knobLight = pixelRect(BORDER, BORDER, knobWidth - 2 * BORDER, innerHeight, knobLightColor);
            getChildren().addAll(border, fill, empty, knob, knobLight);
        } else {
            knobLight = null;
            getChildren().addAll(border, fill, empty, knob);
        }
        setCursor(Cursor.HAND);
        update();

        setOnMousePressed(event -> {
            dragging = true;
            changeFromMouse(event);
        });
        setOnMouseDragged(this::changeFromMouse);
        setOnMouseReleased(event -> {
            dragging = false;
            event.consume();
        });
    }

    /**
     * Crée un rectangle net (sans anticrénelage) aux coordonnées données.
     *
     * @param x      l'abscisse du coin haut-gauche
     * @param y      l'ordonnée du coin haut-gauche
     * @param width  la largeur
     * @param height la hauteur
     * @param color  la couleur de remplissage
     * @return le rectangle créé
     */
    private static Rectangle pixelRect(int x, int y, int width, int height, Color color) {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(color);
        rectangle.setSmooth(false);
        return rectangle;
    }

    /**
     * Calcule la valeur à partir de la position de la souris, met à jour la barre
     * et prévient l'action.
     *
     * @param event l'événement souris (coordonnées locales à la barre)
     */
    private void changeFromMouse(MouseEvent event) {
        int innerWidth = width - 2 * BORDER;
        double newValue = (event.getX() - BORDER) / innerWidth;
        setValue(newValue);
        onUserChange.accept(value);
        event.consume(); // empêche le glissement de la fenêtre
    }

    /**
     * Change la valeur affichée, sans prévenir l'action.
     * La valeur est ramenée entre 0.0 et 1.0.
     *
     * @param newValue la nouvelle valeur
     */
    public void setValue(double newValue) {
        value = Math.clamp(newValue,0,1);
        update();
    }

    /**
     * Indique si l'utilisateur est en train de glisser sur la barre.
     * Pendant ce temps, la mise à jour automatique ne doit pas déplacer le curseur.
     *
     * @return {@code true} pendant un glissement, {@code false} sinon
     */
    public boolean isDragging() {
        return dragging;
    }

    /**
     * Replace la partie remplie, le curseur et la partie vide selon la valeur,
     * en pixels entiers pour rester net.
     */
    private void update() {
        int innerWidth = width - 2 * BORDER;
        int fillWidth = (int) Math.round(value * innerWidth);
        // Le curseur commence au bout de la partie remplie, sans sortir de la barre
        int knobX = Math.min(BORDER + fillWidth, BORDER + innerWidth - knobWidth);

        fill.setWidth(knobX - BORDER);
        knob.setX(knobX);
        if (knobLight != null) {
            knobLight.setX(knobX + BORDER);
        }
        empty.setX(knobX + knobWidth);
        empty.setWidth(BORDER + innerWidth - (knobX + knobWidth));
    }
}
