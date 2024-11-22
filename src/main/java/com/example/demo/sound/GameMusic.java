package com.example.demo.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

public class GameMusic {
    private MediaPlayer backgroundMusicPlayer;

    public void playBackgroundMusic() {
        if (backgroundMusicPlayer == null) {
            try {
                Media music = new Media(Objects.requireNonNull(getClass().getResource("/com/example/demo/sounds/backGround-japanese.mp3")).toExternalForm());
                backgroundMusicPlayer = new MediaPlayer(music);

                // Loop the music
                backgroundMusicPlayer.setOnEndOfMedia(() -> backgroundMusicPlayer.seek(Duration.ZERO));

                // Adjust volume and start playing
                backgroundMusicPlayer.setVolume(0.5); // 50% volume

                // Ensure the media is ready
                backgroundMusicPlayer.setOnReady(() -> {
                    System.out.println("Music ready, starting playback.");
                    backgroundMusicPlayer.play();
                });

                backgroundMusicPlayer.setOnError(() -> {
                    System.err.println("MediaPlayer error: " + backgroundMusicPlayer.getError().getMessage());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            backgroundMusicPlayer.play();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }
}
