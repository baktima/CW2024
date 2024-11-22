	package com.example.demo.level;

	import java.io.IOException;
	import java.util.*;
	import java.util.stream.Collectors;

	import com.example.demo.level.levelView.LevelView;
	import com.example.demo.actor.ActiveActorDestructible;
	import com.example.demo.controller.PauseMenuController;

	import com.example.demo.plane.FighterPlane;
	import com.example.demo.plane.UserPlane;
	import javafx.animation.*;
	import javafx.fxml.FXMLLoader;
	import javafx.scene.Group;
	import javafx.scene.Scene;
	import javafx.scene.image.*;
	import javafx.scene.input.*;
	import javafx.stage.Stage;
	import javafx.util.Duration;
	import javafx.scene.Parent;

	public abstract class LevelParent extends Observable {

		private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
		private static final int MILLISECOND_DELAY = 50;
		private final double screenHeight;
		private final double screenWidth;
		private final double enemyMaximumYPosition;

		private final Group root;
		private final Group gamePlayRoot;
		private final Group pauseMenuRoot;

		private final Timeline timeline;
		private final UserPlane user;
		private final Scene scene;
		private final ImageView background;

		private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
		private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
		private final List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
		private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();

		private int currentNumberOfEnemies;
		private final LevelView levelView;
		private boolean paused = false;
		private Parent cachedPauseMenu = null;

		//testing
		private Stage stage;
		private boolean canFire = true;

        /**
		 * changing the constructor since there's a lot of redundant use of this.
		 * @param playerInitialHealth passing the current player health.
		 * @param backgroundImageName passing the background file path name to the function.
		 * @param screenWidth passing the screen width value to the levelParent.
		 * @param screenHeight passing the screen height value to the levelParent.
		 * making it more readable
		 */
		public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {

			this.screenHeight = screenHeight;
			this.screenWidth = screenWidth;

			//initializing all the variables
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

			gamePlayRoot = new Group();
			pauseMenuRoot = new Group();

			root.getChildren().addAll(gamePlayRoot, pauseMenuRoot);

		}

		//------------------------------------------------------------------

		public Stage getStage() {
			return stage;
		}

		public void setStage(Stage stage){
			this.stage = stage;
        }

		public void cleanup() {
			// Stop animations, clear resources, etc.
			if (timeline != null) {
				timeline.stop();
			}
			root.getChildren().clear();
			deleteObservers();
		}

		//not yet now
		//based from the user reset maybe I can like put the reset heart display, so it can change dynamically;
		private void userReset() {
			// Reset user position to the initial state (e.g., bottom-center of the screen)
			user.setTranslateY(0);

			//resetting heart
			user.setHealth(5);

			//resting the heart display but i modified it from the class itself, maybe pass some value would be better
			levelView.resetHeartDisplay();


			// Reset user's projectiles
			userProjectiles.clear();
		}


	/**
	 * most of these function will be used by the inherited levels
	 */
		private void initializeFriendlyUnits() {
			gamePlayRoot.getChildren().add(user);

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
			//later maybe need to make it nicer.
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

		/**
		 *initializing the background and handle custom inputs
		 */
		private void initializeBackground() {
			background.setFocusTraversable(true);
			background.setFitHeight(screenHeight);
			background.setFitWidth(screenWidth);
			background.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
			background.setOnKeyReleased(e-> handleKeyRelease(e.getCode()));
			gamePlayRoot.getChildren().add(background);
		}

		/**
		 * @param kc where it will receive inputs
		 * handle all the user keyboard inputs in game
		 *
		 * */
		private void handleKeyPress(KeyCode kc) {
			if (kc == KeyCode.UP) user.moveUp();
			if (kc == KeyCode.DOWN) user.moveDown();
			if (kc == KeyCode.SPACE) fireWithCooldown();

			//testing
			if (kc == KeyCode.ESCAPE) togglePauseResume();

		}

		//adding a stopping and resume method to stop the timeline
		private void togglePauseResume() {
			if (paused) {
				resumeGame();
			} else {
				pauseGame();
			}
			paused = !paused;
		}

		private void pauseGame() {
			timeline.pause();
			displayPauseMenu();
		}

		public void restartGame() {
			timeline.stop(); // Stop the timeline

			// Reset game entities
			resetGameEntities();

			// Clear the pause menu
			pauseMenuRoot.getChildren().clear();
			pauseMenuRoot.setVisible(false);
			paused = false;

			root.getChildren().remove(pauseMenuRoot);
			root.getChildren().add(pauseMenuRoot);

			// Reinitialize background and ensure it's in focus
			gamePlayRoot.getChildren().remove(background);
			gamePlayRoot.getChildren().add(0, background);

			// Restart the timeline
			timeline.playFromStart();
		}

		private void resetGameEntities() {
			// Clear all game-related lists
			enemyUnits.clear();
			userProjectiles.clear();
			enemyProjectiles.clear();

			// Reset player position and health
			// Reset user position, health, etc.
			userReset();

			// Reset gameplay root
			gamePlayRoot.getChildren().clear();
			gamePlayRoot.getChildren().add(user); // Add user back to the scene

			// Reset other game state variables
			currentNumberOfEnemies = 0;
		}
		public void resumeGame() {
			timeline.play();
			pauseMenuRoot.getChildren().clear();
			paused = false;

			//this is the function that fix the pause menu supposidely
			gamePlayRoot.getChildren().remove(background);
			gamePlayRoot.getChildren().add(0, background);

		}

		private void displayPauseMenu() {
			try {
				if (cachedPauseMenu == null) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/PauseMenu.fxml"));
					cachedPauseMenu = loader.load();

					// Access the controller to pass necessary references
					PauseMenuController pauseController = loader.getController();
					pauseController.initialize(this);

					double centerX = (screenWidth - cachedPauseMenu.getLayoutBounds().getWidth()) / 4;
					double centerY = (screenHeight - cachedPauseMenu.getLayoutBounds().getHeight()) / 4;
					cachedPauseMenu.setLayoutX(centerX);
					cachedPauseMenu.setLayoutY(centerY);
				}

				// Ensure the pause menu is added to the root and visible
				pauseMenuRoot.getChildren().clear();
				pauseMenuRoot.getChildren().add(cachedPauseMenu);
				cachedPauseMenu.setVisible(true);
				pauseMenuRoot.setVisible(true);

				// Focus the pause menu
				cachedPauseMenu.requestFocus();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @param kc where it will receive inputs
		 * for some reason this part doesn't work really well since the up will overtake the down
		 * maybe will delete later on the future
		 */

		private void handleKeyRelease(KeyCode kc) {
			if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
		}

		private void fireProjectile() {
			ActiveActorDestructible projectile = user.fireProjectile();
			gamePlayRoot.getChildren().add(projectile);
			userProjectiles.add(projectile);
		}

		private void fireWithCooldown(){
			if (canFire){
				fireProjectile();
				canFire = false; // Prevent firing until cooldown expires

				// Start a cooldown timer using the Timeline
                int fireCooldown = 200;
                Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.millis(fireCooldown), e -> canFire = true));
				cooldownTimer.setCycleCount(1);
				cooldownTimer.play();
			}
		}

		private void generateEnemyFire() {
			enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
		}

		private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
			if (projectile != null) {
				gamePlayRoot.getChildren().add(projectile);
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
			gamePlayRoot.getChildren().removeAll(destroyedActors);
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
			gamePlayRoot.getChildren().add(enemy);
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
