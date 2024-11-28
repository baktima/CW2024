package com.example.demo.level;

import com.example.demo.level.levelView.LevelView;
import com.example.demo.plane.EnemyPlane;
import com.example.demo.plane.EnemyPlaneSpecial;
import com.example.demo.actor.ActiveActor;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelTwo";
	private static final int TOTAL_ENEMIES = 6;
	private static final int KILLS_TO_ADVANCE = 2;
	private static final double ENEMY_SPAWN_PROBABILITY = .05;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final double SPECIAL_ENEMY_SPAWN_PROBABILITY = 0.0125;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget())
			goToNextLevel(NEXT_LEVEL);
	}

	//maybe moving this up to the parent class but with parameters since I want to use it again in levelThree
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = GetActorManager().GetCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActor newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}

			//limiting the spawn to upper half of the screen
			if (Math.random() < SPECIAL_ENEMY_SPAWN_PROBABILITY) {
				double newEnemySpecialInitialYPosition = Math.random() * getEnemyMaximumYPosition()/2;
				ActiveActor newEnemy = new EnemyPlaneSpecial(getScreenWidth(), newEnemySpecialInitialYPosition);
				addEnemyUnit(newEnemy);
			}
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
