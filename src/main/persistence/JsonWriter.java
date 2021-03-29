package persistence;

import model.GameAppData;
import org.json.JSONObject;

import java.io.*;

/** citation: code taken and modified from the JasonSerializationDemo,
    https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git **/
//represents writer that writes JSON rep of game to file
public class JsonWriter {

    private PrintWriter writer;       //writer for data
    private static final int TAB = 4; //indent spacer
    private String destination;       //file name where data is saved

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

    //MODIFIES: this
    //EFFECTS: writes JSON representation of gameAPpData to file
    public void write(GameAppData gameAppData) {
        JSONObject j = gameAppData.toJson();
        saveToFile(j.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    public void saveToFile(String j) {
        writer.print(j);
    }
}
