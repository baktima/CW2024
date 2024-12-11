package com.example.demo.level;

import com.example.demo.level.levelViews.LevelView;
import com.example.demo.plane.Boss;

/**
 * Represents the second level of the game. This level introduces a Boss enemy,
 * This class extends {@link LevelParent} and provides specific implementations
 * for Level Two's behavior, including boss management.
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/BossBackGround.png";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;

	/**
	 * Constructs a new LevelTwo instance with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth  the width of the game screen.
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		getTextDisplay().setBossHealthVisible(true);
		getTextDisplay().setCounterVisible(false);

		boss = new Boss(getTextDisplay());
	}

	/**
	 * Checks if the game is over. The game ends if the player's plane is destroyed
	 * or if the boss is defeated, in which case the game transitions to the next level.
	 */
	@Override
	protected void checkIfGameOver() {
		if (getUserIsDestroyed()) {
			loseGame();
		}
		else if (boss.getIsDestroyed()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns enemy units for Level Two. Ensures that the boss and its shield are
	 * added to the gameplay root if they are not already present.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			// Add the boss only if it hasn't been added yet
			if (!getActorManager().getGamePlayRoot().getChildren().contains(boss)) {
				getActorManager().addEnemyUnit(boss);
			}

			// Add shield image only if it hasn't been added yet
			if (!getActorManager().getGamePlayRoot().getChildren().contains(boss.getShieldImage())) {
				getActorManager().getGamePlayRoot().getChildren().add(boss.getShieldImage());
			}
		}
	}

	/**
	 * Instantiates the {@link LevelView} for Level Two with the player's initial health.
	 *
	 * @return the {@link LevelView} instance for Level Two.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		LevelView levelView;
		levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	/**
	 * Restarts the game for Level Two. Resets the boss in addition to other
	 * game state resets defined in the parent class.
	 */
	@Override
	public void restartGame(){

		super.restartGame();
		boss.reset();
		getTextDisplay().updateBossHealth(boss.getHealth()); // Reset boss health display

	}

}
