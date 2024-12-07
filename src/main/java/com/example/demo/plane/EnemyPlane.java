	package com.example.demo.plane;
	
	import com.example.demo.projectile.EnemyProjectile;
	import com.example.demo.actor.ActiveActor;

	/**
	 * Represents a standard enemy plane in the game.
	 * <p>
	 * The EnemyPlane can move horizontally, fire projectiles, and take damage. It has
	 * a specific fire rate and initial health. A specialized constructor is also provided
	 * for creating variations of enemy planes with custom attributes.
	 * </p>
	 */
	public class EnemyPlane extends FighterPlane {
	
		private static final String IMAGE_NAME = "EnemyPlane1.png";
		private static final int IMAGE_HEIGHT = 70;
		private static final int HORIZONTAL_VELOCITY = -5;
		private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
		private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
		private static final int INITIAL_HEALTH = 1;
		private static final double FIRE_RATE = .01;

		/**
		 * Constructs an EnemyPlane with default attributes.
		 *
		 * @param initialXPos The initial X position of the plane.
		 * @param initialYPos The initial Y position of the plane.
		 */
		public EnemyPlane(double initialXPos, double initialYPos) {
			super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		}

		/**
		 * Fires a projectile from the EnemyPlane.
		 * <p>
		 * The firing mechanism is determined probabilistically based on the fire rate.
		 * If the plane fires a projectile in the current frame, an {@link EnemyProjectile}
		 * instance is created with the calculated position.
		 * </p>
		 *
		 * @return An {@link EnemyProjectile} if the plane fires, otherwise {@code null}.
		 */
		@Override
		public ActiveActor fireProjectile() {
				if (Math.random() < FIRE_RATE) {
					double projectileXPosition = getProjectileXPosition(getProjectileXPositionOffset());
					double projectileYPosition = getProjectileYPosition(getProjectileYPositionOffset());
					return new EnemyProjectile(projectileXPosition, projectileYPosition);
				}
			return null;
		}

		/**
		 * Gets the X position offset for projectiles fired by the plane.
		 *
		 * @return The X position offset for projectiles.
		 */
		public static double getProjectileXPositionOffset() {
			return PROJECTILE_X_POSITION_OFFSET;
		}

		/**
		 * Gets the Y position offset for projectiles fired by the plane.
		 *
		 * @return The Y position offset for projectiles.
		 */
		public static double getProjectileYPositionOffset() {
			return PROJECTILE_Y_POSITION_OFFSET;
		}

		/**
		 * Gets the horizontal velocity of the EnemyPlane.
		 *
		 * @return The horizontal velocity, which is a constant value.
		 */
		@Override
		public double getHorizontalVelocity() {
			return HORIZONTAL_VELOCITY;
		}
	
	}
