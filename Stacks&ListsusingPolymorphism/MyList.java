package Lab_4B;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 *
 * @author Sunpreet Singh C0436626
 * @param <E>
 */
public class MyList<E> implements List<E> {

    Node<E> head;
    Node<E> tail;
    int size;
    int capacity;
    int position = 0;

    /**
     * Inserts an element at the given position.
     *
     * @param element element to be added
     * @param index position at which the new element will be inserted
     * @throws IndexOutOfBoundsException if {@code index âˆ‰ [-size(), size()]}
     */
    @Override
    public void add(E element, int index) {
        int s = size();
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Out of bounds can't add here");
        }
        Node<E> node = new Node<>(element);
        Node<E> ptr;

        if (index == 0 || head == null) {
            add(element);
        } else {

            ptr = this.head;
            int i = 0;
            while (ptr.next != null && i < index - 1) {
                ptr = ptr.next;
                i++;
            }
            node.next = ptr.next;
            ptr.next = node;
        }
        size++;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return total number of elements stored in this collection.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Add the given element to the front of this list.
     *
     * @param element element to be added
     */
    @Override
    public void add(E element) {
        Node<E> temp = new Node<>(element);
        Node<E> ptr;
        ptr = this.head;
        temp.next = head;
        head = temp;
        size++;
    }

    /**
     * display the added elements to the list
     *
     * @return string
     */
    @Override
    public String toString() {
        String result = "";

        Node node = head;
        while (node.next != null) {

            result += " " + node.element;
            node = node.next;
        }
        result += " " + node.element;
        return result;

    }

    /**
     * Checks if the list is full or not
     *
     * @return
     */

    public boolean full() {
        return (size == capacity);
    }

    /**
     * Removes the element at the given position from the list and returns it.
     *
     * @param index position of the element to be removed
     * @return the element that was removed
     * @throws IndexOutOfBoundsException if {@code index âˆ‰ [-size(), size())}
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        int s = size();
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("List is full");
        }
        Node<E> prev;

        E temp;
        prev = this.head;
        Node<E> ptr;
        ptr = this.head.next;
        int i = 0;
        if (index == 0) {

            return temp = first();

        } else if (index == s - 1) {
            return last();
        } else {
            while (ptr.next.next != null && i < index - 1) {
                ptr = ptr.next;
                prev = prev.next;
                i++;

            }
            prev.next = ptr.next;
            temp = ptr.element;
            ptr.next = null;
            //ptr=tail;}
        }
        size--;
        return temp;

    }

    /**
     * Remove all elements from this collection.
     */
    @Override
    public void clear() {

        head = null;

    }

    /**
     * Get the element stored at the beginning of the list. This element is
     * <em>not</em> removed from the collection.
     *
     * @return the first element stored in this collection.
     * @throws NoSuchElementException if the list is empty.
     */
    @Override
    public E first() throws NoSuchElementException {
        E temp;
// Node<E> ptr;
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else {
            Node<E> ptr;
            ptr = this.head;

            temp = ptr.element;
        }
        return temp;

    }

    /**
     * checks if the list is empty or not
     *
     * @return true if the size is 0 which means the list is empty
     */
    boolean isEmpty() {
        return size == 0;

    }

    /**
     * Get the element stored at the end of list. This element is <em>not</em>
     * removed from the collection.
     *
     * @return the last element stored in this collection.
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        Node<E> ptr;
        E temp = null;
        // initiating the walk
        ptr = this.head;
        int i = 0;
        while (ptr.next.next != null && i < size) {
            ptr = ptr.next;
            i++;
        }
        // remove the node

        temp = ptr.next.element;

        // update and reconnect
        tail = ptr;
        //ptr.next = null;

        return temp;

    }

    /**
     * Get the element stored at the given position in the list.
     *
     * @param index position of the element to retrieve
     * @return the element at the given position
     * @throws IndexOutOfBoundsException if {@code index âˆ‰ [-size(), size())}
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        int s = size();
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("List is full");
        }
        Node<E> prev;
        E temp;
        prev = this.head;
        Node<E> ptr;
        ptr = this.head.next;
        int i = 0;
        if (index == 0) {

            return temp = first();

        } else if (index == s - 1) {
            return last();
        } else {
            while (ptr.next.next != null && i < index - 1) {
                ptr = ptr.next;
                prev = prev.next;
                i++;

            }

            temp = ptr.element;

        }
        //
        return temp;

    }

    /**
     * Get the position of the first occurrence of the given element. Elements
     * in the collection will be compared to the argument using
     * {@link Object#equals(Object)}.
     *
     * @param element the element to search for
     * @return position index
     * @throws NoSuchElementException if the given {@code element} is not stored
     * in this collection.
     */
    @Override
    public int indexOf(E element) throws NoSuchElementException {

        int index = 0;
        Node<E> current = head;
        while (current != null) {
            if ((current.element).equals(element)) {
                return index;
            }

            index++;
            current = current.next;
        }
        if (element != current) {
            throw new NoSuchElementException("Element not found in the list! ");
        } else {
            return index;
        }

    }

    public boolean Equals(E element) {
        return (this == element);
    }

    /**
     * Reverses the order of all elements in this list.
     */
    @Override
    public void reverse() {
        MyList<E> temp = new MyList<>();
        Node<E> ptr = this.head;
        while (ptr != null) {
            temp.add(ptr.getElement());
            ptr = ptr.next;

        }
        System.out.println("Reverse list is " + temp);
    }

    /**
     * Splits the list in two at the given position. The element at that
     * position and all following elements will be removed from this list and
     * returned as part of a new list.
     *
     * @param index position of the first element to be removed
     * @return a new list containing all elements that were removed
     * @throws IndexOutOfBoundsException if {@code index âˆ‰ [-size(), size())}
     */
    @Override
    public MyList<E> cut(int index) throws IndexOutOfBoundsException {

        if (size == 0 || index >= size) {
            throw new IndexOutOfBoundsException("List is full");
        }
        MyList<E> tempList = new MyList<>();
        Node<E> ptr = this.head;
        Node<E> ptr2;
        Node<E> ptrCut;
        int i = 0;
        if (index < size - 1 && index > 0) {
            while (ptr.next != null && i < index - 1) {
                ptr = ptr.next;
                i++;
            }
            ptrCut = ptr;
            tempList.head = ptr.next;
            ptr2 = tempList.head;
            while (ptr2.next != null) {
                ptr2 = ptr2.next;
                tempList.size++;
            }
            tempList.tail = ptr2;
            ptrCut.setNext(null);
            this.tail = ptrCut;
            this.size = (index - 1);
        }
        if (index == size - 1) {
            tempList.add(this.tail.getElement());
            this.remove(index);

        }

        return tempList;

    }

}
