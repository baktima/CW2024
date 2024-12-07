package com.example.demo.sound;

import javafx.scene.media.AudioClip;

import java.util.Objects;

/**
 * The {@code SoundEffects} class manages the playback of sound effects in the game.
 * It includes support for global volume adjustment and specific sound effect playback.
 */
public class SoundEffects {
    private static double INITIAL_SFX_VOLUME = 1.0; // Default global volume
    private static final String GUNSHOT_MP3 ="/com/example/demo/sounds/gun-shot.mp3";
    private static final AudioClip GUN_SHOT_CLIP = new AudioClip(Objects.requireNonNull(SoundEffects.class.getResource(GUNSHOT_MP3)).toExternalForm());

    /**
     * Retrieves the current global volume level.
     *
     * @return the global volume level as a double (range: 0.0 to 1.0)
     */
    public static double getInitialSfxVolume() {
        return INITIAL_SFX_VOLUME;
    }

    /**
     * Sets the global volume for all sound effects.
     * This method updates the volume of all currently loaded {@link AudioClip} objects.
     *
     * @param volume the new global volume level (range: 0.0 to 1.0)
     */
    public static void setInitialSfxVolume(double volume) {
        INITIAL_SFX_VOLUME = volume;
        GUN_SHOT_CLIP.setVolume(volume);
    }

    /**
     * Plays the gunshot sound effect.
     */
    public void playGunShotSound() {
        GUN_SHOT_CLIP.play();
    }
}
