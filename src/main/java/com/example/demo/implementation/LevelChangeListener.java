package com.example.demo.implementation;

/**
 * Represents a listener for level changes in a game.
 * <p>
 * Implementing classes will define the behavior that should occur
 * when the game transitions to a new level.
 * </p>
 */
public interface LevelChangeListener {

    /**
     * Called when the level changes.
     *
     * @param nextLevelName the name of the next level that the game is transitioning to.
     */
    void levelChange(String nextLevelName); 
}
