package com.example.demo.level;

import com.example.demo.level.levelViews.LevelView;

/**
 * Represents the third level of the game. This level features increased difficulty
 * with multiple types of enemies, including regular, special, and tanker planes.
 * The player must reach a specified number of kills to win the level.
 * This class extends {@link LevelParent} and provides specific implementations
 * for Level Three's behavior, including enemy spawning, game-over logic,
 * and level-specific views.
 */
public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/BackGroundLevel3.png";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 20;
    private static final double ENEMY_SPAWN_PROBABILITY = .05;
    private static final int PLAYER_INITIAL_HEALTH = 6;
    private static final double SPECIAL_ENEMY_SPAWN_PROBABILITY = 0.0125;
    private static final double TANKER_SPAWN_PROBABILITY = 0.0125;


    /**
     * Constructs the LevelThree instance with specified screen dimensions.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth the width of the game screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        getTextDisplay().setBossHealthVisible(false);
    }

    /**
     * Checks whether the game is over, either by user destruction or achieving the kill target.
     */
    @Override
    protected void checkIfGameOver() {
        if (getUserIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget())
            winGame();
    }

    /**
     * Determines if the user has reached the required number of kills to advance to the next level.
     *
     * @return true if the user has reached or exceeded the kill target, false otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Spawns enemy units based on predefined probabilities and screen constraints.
     * Includes normal enemies, special enemies, and tanker planes.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getActorManager().getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            spawnRegularEnemies(ENEMY_SPAWN_PROBABILITY);
            spawnSpecialEnemies(SPECIAL_ENEMY_SPAWN_PROBABILITY); // Upper quarter
            spawnTankerEnemies(TANKER_SPAWN_PROBABILITY);
        }
    }

    /**
     * Creates and returns the view associated with this level.
     *
     * @return a new LevelView instance for LevelThree.
     */
    @Override
    protected LevelView instantiateLevelView() {
        LevelView levelView;
        levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }
}
