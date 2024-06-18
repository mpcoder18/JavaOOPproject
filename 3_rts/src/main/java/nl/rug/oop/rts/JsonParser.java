package nl.rug.oop.rts;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public Object parse(String jsonString) {
        jsonString = jsonString.trim();
        if (jsonString.startsWith("{")) {
            return parseObject(jsonString);
        } else if (jsonString.startsWith("[")) {
            return parseArray(jsonString, 4);
        } else {
            throw new IllegalArgumentException("Invalid JSON string");
        }
    }


    private JsonList parseArray(String jsonString, Integer indent) {
        System.out.println("Array: " + jsonString);
        JsonList jsonArray = new JsonList(new Object[0]);
        List<Object> values = new ArrayList<>();
        jsonString = jsonString.substring(1);
        String[] lines = jsonString.split("\n");
        String indentString = "";
        List<Integer> objectIdx = new ArrayList<>();
        for (int i = 0; i < indent; i++) {
            indentString += " ";
        }
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith(indentString) && !line.startsWith(indentString + " ")) {
                System.out.println("Line: " + line);
                objectIdx.add(i);
            }
        }
        for (int i = 0; i < objectIdx.size(); i++) {
            if (lines[objectIdx.get(i)].contains("{")) {
                int objectStart = objectIdx.get(i);
                int objectEnd = objectIdx.get(i + 1);
                String object = lines[objectStart];
                for (int j = objectStart + 1; j < objectEnd +1; j++) {
                    object += "\n" + lines[j];
                }
                System.out.println("Parsing object: " + object);
                jsonArray.add(parseObject(object, indent + 2));
            }
        }

        return jsonArray;
    }


    private JsonObject parseObject(String jsonString) {
        JsonObject jsonObject = new JsonObject();
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        // Print all lines that have exactly two spaces at the beginning of the line not followed by a space
        String[] lines = jsonString.split("\n");
        List<String> pairs = new ArrayList<>();
        List<Integer> pairStarts = new ArrayList<>();
        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            if (line.startsWith("  ") && !line.startsWith("   ")) {
                pairs.add(line);
                pairStarts.add(j);
            }
        }
        for (int i = 0; i < pairs.size(); i++) {
            String pair = pairs.get(i);
            int pairStart = pairStarts.get(i);
            // If "[" is found in the pair, find the corresponding "]"
            if (pair.contains("[")) {
                int arrayStart = pairStart;
                int arrayEnd = pairStarts.get(i + 1);
                String key = pair.substring(0, pair.indexOf(":")).trim();
                key = key.substring(1, key.length() - 1);
                System.out.println("Parsing array: " + key);
                // Join all lines between the array start and end
                String array = pair.substring(pair.indexOf(":") + 1);
                for (int j = arrayStart+1; j < arrayEnd+1; j++) {
                    array += "\n" + lines[j];
                }
                jsonObject.put(key, parseArray(array.trim(), 4));
            } else if (pair.contains("]")) {
                // Skip
                continue;
            } else {
                String[] keyValue = pair.split(":");
                String key = keyValue[0].trim();
                key = key.substring(1, key.length() - 1);
                String value = keyValue[1].trim();
                if (value.endsWith(",")) {
                    value = value.substring(0, value.length() - 1);
                }
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                System.out.println("Key: " + key + ", Value: " + value);

                if (key.equals("NodeSize") || key.equals("X") || key.equals("Y") || key.equals("Id") || key.equals("Health") || key.equals("Strength") || key.equals("SimulationStep") || key.equals("StartNode") || key.equals("EndNode")) {
                    jsonObject.put(key, Integer.parseInt(value));
                } else {
                    jsonObject.put(key, value);
                }
            }
        }
        return jsonObject;
    }

    private JsonObject parseObject(String jsonString, int indent) {
        JsonObject jsonObject = new JsonObject();
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        String[] lines = jsonString.split("\n");
        List<String> pairs = new ArrayList<>();
        List<Integer> pairStarts = new ArrayList<>();
        String indentString = "";
        for (int i = 0; i < indent; i++) {
            indentString += " ";
        }
        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            if (line.startsWith(indentString) && !line.startsWith(indentString + " ")) {
                pairs.add(line);
                pairStarts.add(j);
            }
        }
        for (int i = 0; i < pairs.size(); i++) {
            String pair = pairs.get(i);
            int pairStart = pairStarts.get(i);
            if (pair.contains("[]")) {
                continue;
            } else if (pair.contains("[")) {
                int arrayStart = pairStart;
                int arrayEnd = pairStarts.get(i + 1);
                String key = pair.substring(0, pair.indexOf(":")).trim();
                key = key.substring(1, key.length() - 1);
                System.out.println("Parsing array: " + key);
                String array = pair.substring(pair.indexOf(":") + 1);
                for (int j = arrayStart+1; j < arrayEnd+1; j++) {
                    array += "\n" + lines[j];
                }
                jsonObject.put(key, parseArray(array.trim(), indent + 2));
            } else if (pair.contains("]")) {
                continue;
            } else {
                String[] keyValue = pair.split(":");
                String key = keyValue[0].trim();
                key = key.substring(1, key.length() - 1);
                String value = keyValue[1].trim();
                // If value ends with a comma, remove it
                if (value.endsWith(",")) {
                    value = value.substring(0, value.length() - 1);
                }
                // If value is surrounded by quotes, remove them
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                System.out.println("Key: " + key + ", Value: " + value);
                if (key.equals("NodeSize") || key.equals("X") || key.equals("Y") || key.equals("Id") || key.equals("Health") || key.equals("Strength") || key.equals("SimulationStep") || key.equals("StartNode") || key.equals("EndNode")) {
                    jsonObject.put(key, Integer.parseInt(value));
                } else {
                    jsonObject.put(key, value);
                }
            }
        }
        return jsonObject;
    }
}