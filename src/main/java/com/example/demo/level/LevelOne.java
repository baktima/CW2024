package com.example.demo.level;

import com.example.demo.level.levelView.LevelView;
import com.example.demo.plane.EnemyPlane;
import com.example.demo.plane.EnemyPlaneSpecial;
import com.example.demo.actor.ActiveActor;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/MetalSlugBackground1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 1;
	private static final double ENEMY_SPAWN_PROBABILITY = .05;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final double SPECIAL_ENEMY_SPAWN_PROBABILITY = 0.0125;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void checkIfGameOver() {
		if (getUserIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	//maybe moving this up to the parent class but with parameters since I want to use it again in levelThree
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getActorManager().getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			spawnRegularEnemies(ENEMY_SPAWN_PROBABILITY);
			spawnSpecialEnemies(SPECIAL_ENEMY_SPAWN_PROBABILITY, 0.25); // Upper half
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	@Override
	public void restartGame(){
		super.restartGame();
		getUser().setNumberOfKills(0);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
