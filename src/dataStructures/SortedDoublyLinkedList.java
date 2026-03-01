package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

import java.io.*;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {

    @Serial private static final long serialVersionUID = 37L;

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
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        this.comparator = comparator;
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

            E element = (E) ois.readObject();
            add(element);
        }
    }

    /**
     * Returns true iff the list contains no elements.
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of elements in the list.
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) {
        //TODO: Left as an exercise.
        if(isEmpty()) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMax( ) {
        if(isEmpty()) throw new NoSuchElementException();
        return tail.getElement();
    }
    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * Best Case: O(1)
     * Worst Case: O(n)
     * Average Case: O(n)
     * @return element in the list or null
     */
    public E get(E element) {
        DoublyListNode<E> result = head;
        int i = 0;
        while(i < currentSize && !result.getElement().equals(element)){
            result = result.getNext();
        }
        if(i < currentSize) return result.getElement();
        return null;
    }

    /**
     * Returns true iff the element exists in the list.
     * Best Case: O(1)
     * Worst Case: O(n)
     * Average Case: O(n)
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        DoublyListNode<E> current = head;
        while (current != null) {
            if (current.getElement().equals(element)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * Best Case: O(1)
     * Worst Case: O(n)
     * Average Case: O(n)
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     */
    public void add(E element) {
        DoublyListNode<E> node = new DoublyListNode<>(element);
        if(isEmpty()) {     //First node we are adding
            head = node;
            tail = node;
        }
        else{
            DoublyListNode<E> n = head;
            int i = 0;
            while(i < currentSize && comparator.compare(n.getElement(), node.getElement()) < 0){
                n = n.getNext();
                i++;
            }
            if( i == 0){    //Node is going in the first position
                node.setNext(head);
                head.setPrevious(node);
                head = node;
            }
            else if(i == currentSize){  //Node is going in the first position
                node.setPrevious(tail);
                tail.setNext(node);
                tail = node;
            }
            else {
                n.getPrevious().setNext(node);
                node.setPrevious(n.getPrevious());
                node.setNext(n);
                n.setPrevious(node);
            }
        }
        currentSize++;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * Best Case: O(1)
     * Worst Case: O(n)
     * Average Case: O(n)
     * @return element removed from the list or null if !belongs(element)
     */
    public E remove(E element) {
        if(!contains(element)) return null;
        int i = 0;
        DoublyListNode<E> node = new DoublyListNode<>(element);
        DoublyListNode<E> n = head;
        while(i < currentSize && !n.getElement().equals(node.getElement())){
            i++;
            n = n.getNext();
        }
        if(i == 0){     //Removes first
            if(size() == 1) {     //There's only this element in the list
                head = null;
                tail = null;
            }
            else{
                n.getNext().setPrevious(null);
                head = n.getNext();
            }
        }
        else if(i == currentSize - 1){      //Removes last
            if(size() == 1){
                head = null;
                tail = null;
            }
            else{
                n.getPrevious().setNext(null);
                tail = n.getPrevious();
            }
        }
        else {
            n.getNext().setPrevious(n.getPrevious());
            n.getPrevious().setNext(n.getNext());
        }
        currentSize--;
        return node.getElement();
    }
}
