package model;

import exceptions.NoSuchItemExistsException;
import org.json.JSONObject;
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
        try {
            a = new Item(1);
            b = new Item(2);
            c = new Item(3);
            d = new Item(4);
            e = new Item(5);
        } catch (Exception e) {
            fail("all of those should pass");
        }
        random = new Item();
    }

    @Test
    public void testChangeInHealth() {
        assertEquals(20, a.getChangeInHealth());
        assertEquals(-10, b.getChangeInHealth());
        assertEquals(0, c.getChangeInHealth());
        assertEquals(20, d.getChangeInHealth());
        assertEquals(-30, e.getChangeInHealth());

        //test random case
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
            default:
                x = 0;

        }
//        System.out.println(random.getNum());
        assertEquals(x, random.getChangeInHealth());
    }

    @Test
    public void testChangeInProgress() {
        assertEquals(-20, a.getChangeInProgress());
        assertEquals(20, b.getChangeInProgress());
        assertEquals(30, c.getChangeInProgress());
        assertEquals(-10, d.getChangeInProgress());
        assertEquals(0, e.getChangeInProgress());

        //test random case
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
            default:
                x = 0;
        }
        assertEquals(x, random.getChangeInProgress());
    }

    @Test  //also tests set up item--kind of
    public void testGetName() {
        assertEquals("A purple kiwi", a.getName());
        assertEquals("A water bottle filled with an unknown substance", b.getName());
        assertEquals("A feather that shimmers brown and gold", c.getName());
        assertEquals("A book that looks like it's been dipped in mud and dried under the sun", d.getName());
        assertEquals("A ladybug. An actual live animal", e.getName());

        //test random case
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
                str = "A book that looks like it's been dipped in mud and dried under the sun";
                break;
            default:
                str = "A ladybug. An actual live animal";
        }
        assertEquals(str, random.getName());
    }

    @Test
    public void testToJson() {
        JSONObject jTest = a.toJson();
        assertEquals(a.getNum(), jTest.get("item num"));
    }

    @Test
    public void testSetUpNoExceptions() {
        try {
            Item a = new Item();
            Item b = new Item(1);
            Item c = new Item(2);
            Item d = new Item(3);
            Item e = new Item(4);
            Item f = new Item(5);
        } catch (NoSuchItemExistsException e) {
            fail("THere should be no exceptions thrown");
        }
    }

    @Test
    public void testSetUpYesException() {
        try {
            Item a = new Item(6);
            fail("should not reach this spot");
        } catch (NoSuchItemExistsException e) {
            //exception should be thrown
        }

        try {
            Item b = new Item(-1);
            fail("should not reach this spot");
        } catch (NoSuchItemExistsException e) {
            //exception should be thrown
        }
        try {
            Item b = new Item(0);
            fail("should not reach this spot");
        } catch (NoSuchItemExistsException e) {
            //exception should be thrown
        }

        try {
            Item c = new Item(88828787);
            fail("should not reach this spot");
        } catch (NoSuchItemExistsException e) {
            //exception should be thrown
        }
    }
}
