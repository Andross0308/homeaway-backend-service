package dataStructures;

import dataStructures.exceptions.*;

/**
 * Ordered Dictionary interface
 *
 * @author AED team
 * @version 1.0
 * 
 * @param <K> Generic type Key, must extend comparable
 * @param <V> Generic type Value 
 */
public interface SortedMap<K extends Comparable<K>, V>
        extends Map<K,V> {

    /**
     * Returns the entry with the smallest key in the dictionary.
     *
     * @return the furthest left element in the map
     * @throws EmptyMapException if the map doesn't have any elements
     */
    Entry<K,V> minEntry( );

    /**
     * Returns the entry with the largest key in the dictionary.
     *
     * @return the furthest left element in the map
     * @throws EmptyMapException if the map doesn't have any elements
     */
    Entry<K,V> maxEntry( );

} 

