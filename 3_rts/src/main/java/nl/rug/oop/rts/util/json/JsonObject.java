package nl.rug.oop.rts.util.json;

import lombok.NoArgsConstructor;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventRecord;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to represent a JSON object.
 */
@NoArgsConstructor
public class JsonObject {
    private final Map<String, Object> values = new HashMap<>();

    /**
     * Create a new JsonObject from a JSON string.
     *
     * @param jsonString the JSON string
     */
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

    /**
     * Put a list of values into the JsonObject.
     *
     * @param key   the key
     * @param value the list of values
     * @param <T>   the type of the values
     * @return the JsonObject
     */
    public <T> JsonObject putList(String key, List<T> value) {
        JsonList list = new JsonList(new Object[0]);
        for (T obj : value) {
            if (obj instanceof JsonObject) {
                list.add(obj);
            } else if (obj instanceof JsonList) {
                list.add(obj);
            } else if (obj instanceof Node) {
                list.add(((Node) obj).toJson());
            } else if (obj instanceof Edge) {
                list.add(((Edge) obj).toJson());
            } else if (obj instanceof EventRecord) {
                list.add(((EventRecord) obj).toJson());
            } else if (obj instanceof Army) {
                list.add(((Army) obj).toJson());
            } else if (obj instanceof Unit) {
                list.add(((Unit) obj).toJson());
            } else if (obj instanceof Event) {
                list.add(((Event) obj).toJson());
            } else {
                list.add(obj);
            }
        }
        values.put(key, list);
        return this;
    }

    public Object get(String key) {
        return values.get(key);
    }

    /**
     * Get the JsonList associated with a key.
     *
     * @param key the key
     * @return the JsonList
     */
    public JsonList getList(String key) {
        if (!values.containsKey(key)) {
            return null;
        }
        return (JsonList) values.get(key);
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
     * Convert the JsonObject to a JSON string with indentation.
     *
     * @param indent the number of spaces to indent
     * @return the JSON string
     */
    public String toJsonString(int indent) {
        if (values.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            stringBuilder.append("  ".repeat(indent)).append("\"").append(entry.getKey()).append("\": ");
            Object value = entry.getValue();
            if (value instanceof JsonObject) {
                stringBuilder.append(((JsonObject) value).toJsonString(indent + 1));
            } else if (value instanceof JsonList) {
                stringBuilder.append(((JsonList) value).toJsonString(indent + 1));
            } else if (value instanceof String) {
                stringBuilder.append("\"").append(value).append("\"");
            } else {
                stringBuilder.append(value);
            }
            stringBuilder.append(",\n");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 2) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append("  ".repeat(indent - 1)).append("}");
        return stringBuilder.toString();
    }
}
