package dataStructures;

import java.io.*;

/**
 * Advanced Binary Search Tree
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
abstract class AdvancedBSTree <K extends Comparable<K>,V> extends BSTSortedMap<K,V>{

    @Serial private static final long serialVersionUID = 20L;

    /**
     * Performs a single left rotation rooted at z node.
     * Node y was a  right  child  of z before the  rotation,
     * then z becomes the left child of y after the rotation.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param z - root of the rotation
     * @pre: z has a right child
     */
    protected void rotateLeft( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> rightSon = (BTNode<Entry<K,V>>) z.getRightChild();
        BTNode<Entry<K,V>> t2 = (BTNode<Entry<K,V>>) rightSon.getLeftChild();
        BTNode<Entry<K,V>> parentZ = (BTNode<Entry<K,V>>) z.getParent();
        z.setRightChild(t2);
        if (t2 != null) {
            t2.setParent(z);
        }
        rightSon.setLeftChild(z);
        z.setParent(rightSon);
        if (parentZ != null) {
            if (z == parentZ.getLeftChild()) {
                parentZ.setLeftChild(rightSon);
            } else {
                parentZ.setRightChild(rightSon);
            }
        }
        rightSon.setParent(parentZ);
    }

    /**
     * Performs a single right rotation rooted at z node.
     * Node y was a left  child  of z before the  rotation,
     * then z becomes the right child of y after the rotation.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param z - root of the rotation
     * @pre: z has a left child
     */
    protected void rotateRight( BTNode<Entry<K,V>> z) {
        BTNode<Entry<K, V>> y = (BTNode<Entry<K, V>>) z.getLeftChild();
        BTNode<Entry<K, V>> t2 = (BTNode<Entry<K, V>>) y.getRightChild();
        z.setLeftChild(t2);
        if (t2 != null) {
            t2.setParent(z);
        }
        BTNode<Entry<K,V>> parentZ = (BTNode<Entry<K,V>>) z.getParent();
        y.setRightChild(z);
        z.setParent(y);
        if (parentZ != null) {
            if (z == parentZ.getLeftChild()) {
                parentZ.setLeftChild(y);
            } else {
                parentZ.setRightChild(y);
            }
        }
        y.setParent(parentZ);
    }

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @param x - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *          /  \      /  \       /  \        /  \
     *        y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *       /  \      /  \           /  \         /  \
     *     x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *    /  \          /  \       /  \             /  \
     *   t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return the new root of the restructured subtree
     */
    protected BTNode<Entry<K,V>> restructure (BTNode<Entry<K,V>> x) {
        if(x.isRoot()) return null;
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) x.getParent();  //parent
        if(y.getParent() == null) return null;
        BTNode<Entry<K,V>> z = (BTNode<Entry<K,V>>) y.getParent();  //grandparent
        if(y == z.getLeftChild()){
            if(x == y.getLeftChild()){
                rotateRight(z);
                return y;
            } else if(x == y.getRightChild()){
                rotateLeft(y);
                rotateRight(z);
                return x;
            }
        } else{
            if(x == y.getRightChild()){
                rotateLeft(z);
                return y;
            } else{
                rotateRight(y);
                rotateLeft(z);
                return x;
            }
        }return null;
    }
}
