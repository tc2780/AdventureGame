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

//represents a reader that reads gameApp from JSOn data stored in a file
// TODO citation: code taken and modified from the JasonSerializationDemo,
//  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public GameAppData read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameApp(jsonObject);
    }

    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

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

    private Inventory addItems(JSONArray items, Inventory inventory) {
        for (Object json : items) {
            JSONObject item = (JSONObject) json;
            inventory = addItem(item, inventory);
        }
        return inventory;
    }

    private Inventory addItem(JSONObject json, Inventory inventory) {
        int num = json.getInt("item num");
        inventory.addItem(new Item(num));
        return inventory;
    }

}
