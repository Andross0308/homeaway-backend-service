package App;

import Service.Service;
import Students.StudentTypeEnum;
import dataStructures.Iterator;

import java.io.Serializable;

public interface ServiceCollection extends Serializable {

    int DEFAULT_MINIMUM_INTEGER = 0;
    int DEFAULT_MAX_DISCOUNT = 100;

    /**
     * Verifies if the system as services
     * @return true if there is, at least, one service in the system
     */
    boolean hasServices();

    /**
     * Returns a Service that has the given name
     * @param name: Name of the service
     * @return a Service with the given name or null,
     * if no services has that name
     */
    Service getServiceByName(String name);

    /**
     * Adds a new service to the collection
     * @param type: Type of the service
     * @param lat: Latitude coordinate of the service
     * @param log: Longitude coordinate of the service
     * @param price: Price of the service
     * @param value: Value of the service
     * @param name: Name of the service
     * @param numRatings: Number of ratings in the system
     */
    void createNewService(String type, long lat, long log, int price, int value, String name, int numRatings);

    /**
     * Removes the service with the given name of the sorted maps to give a new evaluation
     * @param name: Name of the service
     * @return the service removed or null, if the service doesn't exist
     */
    Service giveServiceToEvaluation(String name);


    /**
     * Updates a service in the sorted lists because
     * his average rating has changed
     * @param s: Service that received an evaluation and average changed
     */
    void updateServiceEvaluation(Service s);


    /**
     * Gives the most relevant service of the given type, according to the student type
     * @param studentType: Valid Student Type
     * @param serviceType: Valid Service Type
     * @return the most relevant service of the given type, being the one with best rating
     * in case of a BOOKISH or OUTGOING student or the cheapest in case of a THRIFTY
     */
    Service giveMostRelevantService(StudentTypeEnum studentType, String serviceType);

    /**
     * Gives an iterator that will iterate over all services, in insertion order
     * @return an iterator of services
     */
    Iterator<Service> servicesInOrderIterator();

    /**
     * Gives an iterator that iterates over all services, in rating order
     * @return an iterator of services
     */
    Iterator<Service> servicesInRatingOrder();

    /**
     * Gives an iterator that iterates over all services of a given type,
     * in rating order
     * @param type: Type of the Service
     * @return an iterator of services
     */
    Iterator<Service> servicesOfGivenType(String type);

    /**
     * Gives a filtered iterator that iterates over all services
     * but only gives the ones that have received the given tag in their description
     * @param tag: Tag to filter
     * @return an iterator of services
     */
    Iterator<Service> filteredIterator(String tag);

}
