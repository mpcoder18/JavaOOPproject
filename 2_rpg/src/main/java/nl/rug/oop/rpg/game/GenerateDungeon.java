package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.entities.*;
import nl.rug.oop.rpg.environment.*;

import java.util.List;
import java.util.Random;

public class GenerateDungeon {
    Game game;

    public GenerateDungeon(Game game) {
        this.game = game;
    }

    public List<Room> generateDungeon(Room startRoom, int size) {
        List<Room> dungeon = new java.util.ArrayList<>();
        dungeon.add(startRoom);
        for (int i = 0; i < size; i++) {
            dungeon.add(generateRoom());
        }

        // Add doors to the dungeon to connect the rooms
        Random random = new Random();
        for (Room room : dungeon) {
            int numberOfDoors = random.nextInt(3) + 1; // 1 to 3 doors
            for (int i = 0; i < numberOfDoors; i++) {
                Room connectingRoom = dungeon.get(random.nextInt(dungeon.size()));
                if (room != connectingRoom && !room.isConnectedTo(connectingRoom)) {
                    Door door = new Door("A door", connectingRoom);
                    room.addDoor(door);
                }
            }
        }
        return dungeon;
    }

    public Room generateRoom() {
        Room room = new Room();

        // Generate a random description for the room
        room.setDescription(generateRoomDescription());

        // Add up to 3 NPCs to the room
        int numberOfNPCs = (int) (Math.random() * 3);
        for (int i = 0; i < numberOfNPCs; i++) {
            room.addNPC(randomNPC());
        }

        return room;
    }

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

    public NPC randomNPC() {
        // Generate either a Trader, Wizard, Healer or Enemy
        int npcType = (int) (Math.random() * 4);
        return switch (npcType) {
            case 0 -> new Trader("Trader", (int) (Math.random() * 100), (int) (Math.random() * 100));
            case 1 -> new Wizard(this.game, "Wizard", (int) (Math.random() * 100), (int) (Math.random() * 100));
            case 2 -> new Healer("Healer", (int) (Math.random() * 100), (int) (Math.random() * 100));
            case 3 -> new Enemy(this.game, "Enemy", (int) (Math.random() * 100), (int) (Math.random() * 100));
            default -> null;
        };
    }
}
