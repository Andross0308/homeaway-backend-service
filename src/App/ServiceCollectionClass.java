package App;

import Exceptions.*;
import Service.*;
import Students.StudentTypeEnum;
import dataStructures.*;

import java.io.Serial;

public class ServiceCollectionClass implements ServiceCollection{


    @Serial private static final long serialVersionUID = 5L;


    //Data Structure that serves to get service with if is name in constant time
    private final Map<String, Service> servicesByName;
    //Stores the services in insertion order
    private final List<Service> servicesByInsertionOrder;
    //Stores the services ordered by rating and last updated
    private final SortedMap<Service, Service> servicesOrderedByRating;
    //Stores the services divided by their types, the services are sorted by rating and last updated
    private final Map<String, SortedMap<Service, Service>> servicesByType;
    //Stores the less expensive service of every type
    private final Map<String, Service> cheapestServiceByType;
    //Stores the service with the best average of every type
    private final Map<String, Service> bestAverageServiceByType;

    /**
     * Constructor
     */
    public ServiceCollectionClass(){
        this.servicesByName = new ClosedHashTable<>(Service.INITIAL_SERVICE_DATASTRUCTURES_SIZE);
        this.servicesByInsertionOrder = new DoublyLinkedList<>();
        this.servicesOrderedByRating = new AVLSortedMap<>();
        this.servicesByType = new ClosedHashTable<>(ServiceTypeEnum.values().length);
        this.cheapestServiceByType = new ClosedHashTable<>(ServiceTypeEnum.values().length);
        this.bestAverageServiceByType = new ClosedHashTable<>(ServiceTypeEnum.values().length);
        for(ServiceTypeEnum type: ServiceTypeEnum.values()){
            servicesByType.put(type.name(), new AVLSortedMap<>());
            cheapestServiceByType.put(type.name(), null);
            bestAverageServiceByType.put(type.name(), null);
        }
    }

    /**
     * Verifies if the system as services
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return true if there is, at least, one service in the system
     */
    @Override
    public boolean hasServices() {
        return servicesByInsertionOrder.isEmpty();
    }

    /**
     * Returns a Service that has the given name
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of services in the system;
     * Average Case: O(1);
     * @param name: Name of the service
     * @return a Service with the given name or null,
     * if no services has that name
     */
    @Override
    public Service getServiceByName(String name) {
        return servicesByName.get(name.toLowerCase());
    }

    /**
     * Adds a new service to the collection
     * Best Case: O(log n), n being the number of services in the collection
     * Best Case: O(n), n being the number of services in the collection
     * Best Case: O(log n), n being the number of services in the collection
     * @param type: Type of the service
     * @param lat: Latitude coordinate of the service
     * @param log: Longitude coordinate of the service
     * @param price: Price of the service
     * @param value: Value of the service
     * @param name: Name of the service
     * @param numRatings: Number of ratings in the system
     * @throws ObjectAlreadyExists ig a service with the given name already exists in the system
     */
    @Override
    public void createNewService(String type, long lat, long log, int price, int value, String name, int numRatings) {
        if(getServiceByName(name) != null)
            throw new ObjectAlreadyExists();
        Service s = createTypeService(type, lat, log, price, value, name, numRatings);
        String t = ServiceTypeEnum.getValue(type).name();
        servicesByName.put(name.toLowerCase(), s);
        servicesByInsertionOrder.addLast(s);
        servicesOrderedByRating.put(s, s);
        servicesByType.get(t).put(s, s);
        if( cheapestServiceByType.get(t) == null ||
                cheapestServiceByType.get(t).getPrice() > s.getPrice())
            cheapestServiceByType.put(t, s);
        if(bestAverageServiceByType.get(t) == null)
            bestAverageServiceByType.put(t, servicesByType.get(t).minEntry().value());
    }

    /**
     * Verifies the type of the service and creates a new service of that type
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @param type:  Type of the service
     * @param lat:   Latitude coordinates
     * @param log:   Longitude coordinates
     * @param price: Price of the service
     * @param value: Value of the service
     * @param name:  Name of the service
     */
    private Service createTypeService(String type, long lat, long log, int price, int value, String name, int numRatings) {
        ServiceTypeEnum t = ServiceTypeEnum.getValue(type);
        return switch (t) {
            case ServiceTypeEnum.EATING -> createNewEatingService(lat, log, price, value, name, numRatings);
            case ServiceTypeEnum.LODGING -> createNewLodgingService(lat, log, price, value, name, numRatings);
            case ServiceTypeEnum.LEISURE -> createNewLeisureService(lat, log, price, value, name, numRatings);
        };
    }

    /**
     * Creates a new eating service in the system and adds it to the services data structures
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @param lat:      Latitude Coordinate
     * @param log:      Longitude Coordinate
     * @param price:    Price of the menu
     * @param capacity: Max number of students that can be simultaneous in the service
     * @param name:     Name of the service
     * @throws NegativeEatingServicePrice if the price of the menu is negative or zero
     * @throws InvalidServiceCapacity     if the capacity is negative or zero
     */
    private Service createNewEatingService(long lat, long log, int price, int capacity, String name, int numRatings) {
        if (price <= DEFAULT_MINIMUM_INTEGER) throw new NegativeEatingServicePrice();
        if (capacity <= DEFAULT_MINIMUM_INTEGER) throw new InvalidServiceCapacity();
        return new EatingServiceClass(lat, log, price, capacity, name, numRatings);
    }

    /**
     * Creates a new lodging service in the system and adds it to the services data structures
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @param lat:      Latitude Coordinate
     * @param log:      Longitude Coordinate
     * @param price:    Price of the room
     * @param capacity: Max number of students that can be simultaneous in the service
     * @param name:     Name of the service
     * @throws NegativeLodgingServiceRoomPrice if the price of the room is negative or zero
     * @throws InvalidServiceCapacity          if the capacity is negative or zero
     */
    private Service createNewLodgingService(long lat, long log, int price, int capacity, String name, int numRatings) {
        if (price <= DEFAULT_MINIMUM_INTEGER) throw new NegativeLodgingServiceRoomPrice();
        if (capacity <= DEFAULT_MINIMUM_INTEGER) throw new InvalidServiceCapacity();
        return new LodgingServiceClass(lat, log, price, capacity, name, numRatings);
    }

    /**
     * Creates a new leisure service in the system and adds it to the services data structures
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     *
     * @param lat:      Latitude Coordinate
     * @param log:      Longitude Coordinate
     * @param price:    Price of the ticket
     * @param discount: Students discount
     * @param name:     Name of the service
     * @throws NegativeLeisureServiceTicketPrice if the price of the ticket is negative or zero
     * @throws InvalidLeisureServiceDiscount     if the discount is negative or bigger than 0
     */
    private Service createNewLeisureService(long lat, long log, int price, int discount, String name, int numRatings) {
        if (price <= DEFAULT_MINIMUM_INTEGER) throw new NegativeLeisureServiceTicketPrice();
        if (discount < DEFAULT_MINIMUM_INTEGER || discount > DEFAULT_MAX_DISCOUNT)
            throw new InvalidLeisureServiceDiscount();
        return new LeisureServiceClass(lat, log, price, discount, name, numRatings);
    }

    /**
     * Removes the service with the given name of the sorted maps to give a new evaluation
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of service ;
     * Average Case: O(log n), n being the number of service;
     * @param name : Name of the service
     * @return the service removed or null, if the service doesn't exist
     */
    @Override
    public Service giveServiceToEvaluation(String name) {
        Service s = getServiceByName(name);
        if(s == null) return null;
        servicesOrderedByRating.remove(s);
        servicesByType.get(s.getRecord().type().toUpperCase()).remove(s);
        return s;
    }

    /**
     * Updates a service in the sorted lists because
     * his average rating has changed
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of services in the collection;
     * Average Case: O(log n), n being the number of services in the collection;
     * @param s : Service that received an evaluation and average changed
     */
    @Override
    public void updateServiceEvaluation(Service s) {
        servicesOrderedByRating.put(s, s);
        String type = ServiceTypeEnum.getValue(s.getRecord().type()).name();
        servicesByType.get(type).put(s,s);
        bestAverageServiceByType.put(type, servicesByType.get(type).minEntry().value());
    }

    /**
     * Gives the most relevant service of the given type, according to the student type
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of valid services type;
     * Average Case: O(1);
     * @param studentType : Valid Student Type
     * @param serviceType : String of a Valid Service Type
     * @return the most relevant service of the given type, being the one with best rating
     * in case of a BOOKISH or OUTGOING student or the cheapest in case of a THRIFTY
     */
    @Override
    public Service giveMostRelevantService(StudentTypeEnum studentType, String serviceType) {
        if(studentType == StudentTypeEnum.THRIFTY)
            return cheapestServiceByType.get(serviceType);
        return bestAverageServiceByType.get(serviceType);
    }

    /**
     * Gives an iterator that will iterate over all services, in insertion order
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return an iterator of services
     */
    @Override
    public Iterator<Service> servicesInOrderIterator() {
        return servicesByInsertionOrder.iterator();
    }

    /**
     * Gives an iterator that iterates over all services, in rating order
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return an iterator of services
     */
    @Override
    public Iterator<Service> servicesInRatingOrder() {
        return servicesOrderedByRating.values();
    }

    /**
     * Gives an iterator that iterates over all services of a given type,
     * in rating order
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of valid services type;
     * Average Case: O(1);
     * @param type : Type of the Service
     * @return an iterator of services
     */
    @Override
    public Iterator<Service> servicesOfGivenType(String type) {
        SortedMap<Service, Service> elems = servicesByType.get(type.toUpperCase());
        return elems.values();
    }

    /**
     * Gives a filtered iterator that iterates over all services
     * but only gives the ones that have received the given tag in their description
     * Best Case: O(1);
     * Worst Case: O(n), n being the length of the tag;
     * Average Case: O(n), n being the length of the tag;
     * @param tag : Tag to filter
     * @return an iterator of services
     */
    @Override
    public Iterator<Service> filteredIterator(String tag) {
        return new FilterIterator<>(servicesByInsertionOrder.iterator(), new ServiceFilter(tag.toCharArray()));
    }
}
