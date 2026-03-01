package dataStructures;
import dataStructures.exceptions.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * List in Array
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class ListInArray<E> implements List<E> {

    @Serial private static final long serialVersionUID = 30L;

    /**
     * Array of generic elements E.
     */
    private transient E[] elems;

    /**
     * Number of elements in array.
     */
    private transient int counter;


    /**
     * Constructor with capacity.
     * @param dimension - initial capacity of array.
     */
    public ListInArray(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }


    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(counter);
        for(int i = 0; i < counter; i++){
            oos.writeObject(get(i));
        }
        oos.flush();
    }

    /**
     * Returns true iff the list contains no elements.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return counter==0;
    }

    /**
     * Returns the number of elements in the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return number of elements in the list
     */
    public int size() {
        return counter;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);
    }

    /**
     * Returns the first element of the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst() {
        return elems[0];
    }

    /**
     * Returns the last element of the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast() {
        if(isEmpty())   throw new NoSuchElementException();
        return elems[counter - 1];
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get(int position) {
        if(position < 0 || position > counter) throw new InvalidPositionException();
        return elems[position];
    }


    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(n), n being the number of elements in the list;
     *
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf(E element) {
        int result = NOT_FOUND;
        int i = 0;
        while(i < counter && !elems[i].equals(element)){
            i++;
        }
        if(i < counter) result = i;
        return result;
    }

    /**
     * Verifies if the array is full
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return if the array is full
     */
    private boolean isFull(){
        return elems.length == counter;
    }

    /**
     * The array grows double is size
     * Best Case: O(n), n being the number of elements in the list
     * Worst Case: O(n), n being the number of elements in the list
     * Average Case: O(n), n being the number of elements in the list
     */
    public void grow(){
        E[] newElems = (E[]) new Object[counter * 2];
        for(int i = 0; i < counter; i++){
            newElems[i] = elems[i];
        }
        elems = newElems;
    }

    /**
     *
     * Inserts the specified element at the first position in the list.
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(n), n being the number of elements in the list;
     * @param element to be inserted
     */
    public void addFirst(E element) {
        if(isFull())
            grow();
        // Shift all elements to the right
        for (int i = counter - 1; i >= 0; i--) {
            elems[i + 1] = elems[i];
        }

        // Insert new element at the beginning
        elems[0] = element;
        counter++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(1);
     * @param element to be inserted
     */
    public void addLast(E element) {
        if(isFull()){
            grow();
        }
        elems[counter++] = element;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(n), n being the number of elements in the list;
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     *
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add(int position, E element) {
        if(0 > position && position > size()) throw new InvalidPositionException();
        if(position == 0) addFirst(element);
        else if(position == size()) addLast(element);
        else
            for(int i = counter - 1; i < position; i++){
                elems[i + 1] = elems[i];
            }
            elems[position] = element;
            counter++;
    }

    /**
     * Removes and returns the element at the first position in the list.
     * Best Case: O(n), n being the number of elements in the list;
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(n), n being the number of elements in the list;
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst() {
       if(isEmpty()) throw new NoSuchElementException();
       E result = elems[0];
        for(int i = 0; i < counter; i++){
            elems[i] = elems[i+1];
        }
        counter--;
        return result;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast() {
        if(isEmpty()) throw new NoSuchElementException();
        counter--;
        E result = elems[counter];
        elems[counter] = null;
        return result;
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(n), n being the number of elements in the list;
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     *
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove(int position) {
        if(position < 0 && position > size()) throw new InvalidPositionException();
        if(position == 0) return removeFirst();
        else if(position == size()) return removeLast();
        else {
            E result = elems[position];
            for (int i = position; i < counter - 1; i++) {
                elems[i] = elems[i + 1];
            }
            counter--;
            return result;
        }
    }
}
