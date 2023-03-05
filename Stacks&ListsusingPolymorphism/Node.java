package Lab_4B;

/**
 *
 * @author Sunpreet Singh C0436626
 * @param <T>
 */
public class Node<T> {

    T element;
    Node<T> next;
    Node<T> prev;

    Node(T element) {
        this.element = element;

    }

    Node(T element, Node<T> next) {
        this.element = element;
        this.next = next;

    }

    Node(Node<T> prev) {

        this.prev = prev;
    }

    public void setElement(T e) {
        this.element = e;
    }

    public T getElement() {
        return element;
    }

    public void setNext(Node<T> next) {
        this.next = next;

    }

    public Node<T> getNext() {
        return next;
    }

    public void setPrevious(Node<T> prev) {
        this.prev = prev;
    }

    public Node<T> getPrevious() {
        return prev;
    }

    @Override
    public String toString() {
        String s = "";
        s = "The letter is: " + getElement() + "\n";
        return s;
    }
}
