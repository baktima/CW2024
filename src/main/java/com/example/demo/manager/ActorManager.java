package com.example.demo.manager;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actor.ActiveActor;
import com.example.demo.display.menu.MainMenu;
import javafx.scene.Group;

/**
 * Manages the actors in the game, including friendly units, enemy units, and projectiles.
 * Handles the addition, update, removal, and collision detection for all actors in the game.
 *
 * <p>This class is responsible for managing the state and interactions between all active actors
 * in the game, such as the player, enemies, and projectiles. It ensures that actors are updated,
 * destroyed when necessary, and checks for collisions between various actor types.</p>
 */
public class ActorManager {
    private final List<ActiveActor> friendlyUnits = new ArrayList<>();
    private final List<ActiveActor> enemyUnits = new ArrayList<>();
    private final List<ActiveActor> userProjectiles = new ArrayList<>();
    private final List<ActiveActor> enemyProjectiles = new ArrayList<>();
    private final Group gamePlayRoot;

    /**
     * Constructs an ActorManager that manages most of the actors on the gameplay.
     *
     * @param gamePlayRoot the root node in the scene graph where actors will be added
     */
    public ActorManager(Group gamePlayRoot) {
        this.gamePlayRoot = gamePlayRoot;
    }

    /**
     * Updates all actors and checks for collisions between them.
     * This method is called every game frame to keep the game state current.
     */
    public void updateActorsAndCollision(){
        updateActors();
        handleAllCollisions();
        removeAllDestroyedActors();
    }

    /**
     * Updates the state of all actors (friendly units, enemy units, user projectiles, and enemy projectiles).
     */
    private void updateActors() {
        Arrays.asList(friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles)
                .forEach(actors -> actors.forEach(ActiveActor::updateActor));
    }

    /**
     * Adds a friendly unit to the game and the root scene graph.
     *
     * @param actor the friendly actor to add
     */
    public void addFriendlyUnit(ActiveActor actor) {
        friendlyUnits.add(actor);
        gamePlayRoot.getChildren().add(actor);
    }

    /**
     * Adds an enemy unit to the game and the root scene graph.
     *
     * @param actor the enemy actor to add
     */
    public void addEnemyUnit(ActiveActor actor) {
        enemyUnits.add(actor);
        gamePlayRoot.getChildren().add(actor);
    }

    /**
     * Adds a user projectile to the game and the root scene graph.
     *
     * @param projectile the user projectile to add
     */
    public void addUserProjectile(ActiveActor projectile) {
        userProjectiles.add(projectile);
        gamePlayRoot.getChildren().add(projectile);
    }

    /**
     * Adds an enemy projectile to the game and the root scene graph.
     *
     * @param projectile the enemy projectile to add
     */
    public void addEnemyProjectile(ActiveActor projectile) {
        enemyProjectiles.add(projectile);
        gamePlayRoot.getChildren().add(projectile);
    }

    /**
     * Removes all destroyed actors from the game and the root scene graph.
     */
    // Remove Destroyed Actors by making it as an array list and iterate one by one to the removed
    //destroyed
    //temporary making the user not destroyed so the restart works
    private void removeAllDestroyedActors() {
        Arrays.asList(enemyUnits, userProjectiles, enemyProjectiles)
                .forEach(this::removeDestroyedActors);
    }

    /**
     * Removes destroyed actors from a given list and the game root.
     *
     * @param actors the list of actors to check for destruction
     */
    public void removeDestroyedActors(List<ActiveActor> actors) {
        List<ActiveActor> destroyedActors = actors.stream()
                .filter(ActiveActor::getIsDestroyed)
                .collect(Collectors.toList());
        gamePlayRoot.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    /**
     * Checks for collisions between two sets of actors and applies damage to the actors involved.
     *
     * @param actors1 the first list of actors
     * @param actors2 the second list of actors
     */
    // Handle Collisions
    //remove handle collision from levelParent to here
    private void handleCollisions(List<ActiveActor> actors1, List<ActiveActor> actors2) {
        for (ActiveActor actor1 : actors1) {
            for (ActiveActor actor2 : actors2) {
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                }
            }
        }
    }

    /**
     * Handles the logic when enemy units have penetrated the defenses of the friendly units.
     */
    public void handleEnemyPenetration() {
        for(ActiveActor user : friendlyUnits) {
            for (ActiveActor enemy : enemyUnits) {
                if (enemyHasPenetratedDefenses(enemy)) {
                    user.takeDamage();
                    enemy.destroy();
                }
            }
        }

    }

    /**
     * Checks if an enemy unit has moved past the defenses.
     *
     * @param enemy the enemy actor to check
     * @return true if the enemy has penetrated the defenses, false otherwise
     */
    private boolean enemyHasPenetratedDefenses(ActiveActor enemy) {
        return Math.abs(enemy.getTranslateX()) > MainMenu.getScreenWidth();
    }

    /**
     * Handles collisions between friendly units and enemy units.
     */
    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     */
    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Handles all types of collisions in the game, including between planes and projectiles.
     */
    private void handleAllCollisions(){
        handlePlaneCollisions();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
    }

    // Getters for Actor Lists
    /**
     * Gets the current number of enemy units in the game.
     *
     * @return the number of enemy units
     */
    public int getCurrentNumberOfEnemies(){
        return enemyUnits.size();
    }

    /**
     * Gets the list of enemy units in the game.
     *
     * @return the list of enemy units
     */
    public List<ActiveActor> getEnemyUnits() {
        return enemyUnits;
    }

    /**
     * Gets the list of user projectiles in the game.
     *
     * @return the list of user projectiles
     */
    public List<ActiveActor> getUserProjectiles() {
        return userProjectiles;
    }

    /**
     * Gets the list of enemy projectiles in the game.
     *
     * @return the list of enemy projectiles
     */
    public List<ActiveActor> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    /**
     * Gets the root group that contains all actors for rendering.
     *
     * @return the root group
     */
    public Group getGamePlayRoot(){
        return gamePlayRoot;
    }
}

