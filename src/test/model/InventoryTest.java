package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory test;
    private Item a;
    private Item b;
    private Item c;
    private Item d;
    private Item e;

    @BeforeEach
    public void setUp() {
        test = new Inventory();
        try {
            a = new Item(1);
            b = new Item(2);
            c = new Item(3);
            d = new Item(4);
            e = new Item(5);
        } catch (Exception e) {
            fail("no exceptions should be thrown");
        }
    }

    @Test
    public void testAddItem() {
        assertEquals(0, test.length());

        test.addItem(a);
        assertEquals(1, test.length());
        assertEquals("[" + a.getName() + "]", test.getAllItemNames());

        test.addItem(b);
        assertEquals(2, test.length());

        test.addItem(c);
        assertEquals(3, test.length());
        assertEquals("[" + a.getName() + ", " + b.getName() + ", " + c.getName() + "]", test.getAllItemNames());

        test.addItem(e);
        assertEquals(3, test.length());
    }

    @Test
    public void testGetItems() {
        assertEquals(0, test.getItems().size());

        test.addItem(a);
        assertEquals(new ArrayList<Item>(Arrays.asList(a)), test.getItems());

        test.addItem(b);
        test.addItem(c);
        assertEquals(new ArrayList<Item>(Arrays.asList(a, b, c)), test.getItems());

        test.addItem(d);
        assertEquals(new ArrayList<Item>(Arrays.asList(a, b, c)), test.getItems());

    }

    @Test
    public void testGetItemAtIndex() {
        test.addItem(a);
        assertEquals(a, test.getItemAtSpot(1));
        assertEquals(0, test.length());

        test.addItem(a);
        test.addItem(b);
        assertEquals(a, test.getItemAtSpot(1));
        assertEquals(1, test.length());
        assertEquals(b, test.getItemAtSpot(1));
        assertEquals(0, test.length());

        test.addItem(a);
        test.addItem(b);
        test.addItem(c);
        assertEquals(b, test.getItemAtSpot(2));
        assertEquals("[" + a.getName() + ", " + c.getName() + "]", test.getAllItemNames());

        test.addItem(a);
        test.addItem(b);
        try {
            Item item = test.getItemAtSpot(1);
            assertEquals(a, item);
        } catch (IndexOutOfBoundsException e) {
            fail("should not be thrown");
        }
    }

    @Test
    public void testGetItemIndexOutOfBounds() {
        try {
            test.getItemAtSpot(1);
            fail("Test should not reach this point");
        } catch (IndexOutOfBoundsException e) {
            //should be thrown
        }
        try {
            test.getItemAtSpot(0);
            fail("Test should not reach this point");
        } catch (IndexOutOfBoundsException e) {
            //should be thrown
        }
        try {
            test.getItemAtSpot(-1);
            fail("Test should not reach this point");
        } catch (IndexOutOfBoundsException e) {
            //should be thrown
        }
        test.addItem(a);
        test.addItem(b);
        try {
            Item item = test.getItemAtSpot(5);
            fail("should not reach this point");
        } catch (IndexOutOfBoundsException e) {
            //should be caught
        }
    }

    @Test
    public void testHaveItem() {
        assertFalse(test.haveItem("yellow"));

        test.addItem(a);
        assertTrue(test.haveItem(a.getName()));
        assertFalse(test.haveItem("Sdfsfd"));

        test.addItem(b);
        test.addItem(c);
        assertTrue(test.haveItem(a.getName()));
        assertTrue(test.haveItem(b.getName()));
        assertTrue(test.haveItem(c.getName()));
        assertFalse(test.haveItem("sdfsd"));
    }

    @Test
    public void testGetItemNoExceptions() {
        test.addItem(a);
        assertEquals(a, test.getItem(a.getName()));
        assertEquals(0, test.length());

        test.addItem(a);
        test.addItem(b);
        assertEquals(a, test.getItem(a.getName()));

        assertEquals(b, test.getItem(b.getName()));

        test.addItem(a);
        test.addItem(b);
        test.addItem(c);
        assertEquals(c, test.getItem(c.getName()));
        assertEquals(2, test.length());
    }

    @Test
    public void testGetItemIllegalParameter() {
        try {
            test.getItem("sdfsdf");
            fail("should not reach this point");
        } catch (Exception e) {
            //exception should be thrown
        }
    }

    @Test
    public void testGetAllItemNames() {
        assertEquals("[]", test.getAllItemNames());

        test.addItem(a);
        assertEquals("[" + a.getName() + "]", test.getAllItemNames());

        test.addItem(b);
        assertEquals("[" + a.getName() + ", " + b.getName() + "]", test.getAllItemNames());

        test.addItem(c);
        assertEquals("[" + a.getName() + ", " + b.getName() + ", " + c.getName() + "]", test.getAllItemNames());

    }

    @Test
    public void testLength() {
        assertEquals(0, test.length());

        test.addItem(a);
        assertEquals(1, test.length());

        test.addItem(b);
        assertEquals(2, test.length());

        test.addItem(c);
        assertEquals(3, test.length());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(test.isEmpty());

        test.addItem(a);
        assertFalse(test.isEmpty());

        test.addItem(b);
        assertFalse(test.isEmpty());

        test.addItem(c);
        assertFalse(test.isEmpty());
    }

    @Test
    public void testIsFull() {
        assertFalse(test.isFull());

        test.addItem(a);
        assertFalse(test.isFull());

        test.addItem(b);
        assertFalse(test.isFull());

        test.addItem(c);
        assertTrue(test.isFull());
    }

    @Test
    public void testToJson() {
        test.addItem(a);
        test.addItem(b);
        JSONArray jTest = test.toJson();

        JSONObject aJson = (JSONObject) jTest.get(0);
        assertEquals(a.getNum(), aJson.getInt("item num"));

        JSONObject bJson = (JSONObject) jTest.get(1);
        assertEquals(b.getNum(), bJson.getInt("item num"));
    }
}
