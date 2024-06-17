package nl.rug.oop.rts;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to represent a JSON object.
 */
public class JsonObject {
    private Map<String, Object> values = new HashMap<>();

    public JsonObject put(String key, Object value) {
        values.put(key, value);
        return this;
    }

    public Object get(String key) {
        return values.get(key);
    }

    public boolean containsKey(String key) {
        return values.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return values.containsValue(value);
    }

    public void remove(String key) {
        values.remove(key);
    }

    public void clear() {
        values.clear();
    }

    public int size() {
        return values.size();
    }

    /**
     * Convert the JsonObject to a JSON string.
     *
     * @return the JSON string representation of the JsonObject
     */
    public String toJsonString() { // TODO: Fix list being serialized as a string
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            stringBuilder.append("\"").append(entry.getKey()).append("\": ");
            if (entry.getValue() instanceof JsonObject) {
                stringBuilder.append(((JsonObject) entry.getValue()).toJsonString());
            } else if (entry.getValue() instanceof String) {
                stringBuilder.append("\"").append(entry.getValue()).append("\"");
            } else {
                stringBuilder.append(entry.getValue());
            }
            stringBuilder.append(", ");
        }
        if (!values.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
