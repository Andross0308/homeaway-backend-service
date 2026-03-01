package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     *  Iterator of elements to filter.
     */
    final Iterator<E>  iterator;

    /**
     *  Filter.
     */
    final Predicate<E>  criterion;

    /**
     * Node with the next element in the iteration.
     */
    E nextToReturn;


    /**
     * Constructor
     * @param list to be iterated
     * @param criterion filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> criterion) {
        this.iterator = list;
        this.criterion = criterion;
    }

    /**
     * Returns true if next would return an element
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the number of elements in the iterator
     *  Average Case: O(n), n being the number of elements in the iterator
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        if(nextToReturn != null) return true;

        while(iterator.hasNext()){
            E candidate = iterator.next();
            if(criterion.check(candidate)){
                nextToReturn = candidate;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the next element in the iteration.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the number of elements in the iterator
     *  Average Case: O(1)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if(!hasNext()) throw new NoSuchElementException();
        E next = nextToReturn;
        nextToReturn = null;
        return next;
    }

    /**
     * Restarts the iteration.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        iterator.rewind();
        nextToReturn = null;
    }

}
