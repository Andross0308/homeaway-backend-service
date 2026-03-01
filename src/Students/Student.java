package Students;

import java.io.Serializable;

public interface Student extends Serializable{

    int INITIAL_STUDENT_DATASTRUCTURES_VALUE = 100;

    /**
     * Verifies if the given student is the same has this one based on name
     * @param other: Other student
     * @return if the name are the same, false otherwise
     */
    boolean equals(Object other);

    /**
     * Changes the current Location of the student
     * @param name: Name of the new location
     */
    void changeLocation(String name);

    /**
     * Changes the home of the student
     * and changes his location to his new home
     * @param name: Name of the new home
     */
    void changeHome(String name);

    /**
     * Gives the record that stores type,
     * name and country of the student
     * @return student record
     */
    StudentRecord getRecord();

    /**
     * Gives the home of the student
     * @return student home
     */
    String getHome();

    /**
     * Gives the current location of the student
     * @return student location
     */
    String getLocation();
}
