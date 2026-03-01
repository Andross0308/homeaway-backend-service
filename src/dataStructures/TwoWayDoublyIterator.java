package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
class TwoWayDoublyIterator<E> extends DoublyIterator<E>
        implements TwoWayIterator<E> {

    /**
     * Node with the last element in the iteration.
     */
    private final DoublyListNode<E> lastNode;
    /**
     * Node with the previous element in the iteration.
     */
    DoublyListNode<E> prevToReturn;

    /**
     * DoublyLLIterator constructor
     *
     * @param first - Node with the first element of the iteration
     * @param last  - Node with the last element of the iteration
     */
    public TwoWayDoublyIterator(DoublyListNode<E> first, DoublyListNode<E> last) {
        super(first);
        lastNode = last;
        prevToReturn = last;
    }

    /**
     * Returns true if previous would return an element
     * rather than throwing an exception.
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return true iff the iteration has more elements in the reverse direction
     */
    public boolean hasPrevious( ) {
        return prevToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next( ){
        if(!hasNext()) throw new NoSuchElementException();
       return super.next();
    }

    /**
     * Returns the previous element in the iteration.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return previous element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E previous( ) {
        if(!hasNext()) throw new NoSuchElementException();
        DoublyListNode<E> node = prevToReturn;
        prevToReturn = prevToReturn.getPrevious();
        return node.getElement();
    }

    /**
     * Restarts the iteration in the reverse direction.
     * After fullForward, if iteration is not empty,
     * previous will return the last element
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     */
    public void fullForward() {prevToReturn = lastNode;}

    /**
     * Restart the iterator
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     */
    public void rewind() {super.rewind();}
}
