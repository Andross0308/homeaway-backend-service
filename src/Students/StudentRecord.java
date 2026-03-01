package Students;

import java.io.Serial;
import java.io.Serializable;

public record StudentRecord(String type, String name, String country) implements Serializable {


    @Serial
    private static final long serialVersionUID = 15L;

    /**
     * Gives the country of the student
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return student country
     */
    @Override
    public String country() {
        return country;
    }

    /**
     * Gives the name of the student
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return student name
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Gives the type of the student
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return student type
     */
    @Override
    public String type() {
        return type;
    }
}
