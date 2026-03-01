package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.io.*;

/**
 * Implementation of Doubly Linked List
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class DoublyLinkedList<E> implements TwoWayList<E> {

    @Serial
    private static final long serialVersionUID = 27L;
    /**
     *  Node at the head of the list.
     */
    private transient DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private transient DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private transient int currentSize;

    /**
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public DoublyLinkedList( ) {
        head = null;
        tail = null;
        currentSize = 0;
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(currentSize);

        DoublyListNode<E> node = head;
        while (node != null) {
            oos.writeObject(node.getElement());
            node = node.getNext();
        }
        oos.flush();
    }


    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int size = ois.readInt();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            addLast(element);
        }
    }

    /**
     * Returns true iff the list contains no elements.
     *  Best Case: O(1);
     *  Worst Case: O(1);
     *  Average Case: O(1);
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     *  Best Case: O(1);
     *  Worst Case: O(1);
     *  Average Case: O(1);
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     *  Best Case: O(1);
     *  Worst Case: O(1);
     *  Average Case: O(1);
     * @return Two-Way Iterator of the elements in the list
     */

    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }
    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     *  Best Case: O(1);
     *  Worst Case: O(1);
     *  Average Case: O(1);
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Inserts the element at the first position in the list.
     *  Best Case: O(1);
     *  Worst Case: O(1);
     *  Average Case: O(1);
     * @param element - Element to be inserted
     */
    public void addFirst( E element ) {
        DoublyListNode<E> newFirst = new DoublyListNode<>(element);
        if(isEmpty()) {
            tail = newFirst;
        }
        else{
            newFirst.setNext(head);
            head.setPrevious(newFirst);
        }
        head = newFirst;
        currentSize++;
    }

    /**
     * Inserts the element at the last position in the list.
     *  Best Case: O(1);
     *  Worst Case: O(1);
     *  Average Case: O(1);
     * @param element - Element to be inserted
     */
    public void addLast( E element ) {
        DoublyListNode<E> newEnd = new DoublyListNode<>(element);
        if(isEmpty()){
            head= newEnd;
        }
        else{
            newEnd.setPrevious(tail);
            tail.setNext(newEnd);
        }
        tail = newEnd;
        currentSize++;
    }

    /**
     * Returns the first element of the list.
     *  Best Case: O(1);
     *  Worst Case: O(1);
     *  Average Case: O(1);
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst( ) {
        if(isEmpty()) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast( ) {
        if(isEmpty()) throw new NoSuchElementException();
        return tail.getElement();
    }

   

     /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the number of elements in the list
     *  Average Case: O(n), n being the number of elements in the list
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get( int position ) {
        if(position < 0 || position > size() - 1) throw new InvalidPositionException();
        if(position == 0) return getFirst();
        if(position == size()-1) return getLast();
        else{
            DoublyListNode<E> node = head;
            for(int i = 0; i < position; i++){
                node = node.getNext();
            }
            return node.getElement();
        }
    }
    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the number of elements in the list
     *  Average Case: O(n), n being the number of elements in the list
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf( E element ) {
        DoublyListNode<E> node = head;
        int i = 0;
        while(i < currentSize && !node.getElement().equals(element)){
            i++;
            node = node.getNext();
        }
        if(i == size()) return NOT_FOUND;
        return i;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the number of elements in the list
     *  Average Case: O(n), n being the number of elements in the list
     * @param position - position where to insert element
     * @param element - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add( int position, E element ) {
        if(position < 0 || position > size()) throw new InvalidPositionException();
        if(position == 0) addFirst(element);
        else if(position == size()) addLast(element);
        else{
            DoublyListNode<E> n = head;
            for(int i = 0; i < position; i++){
                n = n.getNext();
            }
            DoublyListNode<E> node = new DoublyListNode<>(element);
            n.getPrevious().setNext(node);
            node.setPrevious(n.getPrevious());
            node.setNext(n);
            n.setPrevious(node);
            currentSize++;
        }
    }

    /**
     * Removes and returns the element at the first position in the list.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst( ) {
        if (isEmpty()) throw new NoSuchElementException();
        DoublyListNode<E> node = head;
        head = head.getNext();
        if (head != null)
            head.setPrevious(null);
        else
            tail = null;
        currentSize--;
        return node.getElement();
    }

    /**
     * Removes and returns the element at the last position in the list.
     *  Best Case: O(1)
     *  Worst Case: O(1)
     *  Average Case: O(1)
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast( ) {
        if (isEmpty()) throw new NoSuchElementException();
        DoublyListNode<E> node = tail;
        tail = tail.getPrevious();
        if (tail != null)
            tail.setNext(null);
        else
            head = null;

        currentSize--;
        return node.getElement();
    }

    /**
     *  Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     *  Best Case: O(1)
     *  Worst Case: O(n), n being the number of elements in the list
     *  Average Case: O(n), n being the number of elements in the list
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove( int position ) {
        //TODO: Left as an exercise.
        if(position < 0 || position > currentSize - 1)
            throw new InvalidPositionException();
        else if(position == 0)
            return removeFirst();
        else if(position == currentSize - 1)
            return removeLast();
        else {
            DoublyListNode<E> n = head;
            for (int i = 0; i < position; i++) {
                n = n.getNext();
            }
            n.getPrevious().setNext(n.getNext());
            n.getNext().setPrevious(n.getPrevious());
            currentSize--;
            return n.getElement();
        }
    }
}
