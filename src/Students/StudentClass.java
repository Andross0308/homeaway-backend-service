package Students;


import java.io.Serial;

public abstract class StudentClass implements Student{


    @Serial
    private static final long serialVersionUID = 14L;

    //Record to store all immutable fields of the student
    private final StudentRecord record;
    //Home of the student
    private String home;
    //Current Location of the student
    private String location;


    /**
     * Constructor
     * @param type: Type of the student(bookish,outgoing or thrifty)
     * @param name: Name of the student
     * @param country: Country of the student
     * @param home: Home of the student
     */
    public StudentClass(String type, String name, String country, String home){
        this.record = new StudentRecord(type, name, country);
        this.home = home;
        this.location = home;
    }

    /**
     * Verifies if the given student is the same has this one based on name
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param other: Other student
     * @return if the name are the same, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof Student)
            return ((Student)  other).getRecord().name().equalsIgnoreCase(this.record.name());
        return false;
    }

    /**
     * Changes the current Location of the student
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param name: Name of the new location
     */
    @Override
    public void changeLocation(String name) {
        location = name;
    }

    /**
     * Changes the home of the student
     * and changes his location to his new home
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @param name: Name of the new home
     */
    @Override
    public void changeHome(String name) {
        home = name;
        changeLocation(home);
    }

    /**
     * Gives the record that stores type,
     * name and country of the student
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return student record
     */
    @Override
    public StudentRecord getRecord() {
        return record;
    }

    /**
     * Gives the home of the student
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return student home
     */
    @Override
    public String getHome() {
        return home;
    }


    /**
     * Gives the current location of the student
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return student location
     */
    @Override
    public String getLocation() {
        return location;
    }
}
