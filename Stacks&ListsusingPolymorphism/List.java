package Lab_4B;

/**
 *
 * @author Sunpreet Singh C0436626
 */
import ca.camosun.comp139.solution.lab1.Patient;
import java.util.NoSuchElementException;

/**
 * An ordered, indexed collection.
 *
 * <p>
 * List element positions are indexed starting from {@code 0}. Negative
 * positions may also be used, in which case the value will be interpreted as an
 * offset from the end of the list. For example, the final element in any
 * non-empty list may be accessed at the index {@code -1}.</p>
 *
 * @author mterpstra
 * @param <E>
 */
public interface List<E> {

    /**
     * Returns the number of elements in the list.
     *
     * @return total number of elements stored in this collection.
     */
    int size();

    /**
     * Remove all elements from this collection.
     */
    void clear();

    /**
     * Get the element stored at the beginning of the list. This element is
     * <em>not</em> removed from the collection.
     *
     * @return the first element stored in this collection.
     * @throws NoSuchElementException if the list is empty.
     */
    E first() throws NoSuchElementException;

    /**
     * Get the element stored at the end of list. This element is <em>not</em>
     * removed from the collection.
     *
     * @return the last element stored in this collection.
     * @throws NoSuchElementException if the list is empty
     */
    E last() throws NoSuchElementException;

    /**
     * Get the element stored at the given position in the list.
     *
     * @param index position of the element to retrieve
     * @return the element at the given position
     * @throws IndexOutOfBoundsException if {@code index âˆ‰ [-size(), size())}
     */
    E get(int index) throws IndexOutOfBoundsException;

    /**
     * Add the given element to the front of this list.
     *
     * @param element element to be added
     */
    void add(E element);

    /**
     * Inserts an element at the given position.
     *
     * @param element element to be added
     * @param index position at which the new element will be inserted
     * @throws IndexOutOfBoundsException if {@code index âˆ‰ [-size(), size()]}
     */
    void add(E element, int index) throws IndexOutOfBoundsException;

    /**
     * Removes the element at the given position from the list and returns it.
     *
     * @param index position of the element to be removed
     * @return the element that was removed
     * @throws IndexOutOfBoundsException if {@code index âˆ‰ [-size(), size())}
     */
    E remove(int index) throws IndexOutOfBoundsException;

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
    int indexOf(E element) throws NoSuchElementException;

    /**
     * Reverses the order of all elements in this list.
     */
    void reverse();

    /**
     * Splits the list in two at the given position. The element at that
     * position and all following elements will be removed from this list and
     * returned as part of a new list.
     *
     * @param index position of the first element to be removed
     * @return a new list containing all elements that were removed
     * @throws IndexOutOfBoundsException if {@code index âˆ‰ [-size(), size())}
     */
    List<E> cut(int index) throws IndexOutOfBoundsException;

    public <T extends Patient> void add(int current, T remove);
}
