package App;

import java.io.Serializable;
import Students.Student;
import dataStructures.Iterator;
import Service.Service;

public interface StudentCollection extends Serializable {

    /**
     * Gives the student with the given name, if he exists in the collection
     *
     * @param name: Name of the student
     * @return the student with name or null, if no student has the given name
     */
    Student getStudentByName(String name);

    /**
     * Removes a student with the given name from the collection, if it exists
     *
     * @param name: Name of the student
     * @return the student with the given name, or null if the student doesn't exist
     */
    Student removeStudent(String name);


    /**
     * Adds a new student to the collection
     * @param type: Type of the student
     * @param name: Name of the student
     * @param country: Country of the student
     * @param home: Home of the student
     * @return the recently created student
     */
    Student addNewStudent(String type, String name, String country, Service home);

    /**
     * Gives an iterator that iterates over all students, in alphabetical order
     * @return an iterator of students
     */
    Iterator<Student> studentInAlphabeticalOrderIterator();

    /**
     * Gives an iterator that iterates over all students of a given country,
     * in insertion order
     * @param country: Country to iterate
     * @return an iterator of students
     */
    Iterator<Student> studentsOfCountry(String country);
}
