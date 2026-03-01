package Students;


import Service.Service;
import dataStructures.Iterator;

public interface BookishOutgoingStudent extends Student{

    /**
     * Ads a new location to the ones he has visited
     * If bookish, only stores Leisure Services
     * If outgoing, stores every service
     * @param service: Service the student attends
     * @pre: service != null
     */
    void addVisitedLocation(Service service);

    /**
     * Gives an Iterator of all stored services visited by the student
     * @return an Iterator of Strings
     */
    Iterator<String> showVisitedLocations();
}
