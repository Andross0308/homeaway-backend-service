package dataStructures;

import java.io.Serial;

/**
 * Binary Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
abstract class BTree<E> extends Tree<E> {

    @Serial
    private static final long serialVersionUID = 25L;


    /**
     * Returns the height of the tree.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the total number of nodes in the tree
     *  Average Case: O(n), n being the total number of nodes in the tree
     * @return the height of the tree
     */
    public int getHeight() {
        if(isEmpty())
            return -1;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Return the further left node of the tree
     *  Best Case: O(1)
     *  Worst Case: O(log n), n being the total number of nodes in the tree
     *  Average Case: O(log n), n being the total number of nodes in the tree
     * @return the minimal value of the tree or null, if tree is empty
     */
    BTNode<E> furtherLeftElement() {
        //TODO: Left as an exercise.
        if(isEmpty()) return null;
        return ((BTNode<E>) root).furtherLeftElement();
    }

    /**
     * Return the further right node of the tree
     *  Best Case: O(1)
     *  Worst Case: O(log n)
     *  Average Case: O(log n)
     * @return the maximal value of the tree or null, if the tree is empty
     */
    BTNode<E> furtherRightElement() {
        if(isEmpty()) return null;
        return ((BTNode<E>) root).furtherRightElement();
    }
}
