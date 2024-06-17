package nl.rug.oop.rts;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to represent a JSON object.
 */
@NoArgsConstructor
public class JsonObject {
    private Map<String, Object> values = new HashMap<>();

    public JsonObject(String jsonString) {
        assert jsonString.startsWith("{") && jsonString.endsWith("}");
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        String[] pairs = jsonString.split(",");
        for (String pair : pairs) {
            if (pair.contains(":")) {
                String[] keyValue = pair.split(":");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                if (value.startsWith("{")) {
                    values.put(key, new JsonObject(value));
                } else if (value.startsWith("[")) {
                    values.put(key, new JsonList(value));
                } else if (value.startsWith("\"")) {
                    values.put(key, value.substring(1, value.length() - 1));
                } else {
                    values.put(key, Integer.parseInt(value));
                }
            }
        }
    }

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
    public String toJsonString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            stringBuilder.append("\"").append(entry.getKey()).append("\": ");
            if (entry.getValue() instanceof JsonObject) {
                stringBuilder.append(((JsonObject) entry.getValue()).toJsonString());
            } else if (entry.getValue() instanceof JsonList) {
                stringBuilder.append(((JsonList) entry.getValue()).toJsonString());
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
