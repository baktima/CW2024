package com.example.demo.level;

import com.example.demo.level.levelView.LevelView;
import com.example.demo.plane.Boss;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/BossBackGround.png";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
	}

	@Override
	protected void checkIfGameOver() {
		if (getUserIsDestroyed()) {
			loseGame();
		}
		else if (boss.getIsDestroyed()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

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

	@Override
	protected LevelView instantiateLevelView() {
		LevelView levelView;
		levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	@Override
	public void restartGame(){

		super.restartGame();
		boss.reset();

	}

}
