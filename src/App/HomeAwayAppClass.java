package App;

import Exceptions.*;
import Service.*;
import Students.*;
import dataStructures.*;

import java.io.Serial;

public class HomeAwayAppClass implements HomeAwayApp {


    @Serial
    private static final long serialVersionUID = 4L;

    //Constants
    private static final int DEFAULT_MINIMUM_INTEGER = 0;
    private static final String ALL = "all";

    //Bounds with the coordinates of the area
    private final Bounds coordinates;

    //Total number of ratings given to the services in the system
    private int numRatings;

    // Collection that stores all service information
    private ServiceCollection serviceCollection;

    // Collection that stores all student information
    private StudentCollection studentsCollection;

    /**
     * Initializes the app with invalid coordinates and invalid name
     */
    public HomeAwayAppClass() {
        this.coordinates = new Bounds(DEFAULT_MINIMUM_INTEGER, DEFAULT_MINIMUM_INTEGER,
                DEFAULT_MINIMUM_INTEGER, DEFAULT_MINIMUM_INTEGER, null);
    }

    /**
     * Constructor of a valid area
     * @param minLatitude:  Minimal value of the latitude to any service
     * @param maxLatitude:  Max value of the latitude to any service
     * @param minLongitude: Minimal value of the longitude to any service
     * @param maxLongitude: Max value of the longitude to any service
     * @param newName:      Name of the area
     */
    public HomeAwayAppClass(long minLatitude, long maxLatitude, long minLongitude, long maxLongitude, String newName) {
        this.coordinates = new Bounds(minLatitude, maxLatitude, minLongitude, maxLongitude, newName);
        this.numRatings = 1;
        this.serviceCollection = new ServiceCollectionClass();
        this.studentsCollection = new StudentCollectionClass();
    }

    /**
     * Verifies if the current area is a valid area
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @throws SystemNotDefinedBounds if the coordinates of the service are the same(Bounds have not been defined)
     */
    @Override
    public void hasBounds() {
        if (coordinates.minLat() == coordinates.maxLat() || coordinates.minLong() == coordinates.maxLong())
            throw new SystemNotDefinedBounds();
    }

    /**
     * Verifies if the given coordinates are valid
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @param minLat: Min value of Latitude
     * @param maxLat: Max value of Latitude
     * @param minLog: Min value of Longitude
     * @param maxLog: Max value of Longitude
     * @return if the min values are lower than the max values
     */
    @Override
    public boolean validBounds(long minLat, long maxLat, long minLog, long maxLog) {
        return minLat < maxLat && minLog < maxLog;
    }

    /**
     * Verifies if the given coordinates are inside the current geographic area
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @param lat: Latitude
     * @param log: Longitude
     * @return if the values are inside the min and max values of the respective coordinate
     */
    private boolean outsideBounds(long lat, long log) {
        return coordinates.maxLat() < lat || lat < coordinates.minLat()
                || coordinates.maxLong() < log || coordinates.minLong() > log;
    }

    /**
     * Creates a new service of the given type, in the respective coordinates,
     * with the given price, value and name and adds it to the service collection
     * Best Case: O(log n), n being the number of services in the collection
     * Worst Case: O(n), n being the number of services in the collection
     * Average Case: O(log n), n being the number of services in the collection
     *
     * @param type:  Type of the service
     * @param lat:   Latitude coordinate
     * @param log:   Longitude coordinate
     * @param price: Price of the service
     * @param value: Value of the service
     * @param name:  Name of the service
     * @throws ServiceOutsideOfBounds if the coordinates of the service are outside the geographic area
     */
    @Override
    public void createNewService(String type, long lat, long log, int price, int value, String name) {
        hasBounds();
        if (outsideBounds(lat, log))
            throw new ServiceOutsideOfBounds();
        serviceCollection.createNewService(type, lat, log, price, value, name, numRatings);
        numRatings++;
    }

    /**
     * Gives the record of a Service with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of services in the collection
     * Average Case: O(1);
     *
     * @param name: Name of the service
     * @return the record with the type, coordinates and name of the service
     */
    @Override
    public ServiceRecord giveServiceRecord(String name) {
        return giveService(name).getRecord();
    }

    /**
     * Gives the record of a Lodging Service with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of services in the collection
     * Average Case: O(1);
     *
     * @param name: Name of the lodging service
     * @return the record with the type, coordinates and name of the lodging service
     */
    @Override
    public ServiceRecord giveLodgingRecord(String name) {
        return giveLodging(name).getRecord();
    }

    /**
     * Gives the Service with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of services in the collection
     * Average Case: O(1);
     *
     * @param name: Name of the service
     * @return the service with that name
     * @throws ServiceDoesNotExistInTheSystem if there is no service with that name n the service collection
     */
    private Service giveService(String name) {
        Service s = serviceCollection.getServiceByName(name);
        if(s == null)
            throw new ServiceDoesNotExistInTheSystem();
        return s;
    }

    /**
     * Gives a list of all the services in the system in insertion order
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @return an Iterator of Services
     * @throws NoServicesToDisplay if there is no services in the system
     */
    @Override
    public Iterator<Service> giveListOfServices() {
        hasBounds();
        if (serviceCollection.hasServices()) throw new NoServicesToDisplay();
        return serviceCollection.servicesInOrderIterator();
    }

    /**
     * Gives the Lodging Service of the given name
     * Best Case: O(1);
     * Best Case: O(n), n being the number of services in the collection;
     * Best Case: O(1);
     *
     * @param name: Name of the lodging service
     * @return the lodging service with that name
     * @throws HomeDoesNotExistInTheSystem if there is no lodging service with that name
     */
    private Service giveLodging(String name) {
        Service s = serviceCollection.getServiceByName(name);
        if (!(s instanceof LodgingServiceClass))
            throw new HomeDoesNotExistInTheSystem();
        return s;
    }

    /**
     * Creates a new student of the given type, with the given name, country and house
     * and adds it to be students collection and the students currently in the house
     * Best Case: O(log n), n being the number of students in the collection;
     * Worst Case: O(n + m), n being the number of services and m the number of students in the collection;
     * Average Case: O(log n), n being the number of students in the collection;
     *
     * @param type:    Type of the student
     * @param name:    Name of the student
     * @param country: Country of the student
     * @param house:   House of the student
     * @throws LodgingIsFull if the house is full
     */
    @Override
    public void createNewStudent(String type, String name, String country, String house) {
        hasBounds();
        EatingLodgingService home = (EatingLodgingService) giveLodging(house);
        if (home.isFull()) throw new LodgingIsFull();
        Student student = studentsCollection.addNewStudent(type, name, country,home);
        home.addStudent(student);
        if (student instanceof BookishOutgoingStudent)
            ((BookishOutgoingStudent) student).addVisitedLocation(home);
    }

    /**
     * Gives the record of the student with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the collection;
     * Average Case: O(1);
     *
     * @param name: Name of the student
     * @return the record with the type, name and country
     */
    @Override
    public StudentRecord giveStudentRecord(String name) {
        return giveStudent(name).getRecord();
    }

    /**
     * Gives the student with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the collection;
     * Average Case: O(1);
     *
     * @param name: Name of the student
     * @return the student with that name
     * @throws DoesNotExistInTheSystem if no student of that name exist in the system
     */
    private Student giveStudent(String name) {
        Student s = studentsCollection.getStudentByName(name);
        if (s == null)
            throw new DoesNotExistInTheSystem();
        return s;
    }

    /**
     * Removes a Student of the given name from the students collection
     * and from his location
     * Best Case: O(1);
     * Worst Case: O(n + m), n being the number of services and m the number of students in the collection;
     * Average Case: O(1);
     *
     * @param name: Name of the student
     * @return the name of the student that was removed
     * @throws DoesNotExistInTheSystem if the name doesn't correspond to any student in the system
     */
    @Override
    public String removeStudent(String name) {
        hasBounds();
        Student s = studentsCollection.removeStudent(name.toLowerCase());
        if (s == null) throw new DoesNotExistInTheSystem();
        Service location = giveService(s.getLocation());
        if (location instanceof EatingLodgingService)
            ((EatingLodgingService) location).removeStudent(s);
        return s.getRecord().name();
    }

    /**
     * Gives a list of all the students of a given country in alphabetical order
     * or in insertion order, if the country is "all"
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of countries in the collection;
     * Average Case: O(1);
     *
     * @param country: Country of the students
     * @return an Iterator of Students
     */
    @Override
    public Iterator<Student> giveListOfStudents(String country) {
        hasBounds();
        if (country.equals(ALL)) {
            return studentsCollection.studentInAlphabeticalOrderIterator();
        } else {
            return studentsCollection.studentsOfCountry(country);
        }
    }

    /**
     * Changes the location of the student with given name to a service with the given name
     * Best Case: O(1);
     * Worst Case: O(n + m), n being the number of services and m the number of students in the collection;
     * Average Case: O(1);
     *
     * @param student:     Student that want to change location
     * @param newLocation: Location the student tries to go
     * @throws InvalidChangeOfLocation         if the new location is a lodging Service
     * @throws StudentLocationIsTheNewLocation if the student location is the new location
     * @throws EatingServiceIsFull             if the student tries to move to an eating that is full
     */
    @Override
    public void changeLocationOfStudent(String student, String newLocation) {
        Student stu = giveStudent(student);
        Service location = giveService(newLocation);
        Service oldLocation = giveService(stu.getLocation());
        if (location instanceof LodgingServiceClass)
            throw new InvalidChangeOfLocation();
        if (location.equals(oldLocation))
            throw new StudentLocationIsTheNewLocation();
        if (location instanceof EatingServiceClass && ((EatingServiceClass) location).isFull())
            throw new EatingServiceIsFull();
        if (oldLocation instanceof EatingServiceClass) {
            ((EatingLodgingService) oldLocation).removeStudent(stu);
        }
        if (location instanceof EatingServiceClass) {
            ((EatingLodgingService) location).addStudent(stu);
        }
        if (stu instanceof BookishOutgoingStudent)
            ((BookishOutgoingStudent) stu).addVisitedLocation(location);
        stu.changeLocation(newLocation);
    }

    /**
     * Verifies if the student is thrifty and is distracted
     * The student is distracted if is new eating they just moved is more expensive than the cheapest that they found
     * Best Case: O(1);
     * Worst Case: O(n + m), n being the number of services and m the number of students in the collection;
     * Average Case: O(1);
     *
     * @param student:  Student that just moved
     * @param location: New location of the service
     * @return if the student is thrifty and, if so, if the new location
     * is more expensive that the cheapest they have found
     */
    @Override
    public boolean thriftyIsDistracted(String student, String location) {
        Student s = giveStudent(student);
        Service service = giveService(location);
        if (service instanceof EatingServiceClass)
            return s instanceof ThriftyStudent && ((ThriftyStudent) s).checkCheapestEating(service);
        return false;
    }

    /**
     * Changes the student with the given name home to the lodging service
     * with the given name that exists in the system
     * Best Case: O(1);
     * Worst Case: O(n + m), n being the number of lodging services and m the number of students in the collection;
     * Average Case: O(1);
     *\
     * @param student: Name of the student that wants to move
     * @param home:    Name of the service the student wants to move
     * @throws StudentTriesMovingToSameHouse if the new house the student tries to move it is there home
     * @throws LodgingIsFull                 if the new house the student tries to move it's full
     * @throws InvalidMoveOfStudent          if student is thrifty and the new home is more expensive that the current home
     */
    @Override
    public void changeStudentHome(String student, String home) {
        EatingLodgingService newHome = (EatingLodgingService) giveLodging(home);
        Student s = giveStudent(student);
        EatingLodgingService oldHome = (EatingLodgingService) giveLodging(s.getHome());
        if (oldHome.equals(newHome))
            throw new StudentTriesMovingToSameHouse();
        if (newHome.isFull())
            throw new LodgingIsFull();
        if (s instanceof ThriftyStudent && oldHome.getPrice() <= newHome.getPrice())
            throw new InvalidMoveOfStudent();
        oldHome.removeStudent(s);
        if (s instanceof BookishOutgoingStudent)
            ((BookishOutgoingStudent) s).addVisitedLocation(newHome);
        s.changeHome(home);
        newHome.addStudent(s);
    }

    /**
     * Gives an Iterator of all the current Students using the service with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of services in the collection;
     * Average Case: O(1);
     *
     * @param name: Name of the service
     * @return a Two Way Iterator of Students
     * @throws DoesNotExistInTheSystem     if the name does not correspond to any service in the system
     * @throws ServiceDoesNotStoreStudents if the service is a Leisure Service
     */
    @Override
    public TwoWayIterator<Student> listUsersOfService(String name) {
        Service s = serviceCollection.getServiceByName(name);
        if (s == null) throw new DoesNotExistInTheSystem();
        if (s instanceof LeisureServiceClass)
            throw new ServiceDoesNotStoreStudents();
        return ((EatingLodgingService) s).giveCurrentStudents();
    }

    /**
     * Locates a student with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the collection;
     * Average Case: O(1);
     *
     * @param name: Name of the student
     * @return the service the student is currently in
     */
    @Override
    public Service whereIsStudent(String name) {
        hasBounds();
        Student s = giveStudent(name);
        return giveService(s.getLocation());
    }

    /**
     * Gives a list of all the visited locations of the student with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the collection;
     * Average Case: O(1);
     *
     * @param name: Name of the student
     * @return an Iterator of String
     * @throws StudentIsThrifty if the student is thrifty, thrifty do not store visited locations
     */
    @Override
    public Iterator<String> showVisitedLocations(String name) {
        hasBounds();
        Student s = giveStudent(name);
        if (s instanceof ThriftyStudent)
            throw new StudentIsThrifty();
        return ((BookishOutgoingStudent) s).showVisitedLocations();
    }

    /**
     * Gives a new evaluation to the service of the given name
     * The evaluation is composed of a rating and a description
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of services in the collection;
     * Average Case: O(log n), n being the number of services in the collection;
     *
     * @param rating:      Number of stars given to the service
     * @param name:        Name of the service
     * @param description: Description of the service given in this evaluation
     * @throws InvalidEvaluationOfService if the number of stars is outside the valid interval
     * @throws DoesNotExistInTheSystem    if there is no service with the given name
     */
    @Override
    public void evaluateService(int rating, String name, String description) {
        hasBounds();
        if (rating < Service.DEFAULT_MIN_RATING || rating > Service.DEFAULT_MAX_RATING)
            throw new InvalidEvaluationOfService();
        Service s = serviceCollection.giveServiceToEvaluation(name);
        if (s == null) throw new DoesNotExistInTheSystem();
        s.newEvaluation(rating, numRatings, description);
        serviceCollection.updateServiceEvaluation(s);
        numRatings++;
    }

    /**
     * Gives a list of all Services ordered by ranking.
     * If two services have the same average, the order should be by which the average was last updated in the service.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @return an iterator or Services
     * @throws NoServicesToDisplay if there is no service in the collection
     */
    @Override
    public Iterator<Service> showServicesByRank() {
        hasBounds();
        if (serviceCollection.hasServices())
            throw new NoServicesToDisplay();
        return serviceCollection.servicesInRatingOrder();
    }
    /**
     * Calculates the distance of two services, using the Manhattan Distance
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @param s1: Service 1
     * @param s2: Service 2
     * @return the distance between of the two services
     */
    private long calculateDistance(Service s1, Service s2) {
        return Math.abs(s1.getRecord().lat() - s2.getRecord().lat())
                + Math.abs(s1.getRecord().log() - s2.getRecord().log());
    }

    /**
     * Finds the closest service of a given type with a given rating that are closest to a student with a given name
     * If two services are tie in distance, the two are given in order
     * of the time at which the average rating was obtained
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of services of the given type in the system;
     * Average Case: O(n), n being the number of services of the given type in the system;
     *
     * @param type:   Type of the service
     * @param rating: Number of stars asked
     * @param name:   Name of the student
     * @return an Iterator of Services
     * @throws StarOutsideOfThePossibleInterval if the given rating is outside the valid interval
     * @throws NoServicesOfTypeInTheSystem      if there is no services of the given type in the system
     * @throws NoServiceWithThatRating          if there is no service of the given rating in the system
     */
    @Override
    public Iterator<Service> showClosestServices(String type, int rating, String name) {
        hasBounds();
        if (rating < Service.DEFAULT_MIN_RATING || rating > Service.DEFAULT_MAX_RATING)
            throw new StarOutsideOfThePossibleInterval();
        Service studentLocation = giveService(giveStudent(name).getLocation());
        Iterator<Service> it = serviceCollection.servicesOfGivenType(ServiceTypeEnum.getValue(type).name());
        if (!it.hasNext()) throw new NoServicesOfTypeInTheSystem();
        Service s = it.next();
        while(it.hasNext() && s.getRating() != rating){s = it.next();}
        boolean sameRating = false;
        long closestDistance = Integer.MAX_VALUE;
        List<Service> closest = new DoublyLinkedList<>();
        if(s.getRating() == rating){
            sameRating = true;
            closest.addLast(s);
            closestDistance = calculateDistance(s, studentLocation);
        }
        while (it.hasNext() && sameRating) {
            s = it.next();
            if(s.getRating() != rating) sameRating = false;
            else{
                long distance = calculateDistance(s, studentLocation);
                if (distance < closestDistance) {
                    closest = new DoublyLinkedList<>();
                    closestDistance = distance;
                }if (distance == closestDistance) closest.addLast(s);
            }
        }
        if (closest.isEmpty()) throw new NoServiceWithThatRating();
        return closest.iterator();
    }

    /**
     * Gives an iterator of Services that contains a given tag in their reviews
     * Best Case: O(1);
     * Worst Case: O(n), n being the length of the tag;
     * Average Case: O(n), n being the length of the tag;
     *
     * @param tag: Specified tag asked
     * @return a filtered iterator of services that are filtered
     * of a filter witch verifies if the service has the tag
     */
    @Override
    public Iterator<Service> listAllTags(String tag) {
        hasBounds();
        return serviceCollection.filteredIterator(tag);
    }

    /**
     * Finds the most relevant service of the given type that best suits the student with the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of students in the system;
     * Average Case: O(1);
     *
     * @param type: Type of service
     * @param student: Name of the student
     * @return the cheapest service of the type if the student is thrifty,
     * otherwise returns the service with the best rating of the type
     */
    @Override
    public String findService(String type, String student) {
        hasBounds();
        Student s = giveStudent(student);
        Service found = serviceCollection.giveMostRelevantService(StudentTypeEnum.getValue(s.getRecord().type()),
                type.toUpperCase());
        if(found == null) throw new NoServicesOfTypeInTheSystem();
        return found.getRecord().name();
    }

    /**
     * Gives the geographic coordinates of the system
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @return the record with the latitude and longitude values of the area
     */
    @Override
    public Bounds getBounds() {
        return coordinates;
    }
}
