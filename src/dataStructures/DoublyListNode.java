package dataStructures;

import java.io.Serial;
import java.io.Serializable;

/**
     * Double List Node Implementation
     * @author AED  Team
     * @version 1.0
     * @param <E> Generic Element
     * 
     */
class DoublyListNode<E> implements Serializable {

        /**
    	 * Serial Version UID of the Class
    	 */
        @Serial
        private static final long serialVersionUID = 28L;
        
    	/**
         * Element stored in the node.
         */
        private E element;

        /**
         * (Pointer to) the previous node.
         */
        private DoublyListNode<E> previous;

        /**
         * (Pointer to) the next node.
         */
        private DoublyListNode<E> next;

        /**
         *  Constructor
         * @param theElement - The element to be contained in the node
         * @param thePrevious - the previous node
         * @param theNext - the next node
         */
        public DoublyListNode(E theElement, DoublyListNode<E> thePrevious,
                              DoublyListNode<E> theNext ) {
                this.element = theElement;
                this.previous = thePrevious;
                this.next = theNext;
        }

        /**
         * Creates a new node with the given element
         * @param theElement to be contained in the node
         *
         */
        public DoublyListNode(E theElement ) {
             this(theElement, null, null);
        }

        /**
         * Gives the element of the node
         *  Best Case: O(1)
         *  Worst Case: O(1)
         *  Average Case: O(1)
         * @return the element contained in the node
         */
        public E getElement( ) {
            return element;
        }

        /**
         * Gives the previous node of the element
         *  Best Case: O(1);
         *  Worst Case: O(1);
         *  Average Case: O(1);
         * @return the previous node
         */
        public DoublyListNode<E> getPrevious( ) {
            return previous;
        }

        /**
         * Gives the next node of the element
         *  Best Case: O(1)
         *  Worst Case: O(1)
         *  Average Case: O(1)
         * @return the next node
         */
        public DoublyListNode<E> getNext( ) {
            return next;
        }

        /**
         * Changes the element of the node
         *  Best Case: O(1)
         *  Worst Case: O(1)
         *  Average Case: O(1)
         * @param newElement - New element to replace the current element
         */
        public void setElement( E newElement ) {
                element = newElement;
        }

        /**
         *  Changes the pointer of the previous node
         *  Best Case: O(1)
         *  Worst Case: O(1)
         *  Average Case: O(1)
         * @param newPrevious - node to replace the current previous node
         */
        public void setPrevious( DoublyListNode<E> newPrevious ) {
                previous = newPrevious;
        }

        /**
         * Changes the pointer of the next node
         *  Best Case: O(1)
         *  Worst Case: O(1)
         *  Average Case: O(1)
         * @param newNext - node to replace the next node
         */
        public void setNext( DoublyListNode<E> newNext ) {
                next = newNext;
        }
    }