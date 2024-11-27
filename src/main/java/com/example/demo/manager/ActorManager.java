package com.example.demo.manager;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actor.ActiveActor;
import javafx.scene.Group;

public class ActorManager {
    //let's just leave it here first
    private List<ActiveActor> friendlyUnits;
    private List<ActiveActor> enemyUnits;
    private List<ActiveActor> userProjectiles;
    private List<ActiveActor> enemyProjectiles;
    private Group gamePlayRoot;

    public ActorManager(Group gamePlayRoot) {
        this.friendlyUnits = new ArrayList<>();
        enemyUnits =new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.gamePlayRoot = gamePlayRoot;
    }

    // Actor Updates
    public void UpdateActors() {
        friendlyUnits.forEach(ActiveActor::updateActor);
        enemyUnits.forEach(ActiveActor::updateActor);
        userProjectiles.forEach(ActiveActor::updateActor);
        enemyProjectiles.forEach(ActiveActor::updateActor);
    }

    // Add Actors
    public void AddFriendlyUnit(ActiveActor actor) {
        friendlyUnits.add(actor);
        gamePlayRoot.getChildren().add(actor);
    }

    public void AddEnemyUnit(ActiveActor actor) {
        enemyUnits.add(actor);
        gamePlayRoot.getChildren().add(actor);

        System.out.println("Added Enemy: " + actor + ", Total Enemies: " + enemyUnits.size());
    }

    public void AddUserProjectile(ActiveActor projectile) {
        userProjectiles.add(projectile);
        gamePlayRoot.getChildren().add(projectile);
    }

    public void AddEnemyProjectile(ActiveActor projectile) {
        enemyProjectiles.add(projectile);
        gamePlayRoot.getChildren().add(projectile);
    }

    // Remove Destroyed Actors by making it as an array list and iterate one by one to the removed
    //destroyed
    public void RemoveAllDestroyedActors() {
        Arrays.asList(friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles)
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
    public void HandleCollisions(List<ActiveActor> actors1, List<ActiveActor> actors2) {
        for (ActiveActor actor1 : actors1) {
            for (ActiveActor actor2 : actors2) {
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    actor1.takeDamage();
                    actor2.destroy();
                }
            }
        }
    }

    public void HandlePlaneCollisions() {
        HandleCollisions(friendlyUnits, enemyUnits);
    }

    public void HandleUserProjectileCollisions() {
        HandleCollisions(userProjectiles, enemyUnits);
    }

    public void HandleEnemyProjectileCollisions() {
        HandleCollisions(enemyProjectiles, friendlyUnits);
    }

    public int GetCurrentNumberOfEnemies(){
        System.out.println(enemyUnits.size());
        return enemyUnits.size();
    }

    // Getters for Actor Lists

    public List<ActiveActor> GetEnemyUnits() {
        return enemyUnits;
    }

    public List<ActiveActor> GetUserProjectiles() {
        return userProjectiles;
    }

    public List<ActiveActor> GetEnemyProjectiles() {
        return enemyProjectiles;
    }

    public Group GetGamePlayRoot(){
        return gamePlayRoot;
    }

    }

