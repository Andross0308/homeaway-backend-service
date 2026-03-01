package App;

import Service.Service;
import Exceptions.NoStudentOfCountryToDisplay;
import Exceptions.NoStudentsToDisplay;
import Exceptions.ObjectAlreadyExists;
import dataStructures.*;
import Students.*;

import java.io.Serial;

public class StudentCollectionClass implements StudentCollection {


    @Serial private static final long serialVersionUID = 6L;

    private static final int MAX_NUMBER_OF_COUNTRIES = 195;

    //Stores all students by their name for quick access
    private final Map<String, Student> studentsByName;
    //Stores all students, dividing them by their country
    private final Map<String, Set<Student>> studentsCountry;
    // Stores all student in alphabetical order
    private final SortedMap<String, Student> studentsOrderedByName;

    /**
     * Constructor
     */
    public StudentCollectionClass(){
        studentsByName = new SepChainHashTable<>(Student.INITIAL_STUDENT_DATASTRUCTURES_VALUE);
        studentsCountry = new SepChainHashTable<>(MAX_NUMBER_OF_COUNTRIES);
        studentsOrderedByName = new AVLSortedMap<>();
    }


    /**
     * Gives the student with the given name, if he exists in the collection
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the collection;
     * Average Case: O(1);
     *
     * @param name: Name of the student
     * @return the student with name or null, if no student has the given name
     */
    @Override
    public Student getStudentByName(String name) {
        return studentsByName.get(name.toLowerCase());
    }

    /**
     * Removes a student with the given name from the collection, if it exists
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the collection;
     * Average Case: O(log n), n being the number of students in the collection;
     * @param name: Name of the student
     * @return the student with the given name, or null if the student doesn't exist
     */
    @Override
    public Student removeStudent(String name) {
        Student s = studentsByName.remove(name.toLowerCase());
        if(s == null) return null;
        studentsCountry.get(s.getRecord().country().toLowerCase()).removeElement(name);
        studentsOrderedByName.remove(name);
        return s;
    }

    /**
     * Adds a new student to the collection
     * Best Case: O(log n), n being the number of students in the collection;
     * Worst Case: O(n), n being the number of students in the collection;
     * Average Case: O(log n), n being the number of students in the collection;
     *
     * @param type: Type of the student
     * @param name: Name of the student
     * @param country: Country of the student
     * @param home: Home of the student
     * @return the recently created student
     * @throws ObjectAlreadyExists if a student with the given name already exists
     */
    @Override
    public Student addNewStudent(String type, String name, String country, Service home) {
        Student student = null;
        StudentTypeEnum t = StudentTypeEnum.getValue(type);
        switch (t) {
            case BOOKISH ->
                    student = new BookishStudentClass(t.name().toLowerCase(), name, country, home.getRecord().name());
            case OUTGOING ->
                    student = new OutgoingStudentClass(t.name().toLowerCase(), name, country, home.getRecord().name());
            case THRIFTY ->
                    student = new ThriftyStudentClass(t.name().toLowerCase(), name, country, home.getRecord().name());
        }
        if (getStudentByName(name) != null)
            throw new ObjectAlreadyExists();
        name = name.toLowerCase();
        country = country.toLowerCase();
        studentsByName.put(name, student);
        Set<Student> countryList = studentsCountry.get(country);
        if(countryList == null){
            countryList = new SetClass<>(Student.INITIAL_STUDENT_DATASTRUCTURES_VALUE);
        }
        countryList.addElement(name, student);
        studentsCountry.put(country, countryList);
        studentsOrderedByName.put(name.toLowerCase(), student);
        return student;
    }

    /**
     * Gives an iterator that iterates over all students, in alphabetical order
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return an iterator of students
     * @throws NoStudentsToDisplay if there is no service in the collection
     */
    @Override
    public Iterator<Student> studentInAlphabeticalOrderIterator() {
        if(studentsOrderedByName.isEmpty()) throw new NoStudentsToDisplay();
        return studentsOrderedByName.values();
    }

    /**
     * Gives an iterator that iterates over all students of a given country,
     * in insertion order
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of countries in the collection;
     * Average Case: O(1);
     * @param country : Country to iterate
     * @return an iterator of students
     * @throws NoStudentsToDisplay if there is no student in the collection
     */
    @Override
    public Iterator<Student> studentsOfCountry(String country) {
        Set<Student> elems = studentsCountry.get(country.toLowerCase());
        if(elems == null || elems.isEmpty()) throw new NoStudentOfCountryToDisplay();
        return elems.iterator();
    }
}
