package com.example.demo.level;

import com.example.demo.level.levelViews.LevelView;

/**
 * Represents the first level of the game. This class extends {@link LevelParent}
 * and provides specific implementations for Level One's behavior, including enemy spawning,
 * level advancement conditions, and initializing level-specific views.
 */
public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/MetalSlugBackground1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 15;
	private static final double ENEMY_SPAWN_PROBABILITY = .05;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final double BOMBER_ENEMY_SPAWN_PROBABILITY = 0.0125;

	/**
	 * Constructs a new LevelOne instance with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth  the width of the game screen.
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		getTextDisplay().setBossHealthVisible(false);
	}

	/**
	 * Checks if the game is over by evaluating if the player has been destroyed
	 * or if the kill target has been reached. If either condition is met, transitions
	 * to the appropriate game state (game over or next level).
	 */
	@Override
	protected void checkIfGameOver() {
		if (getUserIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns enemy units based on predefined probabilities and screen constraints.
	 * Includes normal enemies and bomber enemies.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getActorManager().getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			spawnRegularEnemies(ENEMY_SPAWN_PROBABILITY);
			spawnBomberEnemies(BOMBER_ENEMY_SPAWN_PROBABILITY); // Upper half
		}
	}

	/**
	 * Instantiates the {@link LevelView} for Level One with the player's initial health.
	 *
	 * @return the {@link LevelView} instance for Level One.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Restarts the game for Level One. Resets the user's number of kills in addition
	 * to other game state resets defined in the parent class.
	 */
	@Override
	public void restartGame(){
		super.restartGame();
		getUser().setNumberOfKills(0);
	}

	/**
	 * Checks if the player has reached the required number of kills to advance
	 * to the next level.
	 *
	 * @return {@code true} if the player has achieved the required number of kills; {@code false} otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
