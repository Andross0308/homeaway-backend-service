package dataStructures;

import dataStructures.exceptions.*;

import java.io.*;

public class SinglyLinkedList<E> implements List<E>, Serializable {

    @Serial private static final long serialVersionUID = 35L;
    /**
     *  Node at the head of the list.
     */
    private transient SinglyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private transient SinglyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private transient int currentSize;
    /**
     * Constructor of an empty singly linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SinglyLinkedList( ) {
        head = null;
        tail = null;
        currentSize = 0;
    }
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(currentSize);

        SinglyListNode<E> node = head;
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
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new SinglyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E getFirst() {
        if(isEmpty()) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E getLast() {
        if(isEmpty()) throw new NoSuchElementException();
        return tail.getElement();
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(n), n being the number of elements in the list;
     *
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    @Override
    public E get(int position) {
        if(position < 0 || position > size() - 1) throw new InvalidPositionException();
        SinglyListNode<E> current = head;
        int i = 0;
        while(i < position){
            i++;
            current = current.getNext();
        }
        return current.getElement();
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
    @Override
    public int indexOf(E element) {
        int i = 0;
        SinglyListNode<E> current = head;
        while(i < size() && !current.getElement().equals(element)){
            i++;
            current = current.getNext();
        }
        if(i == size()) i = NOT_FOUND;
        return i;
    }

    /**
     * Inserts the specified element at the first position in the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param element to be inserted
     */
    @Override
    public void addFirst(E element) {
        SinglyListNode<E> node = new SinglyListNode<>(element, head);
        head = node;
        if(isEmpty()){
            tail = node;
        }
        currentSize++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param element to be inserted
     */
    @Override
    public void addLast(E element) {
        SinglyListNode<E> node = new SinglyListNode<>(element);
        if(isEmpty()){
            head = node;
        }else{
            tail.setNext(node);
        }
        tail = node;
        currentSize++;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(n), n being the number of elements in the list;
     *
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    @Override
    public void add(int position, E element) {
        if ( position < 0 || position > currentSize )
            throw new InvalidPositionException();
        if(position == 0) addFirst(element);
        else if(position == currentSize) addLast(element);
        else{
            SinglyListNode<E> node = head;
            for(int i = 0; i < position - 1; i++){
                node = node.getNext();
            }
            SinglyListNode<E> newNode = new SinglyListNode<>(element, node.getNext());
            node.setNext(newNode);
            currentSize++;
        }
    }


    /**
     * Removes and returns the element at the first position in the list.
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E removeFirst() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        E element = head.getElement();
        head = head.getNext();
        currentSize--;

        if (isEmpty()) {
            tail = null;
        }
        return element;
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
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        E element = tail.getElement();

        if (currentSize == 1) {
            head = null;
            tail = null;
        } else {
            SinglyListNode<E> previous = head;
            while (previous.getNext() != tail) {
                previous = previous.getNext();
            }

            previous.setNext(null);
            tail = previous;
        }

        currentSize--;
        return element;
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * Best Case: O(1);
     * Worst Case: O(n), n being the number of elements in the list;
     * Average Case: O(n), n being the number of elements in the list;
     *
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    @Override
    public E remove(int position) {
        if (position < 0 || position >= currentSize) {
            throw new InvalidPositionException();
        }

        if (position == 0) {
            return removeFirst();
        } else if (position == currentSize - 1) {
            return removeLast();
        } else {
            SinglyListNode<E> previous = head;
            for (int i = 0; i < position - 1; i++) {
                previous = previous.getNext();
            }

            // Remover o nó
            SinglyListNode<E> toRemove = previous.getNext();
            E element = toRemove.getElement();
            previous.setNext(toRemove.getNext());

            currentSize--;
            return element;
        }
    }

}
