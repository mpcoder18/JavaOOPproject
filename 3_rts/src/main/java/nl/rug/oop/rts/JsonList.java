package nl.rug.oop.rts;

public class JsonList {
    private Object[] values;

    public JsonList(Object[] values) {
        this.values = values;
    }

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

    public Object get(int index) {
        return values[index];
    }

    public void set(int index, Object value) {
        values[index] = value;
    }

    public void add(Object value) {
        Object[] newValues = new Object[values.length + 1];
        System.arraycopy(values, 0, newValues, 0, values.length);
        newValues[values.length] = value;
        values = newValues;
    }

    public void remove(int index) {
        Object[] newValues = new Object[values.length - 1];
        System.arraycopy(values, 0, newValues, 0, index);
        System.arraycopy(values, index + 1, newValues, index, values.length - index - 1);
        values = newValues;
    }

    public int size() {
        return values.length;
    }

    public String toJsonString(int indent) { // TODO: remove trailing comma
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[\n");
        for (Object value : values) {
            stringBuilder.append("  ".repeat(Math.max(0, indent)));
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
        if (values.length > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append("  ".repeat(Math.max(0, indent - 1)));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
