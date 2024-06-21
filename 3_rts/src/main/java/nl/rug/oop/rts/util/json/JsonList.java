package nl.rug.oop.rts.util.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a JSON list.
 */
public class JsonList implements Jsonable {
    private Object[] values;

    public JsonList(Object[] values) {
        this.values = values;
    }

    /**
     * Create a new JsonList from a JSON string.
     *
     * @param jsonString the JSON string
     */
    public JsonList(String jsonString) {
        assert jsonString.startsWith("[") && jsonString.endsWith("]");
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        String[] elements = jsonString.split(",");
        values = new Object[elements.length];
        for (int i = 0; i < elements.length; i++) {
            String element = elements[i].trim();
            if (element.startsWith("{")) {
                values[i] = new JsonObject(element);
            } else if (element.startsWith("[")) {
                values[i] = new JsonList(element);
            } else if (element.startsWith("\"")) {
                values[i] = element.substring(1, element.length() - 1);
            } else if (!element.isEmpty() && !element.equals("]") && !element.equals("]}")) {
                values[i] = Integer.parseInt(element);
            }
        }
    }

    /**
     * Add a value to the JsonList.
     *
     * @param value the value to add
     */
    public void add(Object value) {
        Object[] newValues = new Object[values.length + 1];
        System.arraycopy(values, 0, newValues, 0, values.length);
        newValues[values.length] = value;
        values = newValues;
    }

    /**
     * Convert the JsonList to a JSON string with indentation.
     *
     * @param indent the number of spaces to indent
     * @return the JSON string
     */
    public String toJsonString(int indent) {
        if (values.length == 0) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[\n");
        for (Object value : values) {
            stringBuilder.append("  ".repeat(indent));
            appendJsonString(value, stringBuilder, indent);
        }
        if (stringBuilder.charAt(stringBuilder.length() - 2) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append("  ".repeat(indent - 1)).append("]");
        return stringBuilder.toString();
    }

    /**
     * Get the values of the JsonList.
     *
     * @return the values of the JsonList
     */
    public List<Object> getValues() {
        if (values == null) {
            return new ArrayList<>();
        }
        return List.of(values);
    }
}
