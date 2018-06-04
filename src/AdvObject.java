/*
 * File: AdvObject.java
 * --------------------
 * This file defines a class that models an object in the
 * Adventure game.
 */

import java.util.Scanner;

/* Class: AdvObject */

/**
 * This class defines an object in the Adventure game.  An object is characterized by the following
 * properties:
 *
 * <ul>
 * <li>Its name, which is the noun used to refer to the object
 * <li>Its description, which is a string giving a short description
 * <li>The room number in which the object initially lives
 * </li>
 *
 * The external format of the objects file is described in the assignment handout.  The comments on
 * the methods exported by this class show how to use the initialized data structure.
 */

public class AdvObject {

  private String name;
  private int roomNumber;
  private String description;
  /* Method: getName() */

  /**
   * Reads the data for this object from the Scanner scan, which must have been opened by the
   * caller. This method returns the object if the object initialization is successful; if there are
   * no more objects to read, readFromFile returns null
   *
   * @param scan A Scanner open on the objects data file
   * @return the object if an object is successfully read; null at end of file
   * @usage AdvObject object = AdvObject.readFromFile(scan);
   */
  public static AdvObject readFromFile(Scanner scan) {
    if (!scan.hasNextLine()) {
      return null;
    }
    AdvObject object = new AdvObject();

    object.name = scan.nextLine();
    object.description = scan.nextLine();
    object.roomNumber = scan.nextInt();
    scan.nextLine();
    if (scan.hasNextLine()) {
      scan.nextLine();
    }
    return object;
  }

  /* Method: getDescription() */

  /**
   * Returns the object name, which is the word used to refer to it.
   *
   * @return The name of the object
   * @usage String name = obj.getName();
   */
  public String getName() {
    return name;
  }


  /* Method: getInitialLocation() */

  /**
   * Returns the one-line description of the object.  This description should start with an article,
   * as in "a set of keys" or "an emerald the size of a plover's egg."
   *
   * @return The description of the object
   * @usage String name = obj.getDescription();
   */
  public String getDescription() {
    return description;
  }

  /* Method: readFromFile(scan) */

  /**
   * Returns the initial location of the object.
   *
   * @return The room number in which the object initially resides
   * @usage int roomNumber = obj.getInitialLocation();
   */
  public int getInitialLocation() {
    return roomNumber;
  }

  @Override
  public String toString() {
    return this.name;
  }

  /* Private instance variables */
  // Add your own instance variables here

}
	

