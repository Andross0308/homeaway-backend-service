package dataStructures;

import java.io.*;

/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    @Serial private static final long serialVersionUID = 26L;

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        // Compiler gives a warning.
        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException{
        oos.defaultWriteObject();
        oos.writeInt(table.length);

        for(Entry<K,V> e: table){
            if(e == REMOVED_CELL){
                oos.writeBoolean(true); //REMOVED_CELL MARKER
                oos.writeObject(null);
                oos.writeObject(null);
            }else if(e == null){
                oos.writeBoolean(false);
                oos.writeObject(null);
                oos.writeObject(null);
            }else{
                oos.writeBoolean(false);
                oos.writeObject(e.key());
                oos.writeObject(e.value());
            }
        }
    }

    @Serial
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        int tableSize = ois.readInt();

        this.table = (Entry<K, V>[]) new Entry[tableSize];
        this.maxSize = (int)(tableSize * MAX_LOAD_FACTOR);
        this.currentSize = 0;

        for(int i = 0; i < tableSize; i++){
            boolean removeCell = ois.readBoolean();
            K key = (K) ois.readObject();
            V value = (V) ois.readObject();
            if(removeCell)
                table[i] = (Entry<K, V>) REMOVED_CELL;
            else if(key == null)
                table[i] = null;
            else {
                table[i] = new Entry<>(key, value);
                currentSize++;
            }
        }
    }

    //Methods for handling collisions.
    // Returns the hash value of the specified key.
    int hash( K key, int i ){
        return Math.abs( key.hashCode() + i) % table.length;
    }

    /**
     * Linear Proving
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the length of the table
     *  Average Case: O(1)
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        int i = 0;
        while(i < table.length){
            int index = hash(key, i);
            if(table[index] == null) return NOT_FOUND;
            else if(table[index] != REMOVED_CELL && (table[index].key().equals(key)))
                    return index;
            i++;
        }
        return NOT_FOUND;
    }

    
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the length of the table
     *  Average Case: O(1)
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        int index = searchLinearProving(key);
        if(index == NOT_FOUND) return null;
        return table[index].value();
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the length of the table
     *  Average Case: O(1)
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();
        Entry<K,V> newEntry = new Entry<>(key, value);
        int i = 0;
        while(i < table.length){
            int index = hash(key, i);
            if(table[index] == null || table[index] == REMOVED_CELL){
                table[index] = newEntry;
                currentSize++;
                return null;
            }else if(table[index].key().equals(key)){
                V oldValue = table[index].value();
                table[index] = newEntry;
                return oldValue;
            }
            i++;
        }
        return null;
    }

    /**
     * Increases the size of the array when it is full
     * Best Case: O(1)
     * Worst Case: O(n + m), n being the length of the table and m the number of numbers until the next Prime is found
     * Average Case: O(n + m), n being the length of the table and m the number of numbers until the next Prime is found
     */
    @SuppressWarnings("unchecked")
     private void rehash(){
         Entry<K,V>[] oldTable = table;
        int newCapacity = HashTable.nextPrime(table.length * 2);

        table = (Entry<K,V>[]) new Entry[newCapacity];
        currentSize = 0;
         maxSize = (int)( newCapacity * MAX_LOAD_FACTOR);
         for (Entry<K, V> entry : oldTable) {
             if (entry != null && entry != REMOVED_CELL) {
                 put(entry.key(), entry.value());
             }
         }
     }

   
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * Best Case: O(1)
     * Worst Case: O(n), n being the length of the table
     * Average Case: O(1)
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        int i = 0;
        while(i < table.length){
            int index = hash(key, i);
            if(table[index] == null) return null;
            else if(table[index] != REMOVED_CELL && table[index].key().equals(key)){
                V oldValue = table[index].value();
                table[index] = (Entry<K, V>) REMOVED_CELL;
                return oldValue;
            }
            i++;
        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new ArrayIterator<>(table, currentSize);
    }

}
