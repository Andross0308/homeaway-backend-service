package App;

import Service.Service;
import Service.ServiceRecord;
import Students.Student;
import Students.StudentRecord;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface HomeAwayApp extends Serializable {

    /**
     * Verifies if the current area is a valid area
     */
    void hasBounds();

    /**
     * Verifies if the given coordinates are valid
     * @param minLat: Min value of Latitude
     * @param maxLat: Max value of Latitude
     * @param minLog: Min value of Longitude
     * @param maxLog: Max value of Longitude
     * @return if the min values are lower than the max values
     */
    boolean validBounds(long minLat, long maxLat, long minLog, long maxLog);

    /**
     * Creates a new service of the given type, in the respective coordinates,
     * with the given price, value and name and adds it to the service collection
     * @param type:  Type of the service
     * @param lat:   Latitude coordinate
     * @param log:   Longitude coordinate
     * @param price: Price of the service
     * @param value: Value of the service
     * @param name:  Name of the service
     */
    void createNewService(String type, long lat, long log, int price, int value, String name);

    /**
     * Gives the record of a Service with the given name
     * @param name: Name of the service
     * @return the record with the type, coordinates and name of the service
     */
    ServiceRecord giveServiceRecord(String name);

    /**
     * Gives the record of a Lodging Service with the given name
     * @param name: Name of the lodging service
     * @return the record with the type, coordinates and name of the lodging service
     */
    ServiceRecord giveLodgingRecord(String name);

    /**
     * Gives a list of all the services in the system in insertion order
     * @return an Iterator of Services
     */
    Iterator<Service> giveListOfServices();

    /**
     * Creates a new student of the given type, with the given name, country and house
     * and adds it to be students collection and the students currently in the house
     * @param type:    Type of the student
     * @param name:    Name of the student
     * @param country: Country of the student
     * @param house:   House of the student
     */
    void createNewStudent(String type, String name, String country, String house);

    /**
     * Gives the record of the student with the given name
     * @param name: Name of the student
     * @return the record with the type, name and country
     */
    StudentRecord giveStudentRecord(String name);

    /**
     * Removes a Student of the given name from the students collection
     * and from his location
     *
     * @param name: Name of the student
     * @return the name of the student that was removed
     */
    String removeStudent(String name);

    /**
     * Gives a list of all the students of a given country in alphabetical order
     * or in insertion order, if the country is "all"

     * @param country: Country of the students
     * @return an Iterator of Students
     */
    Iterator<Student> giveListOfStudents(String country);

    /**
     * Changes the location of the student with given name to a service with the given name
     *
     * @param student:     Name of the student that will to change location
     * @param newLocation: Name of the location the student tries to go
     */
    void changeLocationOfStudent(String student, String newLocation);

    /**
     * Verifies if the student is thrifty and is distracted
     * The student is distracted if is new eating they just moved is more expensive than the cheapest that they found
     * @param student:  Student that just moved
     * @param location: New location of the service
     * @return if the student is thrifty and, if so, if the new location
     * is more expensive that the cheapest they have visited
     */
    boolean thriftyIsDistracted(String student, String location);

    /**
     * Changes the student with the given name home to the lodging service
     * with the given name that exists in the system
     *
     * @param student: Name of the student that wants to move
     * @param home:    Name of the service the student wants to move
     */
    void changeStudentHome(String student, String home);

    /**
     * Gives an Iterator of all the current Students in the service with the given name
     *
     * @param name: Name of the service
     * @return a Two Way Iterator of Students
     */
    TwoWayIterator<Student> listUsersOfService(String name);

    /**
     * Locates a student with the given name
     *
     * @param name: Name of the student
     * @return the service the student is currently in
     */
    Service whereIsStudent(String name);

    /**
     * Gives a list of all the visited locations of the student with the given name
     *
     * @param name: Name of the student
     * @return an Iterator of String
     */
    Iterator<String> showVisitedLocations(String name);

    /**
     * Gives a new evaluation to the service of the given name
     * The evaluation is composed of a rating and a description
     *
     * @param rating:      Number of stars given to the service
     * @param name:        Name of the service
     * @param description: Description of the service given in the evaluation
     */
    void evaluateService(int rating, String name, String description);

    /**
     * Gives a list of all Services ordered by their rating.
     * If two services have the same average, the order should be by which the average was last updated in the service.
     *
     * @return an iterator or Services
     */
    Iterator<Service> showServicesByRank();

    /**
     * Finds the closest service of a given type with a given rating that are closest to the student with a given name
     * If two services are tie in distance, the two are given in order
     * of the time at which the average rating was obtained
     *
     * @param type:   Type of the service
     * @param rating: Number of stars asked
     * @param name:   Name of the student
     * @return an Iterator of Services
     */
    Iterator<Service> showClosestServices(String type, int rating, String name);

    /**
     * Gives an iterator of Services that contains a given tag in their reviews
     * @param tag: Specified tag asked
     * @return a filtered iterator of services that are filtered
     * by a filter witch verifies if the service has the tag
     */
    Iterator<Service> listAllTags(String tag);

    /**
     * Finds the most relevant service of the given type that best suits the student with the given name
     * @param type: Type of service
     * @param student: Name of the student
     * @return the cheapest service of the type if the student is thrifty,
     * otherwise returns the service with the best rating of the type
     */
    String findService(String type, String student);

    /**
     * Gives the geographic coordinates of the system
     * @return the record with the latitude and longitude values of the area
     */
    Bounds getBounds();
}
