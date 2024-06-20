package nl.rug.oop.rts.util.json;

/**
 * Interface for JSON objects.
 */
public interface Jsonable {
    String toJsonString(int indent);

    /**
     * Append a JSON string to a StringBuilder.
     *
     * @param value        the value to append
     * @param stringBuilder the StringBuilder to append to
     * @param indent       the indentation level
     */
    default void appendJsonString(Object value, StringBuilder stringBuilder, int indent) {
        if (value instanceof Jsonable) {
            stringBuilder.append(((Jsonable) value).toJsonString(indent + 1));
        } else if (value instanceof String) {
            stringBuilder.append("\"").append(value).append("\"");
        } else {
            stringBuilder.append(value);
        }
        stringBuilder.append(",\n");
    }

}
