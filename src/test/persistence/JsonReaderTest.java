package persistence;

import model.GameAppData;
import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;
import model.Character;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// TODO citation: code taken and modified from the JasonSerializationDemo,
//  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest {

    private JsonReader reader;

    @Test
    public void testReadNoFile() {
        reader = new JsonReader("./data/nothingHere.json");

        try {
            GameAppData g = reader.read();
        } catch (IOException e) {
            //nothing because it catches exception
        }
    }

    @Test
    public void testReadYesFileBasicData() {
        reader = new JsonReader("./data/testReaderBasicData.json");
        GameAppData expected = new GameAppData(new Character("", 100, 0), new Inventory());
        Character expectedUser = expected.getUser();
        Inventory expectedInventory = expected.getInventory();

        try {
            GameAppData g = reader.read();
            Character user = g.getUser();
            Inventory inven = g.getInventory();
            assertEquals(expectedUser.getName(), user.getName());
            assertEquals(expectedUser.getHealth(), user.getHealth());
            assertEquals(expectedUser.getProgress(), user.getProgress());

            assertEquals(expectedInventory.length(), inven.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReadYesFileHasItems() {
        reader = new JsonReader("./data/testReaderHasItems.json");
        Inventory expectedInventory = new Inventory();
        expectedInventory.addItem(new Item(2));
        expectedInventory.addItem(new Item(4));

        Character expectedUser = new Character("sfsd", 40, 0);

        GameAppData expected = new GameAppData(expectedUser, expectedInventory);

        try {
            GameAppData g = reader.read();
            Character user = g.getUser();
            Inventory inven = g.getInventory();
            assertEquals(expectedUser.getName(), user.getName());
            assertEquals(expectedUser.getHealth(), user.getHealth());
            assertEquals(expectedUser.getProgress(), user.getProgress());

            assertEquals(expectedInventory.length(), inven.length());
            assertEquals(expectedInventory.getItemAtSpot(1).getNum(), inven.getItemAtSpot(1).getNum());
            assertEquals(expectedInventory.getItemAtSpot(1).getNum(), inven.getItemAtSpot(1).getNum());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
