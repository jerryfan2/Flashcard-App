package persistence;

import org.json.JSONObject;

// interface for writable classes
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
