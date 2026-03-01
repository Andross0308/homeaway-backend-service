package Service;

import java.lang.Comparable;

import java.io.Serializable;

public interface Service extends Serializable, Comparable<Service> {

     int INITIAL_SERVICE_DATASTRUCTURES_SIZE = 100;
     int DEFAULT_MIN_RATING = 1;
     int DEFAULT_MAX_RATING = 5;

    /**
     * Verifies if the other Service is the same as this one based on the name
     * @param other: Other service
     * @pre other != null
     * @return true if this and other have the same name, false otherwise
     */
    boolean equals(Object other);

    /**
     * Gives the price of the service
     * @return service price
     */
    int getPrice();

    /**
     * Gives the record of the service that stores type, coordinates and name
     * @return service record
     */
    ServiceRecord getRecord();

    /**
     * Gives the rating of the service
     * @return service rating
     */
    int getRating();

    /**
     * Gives the last time the service rating has updated
     * @return service lastChanged
     */
    int getLastUpdated();

    /**
     * Gives the string that contains all the tags given to the service
     * @return all tags of the service
     */
    String getAllTags();

    /**
     * Gives a new evaluation to the service, calculates a new average rating and,
     * if it changes, the lastUpdated changes to numRating
     * and adds the tag to the one that already exist in the service
     * @param star: New rating to add to the service
     * @param numRating: Total number of ratings in the system
     * @param tags: Tags of the description given in the evaluation
     */
    void newEvaluation(int star, int numRating, String tags);
}
