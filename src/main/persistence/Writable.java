package persistence;

import org.json.JSONObject;

// TODO citation: code taken and modified from the JasonSerializationDemo,
//  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {
    //EFFECTS: will return this as a JSON object
    JSONObject toJson();
}
