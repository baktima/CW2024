//package com.example.demo.level;
//
//import com.example.demo.level.levelViews.LevelView;
//import com.example.demo.plane.UserPlane;
//import com.example.demo.manager.ActorManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
////lots of the method were private or non-existence from internet.
//class LevelOneTest {
//
//    private LevelOne levelOne;
//    private UserPlane mockUserPlane;
//    private ActorManager mockActorManager;
//
//    @BeforeEach
//    void setUp() {
//        // Create mock instances of required dependencies
//        mockUserPlane = mock(UserPlane.class);
//        mockActorManager = mock(ActorManager.class);
//
//        // Initialize LevelOne with mock dependencies
//        levelOne = new LevelOne(800, 600);
//        levelOne.setActorManager(mockActorManager);
//        levelOne.setUser(mockUserPlane);
//    }
//
//    @Test
//    void testInitialSetup() {
//        // Verify initial setup
//        assertNotNull(levelOne, "LevelOne should be initialized properly.");
//        assertFalse(levelOne.getTextDisplay().isBossHealthVisible(), "Boss health should not be visible in LevelOne.");
//    }
//
//    @Test
//    void testCheckIfGameOver_PlayerDestroyed() {
//        // Simulate user destroyed
//        when(mockUserPlane.getIsDestroyed()).thenReturn(true);
//
//        // Call method under test
//        levelOne.checkIfGameOver();
//
//        // Verify that the game transitions to the game over state
//        verify(mockActorManager).loseGame();
//    }
//
//    @Test
//    void testCheckIfGameOver_KillTargetReached() {
//        // Simulate user reaching the kill target
//        when(mockUserPlane.getNumberOfKills()).thenReturn(15);
//
//        // Call method under test
//        levelOne.checkIfGameOver();
//
//        // Verify that the game transitions to the next level
//        verify(mockActorManager).goToNextLevel("com.example.demo.level.LevelTwo");
//    }
//
//    @Test
//    void testCheckIfGameOver_NoConditionMet() {
//        // Simulate user not being destroyed and not reaching kill target
//        when(mockUserPlane.getIsDestroyed()).thenReturn(false);
//        when(mockUserPlane.getNumberOfKills()).thenReturn(10);
//
//        // Call method under test
//        levelOne.checkIfGameOver();
//
//        // Verify that no transitions occur
//        verify(mockActorManager, never()).loseGame();
//        verify(mockActorManager, never()).goToNextLevel(anyString());
//    }
//
//    @Test
//    void testSpawnEnemyUnits() {
//        // Simulate fewer enemies than the total allowed
//        when(mockActorManager.getCurrentNumberOfEnemies()).thenReturn(2);
//
//        // Call method under test
//        levelOne.spawnEnemyUnits();
//
//        // Verify regular and special enemy spawning
//        verify(mockActorManager, atLeast(1)).spawnRegularEnemies(anyDouble());
//        verify(mockActorManager, atLeast(1)).spawnSpecialEnemies(anyDouble());
//    }
//
//    @Test
//    void testUserHasReachedKillTarget() {
//        // Simulate user having enough kills to advance
//        when(mockUserPlane.getNumberOfKills()).thenReturn(15);
//
//        // Call private method through reflection
//        boolean result = levelOne.userHasReachedKillTarget();
//
//        // Verify result
//        assertTrue(result, "User should reach kill target when they have the required kills.");
//
//        // Edge Case: Verify that kill target is not reached when kills are below the target
//        when(mockUserPlane.getNumberOfKills()).thenReturn(14);
//        result = levelOne.userHasReachedKillTarget();
//        assertFalse(result, "User should not reach kill target when kills are below the required number.");
//    }
//
//    @Test
//    void testRestartGame() {
//        // Simulate restarting the game
//        levelOne.restartGame();
//
//        // Verify that the user's kills are reset
//        verify(mockUserPlane).setNumberOfKills(0);
//
//        // Edge Case: Verify that health and game state reset properly
//        assertEquals(5, mockUserPlane.getHealth(), "Player health should reset to initial value.");
//    }
//
//    @Test
//    void testInstantiateLevelView() {
//        // Verify that a LevelView instance is created correctly
//        LevelView levelView = levelOne.instantiateLevelView();
//        assertNotNull(levelView, "LevelView should be instantiated for LevelOne.");
//    }
//}
