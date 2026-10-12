/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : UiConstants.java
 * Last Modified : 2026-10-12, 02:57AM
 */

package com.BernierEthan.MusicApp;

import javafx.scene.paint.Color;

/**
 * Toutes les valeurs réglables de l'interface : taille de la fenêtre, positions,
 * couleurs, noms des images et des polices.
 * Les positions sont en pixels « pixel art » (avant agrandissement), mesurées
 * sur la maquette Lecteur.png. Le coin haut-gauche de la fenêtre est (0, 0).
 * La carte de la maquette commence en ({@link #CARD_X}, {@link #CARD_Y}) :
 * une position lue sur la maquette s'obtient en lui ajoutant ces deux valeurs.
 */
public final class UiConstants {

    /**
     * Empêche de créer un objet : cette classe ne contient que des constantes.
     */
    private UiConstants() {
    }

    // ---------- Fenêtre ----------

    /** Largeur de la fenêtre en pixels pixel art. */
    public static final int WINDOW_WIDTH = 400;
    /** Hauteur de la fenêtre en pixels pixel art. */
    public static final int WINDOW_HEIGHT = 440;
    /** Facteur d'agrandissement maximum essayé (la fenêtre doit tenir dans l'écran). */
    public static final int MAX_SCALE = 3;
    /** Délai entre deux mises à jour de l'affichage, en millisecondes. */
    public static final int REFRESH_MILLIS = 100;
    /** Volume au démarrage, entre 0.0 et 1.0. */
    public static final double START_VOLUME = 0.7;
    /** Dossier des musiques, relatif au dossier de lancement. */
    public static final String MUSIC_FOLDER = "musique";

    // ---------- Couleurs (mesurées sur la maquette) ----------

    /** Fond clair de la carte. */
    public static final Color CARD_COLOR = Color.web("#dfe7bd");
    /** Vert le plus foncé : titre, temps, contours. */
    public static final Color DARK_COLOR = Color.web("#203e30");
    /** Vert grisé : artiste, album, pourcentage du volume. */
    public static final Color SOFT_TEXT_COLOR = Color.web("#617652");
    /** Bordure des barres de progression et de volume. */
    public static final Color BAR_BORDER_COLOR = Color.web("#355940");
    /** Partie vide des barres (crème). */
    public static final Color BAR_EMPTY_COLOR = Color.web("#f4f0cf");
    /** Partie remplie de la barre de progression. */
    public static final Color PROGRESS_FILL_COLOR = Color.web("#78aa55");
    /** Centre clair du curseur de la barre de progression. */
    public static final Color PROGRESS_KNOB_LIGHT_COLOR = Color.web("#b3d477");
    /** Partie remplie de la barre de volume. */
    public static final Color VOLUME_FILL_COLOR = Color.web("#52794a");

    // ---------- Carte de la maquette ----------

    /** Abscisse de la carte (maquette 384x392) dans la fenêtre. */
    public static final int CARD_X = 8;
    /** Ordonnée de la carte dans la fenêtre, sous la barre du haut. */
    public static final int CARD_Y = 40;
    /** Largeur de la carte. */
    public static final int CARD_WIDTH = 384;
    /** Hauteur de la carte. */
    public static final int CARD_HEIGHT = 392;

    // ---------- Décor ----------

    /** Abscisse du panneau vert. */
    public static final int PANEL_X = 4;
    /** Ordonnée du panneau vert. */
    public static final int PANEL_Y = 4;
    /** Abscisse du trait clair en haut de la carte. */
    public static final int LINE_X = CARD_X;
    /** Ordonnée du trait clair en haut de la carte. */
    public static final int LINE_Y = CARD_Y;
    /** Abscisse de l'illustration des grenouilles. */
    public static final int POND_X = CARD_X + 12;
    /** Ordonnée de l'illustration des grenouilles. */
    public static final int POND_Y = CARD_Y + 12;

    // ---------- Barre du haut (absente de la maquette : positions estimées) ----------

    /** Abscisse du logo « petite mare ». */
    public static final int LOGO_X = 16;
    /** Ordonnée du logo. */
    public static final int LOGO_Y = 15;
    /** Abscisse de la petite note jaune, à droite du logo. */
    public static final int NOTE_X = 129;
    /** Ordonnée de la petite note jaune. */
    public static final int NOTE_Y = 16;
    /** Abscisse du bouton réduire. */
    public static final int MINIMIZE_X = 340;
    /** Abscisse du bouton fermer. */
    public static final int CLOSE_X = 366;
    /** Ordonnée des boutons réduire et fermer. */
    public static final int WINDOW_BUTTONS_Y = 11;

    // ---------- Textes du morceau ----------

    /** Abscisse du titre et de la ligne artiste · album. */
    public static final int TEXT_X = CARD_X + 13;
    /** Ligne de base du titre (bas des lettres sans jambage). */
    public static final int TITLE_BASELINE = CARD_Y + 186;
    /** Largeur maximale du titre avant d'être coupé par « … » (s'arrête avant le cœur). */
    public static final int TITLE_MAX_WIDTH = 324;
    /** Ligne de base de « artiste · album ». */
    public static final int INFO_BASELINE = CARD_Y + 203;
    /** Abscisse du cœur. */
    public static final int HEART_X = CARD_X + 344;
    /** Ordonnée du cœur. */
    public static final int HEART_Y = CARD_Y + 172;
    /** Titre affiché quand le dossier de musique est vide. */
    public static final String EMPTY_TITLE = "La mare est calme...";
    /** Seconde ligne affichée quand le dossier de musique est vide. */
    public static final String EMPTY_INFO = "Ajoute des musiques dans le dossier « musique »";

    // ---------- Barre de progression ----------

    /** Abscisse de la barre de progression. */
    public static final int PROGRESS_X = CARD_X + 12;
    /** Ordonnée de la barre de progression. */
    public static final int PROGRESS_Y = CARD_Y + 221;
    /** Largeur de la barre de progression. */
    public static final int PROGRESS_WIDTH = 360;
    /** Hauteur de la barre de progression. */
    public static final int PROGRESS_HEIGHT = 10;
    /** Largeur du curseur de la barre de progression. */
    public static final int PROGRESS_KNOB_WIDTH = 8;
    /** Abscisse du temps écoulé (aligné à gauche). */
    public static final int TIME_LEFT_X = CARD_X + 13;
    /** Bord droit de la durée totale (alignée à droite). */
    public static final int TIME_RIGHT_X = CARD_X + 372;
    /** Ligne de base des deux temps. */
    public static final int TIME_BASELINE = CARD_Y + 248;

    // ---------- Boutons de lecture ----------

    /** Abscisse du bouton aléatoire (28x28). */
    public static final int SHUFFLE_X = CARD_X + 66;
    /** Ordonnée du bouton aléatoire. */
    public static final int SHUFFLE_Y = CARD_Y + 274;
    /** Abscisse du bouton précédent (38x38). */
    public static final int PREVIOUS_X = CARD_X + 112;
    /** Ordonnée du bouton précédent. */
    public static final int PREVIOUS_Y = CARD_Y + 270;
    /** Abscisse du gros bouton lecture/pause (48x44). */
    public static final int PLAY_X = CARD_X + 168;
    /** Ordonnée du gros bouton lecture/pause. */
    public static final int PLAY_Y = CARD_Y + 267;
    /** Abscisse du bouton suivant (38x38). */
    public static final int NEXT_X = CARD_X + 234;
    /** Ordonnée du bouton suivant. */
    public static final int NEXT_Y = CARD_Y + 270;
    /** Abscisse du bouton répétition (28x28). */
    public static final int REPEAT_X = CARD_X + 290;
    /** Ordonnée du bouton répétition. */
    public static final int REPEAT_Y = CARD_Y + 274;
    /** Décalage vers le bas d'un bouton pendant qu'on appuie dessus. */
    public static final int PRESS_OFFSET = 2;

    // ---------- Volume ----------

    /** Abscisse de l'icône haut-parleur. */
    public static final int SPEAKER_X = CARD_X + 12;
    /** Ordonnée de l'icône haut-parleur. */
    public static final int SPEAKER_Y = CARD_Y + 328;
    /** Abscisse du mot « Volume ». */
    public static final int VOLUME_LABEL_X = CARD_X + 39;
    /** Ordonnée du mot « Volume ». */
    public static final int VOLUME_LABEL_Y = CARD_Y + 331;
    /** Abscisse de la barre de volume. */
    public static final int VOLUME_X = CARD_X + 94;
    /** Ordonnée de la barre de volume. */
    public static final int VOLUME_Y = CARD_Y + 331;
    /** Largeur de la barre de volume. */
    public static final int VOLUME_WIDTH = 244;
    /** Hauteur de la barre de volume. */
    public static final int VOLUME_HEIGHT = 8;
    /** Largeur du curseur de la barre de volume. */
    public static final int VOLUME_KNOB_WIDTH = 6;
    /** Bord droit du pourcentage du volume (aligné à droite). */
    public static final int VOLUME_VALUE_RIGHT_X = CARD_X + 373;
    /** Ligne de base du pourcentage du volume. */
    public static final int VOLUME_VALUE_BASELINE = CARD_Y + 339;

    // ---------- Bas de la carte ----------

    /** Abscisse de l'icône égaliseur. */
    public static final int EQUALIZER_X = CARD_X + 12;
    /** Ordonnée de l'icône égaliseur. */
    public static final int EQUALIZER_Y = CARD_Y + 363;
    /** Abscisse de « EN LECTURE ». */
    public static final int STATUS_X = CARD_X + 33;
    /** Ordonnée de « EN LECTURE ». */
    public static final int STATUS_Y = CARD_Y + 365;
    /** Bord droit du petit message d'ambiance (aligné à droite). */
    public static final int MOOD_RIGHT_X = CARD_X + 372;
    /** Ordonnée du petit message d'ambiance. */
    public static final int MOOD_Y = CARD_Y + 364;

    // ---------- Polices ----------

    /** Fichier de la police du titre, dans src/main/resources/fonts/. */
    public static final String TITLE_FONT_FILE = "PixelifySans-Regular.ttf";
    /** Taille de la police du titre. */
    public static final double TITLE_FONT_SIZE = 20;
    /** Fichier de la police des petits textes, dans src/main/resources/fonts/. */
    public static final String SMALL_FONT_FILE = "DepartureMono-Regular.otf";
    /** Taille de la police des petits textes. */
    public static final double SMALL_FONT_SIZE = 11;
    /** Police utilisée pour le titre si son fichier est absent. */
    public static final String TITLE_FALLBACK_FONT = "Verdana";
    /** Police utilisée pour les petits textes si son fichier est absent. */
    public static final String SMALL_FALLBACK_FONT = "Consolas";

    // ---------- Images (dans src/main/resources/images/) ----------

    /** Fond extérieur de la fenêtre (400x440). */
    public static final String IMG_OUTLINE = "Contour crénelé.png";
    /** Panneau vert (392x432). */
    public static final String IMG_PANEL = "Biseau de la fenêtre.png";
    /** Lianes et perles posées par-dessus tout le reste (400x438). */
    public static final String IMG_VINES = "Berges · mousse et pileas pixelisés.png";
    /** Illustration des grenouilles (360x140). */
    public static final String IMG_POND = "Jardin aquatique miniature.png";
    /** Trait clair en haut de la carte (384x2). */
    public static final String IMG_LINE = "Reflet du cadre.png";
    /** Logo « petite mare » (107x15). */
    public static final String IMG_LOGO = "Nom du lecteur.png";
    /** Petite note jaune (12x14). */
    public static final String IMG_NOTE = "Icône pixel.png";
    /** Bouton fermer (22x22). */
    public static final String IMG_CLOSE = "Fermer.png";
    /** Bouton réduire (22x22). */
    public static final String IMG_MINIMIZE = "Réduire.png";
    /** Cœur (28x28). */
    public static final String IMG_HEART = "Morceau favori.png";
    /** Bouton lecture (48x44) : PROVISOIRE, généré à partir du bouton pause. */
    public static final String IMG_PLAY = "Lecture.png";
    /** Bouton pause (48x44). */
    public static final String IMG_PAUSE = "Mettre en pause.png";
    /** Bouton précédent (38x38). */
    public static final String IMG_PREVIOUS = "Morceau précédent.png";
    /** Bouton suivant (38x38). */
    public static final String IMG_NEXT = "Morceau suivant.png";
    /** Bouton aléatoire allumé (28x28). */
    public static final String IMG_SHUFFLE_ON = "Lecture aléatoire.png";
    /** Bouton aléatoire éteint (28x28) : PROVISOIRE. */
    public static final String IMG_SHUFFLE_OFF = "Lecture aléatoire désactivée.png";
    /** Bouton répétition éteinte (28x28) : PROVISOIRE. */
    public static final String IMG_REPEAT_OFF = "Répétition désactivée.png";
    /** Bouton répétition de toute la playlist (28x28). */
    public static final String IMG_REPEAT_ALL = "Répétition active.png";
    /** Bouton répétition d'un seul morceau (28x28) : PROVISOIRE. */
    public static final String IMG_REPEAT_ONE = "Répétition un seul.png";
    /** Icône haut-parleur (16x14). */
    public static final String IMG_SPEAKER = "Icône pixel-1.png";
    /** Mot « Volume » (44x9). */
    public static final String IMG_VOLUME_LABEL = "Libellé volume.png";
    /** Icône égaliseur (14x10). */
    public static final String IMG_EQUALIZER = "Animation sonore.png";
    /** Texte « EN LECTURE » (53x7). */
    public static final String IMG_STATUS = "État de lecture.png";
    /** Texte << EN PAUSE >>(53x7). */
    public static final String IMG_STATUS_PAUSE = "État de lecture en pause.png";
    /** Petit message d'ambiance en bas à droite (63x9). */
    public static final String IMG_MOOD = "Ambiance.png";
}
