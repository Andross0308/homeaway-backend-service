package dataStructures;

import java.io.Serial;
/**
 * Array Iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class ArrayIterator<E> implements Iterator<E> {

    @Serial private static final long serialVersionUID = 0L;

    private final E[] elems;
    private final int counter;
    private int current;
    
    public ArrayIterator(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        rewind();
    }

    /**
     * Returns the iterator to the first element
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     */
    @Override
    public void rewind() {
        current = 0;
    }

    /**
     * Verifies if the iterator has elements to return
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return if there is a next element to return
     */
    @Override
    public boolean hasNext() {
        return current < counter;
    }

    /**
     * Returns the next element of the array
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return the next element
     * @pre hasNext()
     */
    @Override
    public E next() {
        return elems[current++];
    }

}
