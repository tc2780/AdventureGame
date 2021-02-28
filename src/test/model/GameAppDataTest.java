package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameAppDataTest {

    private GameAppData test;
    private Character user;
    private Inventory stuff;

    @BeforeEach
    public void setUp() {
        user = new Character("Hello", 20, 20);
        stuff = new Inventory();
        stuff.addItem(new Item());
        stuff.addItem(new Item());
        test = new GameAppData(user, stuff);
    }

    @Test
    public void testGetters() {
        assertEquals(user, test.getUser());
        assertEquals(stuff, test.getInventory());
    }

}
