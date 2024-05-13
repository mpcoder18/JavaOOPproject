package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.entities.*;
import nl.rug.oop.rpg.environment.*;

import java.util.*;

/**
 * Class to generate a dungeon.
 */
public class GenerateDungeon {
    private final Game game;

    public GenerateDungeon(Game game) {
        this.game = game;
    }

    /**
     * Generate a dungeon with a given size.
     *
     * @param startRoom The starting room of the dungeon
     * @param size      The size of the dungeon
     * @return a list of rooms representing the dungeon
     */
    public List<Room> generateDungeon(Room startRoom, int size) {
        List<Room> dungeon = new ArrayList<>();
        dungeon.add(startRoom);
        for (int i = 0; i < size; i++) {
            dungeon.add(generateRoom());
        }

        List<String> colors = List.of(
                "red",
                "blue",
                "green",
                "yellow",
                "purple",
                "orange",
                "pink",
                "black",
                "white",
                "brown"
        );

        List<String> materials = List.of(
                "wooden",
                "stone",
                "metal",
                "glass",
                "plastic",
                "paper",
                "fabric",
                "ceramic",
                "clay",
                "rubber"
        );

        // To check: a single door might connect the same room
        Random random = new Random();
        for (Room room : dungeon) {
            Set<Room> connectedRooms = new HashSet<>();
            int numberOfDoors = random.nextInt(2) + 1;
            for (int i = 0; i < numberOfDoors; i++) {
                Room connectingRoom = dungeon.get(random.nextInt(dungeon.size()));
                if (room != connectingRoom && !room.isConnectedTo(connectingRoom) &&
                        !connectedRooms.contains(connectingRoom) && connectingRoom.getDoors().size() < 4) {
                    Door door = new Door("A " + colors.get(random.nextInt(colors.size())) + " door made of " +
                            materials.get(random.nextInt(materials.size())), connectingRoom);
                    room.addDoor(door);
                    connectedRooms.add(connectingRoom);
                }
            }
        }

        // Add an exit door to a random room that is not the start room
        Room exitRoom = dungeon.get(random.nextInt(dungeon.size()));
        while (exitRoom == startRoom) {
            exitRoom = dungeon.get(random.nextInt(dungeon.size()));
        }
        Door exitDoor = new ExitDoor("An exit door", null);
        exitRoom.addDoor(exitDoor);

        return dungeon;
    }

    /**
     * Generate a random room.
     *
     * @return Room A random room
     */
    public Room generateRoom() {
        Room room = new Room();

        // Generate a random description for the room
        room.setDescription(generateRoomDescription());

        // Add up to 3 NPCs to the room
        int numberOfNPCs = (int) (Math.random() * 3 + 1);
        for (int i = 0; i < numberOfNPCs; i++) {
            room.addNPC(randomNPC());
        }

        return room;
    }

    /**
     * Generate a random room description.
     *
     * @return String A random room description
     */
    public String generateRoomDescription() {
        String description = "You are in a ";
        List<String> colors = List.of(
                "dimly lit",
                "dark",
                "bright",
                "colorful",
                "dull",
                "gloomy",
                "shadowy",
                "well-lit",
                "spacious",
                "cramped"
        );
        List<String> atmospheres = List.of(
                "dusty",
                "smelly",
                "cold",
                "hot",
                "humid",
                "dry",
                "wet",
                "moldy",
                "stuffy",
                "clean"
        );
        List<String> sizes = List.of(
                "small",
                "large",
                "tiny",
                "huge",
                "narrow",
                "wide",
                "spacious",
                "cramped",
                "cozy",
                "airy"
        );

        description += colors.get((int) (Math.random() * colors.size())) + " room, ";
        description += atmospheres.get((int) (Math.random() * atmospheres.size())) + " and ";
        description += sizes.get((int) (Math.random() * sizes.size())) + ". ";
        return description;
    }

    /**
     * Generate a random NPC. Enemies have a 75% chance of appearing, while friendly NPCs have a 25% chance.
     * All have a random damage and health.
     *
     * @return NPC A random NPC
     */
    public NPC randomNPC() {
        Random random = new Random();
        int damage = random.nextInt(5) + 1;
        int health = random.nextInt(100) + 1;
        if (Math.random() < 0.75) {
            return (NPC) new Enemy(this.game, "A monster", damage, health);
        } else {
            return switch ((int) (Math.random() * 3)) {
                case 0 -> new Wizard(this.game, "A wizard", damage, health);
                case 1 -> new Healer("A healer", damage, health);
                case 2 -> new Trader("A merchant", damage, health);
                default -> null;
            };
        }
    }
}
