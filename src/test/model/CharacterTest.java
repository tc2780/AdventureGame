package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterTest {
    private Character test;

    @BeforeEach
    public void setUp() {
        test = new Character("Bob");
    }

    @Test
    public void testSetName() {
        assertEquals("Bob", test.getName());
        test.setName("Blob");
        assertEquals("Blob", test.getName());
    }

    @Test
    public void testSetProgress() {
        assertEquals(0, test.getProgress());
        test.setProgress(86);
        assertEquals(86, test.getProgress());
    }

    @Test
    public void setHealth() {
        assertEquals(100, test.getHealth());
        test.setHealth(50);
        assertEquals(50, test.getHealth());
    }

    @Test
    public void testHealOrDamage() {
        assertEquals(100, test.getHealth());

        test.gainLoseHealth(-50);
        assertEquals(50, test.getHealth());

        test.gainLoseHealth(-50);
        assertEquals(0, test.getHealth());

        test.gainLoseHealth(-50);
        assertEquals(0, test.getHealth());

        test.gainLoseHealth(50);
        assertEquals(50, test.getHealth());

        test.gainLoseHealth(100);
        assertEquals(100, test.getHealth());
    }

    @Test
    public void testGainLoseProgress() {
        assertEquals(0, test.getProgress());

        test.gainLoseProgress(50);
        assertEquals(50, test.getProgress());

        test.gainLoseProgress(50);
        assertEquals(100, test.getProgress());

        test.gainLoseProgress(100);
        assertEquals(100, test.getProgress());

        test.gainLoseProgress(-50);
        assertEquals(50, test.getProgress());

        test.gainLoseProgress(-150);
        assertEquals(0, test.getProgress());
    }

    @Test
    public void testStillAlive() {
        assertTrue(test.stillAlive());

        test.gainLoseHealth(-200);
        assertFalse(test.stillAlive());
    }
}