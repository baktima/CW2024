# CW2024 Project Report

## 1. GitHub Link
[CW2024 GitHub Repository](https://github.com/baktima/CW2024)

---

## 2. Compilation Instructions

### Prerequisites
1. **Java SDK**: Ensure Java SDK 19 or higher is installed.

To check java version run this command on the terminal.
```bash
   java -version
   javac -version
   ```

2. **Maven**: While the project includes a Maven wrapper, you can verify if Maven is installed globally by running:
   ```bash
   mvn -v
   ```

### Step 1: Clone the Repository
1. Clone the GitHub repository to your desired directory:
   ```bash
   git clone https://github.com/baktima/CW2024.git
   ```

### Step 2: Build the Project
1. Navigate to the root directory of the cloned repository.
2. Use Maven to clean and install the project.
    - If Maven is installed globally:
      ```bash
      mvn clean install
      ```
    - If using the Maven wrapper:
      ```bash
      ./mvnw clean install
      ```

### Step 3: Run the Application
1. To run the application, execute the following commands:
    - With Maven:
      ```bash
      mvn javafx:run
      ```
    - With the Maven wrapper:
      ```bash
      ./mvnw javafx:run
      ```

### Notes
- If you’re using IntelliJ IDEA, run the Maven wrapper commands (`./mvnw`) or running the main class if it works.
- Ensure your terminal is in the **root directory** of the cloned project before executing the commands.


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
   - Added sound effects when the user is shooting.
   - Added background music.
   - Added sliders to control the volume for sound effects (SFX) and background music.

- **New Player Skills:**
   - Added a skill to clear all bullets.

- **Images:**
    - Changing the images for the planes and the bullets. 
---

## 4. Implemented but Not Working
- Explained on the unexpected problem on the persistent input section. 

---

## 5. Features Not Implemented
- Items
- Animations
- Additional SFX
- More plane types and levels
- About section
- Enemy planes (except the boss) can fly off the screen due to the absence of bounding collisions.

#### Reasons: 
1. Not enough time.
2. No more willpower to do this project anymore.

---

## 6. New Java Classes 

### 1. **Controller** (*Location:* `com.example.demo.controller`)
- **BaseGameEndMenuController**:
   - *Purpose:* Base class for game end menu controllers (example game over menu and win menu).

- **GameOverMenuController**:
   - *Purpose:* Manages the Game Over menu.

- **WinMenuController**:
   - *Purpose:* Handles the Win menu.

- **PauseMenuController**:
   - *Purpose:* Manages the Pause menu.

- **MainMenuController**:
   - *Purpose:* Controls the Main Menu.

- **LevelMenuController**:
   - *Purpose:* Handles the Level Menu.

---

### 2. **Display** (*Location:* `com.example.demo.display`)
- **GameOverMenu**:
   - *Purpose:* Visual display for the Game Over screen.

- **LevelMenu**:
   - *Purpose:* Visual display for level selection.

- **MainMenu**:
   - *Purpose:* Visual display for the main menu.

- **PauseMenu**:
   - *Purpose:* Visual display for the Pause menu.

- **WinMenu**:
   - *Purpose:* Visual display for the Win menu.

- **TextDisplay**:
   - *Purpose:* Manages on-screen text like killCount, skill cooldown, boss health, etc.

---

### 3. **Implementation (Interface)** (*Location:* `com.example.demo.implementation`)
- **LevelChangeListener**:
   - *Purpose:* Interface for handling level transitions.

---

### 4. **Level** (*Location:* `com.example.demo.level`)
- **LevelEndless**:
   - *Purpose:* Represents endless mode for survival.

- **LevelThree**:
   - *Purpose:* Represents the third level.

---

### 5. **Manager** (*Location:* `com.example.demo.manager`)
- **ActorManager**:
   - *Purpose:* Manages all active actors (enemies, projectiles, and the player).

- **GameLoopManager**:
   - *Purpose:* Handle some of the gameLoop for restarting and resuming.

- **InputManager**:
   - *Purpose:* Handles user input (keyboard and mouse).

---

### 6. **Plane** (*Location:* `com.example.demo.plane`)
- **BomberPlane**:
   - *Purpose:* Enemy plane with 2 health and projectile that travels to bottom left.

- **TankerPlane**:
   - *Purpose:* Enemy plane with high health (8) and zigzag movement.

---

### 7. **Projectile** (*Location:* `com.example.demo.projectile`)
- **BomberProjectile**:
   - *Purpose:* Projectiles fired by `BomberPlane`.

---

### 8. **Sound** (*Location:* `com.example.demo.sound`)
- **GameMusic**:
   - *Purpose:* Handles background music.

- **SoundEffects**:
   - *Purpose:* Manages sound effects for actions like the user shooting.
  
---

## 7. Modified Java Classes

### 1. LevelParent.java
1. Changed `initializeFriendlyUnits()` to private:
   - **Reason:** To reduce code repetition in child classes.
2. Split `updateScene()` into smaller methods:
   - **Reason:** Improved readability by separating functionality.
3. Delegated input handling to `InputManager`:
   - **Reason:** Simplified and centralized input management.
4. Delegated most of `ActiveActor` interactions to `ActorManager`:
   - **Reason:** Improved readability and abstraction.
   - **Example:** `handleEnemyCollisions()` and other collision handling, `enemyHasPenetratedDefence()`,
   `removeDestroyedActors()`. 
5. Added `cleanUp()` for error resolution:
   - **Reason:** Prevents errors when changing levels and also for cleaning the objects when the gameplay is done.
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
   - **Reason:** Reduced redundancy (code repetition) across child classes.

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

### 2. Persistent Input After Pause and Restart
- **Problem:** When restarting the game after the player's plane dies, the plane would continue moving based on the last input without new user input.
- **Solution:** Not solved (any input will make the user plane go back to normal).
- 
- **Problem:** When pausing the game and then continue the plane would continue moving based on the last input without new user input.
- **Solution:** Not solved (any input will make the user plane go back to normal).

### 3. Health reduction
- **Problem:** When user plane collide with plane with higher health, rather than reduce only 1 health, it will
reduce the amount of health left that the enemy plane have. 
- **Solution:** Not solved

### 4. TankerPlane move outside of screen
- **Problem:** The tanker plane can go outside the screen if it spawns on the top or bottom of the screen
 and when reach the other side of the screen still also reduce the health of the player.
- **Solution:** Force the spawning of the Tanker plane to spawn around the middle of the screen to prevent
the plane going outside when doing the zigzag move.

### 5. Firing 
- **Problem:** When holding space and then move, it will not continue to fire.  
- **Solution:** Not solved.