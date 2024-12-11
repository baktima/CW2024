package com.example.demo.level;

import com.example.demo.actor.ActiveActor;
import com.example.demo.level.levelViews.LevelView;
import com.example.demo.util.JavaFXTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelParentTest {

    // A concrete subclass of LevelParent for testing purposes.
    private static class TestLevel extends LevelParent {

        public TestLevel(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
            super(backgroundImageName, screenHeight, screenWidth, playerInitialHealth);
        }

        @Override
        protected void checkIfGameOver() {
            // no-op for test
        }

        @Override
        protected LevelView instantiateLevelView() {
            // Return a mock LevelView to avoid UI complexity
            return mock(LevelView.class);
        }

        @Override
        protected void spawnEnemyUnits() {
            // We will call the spawn methods directly in our test,
            // so no automatic spawning here.
        }
    }

    private TestLevel testLevel;

    @BeforeAll
    static void initFX() throws InterruptedException {
        JavaFXTestUtils.initJavaFX();
    }

    @BeforeEach
    void setUp() {
        // Use valid image resource path or adjust if your image path differs
        testLevel = new TestLevel("/com/example/demo/images/MetalSlugBackground1.jpg", 600, 800, 5);
    }

    @Test
    void testRegularEnemySpawnWithinBounds() {
        double maxY = testLevel.getEnemyMaximumYPosition();

        testLevel.spawnRegularEnemies(1.0);

        // Verify that all spawned enemies are within the 0 to maxY range
        for (ActiveActor enemy : testLevel.getActorManager().getEnemyUnits()) {
            double enemyY = enemy.getLayoutY() + enemy.getTranslateY();
            assertTrue(enemyY >= 0 && enemyY <= maxY,
                    "Regular enemy Y position should be within [0, " + maxY + "]. Got: " + enemyY);
        }
    }

    @Test
    void testBomberEnemySpawnWithinBounds() {
        double maxY = testLevel.getEnemyMaximumYPosition();
        double positionMultiplier = 0.25; // from LevelParent's code

        testLevel.spawnBomberEnemies(1.0);

        // Verify Bomber enemies are within [0, maxY * 0.25]
        for (ActiveActor enemy : testLevel.getActorManager().getEnemyUnits()) {
            double enemyY = enemy.getLayoutY() + enemy.getTranslateY();
            assertTrue(enemyY >= 0 && enemyY <= maxY * positionMultiplier,
                    "Bomber enemy Y position should be within [0, " + maxY * positionMultiplier + "]. Got: " + enemyY);
        }
    }

    @Test
    void testTankerEnemySpawnWithinBounds() {
        double maxY = testLevel.getEnemyMaximumYPosition();
        double screenHeightMiddle = maxY / 2;
        double spawnRange = maxY * 0.1;
        double minY = screenHeightMiddle - spawnRange;
        double maxSpawnY = screenHeightMiddle + spawnRange;

        testLevel.spawnTankerEnemies(1.0);

        // Verify tanker enemies are within [minY, maxSpawnY]
        for (ActiveActor enemy : testLevel.getActorManager().getEnemyUnits()) {
            double enemyY = enemy.getLayoutY() + enemy.getTranslateY();
            assertTrue(enemyY >= minY && enemyY <= maxSpawnY,
                    "Tanker enemy Y position should be within [" + minY + ", " + maxSpawnY + "]. Got: " + enemyY);
        }
    }


}
