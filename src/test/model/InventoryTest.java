package model;

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
        a = new Item(1);
        b = new Item(2);
        c = new Item(3);
        d = new Item(4);
        e = new Item(5);
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
        assertEquals(a, test.getItemAtIndex(1));
        assertEquals(0, test.length());

        test.addItem(a);
        test.addItem(b);
        assertEquals(a, test.getItemAtIndex(1));
        assertEquals(1, test.length());
        assertEquals(b, test.getItemAtIndex(1));
        assertEquals(0, test.length());

        test.addItem(a);
        test.addItem(b);
        test.addItem(c);
        assertEquals(b, test.getItemAtIndex(2));
        assertEquals("[" + a.getName() + ", " + c.getName() + "]", test.getAllItemNames());
    }

    @Test
    public void testGetItemAtIndexIllegalParameter() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class,
                () -> { test.getItemAtIndex(1);}
        );
        assertEquals("no item at this index", exc.getMessage());

        IllegalArgumentException exc2 = assertThrows(IllegalArgumentException.class,
                () -> { test.getItemAtIndex(-1);}
        );
        assertEquals("no item at this index", exc.getMessage());
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
    public void testGetItem() {
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
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class,
                () -> { test.getItem("sdf");}
        );

        assertEquals("can not pass in", exc.getMessage());

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
}
