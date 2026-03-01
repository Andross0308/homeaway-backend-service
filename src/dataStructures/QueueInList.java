package dataStructures;

import dataStructures.exceptions.*;

import java.io.Serial;

public class QueueInList<E> implements Queue<E> {

    @Serial
    private static final long serialVersionUID = 32L;

    // Memory of the queue: a list.
    private final List<E> list;

    public QueueInList( ){
        list = new SinglyLinkedList<>();
    }

    /**
     * Returns true iff the queue contains no elements.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return true if it's empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return size
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Inserts the specified element at the rear of the queue.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param element: New element to insert
     */
    @Override
    public void enqueue(E element) {
        list.addLast(element);
    }

    /**
     * Removes and returns the element at the front of the queue.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return the first element in the list
     * @throws EmptyQueueException if the list is empty
     */
    @Override
    public E dequeue() {
        if(isEmpty()) throw new EmptyQueueException();
        return list.removeFirst();
    }
    /**
     * Returns the element at the front of the queue.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return the first element in the list
     * @throws EmptyQueueException if the list is empty
     */
    @Override
    public E peek() {
        if(isEmpty()) throw new EmptyQueueException();
        return list.get(0);
    }
}
