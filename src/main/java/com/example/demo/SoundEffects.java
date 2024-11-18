package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundEffects {
    private static double globalVolume = 0.7; // Default global volume
    private MediaPlayer mediaPlayer;

    public static double getGlobalVolume() {
        return globalVolume;
    }

    public static void setGlobalVolume(double volume) {
        globalVolume = volume;
    }

    public void playSound() {
        Media sound = new Media(getClass().getResource("/com/example/demo/sounds/gun-shot.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);

        // Apply global volume and play
        mediaPlayer.setVolume(globalVolume);
        mediaPlayer.play();
    }

    public void setVolume(double volume) {
        // Adjust volume of currently playing media
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        } else {
            System.err.println("MediaPlayer is not initialized. Play a sound first.");
        }
    }
}
