/*
 * File: AdvRoom.java
 * ------------------
 * This file defines a class that models a single room in the
 * Adventure game.
 */

import java.util.ArrayList;
import java.util.Scanner;

/* Class: AdvRoom */

/**
 * This class defines a single room in the Adventure game. A room is characterized by the following
 * properties:
 *
 * <ul>
 * <li>A room number, which must be greater than zero
 * <li>Its name, which is a one-line string identifying the room
 * <li>Its description, which is a multiline array describing the room
 * <li>A objects of objects contained in the room
 * <li>A flag indicating whether the room has been visited
 * <li>A motion table specifying the exits and where they lead</li>
 *
 * The external format of the room data file is described in the assignment handout. The comments on
 * the methods exported by this class show how to use the initialized data structure.
 */

public class AdvRoom {

  private String name;
  private int roomNumber;
  private String[] description;
  private AdvMotionTableEntry[] motionTable;
  private ArrayList<AdvObject> objects;
  private boolean hasBeenVisited;

  // private constructor
  // to prevent calling the class constructor from outside
  // of this class -> the client must use the readFromFile
  // method
  private AdvRoom() {
  }

  /* Method: getRoomNumber() */

  /**
   * Reads the data for this room from the Scanner scan, which must have been opened by the caller.
   * This method returns a room if the room initialization is successful; if there are no more rooms
   * to read, readFromFile returns null.
   *
   * @param scan A scanner open on the rooms data file
   * @return a room if successfully read; null if at end of file
   * @usage AdvRoom room = AdvRoom.readFromFile(scan);
   */
  public static AdvRoom readFromFile(Scanner scan) {
    if (!scan.hasNextLine()) {
      return null;
    }

    AdvRoom room = new AdvRoom();
    room.roomNumber = scan.nextInt();
    scan.nextLine(); // skip the new line after the room number
    room.name = scan.nextLine();
    // read the description (one line at the time)
    ArrayList<String> list1 = new ArrayList<String>();
    String line;
    while (!(line = scan.nextLine()).trim().equals("-----")) {
      list1.add(line);
    }
    room.description = list1.toArray(new String[list1.size()]);
    // read the motion table
    ArrayList<AdvMotionTableEntry> list2 = new ArrayList<AdvMotionTableEntry>();
    while (scan.hasNextLine()
        && !(line = scan.nextLine().trim()).equals("")) {
      AdvMotionTableEntry entry;
      // \s+ one or more spaces
      String[] parts = line.split("\\s+");
      if (parts[1].contains("/")) {
        String[] subParts = parts[1].split("/");
        entry = new AdvMotionTableEntry(parts[0],
            Integer.parseInt(subParts[0]),
            subParts[1]);
      } else {
        entry = new AdvMotionTableEntry(parts[0],
            Integer.parseInt(parts[1]), null);
      }
      list2.add(entry);

    }
    room.motionTable =
        list2.toArray(new
            AdvMotionTableEntry[list2.size()]);
    room.objects = new ArrayList<>();
    return room;
  }

  /**
   * Returns the room number.
   *
   * @return The room number
   * @usage int roomNumber = room.getRoomNumber();
   */
  public int getRoomNumber() {
    return roomNumber;
  }

  /* Method: getName() */

  @Override
  public String toString() {
    return this.name;
  }

  /* Method: getDescription() */

  /**
   * Returns the room name, which is its one-line description.
   *
   * @return The room name
   * @usage String name = room.getName();
   */
  public String getName() {
    return name; // Replace with your code
  }

  /* Method: addObject(obj) */

  /**
   * Returns an array of strings that correspond to the long description of the room (including the
   * objects of the objects in the room).
   *
   * @return An array of strings giving the long description of the room
   * @usage String[] description = room.getDescription();
   */
  public String[] getDescription() {
    return description; // Replace with your code
  }

  /* Method: removeObject(obj) */

  /**
   * Adds an object to the objects of objects in the room.
   *
   * @param The AdvObject to be added
   * @usage room.addObject(obj);
   */
  public void addObject(AdvObject obj) {
    objects.add(obj);
  }

  /* Method: containsObject(obj) */

  /**
   * Removes an object from the objects of objects in the room.
   *
   * @param The AdvObject to be removed
   * @usage room.removeObject(obj);
   */
  public void removeObject(AdvObject obj) {
    objects.remove(obj);
  }

  /* Method: getObjectCount() */

  /**
   * Checks whether the specified object is in the room.
   *
   * @param The AdvObject being tested
   * @return true if the object is in the room, and false otherwise
   * @usage if (room.containsObject(obj)) . . .
   */
  public boolean containsObject(AdvObject obj) {
    return objects.contains(obj);

  }

  /* Method: getObject(index) */

  /**
   * Returns the number of objects in the room.
   *
   * @return The number of objects in the room
   * @usage int nObjects = room.getObjectCount();
   */
  public int getObjectCount() {
    return objects.size();
  }

  /* Method: setVisited(flag) */

  /**
   * Returns the specified element from the objects of objects in the room.
   *
   * @return The AdvObject at the specified index position
   * @usage AdvObject obj = room.getObject(index);
   */
  public AdvObject getObject(int index) {
    return objects.get(index);
  }

  /* Method: hasBeenVisited() */

  /**
   * Sets the flag indicating that this room has been visited according to the value of the
   * parameter. Calling setVisited(true) means that the room has been visited; calling
   * setVisited(false) restores its initial unvisited state.
   *
   * @param flag The new state of the "visited" flag
   * @usage room.setVisited(flag);
   */
  public void setVisited(boolean flag) {
    hasBeenVisited = flag; // Replace with your code
  }

  /* Method: getMotionTable() */

  /**
   * Returns true if the room has previously been visited.
   *
   * @return true if the room has been visited; false otherwise
   * @usage if (room.hasBeenVisited()) . . .
   */
  public boolean hasBeenVisited() {
    return hasBeenVisited; // Replace with your code
  }

  /* Method: readFromFile */

  /**
   * Returns the motion table associated with this room, which is an array of directions, room
   * numbers, and enabling objects stored in a AdvMotionTableEntry.
   *
   * @return The array of motion table entries associated with this room
   * @usage AdvMotionTableEntry[] motionTable = room.getMotionTable();
   */
  public AdvMotionTableEntry[] getMotionTable() {
    return motionTable;
  }
}


