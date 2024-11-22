package com.example.demo.sound;

import javafx.scene.media.AudioClip;

public class SoundEffects {
    private static double globalVolume = 0.7; // Default global volume

    //preloading the audioclip rather than always making a new instance;
    private static final AudioClip gunShotClip = new AudioClip(SoundEffects.class.getResource("/com/example/demo/sounds/gun-shot.mp3").toExternalForm());

    public static double getGlobalVolume() {
        return globalVolume;
    }

    public static void setGlobalVolume(double volume) {
        globalVolume = volume;
        gunShotClip.setVolume(volume);
    }

    public void playSound() {
        gunShotClip.play();
    }
}
