/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : PlayerView.java
 * Last Modified : 2026-10-12, 02:57AM
 */

package com.BernierEthan.MusicApp;

import static com.BernierEthan.MusicApp.UiConstants.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Interface du lecteur : construit tous les éléments (images, textes, boutons, barres)
 * aux positions de {@link UiConstants}, et les met à jour toutes les 100 ms.
 * Elle ne parle qu'au {@link PlayerController}, jamais directement à la playlist
 * ni au lecteur audio.
 */
public class PlayerView extends Pane {
    /**
     * Logger permettant les messages d'erreurs.
     */
    static Logger logger = Logger.getLogger(PlayerView.class.getName());
    /**
     * Contrôleur qui pilote la musique.
     */
    private final PlayerController controller;
    /**
     * Fenêtre affichant l'interface (pour la déplacer, la réduire).
     */
    private final Stage stage;

    /**
     * Titre du morceau en cours.
     */
    private final Text titleText;
    /**
     * Ligne « artiste · album » du morceau en cours.
     */
    private final Text infoText;
    /**
     * Temps écoulé, à gauche sous la barre de progression.
     */
    private final Text elapsedText;
    /**
     * Durée totale, à droite sous la barre de progression.
     */
    private final Text durationText;
    /**
     * Pourcentage du volume.
     */
    private final Text volumeText;
    /**
     * Barre de progression du morceau.
     */
    private final PixelBar progressBar;
    /**
     * Barre de volume.
     */
    private final PixelBar volumeBar;
    /**
     * Gros bouton lecture / pause.
     */
    private final PixelButton playButton;
    /**
     * Bouton aléatoire.
     */
    private final PixelButton shuffleButton;
    /**
     * Bouton répétition.
     */
    private final PixelButton repeatButton;
    /**
     * Texte « EN LECTURE » / « EN PAUSE » en bas de la carte.
     */
    private final ImageView statusView;

    /**
     * Nombre de vrais pixels de l'écran pour un pixel du dessin (1, 2 ou 3).
     * Les images sont agrandies de ce facteur dès leur chargement.
     */
    private final int pixelScale;

    /** Image du bouton lecture. */
    private final Image playImage;
    /** Image du bouton pause. */
    private final Image pauseImage;
    /** Image du bouton aléatoire allumé. */
    private final Image shuffleOnImage;
    /** Image du bouton aléatoire éteint. */
    private final Image shuffleOffImage;
    /** Image du bouton répétition éteinte. */
    private final Image repeatOffImage;
    /** Image du bouton répétition de toute la playlist. */
    private final Image repeatAllImage;
    /** Image du bouton répétition d'un seul morceau. */
    private final Image repeatOneImage;
    /** Image du statut « EN LECTURE ». */
    private final Image statusPlayingImage;
    /** Image du statut « EN PAUSE ». */
    private final Image statusPausedImage;

    /**
     * Dernier morceau affiché, pour ne réécrire le titre que quand il change.
     */
    private Track shownTrack = null;
    /**
     * Indique si quelque chose a déjà été affiché (pour afficher le message de playlist vide une fois).
     */
    private boolean firstRefreshDone = false;
    /**
     * Écart horizontal entre la souris et le coin de la fenêtre au début d'un glissement.
     */
    private double dragOffsetX;
    /**
     * Écart vertical entre la souris et le coin de la fenêtre au début d'un glissement.
     */
    private double dragOffsetY;

    /**
     * Construit l'interface complète et lance sa mise à jour automatique.
     *
     * @param controller le contrôleur qui pilote la musique
     * @param stage      la fenêtre qui affiche l'interface
     * @param pixelScale le nombre de vrais pixels de l'écran pour un pixel du dessin
     */
    public PlayerView(PlayerController controller, Stage stage, int pixelScale) {
        this.controller = controller;
        this.stage = stage;
        this.pixelScale = pixelScale;
        setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        playImage = loadImage(IMG_PLAY);
        pauseImage = loadImage(IMG_PAUSE);
        shuffleOnImage = loadImage(IMG_SHUFFLE_ON);
        shuffleOffImage = loadImage(IMG_SHUFFLE_OFF);
        repeatOffImage = loadImage(IMG_REPEAT_OFF);
        repeatAllImage = loadImage(IMG_REPEAT_ALL);
        repeatOneImage = loadImage(IMG_REPEAT_ONE);
        statusPlayingImage = loadImage(IMG_STATUS);
        statusPausedImage = loadImage(IMG_STATUS_PAUSE);

        Font titleFont = loadFont(TITLE_FONT_FILE, TITLE_FONT_SIZE, TITLE_FALLBACK_FONT);
        Font smallFont = loadFont(SMALL_FONT_FILE, SMALL_FONT_SIZE, SMALL_FALLBACK_FONT);

        // 1. Le décor, du fond vers l'avant
        addImage(IMG_OUTLINE, 0, 0);
        addImage(IMG_PANEL, PANEL_X, PANEL_Y);
        Rectangle card = new Rectangle(CARD_X, CARD_Y, CARD_WIDTH, CARD_HEIGHT);
        card.setFill(CARD_COLOR);
        card.setSmooth(false);
        getChildren().add(card);
        addImage(IMG_LINE, LINE_X, LINE_Y);
        addImage(IMG_POND, POND_X, POND_Y);

        // 2. Les informations du morceau
        titleText = addText(titleFont, DARK_COLOR, TEXT_X, TITLE_BASELINE);
        infoText = addText(smallFont, SOFT_TEXT_COLOR, TEXT_X, INFO_BASELINE);
        addImage(IMG_HEART, HEART_X, HEART_Y);

        // 3. La progression
        progressBar = new PixelBar(PROGRESS_X, PROGRESS_Y, PROGRESS_WIDTH, PROGRESS_HEIGHT,
                PROGRESS_KNOB_WIDTH, PROGRESS_FILL_COLOR, PROGRESS_KNOB_LIGHT_COLOR,
                value -> controller.seek(value * controller.getDurationSeconds()));
        getChildren().add(progressBar);
        elapsedText = addText(smallFont, DARK_COLOR, TIME_LEFT_X, TIME_BASELINE);
        durationText = addText(smallFont, DARK_COLOR, TIME_RIGHT_X, TIME_BASELINE);

        // 4. Les boutons de lecture
        shuffleButton = new PixelButton(shuffleOffImage, SHUFFLE_X, SHUFFLE_Y, () -> {
            controller.toggleShuffle();
            refreshButtons();
        });
        PixelButton previousButton = new PixelButton(loadImage(IMG_PREVIOUS), PREVIOUS_X, PREVIOUS_Y,
                controller::playPrevious);
        playButton = new PixelButton(pauseImage, PLAY_X, PLAY_Y, () -> {
            controller.togglePlayPause();
            refreshButtons();
        });
        PixelButton nextButton = new PixelButton(loadImage(IMG_NEXT), NEXT_X, NEXT_Y, controller::playNext);
        repeatButton = new PixelButton(repeatOffImage, REPEAT_X, REPEAT_Y, () -> {
            controller.cycleRepeatMode();
            refreshButtons();
        });
        addButtons(shuffleButton, previousButton, playButton, nextButton, repeatButton);

        // 5. Le volume
        addImage(IMG_SPEAKER, SPEAKER_X, SPEAKER_Y);
        addImage(IMG_VOLUME_LABEL, VOLUME_LABEL_X, VOLUME_LABEL_Y);
        volumeBar = new PixelBar(VOLUME_X, VOLUME_Y, VOLUME_WIDTH, VOLUME_HEIGHT,
                VOLUME_KNOB_WIDTH, VOLUME_FILL_COLOR, null, value -> {
                    controller.setVolume(value);
                    refreshVolume();
                });
        getChildren().add(volumeBar);
        volumeText = addText(smallFont, SOFT_TEXT_COLOR, VOLUME_VALUE_RIGHT_X, VOLUME_VALUE_BASELINE);

        // 6. Le bas de la carte
        addImage(IMG_EQUALIZER, EQUALIZER_X, EQUALIZER_Y);
        statusView = addImage(IMG_STATUS, STATUS_X, STATUS_Y);
        Image mood = loadImage(IMG_MOOD);
        int moodWidth = mood == null ? 0 : (int) mood.getWidth() / pixelScale;
        addImage(IMG_MOOD, MOOD_RIGHT_X - moodWidth, MOOD_Y);

        // 7. Les lianes par-dessus tout, sans bloquer les clics
        addImage(IMG_VINES, 0, 0).setMouseTransparent(true);

        // 8. La barre du haut, au-dessus des lianes pour rester cliquable
        addImage(IMG_LOGO, LOGO_X, LOGO_Y);
        addImage(IMG_NOTE, NOTE_X, NOTE_Y);
        addButtons(new PixelButton(loadImage(IMG_MINIMIZE), MINIMIZE_X, WINDOW_BUTTONS_Y,
                        () -> stage.setIconified(true)),
                new PixelButton(loadImage(IMG_CLOSE), CLOSE_X, WINDOW_BUTTONS_Y, Platform::exit));

        enableWindowDrag();
        refreshVolume();
        refresh();

        // Mise à jour automatique toutes les 100 ms
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(REFRESH_MILLIS), event -> refresh()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Permet de déplacer la fenêtre en la faisant glisser avec la souris.
     * Les boutons et les barres consomment leurs clics, donc ils ne déplacent pas la fenêtre.
     */
    private void enableWindowDrag() {
        setOnMousePressed(event -> {
            dragOffsetX = event.getScreenX() - stage.getX();
            dragOffsetY = event.getScreenY() - stage.getY();
        });
        setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - dragOffsetX);
            stage.setY(event.getScreenY() - dragOffsetY);
        });
    }

    /**
     * Met à jour tout ce qui bouge : titre si le morceau a changé, temps,
     * barre de progression et boutons. Appelée toutes les 100 ms.
     */
    private void refresh() {
        Track track = controller.getCurrentTrack();
        if (track != shownTrack || !firstRefreshDone) {
            shownTrack = track;
            firstRefreshDone = true;
            if (track == null) {
                setFittedText(titleText, EMPTY_TITLE, TITLE_MAX_WIDTH);
                setFittedText(infoText, EMPTY_INFO, CARD_WIDTH - 26);
            } else {
                setFittedText(titleText, track.title(), TITLE_MAX_WIDTH);
                setFittedText(infoText, track.artist() + " · " + track.album(), CARD_WIDTH - 26);
            }
        }

        double current = controller.getCurrentSeconds();
        double duration = controller.getDurationSeconds();
        if (!progressBar.isDragging()) {
            progressBar.setValue(duration > 0 ? current / duration : 0);
        }
        elapsedText.setText(formatTime(current));
        setRightAlignedText(durationText, formatTime(duration), TIME_RIGHT_X);

        refreshButtons();
    }

    /**
     * Choisit l'image des boutons lecture/pause, aléatoire et répétition,
     * ainsi que le statut « EN LECTURE » / « EN PAUSE », selon l'état actuel.
     */
    private void refreshButtons() {
        playButton.setImage(controller.isPlaying() ? pauseImage : playImage);
        statusView.setImage(controller.isPlaying() ? statusPlayingImage : statusPausedImage);
        // Recalcule la taille affichée au cas où les deux images n'ont pas la même largeur
        fitToDrawingSize(statusView);
        shuffleButton.setImage(controller.isShuffle() ? shuffleOnImage : shuffleOffImage);
        switch (controller.getRepeatMode()) {
            case ALL -> repeatButton.setImage(repeatAllImage);
            case ONE -> repeatButton.setImage(repeatOneImage);
            default -> repeatButton.setImage(repeatOffImage);
        }
    }

    /**
     * Met à jour la barre de volume et le pourcentage affiché.
     */
    private void refreshVolume() {
        double volume = controller.getVolume();
        volumeBar.setValue(volume);
        setRightAlignedText(volumeText, Math.round(volume * 100) + " %", VOLUME_VALUE_RIGHT_X);
    }

    /**
     * Met une durée au format m:ss (par exemple 83.4 secondes donne "1:23").
     *
     * @param seconds la durée en secondes
     * @return la durée formatée en minutes et secondes
     */
    private static String formatTime(double seconds) {
        int total = (int) seconds;
        int minutes = total / 60;
        int secondes = total % 60;
        return String.format("%d:%02d", minutes, secondes);
    }

    /**
     * Charge une image du dossier resources/images, agrandie de {@link #pixelScale}
     * en recopiant chaque pixel (aucun flou).
     * On lit le fichier deux fois : une pour connaître sa taille, une pour l'agrandir.
     *
     * @param name le nom du fichier, par exemple "Fermer.png"
     * @return l'image agrandie, ou {@code null} si le fichier est introuvable (un message est affiché)
     */
    private Image loadImage(String name) {
        String path = "/images/" + name;
        try (InputStream first = PlayerView.class.getResourceAsStream(path)) {
            if (first == null) {
                logger.info("Image introuvable : images/" + name);
                return null;
            }
            Image original = new Image(first);
            if (pixelScale == 1) {
                return original;
            }
            try (InputStream second = PlayerView.class.getResourceAsStream(path)) {
                // smooth = false : agrandissement « au plus proche voisin », les pixels restent carrés
                return new Image(second, original.getWidth() * pixelScale,
                        original.getHeight() * pixelScale, false, false);
            }
        } catch (IOException _) {
            logger.info("Image illisible : images/" + name);
            return null;
        }
    }

    /**
     * Ramène une image agrandie à sa taille en pixels du dessin.
     * Comme toute l'interface est ensuite agrandie de {@link #pixelScale},
     * chaque pixel de l'image tombe exactement sur un pixel de l'écran.
     *
     * @param view la vue de l'image à ajuster
     */
    private void fitToDrawingSize(ImageView view) {
        if (view.getImage() != null) {
            view.setFitWidth(view.getImage().getWidth() / pixelScale);
            view.setFitHeight(view.getImage().getHeight() / pixelScale);
        }
    }

    /**
     * Ajoute des boutons à l'interface en les ramenant à leur taille en pixels du dessin.
     *
     * @param buttons les boutons à ajouter
     */
    private void addButtons(PixelButton... buttons) {
        for (PixelButton button : buttons) {
            fitToDrawingSize(button);
            getChildren().add(button);
        }
    }

    /**
     * Charge une police du dossier resources/fonts, ou une police par défaut si le fichier n'y est pas.
     *
     * @param fileName     le nom du fichier de police
     * @param size         la taille voulue
     * @param fallbackName le nom de la police installée à utiliser à défaut
     * @return la police chargée
     */
    private static Font loadFont(String fileName, double size, String fallbackName) {
        try (InputStream in = PlayerView.class.getResourceAsStream("/fonts/" + fileName)) {
            if (in != null) {
                Font font = Font.loadFont(in, size);
                if (font != null) {
                    return font;
                }
            }
        } catch (IOException _) {
            logger.info("Police illisible : fonts/" + fileName);
        }
        return Font.font(fallbackName, size);
    }

    /**
     * Ajoute une image nette (sans flou) à la position donnée.
     *
     * @param name le nom du fichier image
     * @param x    l'abscisse du coin haut-gauche
     * @param y    l'ordonnée du coin haut-gauche
     * @return la vue de l'image ajoutée
     */
    private ImageView addImage(String name, int x, int y) {
        ImageView view = new ImageView(loadImage(name));
        view.setSmooth(false);
        fitToDrawingSize(view);
        view.setLayoutX(x);
        view.setLayoutY(y);
        getChildren().add(view);
        return view;
    }

    /**
     * Ajoute un texte dont la position est donnée par sa ligne de base
     * (le bas des lettres sans jambage).
     *
     * @param font     la police
     * @param color    la couleur
     * @param x        l'abscisse du début du texte
     * @param baseline l'ordonnée de la ligne de base
     * @return le texte ajouté, vide pour l'instant
     */
    private Text addText(Font font, Color color, int x, int baseline) {
        Text text = new Text(x, baseline, "");
        text.setFont(font);
        text.setFill(color);
        text.setTextOrigin(VPos.BASELINE);
        text.setFontSmoothingType(FontSmoothingType.GRAY);
        getChildren().add(text);
        return text;
    }

    /**
     * Écrit un texte, raccourci avec « … » s'il dépasse la largeur maximale.
     *
     * @param text     le texte à modifier
     * @param value    la chaîne à écrire
     * @param maxWidth la largeur maximale en pixels
     */
    private static void setFittedText(Text text, String value, double maxWidth) {
        text.setText(value);
        String shortened = value;
        while (text.getLayoutBounds().getWidth() > maxWidth && !shortened.isEmpty()) {
            shortened = shortened.substring(0, shortened.length() - 1);
            text.setText(shortened.strip() + "…");
        }
    }

    /**
     * Écrit un texte aligné à droite : son bord droit est placé à l'abscisse donnée.
     *
     * @param text   le texte à modifier
     * @param value  la chaîne à écrire
     * @param rightX l'abscisse du bord droit
     */
    private static void setRightAlignedText(Text text, String value, int rightX) {
        text.setText(value);
        // Arrondi à un pixel entier pour que le texte reste net
        text.setX(Math.round(rightX - text.getLayoutBounds().getWidth()));
    }
}
