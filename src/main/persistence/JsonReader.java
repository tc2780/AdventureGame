package persistence;

import model.GameAppData;
import model.Inventory;
import model.Item;
import model.Character;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// TODO citation: code taken and modified from the JasonSerializationDemo,
//  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//represents a reader that reads gameApp from JSOn data stored in a file
public class JsonReader {

    private String source;   //file where data is read from

    //EFFECTS: creates a JsonReader with string source
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads file with name of variable source, and returns as GameAppData,
    //          throws IOException if there's an error while reading data from file
    public GameAppData read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameApp(jsonObject);
    }

    //EFFECTS: reads source file as a string, then returns it, throws IOexception if can't read
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: reads GameAppData from JSONObject, and returns as GameAppData
    private GameAppData parseGameApp(JSONObject jobject) {
        JSONObject person = jobject.getJSONObject("user");
        JSONArray items = jobject.getJSONArray("inventory");

        String name = person.getString("name");
        int health = person.getInt("health");
        int progress = person.getInt("progress");
        Character user = new Character(name, health, progress);

        Inventory inventory = new Inventory();
        inventory = addItems(items, inventory);

        GameAppData gameApp = new GameAppData(user, inventory);
        return gameApp;
    }

    //MODIFIES: inventory
    //EFFECTS: reads items stored in JSONArray, adds it to given inventory,
    //         then returns inventory
    private Inventory addItems(JSONArray items, Inventory inventory) {
        for (Object json : items) {
            JSONObject item = (JSONObject) json;
            inventory = addItem(item, inventory);
        }
        return inventory;
    }

    //MODIFIES: inventory
    //EFFECTS: parses item num from JSONObject, initializes item with its item num,
    //         adds item to inventory, and returns inventory
    private Inventory addItem(JSONObject json, Inventory inventory) {
        int num = json.getInt("item num");
        inventory.addItem(new Item(num));
        return inventory;
    }

}
