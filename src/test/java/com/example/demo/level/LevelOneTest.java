package com.example.demo.level;

import com.example.demo.level.levelViews.LevelView;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.JavaFXTestUtils;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LevelOneTest {

    // Test subclass of LevelOne just to mock the LevelView to avoid UI complexity.
    private static class TestLevelOne extends LevelOne {
        public TestLevelOne(double screenHeight, double screenWidth) {
            super(screenHeight, screenWidth);
        }

        @Override
        protected LevelView instantiateLevelView() {
            return mock(LevelView.class);
        }
    }

    private TestLevelOne levelOne;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        // Initialize JavaFX if your code or classes depend on it
        JavaFXTestUtils.initJavaFX();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Instantiate the TestLevelOne which uses the original LevelParent logic
            levelOne = new TestLevelOne(600, 800);
            latch.countDown();
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX initialization timed out");
    }

    @Test
    void testEnemiesAlwaysMaintainedAtFive() {
        ActorManager actorManager = levelOne.getActorManager();

        // Initially no enemies
        assertEquals(0, actorManager.getEnemyUnits().size(), "Initially, no enemies should be present.");

        // loop until we get 5 enemies.
        for (int i = 0; i < 100 && actorManager.getEnemyUnits().size() < 5; i++) {
            levelOne.spawnEnemyUnits();
        }

        // Now we should have 5 enemies (if probabilities are high enough).
        assertEquals(5, actorManager.getEnemyUnits().size(), "After first spawn attempts, there should be 5 enemies.");

        // Simulate destroying 2 enemies
        actorManager.getEnemyUnits().remove(0);
        actorManager.getEnemyUnits().remove(0);
        assertEquals(3, actorManager.getEnemyUnits().size(), "After removing 2 enemies, there should be 3 left.");

        // Attempt to always respawn to see the reaction of the generation where it should always respawn 5 no more no less
        for (int i = 0; i < 1000; i++) {
            levelOne.spawnEnemyUnits();
        }

        assertEquals(5, actorManager.getEnemyUnits().size(),
                "After respawning, total enemies should be back to 5.");
    }
}
