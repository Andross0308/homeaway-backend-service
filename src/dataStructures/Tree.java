package dataStructures;

import java.io.Serial;
import java.io.Serializable;

/**
 * Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
abstract class Tree<E> implements Serializable {

    @Serial private static final long serialVersionUID = 39L;

    /**
     * Root
     */
    protected Node<E> root;

    /**
     * Number of elements
     */
    protected int currentSize;

    public Tree(){
        root=null;
        currentSize=0;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return true if dictionary is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of entries in the dictionary.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return number of elements in the dictionary
     */
    public int size() {
        return currentSize;
    }


    /**
     * Return the root of the tree
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return node root
     */
    Node<E> root(){ return root;}

}