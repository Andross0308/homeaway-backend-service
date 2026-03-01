package Students;

import Service.Service;

import java.io.Serial;

public class ThriftyStudentClass extends StudentClass implements ThriftyStudent {


    @Serial
    private static final long serialVersionUID = 16L;

    //Eating service with the lowest price the student visited
    private Service cheapestEating;

    /**
     * Constructor
     * @param type: Type of the service(thrifty)
     * @param name: Name of the Service
     * @param country: Country of the Service
     * @param home: Home of the Service
     */
    public ThriftyStudentClass(String type, String name, String country, String home){
        super(type, name, country, home);
    }

    /**
     * Verifies if the given eating is cheaper than the one stored and,
     * if so, changes the cheapest to the eating
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param eating: New location of the student
     * @return true if the eating is more expensive than the cheapestEating, false otherwise
     */
    @Override
    public boolean checkCheapestEating(Service eating) {
        if(cheapestEating == null || eating.getPrice() < cheapestEating.getPrice())
            cheapestEating = eating;
        return eating.getPrice() > cheapestEating.getPrice();
    }
}
