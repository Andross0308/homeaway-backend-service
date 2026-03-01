package dataStructures;
import java.io.Serial;
import java.io.Serializable;

/**
 * Singly List Node Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class SinglyListNode<E> implements Serializable {


    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 36L;

    /**
     * Element stored in the node.
     */
    private E element;

    /**
     * (Pointer to) the next node.
     */
    private SinglyListNode<E> next;

    /**
     * Constructor
     * @param theElement - The element to be contained in the node
     * @param theNext - the next node
     */
    public SinglyListNode( E theElement, SinglyListNode<E> theNext ) {
        element = theElement;
        next = theNext;
    }

    /**
     * Constructor with an element and no next element
     * @param theElement to be contained in the node
     */
    public SinglyListNode( E theElement ) {
        this(theElement, null);
    }

    /**
     * Gives the element of the node
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return the element contained in the node
     */
    public E getElement( ) {
        return element;
    }

    /**
     * Gives the node next to this node
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return the next node
     */
    public SinglyListNode<E> getNext( ) {
        return next;
    }

    /**
     * Replaces the element of the node with the newElement
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @param newElement - New element to replace the current element
     */
    public void setElement( E newElement ) {
        element = newElement;
    }

    /**
     * Replaces the next element with the newNext
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @param newNext - node to replace the next node
     */
    public void setNext( SinglyListNode<E> newNext ) {
        next = newNext;
    }
}
