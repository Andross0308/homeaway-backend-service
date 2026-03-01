package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
/**
 * Iterator of keys
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
class KeysIterator<E> implements Iterator<E> {

    private final Iterator<Map.Entry<E,?>> it;

    /**
     * Constructor
     * @param it: Iterator
     */
    public KeysIterator(Iterator<Map.Entry<E,?>> it) {
        this.it = it;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return it.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if(!hasNext()) throw new NoSuchElementException();
        return it.next().key();
    }

    /**
     * Restarts the iteration.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        it.rewind();
    }
}
