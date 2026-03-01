package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * In-order Binary Tree iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
public class InOrderIterator<E> implements Iterator<E> {

    /**
     * Node with the current element
     */
    private BTNode<E> next;

    /**
     * Root Node
     */
    private final BTNode<E> root;

    /**
     * Constructor
     * @param root: Root of the tree
     */
    public  InOrderIterator(BTNode<E> root) {
        this.root=root;
        rewind();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return next!=null;
    }

    /**
     * Returns the next element in the iteration.
     *  Best Case: O(1)
     *  Worst Case: O(log n), n being the number of elements in the tree with the root
     *  Average Case: O(log n), n being the number of elements in the tree with the root
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        E elem=next.getElement();
        advance();
        return elem;
    }

    /**
     * Advances to the next element to return of the tree
     *  Best Case: O(1)
     *  Worst Case: O(log n), n being the number of elements in the tree with the root
     *  Average Case: O(log n), n being the number of elements in the tree with the root
     */
    private void advance() {
        if(next.getRightChild() != null)
            next = ((BTNode<E>) next.getRightChild()).furtherLeftElement();
        else {
            BTNode<E> parent = (BTNode<E>) next.getParent();
            while (parent != null && next == parent.getRightChild()) {
                next = parent;
                parent = (BTNode<E>) parent.getParent();
            }
            next = parent;
        }
    }


    /**
     * Restarts the iteration.
     *  Best Case: O(1)
     *  Worst Case: O(log n), n being the number of elements in the tree with the root
     *  Average Case: O(log n), n being the number of elements in the tree with the root
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        if (root==null)
            next=null;
        else
            next=root.furtherLeftElement();
    }
}
