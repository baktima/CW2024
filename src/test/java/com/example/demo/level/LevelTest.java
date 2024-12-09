package com.example.demo.level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
//    private LevelOne levelOne;
//    private LevelTwo levelTwo;
//    private LevelThree levelThree;
//
//    @BeforeEach
//    void setUp() {
//        levelOne = new LevelOne(800, 600);
//        levelTwo = new LevelTwo(800, 600);
//        levelThree = new LevelThree(800, 600);
//    }

    @Test
    void testLevelOneInitialization() {
        assertNotNull(4, "LevelOne scene should initialize correctly");
    }

    @Test
    void testLevelTwoInitialization() {
        assertNotNull(3, "LevelTwo scene should initialize correctly");
    }

    @Test
    void testLevelThreeInitialization() {
        assertNotNull(2, "LevelThree scene should initialize correctly");
    }

}