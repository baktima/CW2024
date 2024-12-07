		package com.example.demo.level;

		import java.io.IOException;
        import java.util.Objects;

        import com.example.demo.display.menu.GameOverMenu;
		import com.example.demo.display.TextDisplay;
		import com.example.demo.display.menu.PauseMenu;
		import com.example.demo.display.menu.WinMenu;
		import com.example.demo.level.levelViews.LevelView;
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

		/**
		 * Represents the base class for all game levels.
		 * Provides shared functionality such as managing user input, game state, spawning enemies,
		 * handling skill cooldowns, and transitioning between levels.
		 */
		public abstract class LevelParent{

			// Constants
			private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
			private static final int MILLISECOND_DELAY = 25;
			private static final int RESET_HEALTH = 5;
			private static final int SKILL_COOLDOWN = 10;

			// Scene Dimensions
			private final double screenHeight;
			private final double screenWidth;

			// State Variables
			private int currentNumberOfEnemies = 0;
			private boolean paused = false;
			private boolean canFire = true;
			private boolean canClearBullets = true;

			// Cached Menus
			private Parent cachedPauseMenu = null;
			private Parent cachedGameOverMenu = null;
			private Parent cachedWinMenu = null;

			// JavaFX Components
			private Stage stage;
			private final Scene scene;
			private final Group root;
			private final Group gamePlayRoot;
			private final Group menuRoot;
			private final Group backgroundRoot;
			private final ImageView background;

			// Game Entities
			private final UserPlane user;

			// Display Components
			private final TextDisplay textDisplay;
			private final LevelView levelView;

			// Managers and Timelines
			private GameLoopManager gameLoopManager;
			private InputManager inputManager;
			private final ActorManager actorManager;
			private Timeline cooldownTimeline;

			// Event Listeners
			private LevelChangeListener levelChangeListener;

			/**
			 * Constructs a new level with specified background image, dimensions, and initial player health.
			 *
			 * @param backgroundImageName the path to the background image.
			 * @param screenHeight the height of the screen.
			 * @param screenWidth the width of the screen.
			 * @param playerInitialHealth the initial health of the player's plane.
			 */
			protected LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
				// Initialize screen dimensions
				this.screenHeight = screenHeight;
				this.screenWidth = screenWidth;

				// Initialize root container for the scene
				root = new Group();
				scene = new Scene(root, screenWidth, screenHeight);

				// Initialize main game entities
				user = new UserPlane(playerInitialHealth);
				background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
				textDisplay = new TextDisplay();
				levelView = instantiateLevelView(); // Abstract method for level-specific view setup

				// Initialize supporting components
				gamePlayRoot = new Group();  // Root for gameplay elements
				menuRoot = new Group();      // Root for menu elements
				backgroundRoot = new Group(); // Root for background elements
				actorManager = new ActorManager(gamePlayRoot); // Manages game actors

				// Initialize functionality
				initializeTimeline();        // Set up the main game loop
				initializeCooldownTimeline(); // Set up the skill cooldown logic
				initializeInputHandler();    // Configure input handling for the game

				// Add components to the root group
				root.getChildren().addAll(backgroundRoot, gamePlayRoot, menuRoot);

				// Add text display to the root group
				textDisplay.addToRoot(root);
			}

			//-----------------------------------------------------------------

			/**
			 * Notifies the listener about a level change.
			 *
			 * @param nextLevelClassName the fully qualified class name of the next level.
			 */
			protected void notifyLevelChange(String nextLevelClassName) {
				if (levelChangeListener != null) {
					levelChangeListener.levelChange(nextLevelClassName);
				}
			}

			//------------------------------------------------------------------
			//initialization
			/**
			 * Initializes the main game loop.
			 */
			private void initializeTimeline() {
				gameLoopManager = new GameLoopManager(MILLISECOND_DELAY, this::updateScene);
			}

			/**
			 * Initializes the cooldown timeline for skills.
			 */
			private void initializeCooldownTimeline() {
				cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
					String labelText = textDisplay.getSkillTimerLabel().getText();

					if (labelText.startsWith("Cooldown: ")) {
						try {
							// Extract numeric part safely
							String numericPart = labelText.replace("Cooldown: ", "").replace("s", "").trim();
							if (!numericPart.matches("\\d+")) {
								throw new NumberFormatException("Non-numeric cooldown value: " + numericPart);
							}
							int currentCooldown = Integer.parseInt(numericPart);

							// Decrement cooldown
							currentCooldown--;
							if (currentCooldown > 0) {
								textDisplay.updateSkillTimer("Cooldown: " + currentCooldown + "s");
							} else {
								textDisplay.updateSkillTimer("Skill Ready press S to clear all projectile");
								canClearBullets = true; // Reset skill availability
								cooldownTimeline.stop(); // Stop the timeline once the cooldown is over
							}
						} catch (NumberFormatException ex) {
							System.err.println("Error parsing cooldown timer: " + ex.getMessage());
						}
					} else {
						System.err.println("Unexpected label text: " + labelText);
					}
				}));
				cooldownTimeline.setCycleCount(SKILL_COOLDOWN);
			}

			/**
			 * Initializes the input handler for managing key presses and releases.
			 */
			private void initializeInputHandler(){
				inputManager = new InputManager(user, this::togglePauseResume, this::fireWithCooldown, this::clearBulletWithCooldown);
			}

			/**
			 * Initializes the background image and its properties.
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
			 * Initializes friendly units in the game.
			 */
			protected void initializeFriendlyUnits() {
				actorManager.addFriendlyUnit(user);
			}

			//------------------------------------------------------------------
			//Scene and Level Management

			/**
			 * Sets up and returns the scene for the level.
			 *
			 * @return the configured scene.
			 */
			public Scene initializeScene() {

				initializeBackground();
				initializeFriendlyUnits();
				levelView.showHeartDisplay();

				textDisplay.updateSkillTimer("Cooldown: " + SKILL_COOLDOWN + "s"); // Set initial cooldown display
				canClearBullets = false; // Skill starts on cooldown
				startCooldown(); // Start initial cooldown

				scene.setUserData(this);
				return scene;
			}

			/**
			 * Starts the game loop.
			 */
			public void startGame() {
				background.requestFocus();
				gameLoopManager.start();
			}

			/**
			 * Restarts the level, resetting all entities and game state.
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

				user.respawn();

				// Clear the pause menu
				menuRoot.getChildren().clear();
				menuRoot.setVisible(false);
				paused = false;

				levelView.removeGameOverImage();

				// Restart the timeline
				gameLoopManager.playFromBeginning();
				cooldownTimeline.playFromStart();
			}

			/**
			 * Cleans up resources and transitions to the specified next level.
			 *
			 * @param levelName the fully qualified class name of the next level.
			 */
			public void goToNextLevel(String levelName) {
				cleanup();
				notifyLevelChange(levelName);
			}

			/**
			 * Cleans up resources and stops the game loop.
			 */
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

			/**
			 * Reset the user position, health, and skillCooldown.
			 */
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
			 * Abstract method to check whether the game is over.
			 * To be implemented by subclasses.
			 */
			protected abstract void checkIfGameOver();

			//-----------------------------------------------------------------------
			/**
			 * Abstract method to instantiate the level view.
			 * To be implemented by subclasses.
			 *
			 * @return the level-specific view.
			 */
			protected abstract LevelView instantiateLevelView();

			//------------------------------------------------
			//Gameplay

			/**
			 * Updates the scene, including enemy actions, actor collisions, game state, and game-over checks.
			 */
			private void updateScene() {
				handleEnemyActions();
				updateActorAndCollisions();
				updateGameState();
				checkIfGameOver();
			}

			/**
			 * Handles enemy actions such as spawning and firing projectiles.
			 */
			private void handleEnemyActions() {
				spawnEnemyUnits();
				generateEnemyFire();
				updateNumberOfEnemies();
				actorManager.handleEnemyPenetration();
			}

			/**
			 * Abstract method for spawning enemy units. To be implemented by subclasses.
			 */
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
			 */
			protected void spawnSpecialEnemies(double spawnProbability) {
				double positionMultiplier = 0.25;
				if (Math.random() < spawnProbability) {
					double newEnemySpecialInitialYPosition = Math.random() * getEnemyMaximumYPosition() * positionMultiplier;
					ActiveActor newEnemy = new BomberPlane(getScreenWidth(), newEnemySpecialInitialYPosition);
					addEnemyUnit(newEnemy);
				}
			}

			/**
			 * Spawns special-special enemies based on a given probability.
			 *
			 * @param spawnProbability The probability of spawning each special-special enemy.
			 */
			protected void spawnTankerEnemies(double spawnProbability) {
				if (Math.random() < spawnProbability) {
					double screenHeightMiddle = getEnemyMaximumYPosition() / 2; // Middle of the screen height
					double spawnRange = getEnemyMaximumYPosition() * 0.1; // 10% of the screen height
					double newTankerEnemyInitialYPosition = screenHeightMiddle - spawnRange + Math.random() * (2 * spawnRange);

					ActiveActor newEnemy = new TankerPlane(getScreenWidth(), newTankerEnemyInitialYPosition);
					addEnemyUnit(newEnemy);
				}
			}

			/**
			 * Generate the enemy projectile on the game display.
			 */
			private void generateEnemyFire() {
				actorManager.getEnemyUnits().forEach(enemy -> {
					if (enemy instanceof FighterPlane) {
						spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile());
					}
				});
			}

			/**
			 * Fires a projectile from an enemy unit.
			 *
			 * @param projectile the projectile to be fired.
			 */
			private void spawnEnemyProjectile(ActiveActor projectile) {
				if (projectile != null) {
					actorManager.addEnemyProjectile(projectile);
				}
			}

			/**
			 * Fires a projectile from the player's plane.
			 */
			private void fireProjectile() {
				ActiveActor projectile = user.fireProjectile();
				actorManager.addUserProjectile(projectile);
			}

			/**
			 * Fires a projectile with a cooldown.
			 */
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

			/**
			 * Clears all projectiles with a cooldown.
			 */
			private void clearBulletWithCooldown() {
				// Check if the skill is available
				if (!canClearBullets) {
					return; // Exit the method if the skill is on cooldown
				}

				// Trigger the skill
				userSkillClearEnemyBullet();
				canClearBullets = false; // Set the skill to unavailable
				textDisplay.updateSkillTimer("Cooldown: " + SKILL_COOLDOWN + "s");

				startCooldown();
			}

			/**
			 * initializing and starting the cooldown timer for the skill.
			 */
			private void startCooldown() {
				cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
					String timerText = textDisplay.getSkillTimerLabel().getText().replace("Cooldown: ", "").replace("s", "");
					int currentCooldown = Integer.parseInt(timerText);

					currentCooldown--;
					if (currentCooldown > 0) {
						textDisplay.updateSkillTimer("Cooldown: " + currentCooldown + "s");
					} else {
						textDisplay.updateSkillTimer("Skill Ready press S to clear all projectile");
						canClearBullets = true; // Reset skill availability
						cooldownTimeline.stop(); // Stop the cooldown timer
					}
				}));
				cooldownTimeline.setCycleCount(SKILL_COOLDOWN);
				cooldownTimeline.play();
			}

			/**
			 * Removes all bullets from the game scene (the method for clearing projectiles).
			 */
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

			/**
			 * Updates actor positions, checks collisions, and safely deletes destroyed entities.
			 */
			private void updateActorAndCollisions(){
				actorManager.updateActorsAndCollision();
			}

			/**
			 * Updates the game state, such as score and health display.
			 */
			private void updateGameState() {
				updateKillCount();
				updateLevelView();
			}

			/**
			 * Updates the user's kill count.
			 */
			private void updateKillCount() {
				for (int i = 0; i < currentNumberOfEnemies - actorManager.getEnemyUnits().size(); i++) {
					user.incrementKillCount();
					textDisplay.increment();
				}
			}

			/**
			 * Updates the level view, such as health display.
			 */
			private void updateLevelView() {
				levelView.removeHearts(user.getHealth());
			}

			/**
			 * Updates the current number of enemies on the screen.
			 */
			private void updateNumberOfEnemies() {
				currentNumberOfEnemies =actorManager.getEnemyUnits().size();
			}

			/**
			 * Adds an enemy unit to the game.
			 *
			 * @param enemy the enemy unit to add.
			 */
			protected void addEnemyUnit(ActiveActor enemy) {
				actorManager.addEnemyUnit(enemy);
			}

			//----------------------------------------------------------------------
			//GameState

			/**
			 * Toggles between pausing and resuming the game.
			 */
			private void togglePauseResume() {
				if (paused) {
					resumeGame();
				} else {
					pauseGame();
				}
				paused = !paused;
			}

			/**
			 * Pauses the game and displays the pause menu.
			 */
			private void pauseGame() {
				gameLoopManager.stop();
				cooldownTimeline.stop();
				displayPauseMenu();
			}

			/**
			 *Reset all the game entities.
			 */
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
			 * Resumes the game and hides the pause menu.
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

			/**
			 * Handles actions when the player wins the level.
			 */
			protected void winGame() {
				gameLoopManager.stop();
				cooldownTimeline.stop();
				levelView.showWinImage();
				cleanup();
				displayWinMenu();
			}

			/**
			 * Handles actions when the player loses the level.
			 */
			protected void loseGame() {
				gameLoopManager.stop();
				cooldownTimeline.stop();
				levelView.showGameOverImage();
				cleanup();
				displayGameOverMenu();
			}
			//------------------------------------------------------
			//Menu Management

			/**
			 * Displays the pause menu.
			 */
			private void displayPauseMenu() {
				displayMenu("pause");
			}

			/**
			 * Displays the game over menu.
			 */
			private void displayGameOverMenu(){
				displayMenu("gameOver");
			}

			/**
			 * Displays the win menu.
			 */
			private void displayWinMenu(){
				displayMenu("win");
			}

			/**
			 * Displays a menu based on the specified menu type.
			 *
			 * @param menuType the type of menu to display (pause, gameOver, or win).
			 */
			private void displayMenu(String menuType) {
				try {
					Parent menu = switch (menuType) {
                        case "pause" -> {
                            if (cachedPauseMenu == null) {
                                cachedPauseMenu = PauseMenu.showPauseMenu(this);
                            }
                            yield cachedPauseMenu;
                        }
                        case "gameOver" -> {
                            if (cachedGameOverMenu == null) {
                                cachedGameOverMenu = GameOverMenu.showGameOverMenu(this);
                            }
                            yield cachedGameOverMenu;
                        }
                        case "win" -> {
                            if (cachedWinMenu == null) {
                                cachedWinMenu = WinMenu.showWinMenu(this);
                            }
                            yield cachedWinMenu;
                        }
                        default -> throw new IllegalArgumentException("Invalid menu type: " + menuType);
                    };

                    menuRoot.getChildren().clear();
					menuRoot.getChildren().add(menu);
					menu.setVisible(true);
					menuRoot.setVisible(true);
					menu.requestFocus();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//-------------------------------------------------------
			//Input

			/**
			 * Handles key press events.
			 *
			 * @param kc the key code of the pressed key.
			 */
			private void handleKeyPress(KeyCode kc) {
				inputManager.handleKeyPress(kc);
			}

			/**
			 * Handles key release events.
			 *
			 * @param kc the key code of the released key.
			 */
			private void handleKeyRelease(KeyCode kc) {
				inputManager.handleKeyRelease(kc);
			}

			//---------------------------------------------------------
			//getter and setter

			/**
			 * Sets the level change listener.
			 *
			 * @param listener the listener to set.
			 */
			public void setLevelChangeListener(LevelChangeListener listener) {
				this.levelChangeListener = listener;
			}

			/**
			 * Returns the user plane.
			 *
			 * @return the user plane.
			 */
			protected UserPlane getUser() {
				return user;
			}

			/**
			 * Returns the root group of the scene.
			 *
			 * @return the root group.
			 */
			protected Group getRoot() {
				return root;
			}

			/**
			 * Returns the number of enemies currently in the game.
			 *
			 * @return the number of enemies.
			 */
			protected int getCurrentNumberOfEnemies() {
				return actorManager.getEnemyUnits().size();
			}

			/**
			 * Returns the actor manager.
			 *
			 * @return the actor manager.
			 */
			public ActorManager getActorManager(){
				return actorManager;
			}

			/**
			 * Returns the maximum Y position for enemies.
			 *
			 * @return the maximum Y position.
			 */
			protected double getEnemyMaximumYPosition() {
				//EnemyMax Y position = screen_height - screen height adjustment
				return screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
			}

			/**
			 * Returns the width of the screen.
			 *
			 * @return the screen width.
			 */
			public double getScreenWidth() {
				return screenWidth;
			}

			/**
			 * Checks if the user's plane is destroyed.
			 *
			 * @return true if the user's plane is destroyed, false otherwise.
			 */
			protected boolean getUserIsDestroyed() {
				return user.getIsDestroyed();
			}

			/**
			 * Return the current stage of the level.
			 *
			 * @return the stage value.
			 */
			public Stage getStage() {
				return stage;
			}

			//new
			/**
			 * Return the textDisplay.
			 *
			 * @return the textDisplay value.
			 */
			public TextDisplay getTextDisplay() {
				return textDisplay;
			}

			/**
			 * Set the current stage value.
			 */
			public void setStage(Stage stage){
				this.stage = stage;
			}


		}
