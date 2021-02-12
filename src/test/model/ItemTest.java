package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private Item a;
    private Item b;
    private Item c;
    private Item d;
    private Item e;
    private Item random;

    @BeforeEach
    public void setUp() {
        a = new Item(1);
        b = new Item(2);
        c = new Item(3);
        d = new Item(4);
        e = new Item(5);
        random = new Item();
    }

    @Test
    public void testChangeInHealth() {
        assertEquals(20, a.getChangeInHealth());
        assertEquals(-10, b.getChangeInHealth());
        assertEquals(0, c.getChangeInHealth());
        assertEquals(20, d.getChangeInHealth());
        assertEquals(-30, e.getChangeInHealth());

        int x;
        switch (random.getNum()) {
            case 1:
                x = 20;
                break;
            case 2:
                x = -10;
                break;
            case 3:
                x = 0;
                break;
            case 4:
                x = 20;
                break;
            case 5:
                x = -30;
                break;
            default: x = 0;

        }

        System.out.println(random.getNum());
        assertEquals(x, random.getChangeInHealth());
    }

    @Test
    public void testChangeInProgress() {
        assertEquals(-20, a.getChangeInProgress());
        assertEquals(20, b.getChangeInProgress());
        assertEquals(30, c.getChangeInProgress());
        assertEquals(-10, d.getChangeInProgress());
        assertEquals(0, e.getChangeInProgress());

        int x;
        switch (random.getNum()) {
            case 1:
                x = -20;
                break;
            case 2:
                x = 20;
                break;
            case 3:
                x = 30;
                break;
            case 4:
                x = -10;
                break;
            case 5:
                x = 0;
                break;
            default: x = 0;
        }

        assertEquals(x, random.getChangeInProgress());
    }

    @Test  //also tests set up item--kind of
    public void testGetName() {
        assertEquals("A purple kiwi", a.getName());
        assertEquals("A water bottle filled with an unknown substance", b.getName());
        assertEquals("A feather that shimmers brown and gold", c.getName());
        assertEquals("Seems to be a book with the front cover ripped off", d.getName());
        assertEquals("A ladybug. An actual live animal. That was in a chest", e.getName());

        String str;
        switch (random.getNum()) {
            case 1:
                str = "A purple kiwi";
                break;
            case 2:
                str = "A water bottle filled with an unknown substance";
                break;
            case 3:
                str = "A feather that shimmers brown and gold";
                break;
            case 4:
                str = "Seems to be a book with the front cover ripped off";
                break;
            default: str = "A ladybug. An actual live animal. That was in a chest";
        }
        assertEquals(str, random.getName());
    }
}
