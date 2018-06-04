
/*
 * File: Adventure.java
 * --------------------
 * This program plays the Adventure game from Assignment #4.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/* Class: Adventure */

/**
 * This class is the main program class for the Adventure game.
 */

public class Adventure {

  // Use this scanner for any console input
  private static Scanner scan = new Scanner(System.in);
  private SortedMap<Integer, AdvRoom> rooms = new TreeMap<>();
  private List<AdvObject> inventory = new ArrayList<>();
  private Map<String, String> synonyms = new HashMap<>();
  private AdvRoom currentRoom;
  private boolean gameOn = true;

  public Adventure(String name) {

    // Read the file
    // Read the room file: name + "Rooms.txt" -> game.rooms
    try {
      Scanner in = new Scanner(new FileReader(name + "Rooms.txt"));
      AdvRoom room = AdvRoom.readFromFile(in);
      while (room != null) {
        rooms.put(room.getRoomNumber(), room);
        room = AdvRoom.readFromFile(in);
      }
      // System.out.println(rooms.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }

    // read the object file: name + "Objects.txt"
    try {
      Scanner in = new Scanner(new FileReader(name + "Objects.txt"));
      AdvObject object = AdvObject.readFromFile(in);
      while (object != null) {
        rooms.get(object.getInitialLocation()).addObject(object);
        object = AdvObject.readFromFile(in);
      }
      // System.out.println(inventory.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }

    // read the synonym file : name + "Synonyms.txt"
    try {
      BufferedReader in = new BufferedReader(new FileReader(name + "Synonyms.txt"));
      String line;
      while ((line = in.readLine()) != null) {
        String[] values = line.split("=");
        this.synonyms.put(values[0], values[1]);
      }
      // System.out.println(synonyms);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method is used only to test the program
   */
  public static void setScanner(Scanner theScanner) {
    scan = theScanner;
    // Delete the following line when done
  }

  /**
   * Runs the adventure program
   */
  public static void main(String[] args) {
    // AdventureStub.main(args);
    System.out.print("What will be your adventure today?");
    String name = scan.nextLine();
    Adventure game = new Adventure(name);
    game.run();
  }

  // run the game
  public void run() {
    currentRoom = rooms.get(rooms.firstKey());
    executeLookCommand();

    // loop ask for command
    while (gameOn) {
      String[] parts;
      if (currentRoom==null||(!currentRoom.getMotionTable()[0].getDirection().contains("FORCED"))) {
        System.out.print("> ");
        String command = scan.nextLine().trim().toUpperCase();

        // process the command
        // split on one or more spaces: \s+
        parts = command.split("\\s+");

        // Replace any woed with its synonym
        // Loop through the map of synonyms
        // if parts[i] is a key, replace it with the value
        for (int i = 0; i < parts.length; i++) {
          String part = parts[i];
          if (synonyms.containsKey(part)) {
            parts[i] = synonyms.get(part);
          }
        }
      } else {
        parts = new String[1];
        parts[0] = "FORCED";
      }

      if (parts.length > 0) {
        AdvCommand cmd = null;
        AdvObject obj = null;
        if (parts.length > 1) {
          for (int j = 0; j < currentRoom.getObjectCount(); j++) {
            if (currentRoom.getObject(j) != null && currentRoom.getObject(j).getName()
                .equals(parts[1])) {
              obj = currentRoom.getObject(j);
            }
          }
          for (AdvObject item : inventory) {
            if (item != null && item.getName().equals(parts[1])) {
              obj = item;
            }
          }
        }

        switch (parts[0]) {
          case "TAKE":
            // take command
            cmd = AdvCommand.TAKE;
            break;
          case "DROP":
            // drop command
            cmd = AdvCommand.DROP;
            break;
          case "HELP":
            cmd = AdvCommand.HELP;
            break;
          case "LOOK":
            cmd = AdvCommand.LOOK;
            break;
          case "INVENTORY":
            cmd = AdvCommand.INVENTORY;
            break;
          case "QUIT":
            cmd = AdvCommand.QUIT;
            break;
          // other commands
          // LOOK , I (inventory), HELP,
          default: // any motion command
            cmd = new AdvMotionCommand(parts[0]);
            break;
        }
        // execute the command
        cmd.execute(this, obj);
      }
    }
  }



  /* Method: executeMotionCommand(direction) */

  /**
   * Executes a motion command. This method is called from the AdvMotionCommand class to move to a
   * new room.
   *
   * @param direction The string indicating the direction of motion
   */
  public void executeMotionCommand(String direction) {
    int nextRoom = -1;
    for (int i = 0; i < currentRoom.getMotionTable().length; i++) {
      AdvMotionTableEntry entry = currentRoom.getMotionTable()[i];
      if (entry.getDirection().equals(direction) && (entry.getKeyName() == null || (
          entry.getKeyName() != null && hasItem(entry.getKeyName())))) {
        nextRoom = entry.getDestinationRoom();
        break;
      }
    }
    if (nextRoom == -1) {
      System.out.println("Command not found");
    } else {
      currentRoom = rooms.get(nextRoom);
      executeLookCommand();
//      while (currentRoom.getMotionTable()[0].getDirection().equals("FORCED")) {
//        nextRoom = currentRoom.getMotionTable()[0].getDestinationRoom();
//        currentRoom = rooms.get(nextRoom);
//        if (currentRoom!=null){
//          executeLookCommand();
//        }
        if (nextRoom == 0) {
          System.out.println("GAME OVER!");
          gameOn = false;
//          break;
//        }
      }
    }

  }

  private boolean hasItem(String s) {
    for (AdvObject object : inventory) {
      if (object != null && object.getName().equals(s)) {
        return true;
      }
    }
    return false;

  }

  /* Method: executeQuitCommand() */

  /**
   * Implements the QUIT command. This command should ask the user to confirm the quit request and,
   * if so, should exit from the play method. If not, the program should continue as usual.
   */
  public void executeQuitCommand() {
    System.out.println("Are you sure (Y or N)?");
    if (scan.nextLine().toUpperCase().equals("Y")) {
      System.out.println("See you later!");
      gameOn = false;
    }

    // super.executeQuitCommand(); // Replace with your code
  }

  /* Method: executeHelpCommand() */

  /**
   * Implements the HELP command. Your code must include some help text for the user.
   */
  public void executeHelpCommand() {
    System.out.println("Available shortcuts: ");
    System.out.println(synonyms);
    // super.executeHelpCommand(); // Replace with your code
  }

  /* Method: executeLookCommand() */

  /**
   * Implements the LOOK command. This method should give the full description of the room and its
   * contents.
   */
  public void executeLookCommand() {
    if (currentRoom != null) {
      for (int i = 0; i < currentRoom.getDescription().length; i++) {
        System.out.println(currentRoom.getDescription()[i]);
      }
      for (int j = 0; j < currentRoom.getObjectCount(); j++) {
        System.out.println("There is " + currentRoom.getObject(j) + " here");
      }
    }
  }

  /* Method: executeInventoryCommand() */

  /**
   * Implements the INVENTORY command. This method should display a list of what the user is
   * carrying.
   */
  public void executeInventoryCommand() {
    for (AdvObject item : inventory) {
      if (item != null) {
        System.out.println(item.getName() + ": " + item.getDescription());
      }
    }
  }

  /* Method: executeTakeCommand(obj) */

  /**
   * Implements the TAKE command. This method should check that the object is in the room and
   * deliver a suitable message if not.
   *
   * @param obj The AdvObject you want to take
   */
  public void executeTakeCommand(AdvObject obj) {
    inventory.add(obj);
    currentRoom.removeObject(obj);
    System.out.println(obj + " taken");
  }

  /* Method: executeDropCommand(obj) */

  /**
   * Implements the DROP command. This method should check that the user is carrying the object and
   * deliver a suitable message if not.
   *
   * @param obj The AdvObject you want to drop
   */
  public void executeDropCommand(AdvObject obj) {
    inventory.remove(obj);
    currentRoom.addObject(obj);
    System.out.println(obj + " dropped");
  }
}
