package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.entities.*;
import nl.rug.oop.rpg.environment.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        // While the dungeon is not big enough, add rooms to the dungeon
        // expanding from a random room
        Random random = new Random();
        dungeon.add(startRoom);
        while (dungeon.size() < size) {
            Room randomRoom = dungeon.get(new Random().nextInt(dungeon.size()));
            if (randomRoom.getDoors().size() < 4) {
                Room newRoom = generateRoom();
                randomRoom.addDoor(new Door(generateDoorDescription(), newRoom), true);
                dungeon.add(newRoom);
            }
        }

        // Add an exit door to a random room that is not the start room
        Room exitRoom = dungeon.get(random.nextInt(dungeon.size()));
        while (exitRoom == startRoom) {
            exitRoom = dungeon.get(random.nextInt(dungeon.size()));
        }
        Door exitDoor = new ExitDoor("An exit door", null);
        exitRoom.addDoor(exitDoor, false);

        // For every list of doors in the rooms, shuffle them to avoid predictability
        for (Room room : dungeon) {
            room.shuffleDoors();
        }

        // For every room that has less than 4 doors, add a fake door or a locked door.
        fillDoors(dungeon, random);

        return dungeon;
    }

    private void fillDoors(List<Room> dungeon, Random random) {
        for (Room room : dungeon) {
            while (room.getDoors().size() < 4) {
                if (Math.random() < 0.3) {
                    room.addDoor(new FakeDoor(generateDoorDescription(), null), false);
                } else if (Math.random() < 0.5) {
                    room.addDoor(new LockedDoor(this.game, generateDoorDescription() + "It has a lock. ",
                            dungeon.get(random.nextInt(dungeon.size()))), true);
                } else {
                    room.addDoor(new RandomDoor(this.game, generateDoorDescription(),
                            null), false);
                }
            }
        }
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
        // Random damage and health with bottom-heavy distribution
        int damage = (int) (50 * Math.pow(random.nextDouble(), 2));
        int health = (int) (100 * Math.pow(random.nextDouble(), 2));
        String npcAdjective = getNpcAdjective();
        if (Math.random() < 0.75) {
            String enemyType = getEnemyType();
            return new Enemy(this.game, npcAdjective + " " + enemyType, damage, health);
        } else {
            return switch ((int) (Math.random() * 3)) {
                case 0 -> new Wizard(this.game, npcAdjective + " wizard", damage, health);
                case 1 -> new Healer(npcAdjective + " healer", damage, health);
                case 2 -> new Trader(npcAdjective + " trader", damage, health);
                default -> null;
            };
        }
    }

    private static String getEnemyType() {
        List<String> enemyTypes = List.of("goblin", "orc", "troll", "dragon", "skeleton", "zombie", "ghost", "vampire");
        return enemyTypes.get((int) (Math.random() * enemyTypes.size()));
    }

    private static String getNpcAdjective() {
        List<String> npcAdjectives = List.of(
                "A fierce", "A strong", "A weak", "A powerful", "A mighty", "A feeble", "A brave", "A cowardly",
                "A dangerous", "A harmless", "A friendly", "An evil", "A good", "A kind", "A cruel", "A mean"
        );
        return npcAdjectives.get((int) (Math.random() * npcAdjectives.size()));
    }
}
