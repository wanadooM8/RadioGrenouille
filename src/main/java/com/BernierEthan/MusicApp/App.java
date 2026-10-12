/*
 * Bernier Ethan, Copyright (c) 2026
 * Contact : Ethan.bernier66@gmail.com
 *
 * Project Name  : MusicApp
 * File Name     : App.java
 * Last Modified : 2026-10-08, 02:36AM
 */

package com.BernierEthan.MusicApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.lang.management.PlatformLoggingMXBean;
import java.nio.file.Path;

/**
 * Point d'entrée de l'application.
 * Une application JavaFX hérite de Application : JavaFX crée la fenêtre
 * et appelle automatiquement start() au démarrage.
 */
public class App extends Application {

    /**
     * Largeur de la fenêtre en pixels (à ajuster selon la maquette).
     */
    private static final double LARGEUR = 640;
    /**
     * Hauteur de la fenêtre en pixels (à ajuster selon la maquette).
     */
    private static final double HAUTEUR = 704;

    /**
     * Construit la fenêtre, charge la playlist du dossier « musique »,
     * lance la lecture de la première musique et ajoute les boutons
     * Pause, Reprendre, Volume, Précédent, Suivant, Temps, +10 s, Aléatoire et Répéter.
     *
     * @param primaryStage la fenêtre principale, fournie par JavaFX
     * @throws Exception si le dossier de musique ne peut pas être lu
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Le Pane est le conteneur vide dans lequel on posera plus tard
        // les images (fond, grenouille) et les boutons, à la position voulue.
        Pane racine = new Pane();

        // La Scene est le contenu de la fenêtre : un conteneur racine + une taille.
        Scene scene = new Scene(racine, LARGEUR, HAUTEUR);

        // La Stage est la fenêtre elle-même : on lui donne un titre et on y affiche la scène.
        primaryStage.setTitle("Radio Grenouille");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // taille fixe : le pixel art reste net


        Playlist playlist =  new Playlist();
        playlist.loadFolder(Path.of("musique"));
        AudioPlayer audioplayer = new AudioPlayer();
        PlayerController playerController = new PlayerController( playlist, audioplayer);
        playerController.play();



        Button buttonPause = new Button("Pause");
        buttonPause.setOnAction(e -> playerController.pause());

        Button buttonReprendre = new Button("Reprendre");
        buttonReprendre.setOnAction(e -> playerController.resume());

        Button buttonVolume= new Button("Volume");
        buttonVolume.setOnAction(event -> playerController.setVolume(0));

        Button buttonPrevious = new Button("Précédent");
        buttonPrevious.setOnAction(e -> playerController.playPrevious());

        Button buttonNext = new Button("Suivant");
        buttonNext.setOnAction(e -> playerController.playNext());

        buttonPause.setLayoutX(50);
        buttonPause.setLayoutY(50);

        buttonReprendre.setLayoutX(150);
        buttonReprendre.setLayoutY(50);

        buttonPrevious.setLayoutX(250);
        buttonPrevious.setLayoutY(50);

        Button buttonTemps = new Button("Temps");
        buttonTemps.setOnAction(e -> System.out.println("Temps : "
                + formatTime(playerController.getCurrentSeconds()) + " / "
                + formatTime(playerController.getDurationSeconds())));

        Button buttonAvance = new Button("+10 s");
        buttonAvance.setOnAction(e -> playerController.seek(playerController.getCurrentSeconds() + 10));

        Button buttonAleatoire = new Button("Aléatoire");
        buttonAleatoire.setOnAction(e -> {
            playerController.toggleShuffle();
            System.out.println("Aléatoire : " + (playlist.isShuffle() ? "activé" : "désactivé"));
        });

        Button buttonRepeter = new Button("Répéter");
        buttonRepeter.setOnAction(e -> {
            playerController.cycleRepeatMode();
            System.out.println("Répétition : " + playlist.getRepeatMode());
        });

        buttonNext.setLayoutX(350);
        buttonNext.setLayoutY(50);

        buttonTemps.setLayoutX(50);
        buttonTemps.setLayoutY(100);

        buttonAvance.setLayoutX(150);
        buttonAvance.setLayoutY(100);

        buttonAleatoire.setLayoutX(250);
        buttonAleatoire.setLayoutY(100);

        buttonRepeter.setLayoutX(350);
        buttonRepeter.setLayoutY(100);
        racine.getChildren().addAll(buttonPause, buttonReprendre, buttonVolume, buttonPrevious, buttonNext,
                buttonTemps, buttonAvance, buttonAleatoire, buttonRepeter);


        primaryStage.show(); // affiche la fenêtre à l'écran
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
     * Méthode appelée au lancement du programme.
     * {@code launch()} démarre JavaFX, qui appelle ensuite {@link #start(Stage)}.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}