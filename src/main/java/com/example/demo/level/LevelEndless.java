package com.example.demo.level;

/**
 * Represents an endless level in the game that extends {@link LevelThree}.
 * <p>
 * This level continues indefinitely, with the game ending only when the user is destroyed.
 * </p>
 */
public class LevelEndless extends LevelThree{

    /**
     * Constructs a {@code LevelEndless} instance with the specified screen dimensions.
     *
     * @param screenHeight the height of the screen.
     * @param screenWidth  the width of the screen.
     */
    public LevelEndless(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth);
    }

    /**
     * Checks if the game is over.
     * <p>
     * The game ends if the user is destroyed. This overrides the {@code checkIfGameOver} method
     * in {@link LevelThree} to remove other conditions for ending the game.
     * </p>
     */
    @Override
    protected void checkIfGameOver(){
        if (getUserIsDestroyed()) {
            loseGame();
        }
    }
}
