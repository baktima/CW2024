	package com.example.demo.level;

	import java.io.IOException;

	import com.example.demo.display.GameOverMenu;
	import com.example.demo.display.KillDisplay;
	import com.example.demo.display.PauseMenu;
	import com.example.demo.display.WinMenu;
	import com.example.demo.level.levelView.LevelView;
    import com.example.demo.actor.ActiveActor;
	import com.example.demo.manager.ActorManager;
	import com.example.demo.implementation.LevelChangeListener;

	import com.example.demo.plane.FighterPlane;
	import com.example.demo.plane.UserPlane;
	import javafx.animation.*;
    import javafx.scene.Group;
	import javafx.scene.Scene;
	import javafx.scene.image.*;
	import javafx.scene.input.*;
	import javafx.stage.Stage;
	import javafx.util.Duration;
	import javafx.scene.Parent;

	public abstract class LevelParent{

		private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
		private static final int MILLISECOND_DELAY = 50;
		private static final int RESET_HEALTH = 5;
		private final double screenHeight;
		private final double screenWidth;
		private final double enemyMaximumYPosition;

		private final Group root;
		private final Group gamePlayRoot;
		private final Group menuRoot;
		private final Group backgroundRoot;

		private final Timeline timeline;
		private final UserPlane user;
		private final Scene scene;
		private final ImageView background;

		private int currentNumberOfEnemies = 0;
		private final LevelView levelView;
		private boolean paused = false;
		private Parent cachedPauseMenu = null;
		private Parent cachedGameOverMenu = null;
		private Parent cachedWinMenu = null;

		//testing
		private Stage stage;
		private boolean canFire = true;
		private final ActorManager actorManager;
		private final KillDisplay killDisplay;
		private LevelChangeListener levelChangeListener;

        /**
		 * changing the constructor since there's a lot of redundant use of this.
		 * @param playerInitialHealth passing the current player health.
		 * @param backgroundImageName passing the background file path name to the function.
		 * @param screenWidth passing the screen width value to the levelParent.
		 * @param screenHeight passing the screen height value to the levelParent.
		 * making it more readable
		 */
		protected LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {

			this.screenHeight = screenHeight;
			this.screenWidth = screenWidth;

			//initializing all the variables
			root = 	new Group();
			scene = new Scene(root, screenWidth, screenHeight);
			timeline = new Timeline();
			user = new UserPlane(playerInitialHealth);
			background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
			killDisplay = new KillDisplay();


			enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
			levelView = instantiateLevelView();

			initializeTimeline();

			gamePlayRoot = new Group();
			menuRoot = new Group();
			backgroundRoot = new Group();

			//_____
			actorManager = new ActorManager(gamePlayRoot);
			actorManager.updateActors();
			//PauseMenuManager pauseMenuManager = new PauseMenuManager(gamePlayRoot, timeline, gamePlayRoot, background,this);
			//-------

			root.getChildren().addAll(backgroundRoot,gamePlayRoot, menuRoot);
			killDisplay.addToRoot(root);

		}

		//-----------------------------------------------------------------
		public void setLevelChangeListener(LevelChangeListener listener) {
			this.levelChangeListener = listener;
		}

		protected void notifyLevelChange(String nextLevelClassName) {
			if (levelChangeListener != null) {
				levelChangeListener.levelChange(nextLevelClassName);
			}
		}

		//------------------------------------------------------------------

		public void cleanup() {
			// Stop animations, clear resources, etc.
			if (timeline != null) {
				timeline.stop();
			}
			root.getChildren().clear();
		}

		private void userReset() {

			// Reset user position to the initial state (e.g., bottom-center of the screen)
			user.setTranslateY(0);

			//resetting heart
			user.setHealth(RESET_HEALTH);

			//resting the heart display, but I modified it from the class itself, maybe pass some value would be better
			levelView.resetHeartDisplay();

			// Reset user's projectiles
			actorManager.getUserProjectiles().clear();
			user.setNumberOfKills(0);
		}


	/**
	 * most of these function will be used by the inherited levels
	 */
	protected void initializeFriendlyUnits() {
		actorManager.addFriendlyUnit(user);
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
			cleanup();
			notifyLevelChange(levelName);
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
			actorManager.handleEnemyPenetration();

		}

		// Method to update the movement and state of all actors
		private void handleUserActions() {
			actorManager.updateActors();
		}

		// Method to handle collisions between different actors
		private void handleCollisions() {
			actorManager.handleUserProjectileCollisions();
			actorManager.handleEnemyProjectileCollisions();
			actorManager.handlePlaneCollisions();
		}

		// Method to remove actors that have been destroyed
		private void cleanUpActors() {
			actorManager.removeAllDestroyedActors();
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
			backgroundRoot.getChildren().add(background);
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

		/**
		 * Pause the game
		 */
		private void pauseGame() {
			timeline.pause();
			displayPauseMenu();
		}

		/**
		 * Restart the game from the beginning of the current level
		 */
		public void restartGame() {
			timeline.stop(); // Stop the timeline

			// Reset game entities
			resetGameEntities();

			user.setDestroyed(false);

			// Clear the pause menu
			menuRoot.getChildren().clear();
			menuRoot.setVisible(false);
			paused = false;

			root.getChildren().remove(menuRoot);
			root.getChildren().add(menuRoot);
			levelView.removeGameOverImage();
			levelView.removeWinImage();

			// Reinitialize background and ensure it's in focus
			backgroundRoot.getChildren().remove(background);
			backgroundRoot.getChildren().add(0, background);

			// Restart the timeline
			timeline.playFromStart();
		}

		private void resetGameEntities() {
			//clear all entities except user plane
			actorManager.getEnemyUnits().clear();
			actorManager.getUserProjectiles().clear();
			actorManager.getEnemyProjectiles().clear();

			// Reset player position, health, and kill count
			userReset();
			killDisplay.reset();

			//clear everything on the gamePlay plane and add the user
			actorManager.getGamePlayRoot().getChildren().clear();
			actorManager.getGamePlayRoot().getChildren().add(user);

			// Reset other game state variables
			currentNumberOfEnemies = 0;
		}

		/**
		 * Resume the gameplay
		 */
		public void resumeGame() {
			timeline.play();
			menuRoot.getChildren().clear();
			paused = false;

			//this is the code that fix the lingering pause menu
			backgroundRoot.getChildren().remove(background);
			backgroundRoot.getChildren().add(0, background);
		}

		private void displayPauseMenu() {
			try {
				if (cachedPauseMenu == null) {
					cachedPauseMenu = PauseMenu.showPauseMenu(this);
				}

				// Ensure the pause menu is added to the root and visible
				menuRoot.getChildren().clear();
				menuRoot.getChildren().add(cachedPauseMenu);
				cachedPauseMenu.setVisible(true);
				menuRoot.setVisible(true);

				// Focus the pause menu
				cachedPauseMenu.requestFocus();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void displayGameOverMenu(){
			try {
				if (cachedGameOverMenu == null) {
					cachedGameOverMenu = GameOverMenu.showGameOverMenu(this);
				}

				// Ensure the pause menu is added to the root and visible
				menuRoot.getChildren().clear();
				menuRoot.getChildren().add(cachedGameOverMenu);
				cachedGameOverMenu.setVisible(true);
				menuRoot.setVisible(true);

				// Focus the pause menu
				cachedGameOverMenu.requestFocus();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void displayWinMenu(){
			try {
				if (cachedWinMenu == null) {
					cachedWinMenu = WinMenu.showWinMenu(this);
				}

				// Ensure the pause menu is added to the root and visible
				menuRoot.getChildren().clear();
				menuRoot.getChildren().add(cachedWinMenu);
				cachedWinMenu.setVisible(true);
				menuRoot.setVisible(true);

				// Focus the pause menu
				cachedWinMenu.requestFocus();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * @param kc where it will receive inputs
		 */

		private void handleKeyRelease(KeyCode kc) {
			if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
		}

		private void fireProjectile() {
			ActiveActor projectile = user.fireProjectile();
			actorManager.addUserProjectile(projectile);
		}

		private void fireWithCooldown(){
			if (canFire){
				fireProjectile();
				canFire = false; // Prevent firing until cooldown expires

				// Start a cooldown timer using the Timeline
                int fireCooldown = 100;
                Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.millis(fireCooldown), e -> canFire = true));
				cooldownTimer.setCycleCount(1);
				cooldownTimer.play();
			}
		}

		private void generateEnemyFire() {
			actorManager.getEnemyUnits().forEach(enemy -> {
				if (enemy instanceof FighterPlane) {
					spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile());
				}
			});
		}

		private void spawnEnemyProjectile(ActiveActor projectile) {
			if (projectile != null) {
				actorManager.addEnemyProjectile(projectile);
			}
		}

		private void updateLevelView() {
			levelView.removeHearts(user.getHealth());
		}

		private void updateKillCount() {
			for (int i = 0; i < currentNumberOfEnemies - actorManager.getEnemyUnits().size(); i++) {
				user.incrementKillCount();
				killDisplay.increment();
			}
		}

		protected void winGame() {
			timeline.stop();
			levelView.showWinImage();

			displayWinMenu();
		}

		protected void loseGame() {
			timeline.stop();
			levelView.showGameOverImage();

			displayGameOverMenu();
		}

		private void updateNumberOfEnemies() {
			currentNumberOfEnemies =actorManager.getEnemyUnits().size();
		}

		protected void addEnemyUnit(ActiveActor enemy) {
			actorManager.addEnemyUnit(enemy);
		}

		//getter and setter

		protected UserPlane getUser() {
			return user;
		}

		protected Group getRoot() {
			return root;
		}

		/**
		 * return the enemyUnits size
		 */
		protected int getCurrentNumberOfEnemies() {
			return actorManager.getEnemyUnits().size();
		}

		public ActorManager getActorManager(){
			return actorManager;
		}

		protected double getEnemyMaximumYPosition() {
			return enemyMaximumYPosition;
		}

		public double getScreenWidth() {
			return screenWidth;
		}

		protected boolean getUserIsDestroyed() {
			return user.getIsDestroyed();
		}

		public Stage getStage() {
			return stage;
		}

		public void setStage(Stage stage){
			this.stage = stage;
		}

	}
