		package com.example.demo.level;

		import java.io.IOException;

		import com.example.demo.display.menu.GameOverMenu;
		import com.example.demo.display.TextDisplay;
		import com.example.demo.display.menu.PauseMenu;
		import com.example.demo.display.menu.WinMenu;
		import com.example.demo.level.levelView.LevelView;
		import com.example.demo.actor.ActiveActor;
		import com.example.demo.manager.ActorManager;
		import com.example.demo.implementation.LevelChangeListener;

		import com.example.demo.manager.GameLoopManager;
		import com.example.demo.manager.InputManager;
		import com.example.demo.plane.*;
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
			private static final int MILLISECOND_DELAY = 20;
			private static final int RESET_HEALTH = 5;
			private final double screenHeight;
			private final double screenWidth;

			private final Group root;
			private final Group gamePlayRoot;
			private final Group menuRoot;
			private final Group backgroundRoot;

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
			private boolean canClearBullets = true;
			private final ActorManager actorManager;
			private final TextDisplay textDisplay;
			private LevelChangeListener levelChangeListener;
			private GameLoopManager gameLoopManager;
			private InputManager inputManager;
			private static final int SKILL_COOLDOWN = 10;
			private Timeline cooldownTimeline;

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
				user = new UserPlane(playerInitialHealth);
				background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
				textDisplay = new TextDisplay();

				levelView = instantiateLevelView();

				//initializing class
				initializeTimeline();
				initializeCooldownTimeline();
				initializeInputHandler();

				gamePlayRoot = new Group();
				menuRoot = new Group();
				backgroundRoot = new Group();
				actorManager = new ActorManager(gamePlayRoot);

				root.getChildren().addAll(backgroundRoot,gamePlayRoot, menuRoot);
				textDisplay.addToRoot(root);
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
				if (gameLoopManager != null) {
					gameLoopManager.stop();
				}
				menuRoot.getChildren().clear();

				menuRoot.setVisible(false);

				// Clear gameplay-specific elements
				gamePlayRoot.getChildren().clear();

				// Reset cached menus
				cachedPauseMenu = null;
				cachedGameOverMenu = null;
				cachedWinMenu = null;

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

			protected abstract void checkIfGameOver();

		//-----------------------------------------------------------------------
			//handle the spawning enemy
			protected abstract void spawnEnemyUnits();

			/**
			 * Spawns regular enemies based on a given probability.
			 *
			 * @param spawnProbability The probability of spawning each enemy.
			 */
			protected void spawnRegularEnemies(double spawnProbability) {
				if (Math.random() < spawnProbability) {
					double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
					ActiveActor newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
					addEnemyUnit(newEnemy);
				}
			}

			/**
			 * Spawns special enemies within a certain vertical range based on a given probability.
			 *
			 * @param spawnProbability The probability of spawning each special enemy.
			 * @param positionMultiplier The fraction of the screen height to limit spawning.
			 */
			protected void spawnSpecialEnemies(double spawnProbability, double positionMultiplier) {
				if (Math.random() < spawnProbability) {
					double newEnemySpecialInitialYPosition = Math.random() * getEnemyMaximumYPosition() * positionMultiplier;
					ActiveActor newEnemy = new EnemyPlaneSpecial(getScreenWidth(), newEnemySpecialInitialYPosition);
					addEnemyUnit(newEnemy);
				}
			}

			/**
			 * Spawns special-special enemies based on a given probability.
			 *
			 * @param spawnProbability The probability of spawning each special-special enemy.
			 */
			protected void spawnSpecialSpecialEnemies(double spawnProbability) {
				if (Math.random() < spawnProbability) {
					double newEnemySpecialInitialYPosition = Math.random() * getEnemyMaximumYPosition();
					ActiveActor newEnemy = new TankerPlane(getScreenWidth(), newEnemySpecialInitialYPosition);
					addEnemyUnit(newEnemy);
				}
			}
			//-----------------------------------------------------------------------------------------
			protected abstract LevelView instantiateLevelView();

			public Scene initializeScene() {
				initializeBackground();
				initializeFriendlyUnits();
				levelView.showHeartDisplay();
				scene.setUserData(this);
				return scene;
			}
			protected void initializeFriendlyUnits() {
				actorManager.addFriendlyUnit(user);
			}

			public void startGame() {
				background.requestFocus();
				gameLoopManager.start();
			}

			/**
			 *@param levelName the name of the next level will be passed here
			 * clean all the objects of the current level and change to the next level
			 *
			 */

			public void goToNextLevel(String levelName) {
				cleanup();
				notifyLevelChange(levelName);
			}

			private void updateScene() {

				handleEnemyActions();
				updateActorAndCollisions();
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

			//update actor and collisions and safely delete
			private void updateActorAndCollisions(){
				actorManager.updateActorsAndCollision();
			}

			// Method to update various game state elements (e.g., score or health display)
			private void updateGameState() {
				updateKillCount();
				updateLevelView();
			}

			private void initializeTimeline() {
				gameLoopManager = new GameLoopManager(MILLISECOND_DELAY, this::updateScene);
			}

			private void initializeInputHandler(){
				inputManager = new InputManager(user, this::togglePauseResume, this::fireWithCooldown, this::clearBulletWithCooldown);
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
				inputManager.handleKeyPress(kc);
			}

			private void handleKeyRelease(KeyCode kc) {
				inputManager.handleKeyRelease(kc);
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
				gameLoopManager.stop();
				cooldownTimeline.stop();
				displayPauseMenu();
			}

			/**
			 * Restart the game from the beginning of the current level
			 */
			public void restartGame() {
				gameLoopManager.stop();
				cooldownTimeline.stop();// Stop the timeline

				// Reset game entities
				resetGameEntities();

				initializeCooldownTimeline();

				// Reset cooldown text display
				textDisplay.updateSkillTimer("Cooldown: " + SKILL_COOLDOWN + "s");
				canClearBullets = false;

				user.setDestroyed(false);

				// Clear the pause menu
				menuRoot.getChildren().clear();
				menuRoot.setVisible(false);
				paused = false;

//				//remove the menuRoot and add it back (I don't think you actually need this part)
//				root.getChildren().remove(menuRoot);
//				root.getChildren().add(menuRoot);

//				// Reinitialize background and ensure it's in focus (trying this and I think you don't need this part to)
//				backgroundRoot.getChildren().remove(background);
//				backgroundRoot.getChildren().add(0, background);

				levelView.removeGameOverImage();

				// Restart the timeline
				gameLoopManager.playFromBeginning();
				cooldownTimeline.playFromStart();
			}

			private void resetGameEntities() {
				//clear all entities except user plane
				actorManager.getEnemyUnits().clear();
				actorManager.getUserProjectiles().clear();
				actorManager.getEnemyProjectiles().clear();

				// Reset player position, health, and kill count
				userReset();
				textDisplay.reset();

				//clear everything on the gamePlay plane and add the user
				actorManager.getGamePlayRoot().getChildren().clear();
				actorManager.getGamePlayRoot().getChildren().add(user);

				// Reset other game state variables
				currentNumberOfEnemies = 0;
				canFire = true;
			}

			/**
			 * Resume the gameplay
			 */
			public void resumeGame() {

				cooldownTimeline.play();
				gameLoopManager.start();

				if (cachedPauseMenu != null) {
					menuRoot.getChildren().remove(cachedPauseMenu);
					cachedPauseMenu = null; // Optional: Prevent reusing a lingering reference
				}

				menuRoot.getChildren().clear();
				menuRoot.setVisible(false);
				paused = false;

				background.requestFocus();

				//this is the code that fix the lingering pause menu
				backgroundRoot.getChildren().remove(background);
				backgroundRoot.getChildren().add(0, background);

			}

			private void displayPauseMenu() {
//				try {
//					cachedPauseMenu = PauseMenu.showPauseMenu(this);
//					menuRoot.getChildren().clear();
//					menuRoot.getChildren().add(cachedPauseMenu);
//					cachedPauseMenu.setVisible(true);
//					menuRoot.setVisible(true);
//					cachedPauseMenu.requestFocus();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				displayMenu("pause");
			}

			private void displayGameOverMenu(){
//				try {
//					if (cachedGameOverMenu == null) {
//						cachedGameOverMenu = GameOverMenu.showGameOverMenu(this);
//					}
//
//					// Ensure the pause menu is added to the root and visible
//					menuRoot.getChildren().clear();
//					menuRoot.getChildren().add(cachedGameOverMenu);
//					cachedGameOverMenu.setVisible(true);
//					menuRoot.setVisible(true);
//
//					// Focus the pause menu
//					cachedGameOverMenu.requestFocus();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				displayMenu("gameOver");
			}

			private void displayWinMenu(){
//				try {
//					if (cachedWinMenu == null) {
//						cachedWinMenu = WinMenu.showWinMenu(this);
//					}
//
//					// Ensure the pause menu is added to the root and visible
//					menuRoot.getChildren().clear();
//					menuRoot.getChildren().add(cachedWinMenu);
//					cachedWinMenu.setVisible(true);
//					menuRoot.setVisible(true);
//
//					// Focus the pause menu
//					cachedWinMenu.requestFocus();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				displayMenu("win");
			}

			private void displayMenu(String menuType) {
				try {
					Parent menu = null;
					switch (menuType) {
						case "pause":
							if (cachedPauseMenu == null) {
								cachedPauseMenu = PauseMenu.showPauseMenu(this);
							}
							menu = cachedPauseMenu;
							break;
						case "gameOver":
							if (cachedGameOverMenu == null) {
								cachedGameOverMenu = GameOverMenu.showGameOverMenu(this);
							}
							menu = cachedGameOverMenu;
							break;
						case "win":
							if (cachedWinMenu == null) {
								cachedWinMenu = WinMenu.showWinMenu(this);
							}
							menu = cachedWinMenu;
							break;
						default:
							throw new IllegalArgumentException("Invalid menu type: " + menuType);
					}

					menuRoot.getChildren().clear();
					menuRoot.getChildren().add(menu);
					menu.setVisible(true);
					menuRoot.setVisible(true);
					menu.requestFocus();
				} catch (IOException e) {
					e.printStackTrace();
				}
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

			private void clearBulletWithCooldown() {
				// Check if the skill is available
				if (!canClearBullets) {
					System.out.println("Skill is on cooldown!");
					return; // Exit the method if the skill is on cooldown
				}

				// Trigger the skill
				userSkillClearEnemyBullet();
				canClearBullets = false; // Set the skill to unavailable
				textDisplay.updateSkillTimer("Cooldown: " + SKILL_COOLDOWN + "s");

				// Start the cooldown timer
				cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
					int currentCooldown = Integer.parseInt(textDisplay.getSkillTimerLabel().getText().replace("Cooldown: ", "").replace("s", ""));
					currentCooldown--;
					if (currentCooldown > 0) {
						textDisplay.updateSkillTimer("Cooldown: " + currentCooldown + "s");
					} else {
						textDisplay.updateSkillTimer("Skill Ready press S to clear all projectile");
						canClearBullets = true; // Reset skill availability
					}
				}));
				cooldownTimeline.setCycleCount(SKILL_COOLDOWN);
				cooldownTimeline.play();
			}

			private void userSkillClearEnemyBullet() {
				Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
					// Remove all user projectiles from the gamePlayRoot individually
					for (ActiveActor projectile : actorManager.getUserProjectiles()) {
						actorManager.getGamePlayRoot().getChildren().remove(projectile);
					}
					actorManager.getUserProjectiles().clear(); // Clear the list after removing

					// Remove all enemy projectiles from the gamePlayRoot individually
					for (ActiveActor projectile : actorManager.getEnemyProjectiles()) {
						actorManager.getGamePlayRoot().getChildren().remove(projectile);
					}
					actorManager.getEnemyProjectiles().clear(); // Clear the list after removing
				}));
				timeline.setCycleCount(1); // Execute once
				timeline.play();
			}

			private void initializeCooldownTimeline() {
				cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
					int currentCooldown = Integer.parseInt(textDisplay.getSkillTimerLabel().getText()
							.replace("Cooldown: ", "").replace("s", ""));
					currentCooldown--;
					if (currentCooldown > 0) {
						textDisplay.updateSkillTimer("Cooldown: " + currentCooldown + "s");
					} else {
						textDisplay.updateSkillTimer("Skill Ready press S to clear all projectile");
						canClearBullets = true; // Reset skill availability
						cooldownTimeline.stop(); // Stop the timeline once the cooldown is over
					}
				}));
				cooldownTimeline.setCycleCount(SKILL_COOLDOWN); // Set the number of cycles
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
					textDisplay.increment();
				}
			}

			protected void winGame() {
				gameLoopManager.stop();
				cooldownTimeline.stop();
				levelView.showWinImage();
				cleanup();
				displayWinMenu();
			}

			protected void loseGame() {
				gameLoopManager.stop();
				cooldownTimeline.stop();
				levelView.showGameOverImage();
				cleanup();
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
				//EnemyMax Y position = screen_height - screen height adjustment
				return screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
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
