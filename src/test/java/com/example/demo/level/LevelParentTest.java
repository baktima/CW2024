//package com.example.demo.level;
//
//import com.example.demo.actor.ActiveActor;
//import com.example.demo.display.TextDisplay;
//import com.example.demo.level.levelViews.LevelView;
//import com.example.demo.manager.ActorManager;
//import com.example.demo.plane.UserPlane;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class LevelParentTest {
//
//    private LevelParent testLevel;
//
//    @Mock
//    private Stage mockStage;
//
//    @Mock
//    private TextDisplay mockTextDisplay;
//
//    @Mock
//    private ActorManager mockActorManager;
//
//    @Mock
//    private UserPlane mockUser;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Create a concrete subclass of LevelParent for testing
//        testLevel = new LevelParent("testBackground.jpg", 800, 600, 5) {
//            @Override
//            protected void checkIfGameOver() {
//                // Provide a simple implementation for testing
//            }
//
//            @Override
//            protected void spawnEnemyUnits() {
//                // Provide a simple implementation for testing
//            }
//
//            @Override
//            protected LevelView instantiateLevelView() {
//                return mock(LevelView.class); // Mock the LevelView
//            }
//        };
//
//        // Inject mocked dependencies
//        testLevel.setStage(mockStage);
//    }
//
//    @Test
//    void testInitializeScene() {
//        Scene scene = testLevel.initializeScene();
//        assertNotNull(scene, "Scene should be initialized");
//        assertEquals(800, scene.getHeight(), "Scene height should match the provided value");
//        assertEquals(600, scene.getWidth(), "Scene width should match the provided value");
//    }
//
//    @Test
//    void testStartGame() {
//        // Ensure the game starts without exceptions
//        assertDoesNotThrow(() -> testLevel.startGame(), "Starting the game should not throw exceptions");
//    }
//
//    @Test
//    void testRestartGame() {
//        testLevel.restartGame();
//
//        // Verify game state reset
//        assertEquals(0, testLevel.getActorManager().getEnemyUnits().size(), "Enemy units should be cleared on restart");
//        assertFalse(testLevel.getUserIsDestroyed(), "User should not be destroyed on restart");
//    }
//
//    @Test
//    void testGoToNextLevel() {
//        String nextLevelName = "com.example.demo.level.LevelTwo";
//
//        // Call the method
//        assertDoesNotThrow(() -> testLevel.goToNextLevel(nextLevelName), "Should transition to the next level without exceptions");
//    }
//
//    @Test
//    void testSpawnEnemyUnits() {
//        // Subclasses will implement, so mock or override in the subclass test
//        assertDoesNotThrow(() -> testLevel.spawnEnemyUnits(), "Spawning enemy units should not throw exceptions");
//    }
//
//    @Test
//    void testAddEnemyUnit() {
//        ActiveActor mockEnemy = mock(ActiveActor.class);
//
//        testLevel.addEnemyUnit(mockEnemy);
//
//        // Verify the enemy was added
//        assertTrue(testLevel.getActorManager().getEnemyUnits().contains(mockEnemy), "Enemy should be added to the actor manager");
//    }
//
//    @Test
//    void testUserSkillClearEnemyBullet() {
//        // Simulate the skill activation
//        testLevel.getActorManager().getEnemyProjectiles().add(mock(ActiveActor.class));
//        testLevel.userSkillClearEnemyBullet();
//
//        // Verify projectiles are cleared
//        assertEquals(0, testLevel.getActorManager().getEnemyProjectiles().size(), "All enemy projectiles should be cleared");
//    }
//
//    @Test
//    void testUpdateKillCount() {
//        // Simulate updating kill count
//        when(mockActorManager.getEnemyUnits()).thenReturn(mock(Group.class));
//        testLevel.updateKillCount();
//
//        // Verify kills increment
//        verify(mockUser, atLeastOnce()).incrementKillCount();
//    }
//
//    @Test
//    void testEdgeCases() {
//        // Edge case: User kills exactly at the kill target
//        when(mockUser.getNumberOfKills()).thenReturn(15);
//        assertTrue(testLevel.getUserIsDestroyed(), "Game should transition when user reaches kill target");
//
//        // Edge case: No enemies present
//        when(mockActorManager.getEnemyUnits().size()).thenReturn(0);
//        assertDoesNotThrow(() -> testLevel.spawnEnemyUnits(), "Spawning should handle zero enemies");
//
//        // Edge case: Cooldown text malformed
//        doThrow(new NumberFormatException("Malformed cooldown")).when(mockTextDisplay).updateSkillTimer(anyString());
//        assertDoesNotThrow(() -> testLevel.startCooldown(), "Cooldown should not crash on malformed text");
//    }
//}
