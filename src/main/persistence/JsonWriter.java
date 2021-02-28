package persistence;

import model.GameAppData;
import org.json.JSONObject;
import ui.GameApp;

import java.io.*;

//represents writer that writes JSON rep of game to file
// TODO citation: code taken and modified from the JasonSerializationDemo,
//  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {

    private PrintWriter writer;
    private static final int TAB = 4;
    private String destination;

    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens the writer, and will throw the file not found exception if
    //         destination file can't be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void write(GameAppData gameAppData) {
        JSONObject j = gameAppData.toJson();
        saveToFile(j.toString(TAB));
    }

    public void close() {
        writer.close();
    }

    public void saveToFile(String j) {
        writer.print(j);
    }


}
