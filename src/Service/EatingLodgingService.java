package Service;

import Students.Student;
import dataStructures.TwoWayIterator;

public interface EatingLodgingService extends Service{

    /**
     * Verifies if the service still has space for a new student
     * @return true if the service is full, false otherwise
     */
    boolean isFull();

    /**
     * Adds a new student who has gone to this service
     * @param student: Student to add
     * @pre !isFull() && student != null
     */
    void addStudent(Student student);

    /**
     * Removes a student that has left the service
     * @param student: Student to remove
     * student != null
     */
    void removeStudent(Student student);

    /**
     * Gives an Iterator that iterates over all the students currently in the service
     * The iterator can iterate from beginning to end or end to beginning
     * @return a TwoWayIterator of the students
     */
    TwoWayIterator<Student> giveCurrentStudents();
}
