package Students;

import Exceptions.StudentHasNoVisitedLocations;
import Service.Service;
import dataStructures.SetClass;
import dataStructures.Iterator;
import dataStructures.Set;

import java.io.Serial;

public class OutgoingStudentClass extends StudentClass implements BookishOutgoingStudent{

    @Serial
    private static final long serialVersionUID = 13L;

    //Stores all visited locations of the student, without repeats, in insertion order
    private final Set<String> visitedLocations;

    /**
     * Constructor
     * @param type: Type of the Student(outgoing)
     * @param name: Name of the student
     * @param country: Country of the Student
     * @param home: Home of the Student
     */
    public OutgoingStudentClass(String type, String name, String country, String home){
        super(type, name, country, home);
        visitedLocations = new SetClass<>(Service.INITIAL_SERVICE_DATASTRUCTURES_SIZE);
    }

    /**
     * Ads a new location to the ones he has visited
     * If bookish, only stores Leisure Services
     * If outgoing, stores every service
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of visited locations of the student;
     * Average Case: O(1);
     * @param service: Service the student attends
     * @pre: service != null
     */
    @Override
    public void addVisitedLocation(Service service) {
        String name = service.getRecord().name();
        if(visitedLocations.getElement(name.toLowerCase()) == null)
            visitedLocations.addElement(name.toLowerCase(), name);
    }

    /**
     * Gives an Iterator of all stored services visited by the student
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return an Iterator of Strings
     * @throws StudentHasNoVisitedLocations if the student has no services stored
     */
    @Override
    public Iterator<String> showVisitedLocations() {
        if(visitedLocations.isEmpty())
            throw new StudentHasNoVisitedLocations();
        return visitedLocations.iterator();
    }
}
