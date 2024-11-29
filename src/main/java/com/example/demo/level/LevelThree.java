package com.example.demo.level;

import com.example.demo.actor.ActiveActor;
import com.example.demo.level.levelView.LevelView;
import com.example.demo.plane.EnemyPlane;
import com.example.demo.plane.EnemyPlaneSpecial;
import com.example.demo.plane.TankerPlane;

//maybe make the third level have the special enemies and make the special enemies bullet different. (done ish with the tanker)

public class LevelThree extends LevelParent {

    private LevelView levelView;
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/BackGroundLevel3.png";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 1;
    private static final double ENEMY_SPAWN_PROBABILITY = .05;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final double SPECIAL_ENEMY_SPAWN_PROBABILITY = 0.0125;
    private static final double SPECIAL_SPECIAL_ENEMY_SPAWN_PROBABILITY = 0.0125;


    /**
     * @param screenHeight        passing the screen height value to the levelParent.
     *                            making it more readable
     * @param screenWidth         passing the screen width value to the levelParent.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget())
            winGame();
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = GetActorManager().getCurrentNumberOfEnemies();
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

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }
}
