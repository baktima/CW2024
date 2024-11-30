package com.example.demo.manager;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actor.ActiveActor;
import com.example.demo.display.MainMenu;
import javafx.scene.Group;

public class ActorManager {
    private final List<ActiveActor> friendlyUnits = new ArrayList<>();
    private final List<ActiveActor> enemyUnits = new ArrayList<>();
    private final List<ActiveActor> userProjectiles = new ArrayList<>();
    private final List<ActiveActor> enemyProjectiles = new ArrayList<>();
    private final Group gamePlayRoot;

    public ActorManager(Group gamePlayRoot) {
        this.gamePlayRoot = gamePlayRoot;
    }

    public void updateActorsAndCollision(){
        updateActors();
        handleAllCollisions();
        removeAllDestroyedActors();
    }

    // Actor Updates
    private void updateActors() {
        Arrays.asList(friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles)
                .forEach(actors -> actors.forEach(ActiveActor::updateActor));
    }

    // Add Actors
    public void addFriendlyUnit(ActiveActor actor) {
        friendlyUnits.add(actor);
        gamePlayRoot.getChildren().add(actor);
    }

    public void addEnemyUnit(ActiveActor actor) {
        enemyUnits.add(actor);
        gamePlayRoot.getChildren().add(actor);
    }

    public void addUserProjectile(ActiveActor projectile) {
        userProjectiles.add(projectile);
        gamePlayRoot.getChildren().add(projectile);
    }

    public void addEnemyProjectile(ActiveActor projectile) {
        enemyProjectiles.add(projectile);
        gamePlayRoot.getChildren().add(projectile);
    }

    // Remove Destroyed Actors by making it as an array list and iterate one by one to the removed
    //destroyed
    //temporary making the user not destroyed so the restart works
    private void removeAllDestroyedActors() {
        Arrays.asList(enemyUnits, userProjectiles, enemyProjectiles)
                .forEach(this::removeDestroyedActors);
    }

    private void removeDestroyedActors(List<ActiveActor> actors) {
        List<ActiveActor> destroyedActors = actors.stream()
                .filter(ActiveActor::getIsDestroyed)
                .collect(Collectors.toList());
        gamePlayRoot.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

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

    private boolean enemyHasPenetratedDefenses(ActiveActor enemy) {
        return Math.abs(enemy.getTranslateX()) > MainMenu.getScreenWidth();
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

    private void handleAllCollisions(){
        handlePlaneCollisions();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
    }

    // Getters for Actor Lists
    public int getCurrentNumberOfEnemies(){
        return enemyUnits.size();
    }

    public List<ActiveActor> getEnemyUnits() {
        return enemyUnits;
    }

    public List<ActiveActor> getUserProjectiles() {
        return userProjectiles;
    }

    public List<ActiveActor> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    public Group getGamePlayRoot(){
        return gamePlayRoot;
    }
}

