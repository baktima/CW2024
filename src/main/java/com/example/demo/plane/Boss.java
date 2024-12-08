package com.example.demo.plane;

import com.example.demo.display.TextDisplay;
import com.example.demo.display.menu.MainMenu;
import com.example.demo.projectile.BossProjectile;
import com.example.demo.display.ShieldImage;
import com.example.demo.actor.ActiveActor;

import java.util.*;

/**
 * Represents the Boss character in the game.
 * <p>
 * The Boss class extends {@link FighterPlane} and introduces unique behaviors such as
 * shield activation, projectile firing, and vertical zig-zag movement patterns.
 * </p>
 */
public class Boss extends FighterPlane {
	// Constants related to Boss appearance and initial position
	private static final String IMAGE_NAME = "BossPlaneSlug.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final int IMAGE_HEIGHT = 200;

	// Constants related to movement and position constraints
	private static final double HORIZONTAL_VELOCITY = 0;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_LOWER_BOUND = 475;

	// Constants related to projectiles
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;

	// Constants related to shields
	private static final int SHIELD_X_POSITION_OFFSET = 30;
	private static final int SHIELD_Y_POSITION_OFFSET = 50;
	private static final int SHIELD_ON_DURATION = 100; // Frames for shield ON
	private static final int SHIELD_OFF_DURATION = 100; // Frames for shield OFF

	// Constants related to health
	private static final int INITIAL_HEALTH = 50;
	private static int health = 50; // Static health for Boss

	// Instance variables related to movement
	private final List<Integer> movePattern;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;

	// Instance variables related to shield
	private boolean isShielded;
	private int shieldStateFrames;
	private final ShieldImage shieldImage;

	//textDisplay
	private final TextDisplay textDisplay;

	/**
	 * Constructs a new {@code Boss} instance with predefined initial positions, health, and behaviors.
	 *
	 * @param textDisplay The {@link TextDisplay} instance used to update and display the boss's health and other text-related information.
	 */

	public Boss(TextDisplay textDisplay) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, health);

		this.textDisplay = textDisplay;
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		isShielded = false;
		initializeMovePattern();

		textDisplay.updateBossHealth(health);

		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
	}

	//initialize

	/**
	 * Initializes the movement pattern for the Boss, which consists of vertical zigzag moves.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	//update method
	/**
	 * Updates the Boss's position based on its movement pattern and ensures it remains within screen bounds.
	 */
	@Override
	public void updatePosition() {
		moveVertically(getNextMove());

		// Calculate the current position of the Boss
		double currentPositionY = getLayoutY() + getTranslateY();
		double currentPositionX = getLayoutX() + getTranslateX();

		// Ensure the Boss stays within the screen bounds (Y-axis)
		if (currentPositionY < 0) { // Top bound
			setTranslateY(-getLayoutY());
		} else if (currentPositionY > Y_POSITION_LOWER_BOUND) { // Bottom bound
			setTranslateY(Y_POSITION_LOWER_BOUND - getLayoutY());
		}

		// Ensure the Boss stays within the screen bounds (X-axis, if needed)
		if (currentPositionX < 0) { // Left bound
			setTranslateX(-getLayoutX());
		} else if (currentPositionX > MainMenu.getScreenWidth() - IMAGE_HEIGHT) { // Right bound (screen width - boss width)
			setTranslateX(MainMenu.getScreenWidth() - IMAGE_HEIGHT - getLayoutX());
		}

		updateShieldPosition();
	}

	/**
	 * Updates the shield's position to match the Boss's current position.
	 */
	private void updateShieldPosition() {
		if (shieldImage != null) {
			shieldImage.setLayoutX(this.getLayoutX()-SHIELD_X_POSITION_OFFSET + this.getTranslateX());
			shieldImage.setLayoutY(this.getLayoutY()-SHIELD_Y_POSITION_OFFSET + this.getTranslateY());
		}
	}

	/**
	 * Updates the shield's state (activates or deactivates based on timers).
	 */
	private void updateShield() {
		shieldStateFrames++;
		if (isShielded && shieldStateFrames >= SHIELD_ON_DURATION) {
			deactivateShield(); // Turn off shield after ON duration
		} else if (!isShielded && shieldStateFrames >= SHIELD_OFF_DURATION) {
			activateShield(); // Turn on shield after OFF duration
		}
	}

	/**
	 * Updates the current boss instance including the shield.
	 */
	@Override
	public void updateActor() {
		super.updateActor();
		updateShield();
	}

	//shield management
	/**
	 * Activates the Boss's shield and resets the shield state timer.
	 */
	private void activateShield() {
		isShielded = true;
		shieldStateFrames = 0;
		shieldImage.showShield();
	}

	/**
	 * Deactivates the Boss's shield and resets the shield state timer.
	 */
	private void deactivateShield() {
		isShielded = false;
		shieldStateFrames = 0;

		shieldImage.hideShield();
	}

	//movement management

	/**
	 * Retrieves the next movement in the Boss's movement pattern.
	 *
	 * @return The vertical velocity for the next frame.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	//projectile management

	/**
	 * Fires a projectile from the Boss, if firing conditions are met.
	 *
	 * @return A {@link BossProjectile} instance if fired, otherwise {@code null}.
	 */
	@Override
	public ActiveActor fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Determines whether the Boss fires a projectile in the current frame.
	 *
	 * @return {@code true} if the Boss fires a projectile, otherwise {@code false}.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial Y position for a projectile fired by the Boss.
	 *
	 * @return The Y position for the projectile.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	//damage
	/**
	 * Decreases the Boss's health when it takes damage, unless the shield is active.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
			//need to use getHealth() so it can update dynamically.
			textDisplay.updateBossHealth(getHealth());


		}
	}

	//utility

	/**
	 * Resets the Boss to its initial state, including health, position, shield, and movement pattern.
	 */
	public void reset() {
		// Reset health
		super.setHealth(INITIAL_HEALTH);

		// Reset position
		setTranslateY(0);

		// Reset shield status
		isShielded = false;
		shieldImage.hideShield();

		// Reset movement pattern
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		Collections.shuffle(movePattern);
	}

	//getter and setter

	/**
	 * Retrieves the shield image associated with the Boss.
	 *
	 * @return The {@link ShieldImage} of the Boss.
	 */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}

	/**
	 * Retrieves the horizontal velocity of the Boss.
	 *
	 * @return The horizontal velocity.
	 */
	@Override
	public double getHorizontalVelocity() {
		return HORIZONTAL_VELOCITY;
	}
}