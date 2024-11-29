package com.example.demo.manager;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actor.ActiveActor;
import com.example.demo.display.MainMenu;
import javafx.scene.Group;

public class ActorManager {
    private final List<ActiveActor> friendlyUnits;
    private final List<ActiveActor> enemyUnits;
    private final List<ActiveActor> userProjectiles;
    private final List<ActiveActor> enemyProjectiles;
    private final Group gamePlayRoot;

    public ActorManager(Group gamePlayRoot) {
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits =new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.gamePlayRoot = gamePlayRoot;
    }

    // Actor Updates
    public void updateActors() {
        friendlyUnits.forEach(ActiveActor::updateActor);
        enemyUnits.forEach(ActiveActor::updateActor);
        userProjectiles.forEach(ActiveActor::updateActor);
        enemyProjectiles.forEach(ActiveActor::updateActor);
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
    public void removeAllDestroyedActors() {
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
    public void handleCollisions(List<ActiveActor> actors1, List<ActiveActor> actors2) {
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
        return Math.abs(enemy.getTranslateX()) > MainMenu.GetScreenwidth();
    }

    public void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    public void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    public void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    public int getCurrentNumberOfEnemies(){
        return enemyUnits.size();
    }

    // Getters for Actor Lists

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

