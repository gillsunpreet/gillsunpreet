package Lab_4B;

/**
 *
 * @author Sunpreet Singh C0436626
 * @param <E>
 */
public class LinkedStack<E> implements Stack<E> {

    Node<E> head;
    Node<E> tail;
    int size;
    int top = 0;
    int capacity = 5;
    int position = 0;

    private void add(E element) {
        Node<E> temp = new Node<>(element);
        Node<E> ptr;
        ptr = this.head;
        temp.next = head;
        head = temp;
        top++;
        size++;
    }

    public void add(E element, int index) {
        Node<E> d = new Node<>(element);
        Node<E> ptr;
        ptr = this.head;
        int i = 0;
        while (ptr.next != null && i < index - 1) {
            ptr = ptr.next;
            i++;
        }

        d.next = ptr.next;
        ptr.next = d;
        size++;
        top++;

    }

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

    public boolean full() {
        return (size == capacity);
    }

    /**
     * Test whether any elements are currently waiting on the stack.
     *
     * @return true if the stack is empty; false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the number of elements currently waiting on the stack.
     *
     * @return how many items are in this collection
     */
    @Override
    public int size() {
        return this.top;
    }

    /**
     * Add an element to the top of the stack.
     *
     * @param e the element to add to this collection.
     * @return true if the stack is not full and the element was succesfully
     * added; false otherwise.
     */
    @Override
    public boolean push(E e) {
        if (top == 5) {
            System.out.println("Linked stack is full");
        } else {
            Node<E> temp = new Node<>(e, head);
            //add(e);

            head = temp;
            size++;
            top++;
        }
        return false;

    }

    /**
     * Removes the element from the top of the stack and returns it.
     *
     * @return null if the stack is empty; otherwise, the most recently pushed
     * element.
     */
    @Override
    public E pop() {
        E tem = null;

        if (isEmpty()) {
            return null;
        } else {

            tem = head.element;
            head = head.next;
            top--;

            size--;
        }

        return tem;

    }

    /**
     * Returns (but does not remove) the element at the top of the stack.
     *
     * @return null if the stack is empty; otherwise, the most recently pushed
     * element.
     */
    @Override
    public E peek() {
        E tem = null;
        if (isEmpty()) {
            return null;
        } else {

            tem = head.element;
            top--;
            size--;
        }
        return tem;
    }
}
