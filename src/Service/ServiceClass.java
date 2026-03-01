package Service;

import dataStructures.ClosedHashTable;
import dataStructures.Map;

import java.io.Serial;

public abstract class ServiceClass implements Service {

    @Serial
    private static final long serialVersionUID = 10L;

    //Rating to every service when created
    private static final int INITIAL_RATING = 4;

    private static final String SPACE = " ";

    //Global Variables
    private final ServiceRecord record;
    private int lastChanged;
    private int averageRating;
    private int numberOfEvaluations;

    //String containing all tags given to the service
    private String tags;

    //Map that stores the number of times the service received an evaluation
    private final Map<Integer, Integer> stars;

    /**
     * Constructor of a new service with a new name
     * @param type: Valid Service type(Eating, Lodging or Leisure)
     * @param lat: The latitude of the service
     * @param log: The longitude of the service
     * @param name: The name of the service
     * @param numRating: The total number of ratings in the system
     *                 that will say when the averageRating has changed
     */
    public ServiceClass(ServiceTypeEnum type, long lat, long log, String name, int numRating){
        this.record = new ServiceRecord(type.name().toLowerCase(), lat, log, name);
        this.tags = "";
        this.stars = new ClosedHashTable<>(DEFAULT_MAX_RATING);
        for(int i = DEFAULT_MIN_RATING; i < DEFAULT_MAX_RATING + 1; i++){
            stars.put(i, 0);
        }
        stars.put(INITIAL_RATING, 1);
        averageRating = INITIAL_RATING;
        this.numberOfEvaluations = 1;
        this.lastChanged = numRating;
    }

    /**
     * Compares this service to another using their rating and lastUpdated
     * If result > 0, this > other;
     * if result < 0, this < other;
     * if result = 0, this == order;
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param other the object to be compared.
     * @return a negative, positive or 0
     */
    @Override
    public int compareTo(Service other) {
        int result = other.getRating() - this.averageRating;
        if(result == 0)
            result = this.lastChanged - other.getLastUpdated();
        return result;
    }

    /**
     * Calculates the average rating of the service by calculating the average of all evaluations
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return a rounded integer being the average of all evaluations
     */
    private int calculateAverage(){
        float total = 0;
        for(int i = DEFAULT_MIN_RATING; i < DEFAULT_MAX_RATING + 1; i++){
            total += stars.get(i) * i;
        }
        return Math.round(total/ numberOfEvaluations);
    }

    /**
     * Verifies if the other Service is the same as this one based on the name
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param other: Other service
     * @pre: other != null
     * @return if the names of the service are the same, false otherwise
     *
     */
    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Service s))
            return false;
        return s.getRecord().name().equalsIgnoreCase(this.record.name());
    }

    /**
     * Gives the record of the service that stores type, coordinates and name
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service record with the type, coordinates and name of the service
     */
    @Override
    public ServiceRecord getRecord() {
        return record;
    }

    /**
     * Gives the rating of the service
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service averageRating
     */
    @Override
    public int getRating() {
        return averageRating;
    }

    /**
     * Gives the last time the service rating has updated
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service lastChanged
     */
    @Override
    public int getLastUpdated() {
        return lastChanged;
    }

    /**
     * Gives the string that contains all the tags given to the service
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return all tags of the service
     */
    @Override
    public String getAllTags() {
        return tags;
    }

    /**
     * Gives a new evaluation to the service, calculates a new average rating and,
     * if it changes, the lastUpdated changes to numRating
     * and adds the tag to the one that already exist in the service
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of valid ratings for the service;
     * Average Case: O(1);
     * @param star: New rating to add to the service
     * @param numRating: Total number of ratings in the system
     * @param tags: Tags of the description given in the evaluation
     */
    @Override
    public void newEvaluation(int star, int numRating, String tags){
        int newValue = stars.get(star) + 1;
        stars.put(star, newValue);
        numberOfEvaluations++;
        int newAverage = calculateAverage();
        if (newAverage != averageRating){
            averageRating = newAverage;
            lastChanged = numRating;
        }
        this.tags += SPACE + tags.toLowerCase();
    }

}
