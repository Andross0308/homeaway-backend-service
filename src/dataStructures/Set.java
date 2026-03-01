package dataStructures;

import java.io.Serializable;

public interface Set<E> extends Serializable {

    boolean isEmpty();

    int size();
    /**
     * Adds a new Student to the end of the Set
     * @param s: New student to add
     * @pre get(key) == null;
     */
    void addElement(String key, E s);

    /**
     * Removes an Element from the set
     * @param s: Name of the Element
     * @return the removed element or null, if the element doesn't exist
     */
    E removeElement(String s);

    /**
     * Gives the Element with the given name
     * @param name: Name of the element
     * @return the element with that name or null, if the name doesn't correspond with any element
     */
    E getElement(String name);

    /**
     * Gives an Iterator of the Elements in the set
     * @return an Iterator of Elements
     */
    Iterator<E> iterator();

    /**
     * Gives an Iterator of the elements in the set that can be iterated from start to finish
     * or finish to start
     * @return an iterator of Elements
     */
    TwoWayIterator<E> twoWayIterator();
}
