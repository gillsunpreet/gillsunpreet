package Lab_4B;

/**
 *
 * @author Sunpreet Singh C0436626
 */
public class StackTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LinkedStack<String> stk1 = new LinkedStack<>();
        System.out.println("Stack is empty : " + stk1.isEmpty());
        stk1.push("a");
        System.out.println(stk1);
        System.out.println("Top of my stack is: " + stk1.peek());
        stk1.push("b");
        System.out.println(stk1);
        System.out.println("Top of my stack is: " + stk1.peek());
        stk1.push("c");
        System.out.println(stk1);
        System.out.println("Top of my stack is: " + stk1.peek());
        System.out.println("Size of my stack: " + stk1.size());
        stk1.push("d");
        System.out.println(stk1);
        System.out.println("Top of my stack is: " + stk1.peek());
        stk1.push("f");
        System.out.println(stk1);
        System.out.println("Top of my stack is: " + stk1.peek());
        System.out.println("Size of my stack: " + stk1.size());
        System.out.println("Stack is empty : " + stk1.isEmpty());

        System.out.println("Popping the last element added:  " + stk1.pop());
        System.out.println("Updated stack:" + stk1);
        System.out.println("Popping the last element added:  " + stk1.pop());
        System.out.println("Updated stack:" + stk1);
        // adding more elements to my stack using push
        stk1.push("z");
        stk1.push("x");
        System.out.println(stk1);
    }

}
