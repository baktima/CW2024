package com.example.demo.level;

import com.example.demo.actor.ActiveActor;
import com.example.demo.level.levelView.LevelView;
import com.example.demo.plane.EnemyPlane;
import com.example.demo.plane.EnemyPlaneSpecial;
import com.example.demo.plane.TankerPlane;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/BackGroundLevel3.png";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 1;
    private static final double ENEMY_SPAWN_PROBABILITY = .05;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final double SPECIAL_ENEMY_SPAWN_PROBABILITY = 0.0125;
    private static final double SPECIAL_SPECIAL_ENEMY_SPAWN_PROBABILITY = 0.0125;


    /**
     * Constructs the LevelThree instance with specified screen dimensions.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth the width of the game screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
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
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActor newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }

            //limiting the spawn to upper half of the screen
            if (Math.random() < SPECIAL_ENEMY_SPAWN_PROBABILITY) {
                double newEnemySpecialInitialYPosition = Math.random() * getEnemyMaximumYPosition()/4;
                ActiveActor newEnemy = new EnemyPlaneSpecial(getScreenWidth(), newEnemySpecialInitialYPosition);
                addEnemyUnit(newEnemy);
            }
            if (Math.random() < SPECIAL_SPECIAL_ENEMY_SPAWN_PROBABILITY) {
                double newEnemySpecialInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActor newEnemy = new TankerPlane(getScreenWidth(), newEnemySpecialInitialYPosition);
                addEnemyUnit(newEnemy);
            }
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
