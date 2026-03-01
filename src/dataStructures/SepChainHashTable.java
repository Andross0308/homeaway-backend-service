package dataStructures;

import java.io.*;

/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {

    @Serial private static final long serialVersionUID = 33L;

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;

    // The array of Map with singly linked list.
    private Map<K,V>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public SepChainHashTable( int capacity ){
        super(capacity);
        int arraySize  = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        table = (MapSinglyList<K,V>[]) new MapSinglyList[arraySize];
        for(int i = 0; i < arraySize; i++){
            table[i] = new MapSinglyList<>();
        }
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(table.length);
        for (Map<K, V> map : table) {
            if (map != null) {
                oos.writeInt(map.size());
                Iterator<Entry<K, V>> it = map.iterator();
                while (it.hasNext()) {
                    Entry<K, V> entry = it.next();
                    oos.writeObject(entry.key());
                    oos.writeObject(entry.value());
                }
            } else {
                oos.writeInt(0);
            }
        }
    }

    @Serial
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int tableSize = ois.readInt();


        this.table = (Map<K, V>[]) new Map[tableSize];
        this.maxSize = (int)(tableSize * MAX_LOAD_FACTOR);
        this.currentSize = 0;
        for (int i = 0; i < tableSize; i++) {
            int listSize = ois.readInt();
            if (listSize > 0) {
                table[i] = new MapSinglyList<>();
                for (int j = 0; j < listSize; j++) {
                    K key = (K) ois.readObject();
                    V value = (V) ois.readObject();
                    table[i].put(key, value);
                    currentSize++;
                }
            } else {
                table[i] = null;
            }
        }
    }


    // Returns the hash value of the specified key.
    protected int hash( K key ){
        return Math.abs( key.hashCode() ) % table.length;
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
    public V get(K key) {
        //TODO: Left as an exercise.
        if(table[hash(key)] == null) return null;
        return table[hash(key)].get(key);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * Best Case: O(1);
     * Worst Case: O(n);
     * Average Case: O(1);
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {
        if (isFull())
            rehash();
        int index = hash(key);
        if(table[index] == null) table[index] = new MapSinglyList<>();
        int sizeBefore = table[index].size();
        V result = table[index].put(key, value);
        int sizeAfter = table[index].size();
        if (sizeAfter > sizeBefore) {
            currentSize++;
        }
        return result;
    }

    /**
     * Increases the size of the table
     * Best Case: O(n);
     * Worst Case: O(n);
     * Average Case: O(n);
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        Map<K,V>[] oldTable = table;
        int newSize = (int)((table.length * 2) / IDEAL_LOAD_FACTOR);
        table = (Map<K,V>[]) new Map[newSize];
        maxSize = newSize;

        for (Map<K, V> kvMap : oldTable) {
            if (kvMap != null) {
                Iterator<Entry<K, V>> it = kvMap.iterator();
                while (it.hasNext()) {
                    Entry<K, V> e = it.next();
                    int index = Math.abs(e.key().hashCode() % table.length);
                    if (table[index] == null)
                        table[index] = new MapSinglyList<>();
                    table[index].put(e.key(), e.value());
                }
            }
        }
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
        int index = hash(key);
        if(table[index] == null)
            return null;
        int sizeBefore = table[index].size();
        V result = table[index].remove(key);
        if (result != null && table[index].size() < sizeBefore) {
            currentSize--;
        }
        return result;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHashTableIterator<>(table);
    }
}
