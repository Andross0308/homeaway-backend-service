package dataStructures;

import java.io.Serial;

/**
 * Map with a singly linked list with head and size
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
class MapSinglyList<K,V> implements Map<K, V> {

    @Serial
    private static final long serialVersionUID = 31L;

    private SinglyListNode<Entry<K,V>> head;

    private int size;

    public MapSinglyList() {
        head = null;
        size = 0;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return true if dictionary is empty
     */

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of entries in the dictionary.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return number of elements in the dictionary
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * Best Case: O(1);
     * Worst Case: O(n);
     * Average Case: O(1);
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        if(isEmpty()) return null;
        SinglyListNode<Entry<K,V>> node = head;
        boolean found = false;
        while(node != null && !found){
            if(node.getElement().key().equals(key))
                found = true;
            else
                node = node.getNext();
        }
        if(found) return node.getElement().value();
        return null;
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * Best Case: O(1);
     * Worst Case: O(n);
     * Average Case: O(n);
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */

    public V put(K key, V value) {
        //TODO: Left as an exercise.
        Entry<K, V> e = new Entry<>(key, value);
        if (isEmpty()){
            head = new SinglyListNode<>(e, null);
            size++;
            return null;
        }
        else {
            SinglyListNode<Entry<K, V>> node = head;
            while (node != null) {
                if (node.getElement().key().equals(key)) {
                    V v = node.getElement().value();
                    node.setElement(e);
                    return v;
                }
                node = node.getNext();
            }
            head = new SinglyListNode<>(e, head);
        }
        size++;
        return null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * Best Case: O(1);
     * Worst Case: O(n);
     * Average Case: O(n);
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        //TODO: Left as an exercise.
        if(isEmpty()) return null;
        if (head.getElement().key().equals(key)) {
            V value = head.getElement().value();
            head = head.getNext();
            size--;
            return value;
        }
        SinglyListNode<Entry<K,V>> prev = head;
        SinglyListNode<Entry<K,V>> node = head.getNext();
        while(node != null){
            if(node.getElement().key().equals(key)){
                V value = node.getElement().value();
                prev.setNext(node.getNext());
                size--;
                return value;
            }
            prev = node;
            node = node.getNext();
        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return iterator of the entries in the dictionary
     */

    public Iterator<Entry<K, V>> iterator() {
        return new SinglyIterator<>(head);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return iterator of the values in the dictionary
     */
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
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }

}
