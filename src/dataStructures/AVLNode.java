package dataStructures;

import java.io.Serial;

/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    // Height of the node
    protected int height;

    @Serial private static final long serialVersionUID = 21L;

    /**
     *Constructor
     * @param elem: Element of the node
     */
    public AVLNode(E elem) {
        super(elem);
        height=0;
    }

    /**
     * Constructor
     * @param element: Element of the node
     * @param parent: Parent of the node
     * @param left: Left Child of the Node
     * @param right: Right Child of the Node
     */
    public AVLNode( E element, AVLNode<E> parent,
                    AVLNode<E> left, AVLNode<E> right ){
        super(element, parent, left, right);
        height = 1 + Math.max(height((AVLNode<E>) getLeftChild()), height((AVLNode<E>) getRightChild()));
    }
    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        height= 0;
    }

    /**
     * Gives the height of the given node, if the node exists
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @param no: Node of the Tree
     * @return the height of the given node
     */
    private int height(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }
    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * Best Case: O(1)
     * Worst Case: O(log n)
     * Average Case: O(log n)
     * @param node: New left child
     */
    public void setLeftChild(AVLNode<E> node) {
        super.setLeftChild(node);
        height = 1 + Math.max(height((AVLNode<E>) getLeftChild()), height((AVLNode<E>) getRightChild()));

    }

    /**
     * Update the right child and height
     * Best Case: O(1)
     * Worst Case: O(log n)
     * Average Case: O(log n)
     * @param node: New right child
     */
    public void setRightChild(AVLNode<E> node) {
        super.setRightChild(node);
        height = 1 + Math.max(height((AVLNode<E>) getLeftChild()), height((AVLNode<E>) getRightChild()));
    }

}
