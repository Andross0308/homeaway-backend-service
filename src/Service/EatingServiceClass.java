package Service;

import Exceptions.ServiceHasNoStudents;
import Students.Student;
import dataStructures.*;

import java.io.Serial;

public class EatingServiceClass extends ServiceClass implements EatingLodgingService {

    @Serial private static final long serialVersionUID = 7L;

    //Price of the menu
    private final int price;
    //Max number of users in the service
    private final int capacity;
    //Current users of the service
    private final Set<Student> users;


    /**
     * Constructor
     * @param lat: Latitude of the service
     * @param log: Longitude of the service
     * @param price: Price of the room
     * @param capacity: Capacity of the service
     * @param name: Name of the service
     * @param lastChanged: Number of total ratings in the system
     */
    public EatingServiceClass(long lat, long log, int price, int capacity, String name, int lastChanged){
        super(ServiceTypeEnum.EATING, lat, log, name, lastChanged);
        this.price = price;
        this.capacity = capacity;
        this.users = new SetClass<>(Student.INITIAL_STUDENT_DATASTRUCTURES_VALUE);
    }

    /**
     * Verifies if the service still has space for a new student
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return true if the service is full, false otherwise
     */
    @Override
    public boolean isFull() {
        return users.size() == capacity;
    }

    /**
     * Adds a new student who has gone to this service
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the service;
     * Average Case: O(1);
     * @param student: Student to add
     * @pre !isFull() && student != null
     */
    @Override
    public void addStudent(Student student) {
        users.addElement(student.getRecord().name(),student);
    }

    /**
     * Removes a student that has left the service
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the service;
     * Average Case: O(1);
     * @param student: Student to remove
     * student != null
     */
    @Override
    public void removeStudent(Student student) {
        users.removeElement(student.getRecord().name());
    }

    /**
     * Gives an Iterator that iterates over all the students currently in the service
     * The iterator can iterate from beginning to end or end to beginning
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return a TwoWayIterator of the students
     * @throws ServiceHasNoStudents if there are no students currently in the service
     */
    @Override
    public TwoWayIterator<Student> giveCurrentStudents() {
        if(users.isEmpty())
            throw new ServiceHasNoStudents();
        return users.twoWayIterator();
    }

    /**
     * Gives the price of the service
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service price
     */
    @Override
    public int getPrice() {
        return price;
    }
}
