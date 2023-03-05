package Lab_4B;

/**
 *
 * @author Sunpreet Singh C0436626
 */
public interface Stack<E> {

    /**
     * Test whether any elements are currently waiting on the stack.
     *
     * @return true if the stack is empty; false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of elements currently waiting on the stack.
     *
     * @return how many items are in this collection
     */
    int size();

    /**
     * Add an element to the top of the stack.
     *
     * @param e the element to add to this collection.
     * @return true if the stack is not full and the element was succesfully
     * added; false otherwise.
     */
    boolean push(E e);

    /**
     * Removes the element from the top of the stack and returns it.
     *
     * @return null if the stack is empty; otherwise, the most recently pushed
     * element.
     */
    E pop();

    /**
     * Returns (but does not remove) the element at the top of the stack.
     *
     * @return null if the stack is empty; otherwise, the most recently pushed
     * element.
     */
    E peek();
}
