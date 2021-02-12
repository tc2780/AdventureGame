package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChestTest {

    private Chest test;

    @BeforeEach
    public void setUp() {
        test = new Chest();
    }

    @Test
    public void testHas2Items() {
        assertEquals(2, test.length());
    }
}
