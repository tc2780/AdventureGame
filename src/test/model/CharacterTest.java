package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterTest {
    private Character test;
    private Character test2;

    @BeforeEach
    public void setUp() {
        test = new Character("Bob");
        test2 = new Character("Bobby", 50, 50);
    }

    @Test
    public void testSetName() {
        assertEquals("Bob", test.getName());
        test.setName("Blob");
        assertEquals("Blob", test.getName());

        //testing second constructor
        assertEquals("Bobby", test2.getName());
        test2.setName("Blob");
        assertEquals("Blob", test2.getName());
    }

    @Test
    public void testSetProgress() {
        assertEquals(0, test.getProgress());
        test.setProgress(86);
        assertEquals(86, test.getProgress());

        //testing second constructor
        assertEquals(50, test2.getProgress());
        test2.setProgress(90);
        assertEquals(90, test2.getProgress());
    }

    @Test
    public void testSetHealth() {
        assertEquals(100, test.getHealth());
        test.setHealth(50);
        assertEquals(50, test.getHealth());

        //testing second constructor
        assertEquals(50, test2.getHealth());
        test2.setHealth(77);
        assertEquals(77, test2.getHealth());
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

    @Test
    public void testToJson() {
        JSONObject jTest = test.toJson();
        assertEquals("Bob", jTest.get("name"));
        assertEquals(0, jTest.get("progress"));
        assertEquals(100, jTest.get("health"));

        JSONObject jTest2 = test2.toJson();
        assertEquals("Bobby", jTest2.get("name"));
        assertEquals(50, jTest2.get("progress"));
        assertEquals(50, jTest2.get("health"));
    }
}