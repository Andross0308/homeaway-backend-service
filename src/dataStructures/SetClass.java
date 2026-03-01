package dataStructures;

import java.io.Serial;

public class SetClass<E> implements Set<E> {

    @Serial
    private static final long serialVersionUID = 34L;

    private final Map<String, DoublyListNode<E>> mapOfElements;
    private DoublyListNode<E> head;
    private DoublyListNode<E> tail;
    private int size;

    /**
     * Constructor
     */
    public SetClass(int size){
        mapOfElements = new SepChainHashTable<>(size);
        head = null;
        tail = null;
        this.size = 0;
    }

    /**
     * Verifies if the set doesn't have any elements
     * @return true if size is 0, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Gives the total number of elements in the set
     * @return the size of the set
     */
    @Override
    public int size(){
        return size;
    }

    /**
     * Adds a new Element to the end of the Set
     * Best Case: O(1)
     * Worst Case: O(n)
     * Average Case: O(1)
     * @param s : New element to add
     * @pre s != null && key != null
     */
    @Override
    public void addElement(String key, E s) {
        DoublyListNode<E> node = new DoublyListNode<>(s);
        if (head == null){    //First element to add
            head = node;
        }
        else{
            tail.setNext(node);
            node.setPrevious(tail);
        }
        tail = node;
        mapOfElements.put(key.toLowerCase(), node);
        size++;
    }

    /**
     * Removes an Element from the set
     * Best Case: O(1)
     * Worst Case: O(n)
     * Average Case: O(1)
     * @param s: Name of the Element
     * @return the removed element or null, if the element doesn't exist
     * @pre s != null
     */
    @Override
    public E removeElement(String s) {
        DoublyListNode<E> node = mapOfElements.get(s.toLowerCase());
        if(node == null) return null;
        if(node == head){
            if(node == tail){   //List only has one element
                head = null;
                tail = null;
            }else {
                node.getNext().setPrevious(null);
                head = node.getNext();
            }
        }else if(node == tail){
            node.getPrevious().setNext(null);
            tail = node.getPrevious();
        }else{
            node.getNext().setPrevious(node.getPrevious());
            node.getPrevious().setNext(node.getNext());
        }
        size--;
        return node.getElement();
    }

    /**
     * Gives a Student with the given name
     * Best Case: O(1)
     * Worst Case: O(n)
     * Average Case: O(1)
     * @param name : Name of the student
     * @return the student with that name or null, if it doesn't exist
     * @pre name != null
     */
    @Override
    public E getElement(String name) {
        DoublyListNode<E> elem = mapOfElements.get(name);
        if(elem == null) return null;
        return elem.getElement();
    }

    /**
     * Gives an Iterator of the elements in the set
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return na Iterator of elements
     * @pre head != null
     */
    @Override
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Gives an Iterator of the elements in the set that can be iterated from start to finish
     * or finish to start
     * Best Case: O(1)
     * Worst Case: O(1)
     * Average Case: O(1)
     * @return an iterator of Elements
     * @pre head != null && tail != null
     */
    @Override
    public TwoWayIterator<E> twoWayIterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }
}
