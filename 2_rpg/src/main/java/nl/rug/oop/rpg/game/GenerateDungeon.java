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

        Random random = new Random();
        for (Room room : dungeon) {
            Set<Room> connectedRooms = new HashSet<>();
            int numberOfDoors = random.nextInt(2) + 1;
            for (int i = 0; i < numberOfDoors; i++) {
                Room connectingRoom = dungeon.get(random.nextInt(dungeon.size()));
                if (room != connectingRoom && !room.isConnectedTo(connectingRoom) &&
                        !connectedRooms.contains(connectingRoom) && connectingRoom.getDoors().size() < 4) {
                    room.addDoor(new Door(generateDoorDescription(), connectingRoom));
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

        // For every room that has less than 4 doors, add a fake door or a locked door.
        for (Room room : dungeon) {
            while (room.getDoors().size() < 4) {
                if (Math.random() < 0.3) {
                    room.addDoor(new FakeDoor(generateDoorDescription(), null));
                } else if (Math.random() < 0.5) {
                    room.addDoor(new LockedDoor(this.game, generateDoorDescription(),
                            dungeon.get(random.nextInt(dungeon.size()))));
                } else {
                    room.addDoor(new RandomDoor(this.game, generateDoorDescription(),
                            null));
                }
            }
        }

        return dungeon;
    }

    /**
     * Generate a random room.
     *
     * @return Room A random room
     */
    public Room generateRoom() {
        Room room = new Room();

        List<String> roomDescriptions = List.of(
                "You are in a dimly lit room, dusty and small. ",
                "You are in a dark room, smelly and large. ",
                "You are in a bright room, cold and tiny. ",
                "You are in a colorful room, hot and huge. ",
                "You are in a dull room, humid and narrow. ",
                "You are in a gloomy room, dry and wide. ",
                "You are in a shadowy room, wet and spacious. ",
                "You are in a well-lit room, moldy and cramped. ",
                "You are in a spacious room, stuffy and cozy. ",
                "You are in a cramped room, clean and airy. "
        );

        // Generate a random description for the room
        room.setDescription(roomDescriptions.get((int) (Math.random() * roomDescriptions.size())));

        // Add up to 3 NPCs to the room
        int numberOfNPCs = (int) (Math.random() * 3 + 1);
        for (int i = 0; i < numberOfNPCs; i++) {
            room.addNPC(randomNPC());
        }

        return room;
    }

    /**
     * Generate a random door description.
     *
     * @return Door A random door
     */
    private String generateDoorDescription() {
        List<String> doorDescriptions = List.of(
            "A red door made of wooden planks. ",
            "A blue door made of stone. ",
            "A green door made of metal. ",
            "A yellow door made of glass. ",
            "A purple door made of plastic. ",
            "An orange door made of paper. ",
            "A pink door made of fabric. ",
            "A black door made of ceramic. ",
            "A white door made of clay. ",
            "A brown door made of rubber. "
        );

        return doorDescriptions.get((int) (Math.random() * doorDescriptions.size()));
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
