package dataStructures;

import dataStructures.exceptions.*;

import java.io.Serial;

/**
 * Stack in Array
 *
 * @author AED team
 * @version 1.0
 *
 * @param <E> Generic Element
 */
public class StackWithListInArray<E> implements Stack<E> {

    @Serial
    private static final long serialVersionUID = 38L;

    // Default capacity of the stack.
    static final int DEFAULT_CAPACITY = 1000;
    // Top of the empty stack.
    static final int EMPTY = -1;

    // Memory of the stack: a list in array.
    private final List<E> array;
    // capacity
    private final int capacity;

    @SuppressWarnings("rawtypes")
    public StackWithListInArray( int capacity ) {
        array =  new ListInArray(capacity);
	this.capacity=capacity;
    }

    public StackWithListInArray( ) {
        this(DEFAULT_CAPACITY);
    }
    /**
     * Returns true iff the stack contains no
     * elements.
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return true iff the stack contains no
     * elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * Returns the number of elements in the stack.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return number of elements in the stack
     */
    @Override
    public int size() {
	    return array.size();
    }

    /**
     * Returns the element at the top of the stack.
     * Requires
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return element at top of stack
     * @throws EmptyStackException when size = 0
     */
    @Override
    public E top() {
        if(size() == 0) throw new EmptyStackException();
        return array.getLast();
    }

    /**
     * Inserts the specified <code>element</code> onto
     * the top of the stack.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param element element to be inserted onto the stack
     * @throws FullStackException when size = capacity
     */
    @Override
    public void push(E element) {
        if(size() == capacity) throw new FullStackException();
        array.addLast(element);
    }

    
    /**
     * Removes and returns the element at the top of the
     * stack.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return element removed from top of stack
     * @throws EmptyStackException when size = 0
     */
    @Override
    public E pop() {
        if(size() == 0) throw new EmptyStackException();
        return array.removeLast();
    }
}
