package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChestTest {

    private Chest test;
    private Chest test2;

    @BeforeEach
    public void setUp() {
        test = new Chest();
    }

    @Test
    public void testHas2Items() {
        assertEquals(2, test.length());
    }

    @Test
    public void testConstructor2() {
        Item one = new Item();
        Item two = new Item();
        ArrayList<Item> items = new ArrayList<>();
        items.add(one);
        items.add(two);
        test2 = new Chest(items);
        assertEquals(2, test2.length());
    }
}
