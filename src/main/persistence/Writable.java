package persistence;

import org.json.JSONObject;

/** citation: code taken and modified from the JasonSerializationDemo,
    https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git **/
//an interface that represents a class that can be written as a JSONObject
public interface Writable {
    //EFFECTS: will return this as a JSON object
    JSONObject toJson();
}
