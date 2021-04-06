package persistence;

import exceptions.NoSuchItemExistsException;
import model.GameAppData;
import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;
import model.Character;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

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
        } catch (NoSuchItemExistsException e) {
            fail("items should exist");
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
        } catch (NoSuchItemExistsException e) {
            fail("items should exist");
        }
    }

    @Test
    public void testReadYesFileHasItems() {
        reader = new JsonReader("./data/testReaderHasItems.json");
        Inventory expectedInventory = new Inventory();
        Item a = new Item();
        Item b = new Item();
        try {
            a = new Item(2);
            b = new Item(4);
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        expectedInventory.addItem(a);
        expectedInventory.addItem(b);

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
        } catch (NoSuchItemExistsException e) {
            fail("the items should exist");
        }
    }

    /**
     * code referenced: https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
     **/
    @Test
    public void testIfItemSavedHasError() {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
        reader = new JsonReader("./data/testReaderItemException.json");
        try {
            GameAppData g = reader.read();
//            assertEquals("An exception was thrown in JsonReader addItem\n", out.toString());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (NoSuchItemExistsException e) {
            //should be thrown
        }
    }

}
