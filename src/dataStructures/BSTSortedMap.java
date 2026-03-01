package dataStructures;

import dataStructures.exceptions.EmptyMapException;
import java.io.*;
/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V>{

    @Serial private static final long serialVersionUID = 23L;

    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException{
        oos.defaultWriteObject();
        oos.writeInt(currentSize);
        List<BTNode<Entry<K,V>>> list = new SinglyLinkedList<>();
        if(root != null) list.addLast((BTNode<Entry<K,V>>) root);
        while(!list.isEmpty()){
            BTNode<Entry<K,V>> node = list.removeFirst();
            oos.writeObject(node.getElement().key());
            oos.writeObject(node.getElement().value());
            if(node.getLeftChild() != null) list.addLast((BTNode<Entry<K,V>>)  node.getLeftChild());
            if(node.getRightChild() != null) list.addLast((BTNode<Entry<K,V>>)  node.getRightChild());
        }
    }

    @Serial
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        int savedSize = ois.readInt();
        for(int i = 0; i < savedSize; i++){
            K key = (K) ois.readObject();
            V value = (V) ois.readObject();
            put(key, value);
        }
    }

    /**
     * Returns the entry with the smallest key in the dictionary.
     * Best Case: O(1)
     * Worst Case: O(log n), n being the number of nodes in the tree
     * Average Case: O(log n), n being the number of nodes in the tree
     * @return the furthest left element
     * @throws EmptyMapException if the tree is empty
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary
     * Best Case: O(1)
     * Worst Case: O(log n), n being the number of nodes in the tree
     * Average Case: O(log n), n being the number of nodes in the tree.
     * @return the furthest right element
     * @throws EmptyMapException if the tree is empty
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * Best Case: O(1);
     * Worst Case: O(log n), n being the number of nodes in the tree;
     * Average Case: O(log n), n being the number of nodes in the tree;
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }

    /**
     * Searches for an entry in the tree whose key is the specified key,
     * returns the node with that key; otherwise, returns null.
     * Best Case: O(1)
     * Worst Case: O(log n), n being the number of nodes in the tree
     * Average Case: O(log n), n being the number of nodes in the tree.
     * @param node: Root of the tree
     * @param key: Key we are searching for
     * @return the node with the given key or null
     */
    private BTNode<Entry<K,V>> getNode(BTNode<Entry<K,V>> node, K key) {
        while(node != null){
            int index = key.compareTo(node.getElement().key());
            if(index == 0) return node;
            else if(index < 0) node = (BTNode<Entry<K,V>>) node.getLeftChild();
            else node = (BTNode<Entry<K,V>>) node.getRightChild();
        }
        return null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * Best Case: O(1)
     * Worst Case: O(log n), n being the number of nodes in the tree
     * Average Case: O(log n), n being the number of nodes in the tree.
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        BTNode<Entry<K,V>> newNode = new BTNode<>(new Entry<>(key, value));
        BTNode<Entry<K,V>> parent = null;
        int compare = 0;
        if(root == null) root = newNode;
        else{
            BTNode<Entry<K,V>> node = (BTNode<Entry<K,V>>) root;
            while(node != null){
                parent = node;
                compare = key.compareTo(node.getElement().key());
                if(compare == 0){
                    V oldValue = node.getElement().value();
                    node.setElement(newNode.getElement());
                    return oldValue;
                }
                else if(compare < 0) node = (BTNode<Entry<K,V>>) node.getLeftChild();
                else node = (BTNode<Entry<K,V>>) node.getRightChild();
            }
            if(compare < 0) parent.setLeftChild(newNode);
            else parent.setRightChild(newNode);
            newNode.setParent(parent);
        }
        currentSize++;
        return null;
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * Best Case: O(1)
     * Worst Case: O(log n), n being the number of nodes in the tree
     * Average Case: O(log n), n being the number of nodes in the tree.
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {
        if(isEmpty()) return null;
        BTNode<Entry<K,V>> node = getNode((BTNode<Entry<K,V>>) root, key);
        if(node == null) return null;
        V value = node.getElement().value();
        if(node.isLeaf()) removeLeaf(node);
        else if(node.getLeftChild() != null && node.getRightChild() != null) removeFatherOfTwo(node);
        else removeFatherOfOne(node);
        currentSize--;
        return value;
    }

    /**
     * Removes a leaf from the tree;
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param node: Leaf to remove
     */
    private void removeLeaf(BTNode<Entry<K,V>> node){
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) node.getParent();
        if(node == root) root = null;
        else if(node == parent.getLeftChild()) parent.setLeftChild(null);
        else parent.setRightChild(null);
    }

    /**
     * Removes a father of one node in the tree
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param node: Node to remove
     */
    private void removeFatherOfOne(BTNode<Entry<K,V>> node){
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) node.getParent();  //parent of the node to remove
        BTNode<Entry<K,V>> child;       //Child of the parent to remove
        if(node.getRightChild() != null) child = (BTNode<Entry<K,V>>) node.getRightChild();
        else child = (BTNode<Entry<K,V>>) node.getLeftChild();
        child.setParent(parent);    //Defines the father of the node as father of the son
        if(parent == null)  root = child;   //The node to remove is the root
        else if(node == parent.getLeftChild()) parent.setLeftChild(child);
        else parent.setRightChild(child);   //Defines the child as son of the node father
    }

    /**
     * Removes a leaf from the tree;
     * Best Case: O(1);
     * Worst Case: O(log n);
     * Average Case: O(log n);
     * @param node: Node to remove
     */
    private void removeFatherOfTwo(BTNode<Entry<K,V>> node){
        BTNode<Entry<K,V>> successor = ((BTNode<Entry<K,V>>) node.getRightChild()).furtherLeftElement();
        //Gets the element to substitute the node
        node.setElement(successor.getElement());    //Defines the node to remove as the successor
        if(successor.isLeaf()) removeLeaf(successor);   //Removes the successor if it is a leaf
        else removeFatherOfOne(successor);      //Removes the successor if it is a father of one

    }

    /**
     * Returns an iterator of the entries in the dictionary.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return iterator of the values in the dictionary
     */
    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return iterator of the keys in the dictionary
     */
    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {return new KeysIterator(iterator());}
}
