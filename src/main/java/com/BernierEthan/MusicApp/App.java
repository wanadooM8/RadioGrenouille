/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : App.java
 * Last Modified : 2026-10-12, 02:57AM
 */

package com.BernierEthan.MusicApp;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Point d'entrée de l'application.
 * Une application JavaFX hérite de Application : JavaFX crée la fenêtre
 * et appelle automatiquement start() au démarrage.
 */
public class App extends Application {

    /**
     * Charge la playlist du dossier « musique », crée le contrôleur et l'interface,
     * puis affiche une fenêtre transparente sans barre Windows, agrandie
     * d'un facteur entier pour que le pixel art reste net.
     *
     * @param primaryStage la fenêtre principale, fournie par JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        // La logique : playlist, lecteur audio et contrôleur
        Playlist playlist = new Playlist();
        try {
            playlist.loadFolder(findMusicFolder());
        } catch (IOException e) {
            // Dossier absent : la playlist reste vide et l'interface affiche un message
            System.err.println("Dossier de musique introuvable : " + UiConstants.MUSIC_FOLDER);
        }
        AudioPlayer audioPlayer = new AudioPlayer();
        PlayerController controller = new PlayerController(playlist, audioPlayer);
        controller.setVolume(UiConstants.START_VOLUME);

        // L'interface, dessinée en pixels « pixel art » puis agrandie
        Screen screen = Screen.getPrimary();
        double windowsZoom = screen.getOutputScaleX(); // 1.0 = 100 %, 1.25 = 125 %...
        int pixelScale = computePixelScale(screen, windowsZoom);
        // On compense le zoom de Windows pour qu'un pixel du dessin fasse pile pixelScale vrais pixels
        double scale = pixelScale / windowsZoom;
        PlayerView view = new PlayerView(controller, primaryStage, pixelScale);
        Group root = new Group(view);
        root.getTransforms().add(new Scale(scale, scale));

        Scene scene = new Scene(root, UiConstants.WINDOW_WIDTH * scale, UiConstants.WINDOW_HEIGHT * scale);
        scene.setFill(Color.TRANSPARENT); // laisse voir le bureau dans les coins arrondis

        primaryStage.initStyle(StageStyle.TRANSPARENT); // pas de barre Windows
        primaryStage.setTitle("Radio Grenouille");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.play();
    }

    /**
     * Calcule l'agrandissement de la fenêtre, en vrais pixels de l'écran.
     * On cherche le plus grand nombre entier N (au plus {@link UiConstants#MAX_SCALE})
     * tel que la fenêtre agrandie N fois occupe au plus un quart de l'écran
     * (la moitié de sa largeur et la moitié de sa hauteur). Chaque pixel du dessin
     * devient alors un carré de N x N pixels de l'écran, ce qui reste parfaitement net.
     *
     * @param screen      l'écran où s'ouvre la fenêtre
     * @param windowsZoom le zoom d'affichage de Windows (1.0 pour 100 %)
     * @return le facteur entier N
     */
    private static int computePixelScale(Screen screen, double windowsZoom) {
        Rectangle2D bounds = screen.getVisualBounds();
        // La fenêtre ne doit pas dépasser un quart de l'écran
        double maxWidth = bounds.getWidth() * windowsZoom / 2;
        double maxHeight = bounds.getHeight() * windowsZoom / 2;

        int n = 1;
        while (n < UiConstants.MAX_SCALE
                && UiConstants.WINDOW_WIDTH * (n + 1) <= maxWidth
                && UiConstants.WINDOW_HEIGHT * (n + 1) <= maxHeight) {
            n++;
        }
        return n;
    }

    /**
     * Trouve le dossier de musique : à côté de l'exe si l'appli est
     * lancée par jpackage, sinon dans le dossier du projet.
     */
    private static Path findMusicFolder() {
        String exePath = System.getProperty("jpackage.app-path");
        if (exePath == null) {
            return Path.of("musique");
        }
        return Path.of(exePath).getParent().resolve("musique");
    }

    /**
     * Méthode appelée au lancement du programme.
     * {@code launch()} démarre JavaFX, qui appelle ensuite {@link #start(Stage)}.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}
