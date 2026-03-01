package dataStructures;
/*
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private final Map<K,V>[] array;
    private final int size;
    private int current;
    private Iterator<Map.Entry<K,V>> currentIterator;

    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.array = table;
        this.size = table.length;
        this.current = 0;
        this.currentIterator = findBucket();
    }

    /**
     * Returns the next array to iterate
     * Best Case: O(1);
     * Worst Case: O(n);
     * Average Case: O(n);
     * @return an iterator of entry or null
     */
    private Iterator<Map.Entry<K,V>> findBucket(){
        for(int i = current; i < size; i++){
            if(array[i] != null && !array[i].isEmpty()) {
                current = i;
                return array[i].iterator();
            }
        }
        return null;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * Best Case: O(1);
     * Worst Case: O(n);
     * Average Case: O(n);
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        if (currentIterator != null && currentIterator.hasNext()) return true;
        return findBucket() != null;
    }

    /**
     * Returns the next element in the iteration.
     * Best Case: O(1);
     * Worst Case: O(n);
     * Average Case: O(1);
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        if(!hasNext()) throw new NoSuchElementException();
        return currentIterator.next();
    }

    /**
     * Restarts the iteration.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        current = 0;
        currentIterator = findBucket();
    }
}

