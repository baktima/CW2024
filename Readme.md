# CW2024 Project Report

## 1. GitHub Link
[CW2024 GitHub Repository](https://github.com/baktima/CW2024)

---

## 2. Compilation Instructions
- Have Java SDK 19 or higher version installed.
- Maven is not necessary because the project includes a Maven wrapper (if using intellij).

To check if maven is installed or not run this command
````
mvn -v
````

- Clone the GitHub repository to your desired file.
````
git clone https://github.com/baktima/CW2024.git
````

- To use the maven wrapper change from mvn to ./mvnw then the regular maven command (can only be run on the intellij terminal).
````
mvn clean install
./mvnw clean install
````

- If it is not possible to run from the intellij you can run it using this command. 

````
mvn javafx:run
./mvnw javafx:run
````


## 3. Implemented and Working Properly
- **Menus:**
   - Added pause menu and main menu.
   - Pause menu allows returning to the main menu or restarting the current level.
   - Main menu allows navigating to play mode.

- **Level Features:**
   - Added level selection.
   - Added win/lose screens with options to restart or return to the main menu.
   - Added new Level Three and an endless mode.

- **Planes:**
   - Added a new plane `BomberPlane`:
      - Has 2 health.
      - Fires projectiles that travel vertically and horizontally.
   - Added a new plane `TankerPlane`:
      - Has 8 health.
      - Moves in a zigzag pattern but doesn’t fire projectiles.

- **Sound Features:**
   - Added sound effects for shooting.
   - Added background music.
   - Added sliders to control the volume for sound effects (SFX) and background music.

- **New Player Skills:**
   - Added a skill to clear all bullets.

---

## 4. Implemented but Not Working
- Need more testing. 

---

## 5. Features Not Implemented
- Items
- Animations
- Additional SFX
- More plane types and levels
- About section
- Enemy planes (except the boss) can fly off the screen due to the absence of bounding collisions.

#### Reasons: 
1. Not enough time
2. No more willpower to do this project anymore.

---

## 6. New Java Classes

### 1. **Controller**
- **BaseGameEndMenuController**:
   - *Purpose:* Base class for game end menu controllers (example game over menu and win menu).
   - *Location:* `com.example.demo.controller`

- **GameOverMenuController**:
   - *Purpose:* Manages the Game Over menu.
   - *Location:* `com.example.demo.controller`

- **WinMenuController**:
   - *Purpose:* Handles the Win menu.
   - *Location:* `com.example.demo.controller`

- **PauseMenuController**:
   - *Purpose:* Manages the Pause menu.
   - *Location:* `com.example.demo.controller`

- **MainMenuController**:
   - *Purpose:* Controls the Main Menu.
   - *Location:* `com.example.demo.controller`

- **LevelMenuController**:
   - *Purpose:* Handles the Level Menu.
   - *Location:* `com.example.demo.controller`

---

### 2. **Display**
- **GameOverMenu**:
   - *Purpose:* Visual display for the Game Over screen.
   - *Location:* `com.example.demo.display`

- **LevelMenu**:
   - *Purpose:* Visual display for level selection.
   - *Location:* `com.example.demo.display`

- **MainMenu**:
   - *Purpose:* Visual display for the main menu.
   - *Location:* `com.example.demo.display`

- **PauseMenu**:
   - *Purpose:* Visual display for the Pause menu.
   - *Location:* `com.example.demo.display`

- **WinMenu**:
   - *Purpose:* Visual display for the Win menu.
   - *Location:* `com.example.demo.display`

- **TextDisplay**:
   - *Purpose:* Manages on-screen text like killCount, skill cooldown, boss health, etc.
   - *Location:* `com.example.demo.display`

---

### 3. **Implementation (Interface)**
- **LevelChangeListener**:
   - *Purpose:* Interface for handling level transitions.
   - *Location:* `com.example.demo.implementation`

---

### 4. **Level**
- **LevelEndless**:
   - *Purpose:* Represents endless mode for survival.
   - *Location:* `com.example.demo.level`

- **LevelThree**:
   - *Purpose:* Represents the third level.
   - *Location:* `com.example.demo.level`

---

### 5. **Manager**
- **ActorManager**:
   - *Purpose:* Manages all active actors (enemies, projectiles, and the player).
   - *Location:* `com.example.demo.manager`

- **GameLoopManager**:
   - *Purpose:* Handle some of the gameLoop for restarting and resuming. 
   - *Location:* `com.example.demo.manager`

- **InputManager**:
   - *Purpose:* Handles user input (keyboard and mouse).
   - *Location:* `com.example.demo.manager`

---

### 6. **Plane**
- **BomberPlane**:
   - *Purpose:* Enemy plane with 2 health and projectile that travels to bottom left.
   - *Location:* `com.example.demo.plane`

- **TankerPlane**:
   - *Purpose:* Enemy plane with high health (8) and zigzag movement.
   - *Location:* `com.example.demo.plane`

---

### 7. **Projectile**
- **BomberProjectile**:
   - *Purpose:* Projectiles fired by `BomberPlane`.
   - *Location:* `com.example.demo.projectile`

---

### 8. **Sound**
- **GameMusic**:
   - *Purpose:* Handles background music.
   - *Location:* `com.example.demo.sound`

- **SoundEffects**:
   - *Purpose:* Manages sound effects for actions like the user shooting.
   - *Location:* `com.example.demo.sound`

---

## 7. Modified Java Classes

### 1. LevelParent.java
1. Changed `initializeFriendlyUnits()` to private:
   - **Reason:** To reduce code repetition in child classes.
2. Split `updateScene()` into smaller methods:
   - **Reason:** Improved readability by separating functionality.
3. Delegated input handling to `InputManager`:
   - **Reason:** Simplified and centralized input management.
4. Delegated `ActiveActor` interactions to `ActorManager`:
   - **Reason:** Improved readability and abstraction.
5. Added `cleanUp()` for error resolution:
   - **Reason:** Prevents errors when changing levels.
6. Generalized unit spawning:
   - **Reason:** Avoided code repetition across child classes.

### 2. ActiveActor.java
1. Generalized `updateActor()` and `updatePosition()` to the parent class:
   - **Reason:** These methods were mostly identical in child classes, so moving them improved readability.

### 3. Controller.java
1. Replaced `Observable` with a custom interface for level changes:
   - **Reason:** `Observable` is deprecated.
2. Removed `update()` and replaced it with `levelChange()`:
   - **Reason:** Simplified design and eliminated deprecated functionality.

### 4. Main.java
1. Removed unnecessary exceptions:
   - **Reason:** These exceptions were never thrown.

### 5. HeartDisplay.java
1. Changed `initializeHeart()` to public:
   - **Reason:** Required for use in the `LevelView` class.

### 6. EnemyPlane.java, BossProjectile.java, EnemyProjectile.java, UserProjectile.java
1. Generalized `updateActor()` and `updatePosition()` to `ActiveActor`:
   - **Reason:** Reduced redundancy across child classes.

### 7. File Restructuring
1. Categorized files into related packages:
   - **Reason:** Grouped classes by their functionality (e.g., `controller`, `display`, `sound`) to improve readability and maintainability.

### 8. Deletion of Classes
1. **ActiveActorDestructible**:
   - **Reason:** Merged functionality into `ActiveActor` because they were nearly identical.
2. **LevelViewLevelTwo**:
   - **Reason:** No longer needed due to generalization.
3. **WinImage** and **GameOverImage**:
   - **Reason:** Combined functionality into `WinMenu` and `GameOverMenu`.

---

## 8. Unexpected Problems

### 1. Scene Management Errors
- **Problem:** After navigating menus (e.g., `Play > Pause > Exit > Play > Pause > Restart/Resume`), the game would incorrectly resume 
the previous iteration instead of restarting or resuming the current scene.
- **Solution:** Modified the `PauseMenuController` to remove the single instance of the 
pause menu and can always create new instance, which resolved the issue.

### 2. Persistent Input After Restart
- **Problem:** When restarting the game after the player's plane dies, the plane would continue moving based on the last input without new user input.
- **Solution:** Not solved

### 3. Health reduction
- **Problem:** When user plane collide with plane with higher health, rather than reduce only 1 health, it will
reduce the amount of health left that the enemy plane have. 
- **Solution:** Not solved

### 4. TankerPlane move outside of screen
- **Problem:** The tanker plane can go outside the screen if it spawns on the top or bottom of the screen.
- **Solution:** Force the spawning of the Tanker plane to spawn around the middle of the screen to prevent
the plane going outside when doing the zigzag move.