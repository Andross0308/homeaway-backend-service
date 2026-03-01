package dataStructures;

import java.io.Serial;

/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{

    @Serial
    private static final long serialVersionUID = 22L;

    /**
     * Inserts a new entry in the tree and balances the tree
     * or replaces the value if the key already exists
     * Best Case: O(1)
     * Worst Case: O(log n), n being the number of nodes in the tree
     * Average Case: O(log n), n being the number of nodes in the tree
     * @param key: Key of the entry
     * @param value: Value of the entry
     * @return the old value of the entry, or null if the entry is new
     */

    public V put(K key, V value) {
        // Check if key already exists
        V oldValue = super.put(key, value);

        if (oldValue == null) { //New insertion
            // Needs rebalanced
            BTNode<Entry<K, V>> newNode = findNode( (BTNode<Entry<K,V>>) root, key);
            rebalancedUp(newNode);
        }

        return oldValue;
    }

    /**
     * Finds a node with the given key, it starts with the root and,
     * if the key is smaller than that of the node, searches in the left subtree
     * if the key is greater than that of the node, searches in the right subtree
     * The loop ends when we find the key or the node is null
     * Best Case: O(1)
     * Worst Case : O(log n), n being the number of nodes in the tree
     * Average Case: O(log n),  n being the number of nodes in the tree
     * @param node: Root of the tree
     * @param key: key we are looking for
     * @return the node with the given key, or null if no node as the key
     */
    private BTNode<Entry<K,V>> findNode(BTNode<Entry<K,V>> node, K key){
        while(node != null){
            int cmp = key.compareTo(node.getElement().key());
            if(cmp == 0) return node;
            else if(cmp < 0) node = (BTNode<Entry<K,V>>) node.getLeftChild();
            else node = (BTNode<Entry<K,V>>) node.getRightChild();
        }
        return null;
    }

    /**
     * Balances a tree after a new entry to keep the tree balanced
     * Best Case: O(1)
     * Worst Case: O(log n), n being the number of nodes in the tree
     * Average Case: O(log n), n being the number of nodes in the tree
     * @param node: New node in the tree
     */
    private void rebalancedUp(BTNode<Entry<K, V>> node) {
        BTNode<Entry<K, V>> current = node;
        while (current != null) {
            restructure(current);
            if(current.isRoot())
                root = current;
            current = (BTNode<Entry<K, V>>) current.getParent();
        }
    }

    /**
     * Removes the entry with the given key from the tree and balances it
     * If the key doesn't correspond to any entry in the tree, returns null
     * Best Case: O(1)
     * Worst Case: O(log n), n being the number of nodes in the tree
     * Average Case: O(log n), n being the number of nodes in the tree
     * @param key whose entry is to be removed from the map
     * @return the value of the removed node or null, 
     * if the key doesn't correspond to any entry in the tree
     */
    public V remove(K key) {
        BTNode<Entry<K,V>> nodeToRemove = findNode((BTNode<Entry<K,V>>) root, key);
        V value = super.remove(key);
        if(value == null) return null;
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) nodeToRemove.getParent();
        rebalancedUp(parent);
        return value;
    }
}
