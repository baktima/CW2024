package com.example.demo.implementation;

/**
 * Represents an entity that can take damage and be destroyed.
 * <p>
 * This interface defines two key behaviors:
 * <ul>
 *   <li>{@code takeDamage()}: To apply damage to the entity.</li>
 *   <li>{@code destroy()}: To completely destroy the entity.</li>
 * </ul>
 * Classes implementing this interface should provide concrete implementations for
 * how damage is handled and what it means for the entity to be destroyed.
 * </p>
 */
public interface Destructible {

	/**
	 * Applies damage to the entity.
	 * The specifics of how damage is taken should be implemented
	 * by the class that implements this interface.
	 */
	void takeDamage();

	/**
	 * Destroys the entity, rendering it unusable or removing it from the system.
	 * The specifics of destruction should be implemented by the class
	 * that implements this interface.
	 */
	void destroy();
}
