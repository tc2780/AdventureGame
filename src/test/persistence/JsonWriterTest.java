package persistence;

import exceptions.NoSuchItemExistsException;
import model.Character;
import model.GameAppData;
import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// TODO citation: code taken and modified from the JasonSerializationDemo,
//  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest {

    @Test
    public void testWriterNoFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
        } catch (FileNotFoundException e) {
            //exception expected here -- no file by given name
        }
    }

    @Test
    public void testWriterYesFile() {
        try {
            Character expectedC = new Character("", 100, 0);
            Item a = new Item();
            Item b = new Item();
            try {
                a = new Item(1);
                b = new Item(2);
            } catch (Exception e) {
                fail("no exceptions should be thrown");
            }
            Inventory expectedI = new Inventory(a, b);
            GameAppData data = new GameAppData(expectedC, expectedI);//
            JsonWriter writer = new JsonWriter("./data/testWriterYesData.json");
            writer.open();
            writer.write(data);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterYesData.json");
            data = reader.read();
            Character actualC = data.getUser();
            Inventory actualI = data.getInventory();//
            assertEquals(expectedC.getName(), actualC.getName());
            assertEquals(expectedC.getProgress(), actualC.getProgress());
            assertEquals(expectedC.getHealth(), actualC.getHealth());
            assertEquals(expectedI.length(), actualI.length());
            assertEquals(expectedI.getItemAtSpot(1).getNum(), actualI.getItemAtSpot(1).getNum());
            assertEquals(expectedI.getItemAtSpot(1).getNum(), actualI.getItemAtSpot(1).getNum());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NoSuchItemExistsException e) {
            fail("items should exist");
        }
    }
}
