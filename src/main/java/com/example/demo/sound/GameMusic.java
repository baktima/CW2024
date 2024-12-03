package com.example.demo.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

/**
 * The {@code GameMusic} class is responsible for managing the background music of the game.
 * It provides methods to play, loop, and adjust the volume of the music.
 */
public class GameMusic {
    private static GameMusic instance; // Singleton instance
    private MediaPlayer backgroundMusicPlayer;

    private static final double INITIAL_VOLUME = 0.5;

    /**
     * Plays the background music. If the {@link MediaPlayer} is not already initialized,
     * it sets up the media player with the background music, loops it, and starts playback.
     */
    public void playBackgroundMusic() {
        if (backgroundMusicPlayer == null) {
            try {
                Media music = new Media(Objects.requireNonNull(getClass().getResource("/com/example/demo/sounds/BackGroundMusicSlug1.mp3")).toExternalForm());
                backgroundMusicPlayer = new MediaPlayer(music);

                // Loop the music
                backgroundMusicPlayer.setOnEndOfMedia(() -> backgroundMusicPlayer.seek(Duration.ZERO));

                // Adjust volume and start playing
                backgroundMusicPlayer.setVolume(INITIAL_VOLUME); // 50% volume

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

    /**
     * Returns the Singleton instance of {@code GameMusic}.
     *
     * @return The single instance of {@code GameMusic}.
     */
    public static GameMusic getInstance() {
        if (instance == null) {
            instance = new GameMusic();
        }
        return instance;
    }

    /**
     * Sets the volume of the background music.
     *
     * @param volume A double value between 0.0 (mute) and 1.0 (maximum volume).
     */
    public void setVolume(double volume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(volume);
        }
    }

    public void stopMusic(){
        backgroundMusicPlayer.stop();
    }

    /**
     * Gets the current volume of the background music.
     *
     * @return A double value representing the current volume, or the initial volume if the
     * {@link MediaPlayer} is not initialized.
     */
    public double getVolume() {
        return backgroundMusicPlayer != null ? backgroundMusicPlayer.getVolume() : INITIAL_VOLUME;
    }
}
