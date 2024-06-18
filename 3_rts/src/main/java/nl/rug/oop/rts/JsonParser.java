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
        JsonList jsonArray = new JsonList(new Object[0]);
        List<Object> values = new ArrayList<>();
        jsonString = jsonString.substring(1);
        String[] lines = jsonString.split("\n");
        StringBuilder indentString = new StringBuilder();
        List<Integer> objectIdx = new ArrayList<>();
        indentString.append(" ".repeat(Math.max(0, indent)));
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith(indentString.toString()) && !line.startsWith(indentString + " ")) {
                objectIdx.add(i);
            }
        }
        for (int i = 0; i < objectIdx.size(); i++) {
            if (lines[objectIdx.get(i)].contains("{")) {
                int objectStart = objectIdx.get(i);
                int objectEnd = objectIdx.get(i + 1);
                StringBuilder object = new StringBuilder(lines[objectStart]);
                for (int j = objectStart + 1; j < objectEnd +1; j++) {
                    object.append("\n").append(lines[j]);
                }
                jsonArray.add(parseObject(object.toString(), indent + 2));
            }
        }

        return jsonArray;
    }


    private JsonObject parseObject(String jsonString) {
        JsonObject jsonObject = new JsonObject();
        jsonString = jsonString.substring(1, jsonString.length() - 1);
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
                int arrayEnd = pairStarts.get(i + 1);
                String key = pair.substring(0, pair.indexOf(":")).trim();
                key = key.substring(1, key.length() - 1);
                // Join all lines between the array start and end
                StringBuilder array = new StringBuilder(pair.substring(pair.indexOf(":") + 1));
                for (int j = pairStart +1; j < arrayEnd+1; j++) {
                    array.append("\n").append(lines[j]);
                }
                jsonObject.put(key, parseArray(array.toString().trim(), 4));
            } else if (pair.contains("]")) {
                // Skip
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
        StringBuilder indentString = new StringBuilder();
        indentString.append(" ".repeat(Math.max(0, indent)));
        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            if (line.startsWith(indentString.toString()) && !line.startsWith(indentString + " ")) {
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
                int arrayEnd = pairStarts.get(i + 1);
                String key = pair.substring(0, pair.indexOf(":")).trim();
                key = key.substring(1, key.length() - 1);
                StringBuilder array = new StringBuilder(pair.substring(pair.indexOf(":") + 1));
                for (int j = pairStart +1; j < arrayEnd+1; j++) {
                    array.append("\n").append(lines[j]);
                }
                jsonObject.put(key, parseArray(array.toString().trim(), indent + 2));
            } else if (pair.contains("]")) {
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