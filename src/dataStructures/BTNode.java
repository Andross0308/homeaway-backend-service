package dataStructures;

import java.io.Serial;

/**
 * Binary Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class BTNode<E> implements Node<E> {

    @Serial
    private static final long serialVersionUID = 24L;

    // Element stored in the node.
    private E element;

    // (Pointer to) the father.
    private Node<E> parent;

    // (Pointer to) the left child.
    private Node<E> leftChild;

    // (Pointer to) the right child.
    private Node<E> rightChild;

    /**
     * Constructor
     * @param elem: Element of the node
     */
    BTNode(E elem){
        this(elem,null,null,null);
    }

    /**
     * Constructor
     * @param elem:  Element of the node
     * @param parent  Parent of the node
     */
    BTNode(E elem, BTNode<E> parent) {
        this(elem,parent,null,null);
    }
    /**
     * Constructor
     * @param elem: Element of the node
     * @param parent: Parent of the node
     * @param leftChild  Left Child of the node
     * @param rightChild  Right Child of the node
     */
    BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild){
        //TODO: Left as an exercise.
        this.element = elem;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     *  Returns the element of the node
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return the element of the node
     */
    public E getElement() {
        return element;
    }
    /**
     * Returns the left son of node
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return the left child of the node
     */
    public Node<E> getLeftChild(){
        return leftChild;
    }
    /**
     * Returns the right son of node
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return the left child of the node
     */
    public Node<E> getRightChild(){
        return rightChild;
    }
    /**
     * Returns the parent of node
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return the parent of the node
     */
    public Node<E> getParent(){
        return parent;
    }

    /**
     * Returns true if node does not have any children.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return true if node n does not have any children, false otherwise
     */
    boolean isLeaf() {
        return getLeftChild()== null && getRightChild()==null;
    }

    /**
     * Update the element
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @param elem: New element of the node
     */
    public void setElement(E elem) {
        element=elem;
    }

    /**
     * Update the left child
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @param node: New left child of the node
     */
    public void setLeftChild(Node<E> node) {
        leftChild=node;
    }

    /**
     * Update the right child
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @param node: New right child of the node
     */
    public void setRightChild(Node<E> node) {
        rightChild=node;
    }

    /**
     * Update the parent
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @param node: New parent of the node
     */
    public void setParent(Node<E> node) {
        parent=node;
    }

    /**
     * Returns true if is the root
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     */
    boolean isRoot() {
        return getParent()==null;
    }

    /**
     * Returns the height of the subtree rooted at this node.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the number of nodes of the subtree that have this node has root
     *  Average Case: O(n), n being the number of nodes of the subtree that have this node has root
     */

    public int getHeight() {
        int leftHeight = -1;
        int rightHeight = -1;
        if(leftChild != null){
            leftHeight = ((BTNode<E>) leftChild).getHeight();
        }
        if(rightChild != null){
            rightHeight = ((BTNode<E>) rightChild).getHeight();
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Returns the further left element of the node or himself, if he doesn't have left child
     *  Best Case: O(1)
     *  Worst Case: O(n), n is the number of nodes in the left path of this node
     *  Average Case: O(log n), n is the number of nodes in the left path of this node
     * @return himself or the further left element of his left child
     */
    BTNode<E> furtherLeftElement() {
        if(leftChild != null){
            return ((BTNode<E>) leftChild).furtherLeftElement();
        }
        return this;
    }

    /**
     * Returns the further right element of the node or himself, if he doesn't have right child
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the number of nodes of the subtree that have this node has root
     *  Average Case: O(log n), n being the number of nodes of the subtree that have this node has root
     * @return himself or the further right element of his right child
     */
    BTNode<E> furtherRightElement() {
        if(rightChild != null){
            return ((BTNode<E>) rightChild).furtherRightElement();
        }
        return this;
    }

}
