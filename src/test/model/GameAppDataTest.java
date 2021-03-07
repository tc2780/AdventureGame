package model;

import org.json.JSONArray;
import org.json.JSONObject;
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
        stuff.addItem(new Item(1));
        stuff.addItem(new Item(2));
        test = new GameAppData(user, stuff);
    }

    @Test
    public void testGetters() {
        assertEquals(user, test.getUser());
        assertEquals(stuff, test.getInventory());
    }

    @Test
    public void testToJson() {
        JSONObject t = test.toJson();
        JSONObject actualUser = t.getJSONObject("user");
        assertEquals(user.getName(), actualUser.get("name"));
        assertEquals(user.getHealth(), actualUser.get("health"));
        assertEquals(user.getProgress(), actualUser.get("progress"));

        JSONArray stuff = t.getJSONArray("inventory");
        assertEquals(1, stuff.getJSONObject(0).get("item num"));
        assertEquals(2, stuff.getJSONObject(1).get("item num"));
    }

}
