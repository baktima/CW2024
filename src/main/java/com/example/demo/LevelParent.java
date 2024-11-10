package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
	
	private int currentNumberOfEnemies;
	private LevelView levelView;
	private boolean paused = false; 

	/**
	 * changing the constructor since there's a lot of redundant use of this.
	 * @param backgroundImageName, sceenHeight, screenWidth, playerInitialHealth
	 * 
	 * making it more readable 
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		int testing = 1; 
		
		//initializing all of the variables
		root = new Group();
		scene = new Scene(root, screenWidth, screenHeight);
		timeline = new Timeline();
		user = new UserPlane(playerInitialHealth);
		background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		levelView = instantiateLevelView();
		currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}
	

/**
 * most of these function will be used by the inherited levels 
 */
	private void initializeFriendlyUnits() {
		root.getChildren().add(user);
		
	}

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}
	
	/**
	 *@param levelName the name of the next level will be passed here 
	 * destroy the current level and change to the next level
	 * 
	 */

	public void goToNextLevel(String levelName) {
		//the change that makes the transition works 
		user.destroy();
		setChanged();
		notifyObservers(levelName);
	}

	private void updateScene() {
	    handleEnemyActions();
	    handleUserActions();
	    handleCollisions();
	    cleanUpActors();
	    updateGameState();
	    checkIfGameOver();
	}

	// Method to handle enemy-specific actions like spawning and firing
	private void handleEnemyActions() {
	    spawnEnemyUnits();
	    generateEnemyFire();
	    updateNumberOfEnemies();
	    handleEnemyPenetration();
	}

	// Method to update the movement and state of all actors
	private void handleUserActions() {
	    updateActors();
	}

	// Method to handle collisions between different actors
	private void handleCollisions() {
	    handleUserProjectileCollisions();
	    handleEnemyProjectileCollisions();
	    handlePlaneCollisions();
	}

	// Method to remove actors that have been destroyed
	private void cleanUpActors() {
	    removeAllDestroyedActors();
	}

	// Method to update various game state elements (e.g., score or health display)
	private void updateGameState() {
	    updateKillCount();
	    updateLevelView();
	}


	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}
	
	//adding a stopping and resume method to stop the timeline 
	private void togglePauseResume() {
	    if (paused) {
	        timeline.play();
	    } else {
	        timeline.pause();
	    }
	    paused = !paused;
	}


	/**
	 *initializing the background and handle custom inputs 
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
		background.setOnKeyReleased(e-> handleKeyRelease(e.getCode())); 
		root.getChildren().add(background);
	}
	
	/**
	 * @param KeyCode kc where it will receive inputs
	 * handle all the user keyboard inputs in game
	 * 
	 * */
	private void handleKeyPress(KeyCode kc) {
	    if (kc == KeyCode.UP) user.moveUp();
	    if (kc == KeyCode.DOWN) user.moveDown();
	    if (kc == KeyCode.SPACE) fireProjectile();
	    if (kc == KeyCode.ESCAPE) togglePauseResume();
	    
	}
	
	/**
	 * @param KeyCode kc where it will receive inputs
	 * for some reason this part doesn't work really well since the up will overtake the down 
	 * maybe will delete later on the future 
	 */
	
	private void handleKeyRelease(KeyCode kc) {
		if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	/**
	 * return the enemyUnits size 
	 * 
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}
