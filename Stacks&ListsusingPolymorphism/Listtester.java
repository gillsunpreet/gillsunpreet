package Lab_4B;

/**
 *
 * @author Sunpreet Singh C0436626
 */
public class Listtester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyList<Integer> list = new MyList<>();
// Adding elements to my list with the help of add methods
        list.add(0);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("Current size of my list is : " + list.size());
        list.add(1);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("First element of my list is: " + list.first());
        System.out.println("Last element of my list is: " + list.last());
        System.out.println("Current size of my list is : " + list.size());
        list.add(2);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("First element of my list is: " + list.first());
        System.out.println("Last element of my list is: " + list.last());
        System.out.println("Current size of my list is : " + list.size());
        list.add(3);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("First element of my list is: " + list.first());
        System.out.println("Last element of my list is: " + list.last());
        System.out.println("Current size of my list is : " + list.size());
        list.add(4);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("First element of my list is: " + list.first());
        System.out.println("Last element of my list is: " + list.last());
        System.out.println("Current size of my list is : " + list.size());
        list.add(5);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("First element of my list is: " + list.first());
        System.out.println("Last element of my list is: " + list.last());
        System.out.println("Current size of my list is : " + list.size());
        list.add(6, 6);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("First element of my list is: " + list.first());
        System.out.println("Last element of my list is: " + list.last());
        System.out.println("Current size of my list is : " + list.size());
        list.add(12);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("First element of my list is: " + list.first());
        System.out.println("Last element of my list is: " + list.last());
        System.out.println("Current size of my list is : " + list.size());
        list.add(13);
        System.out.println("Current List: " + '\n' + list);
        System.out.println("First element of my list is: " + list.first());
        System.out.println("Last element of my list is: " + list.last());
        System.out.println("Current size of my list is : " + list.size());
        System.out.println("Current List: " + '\n' + list);
        System.out.println("Index 6 element: " + list.remove(0));
        System.out.println(list);
        System.out.println("Getting index 0 element: " + list.get(0));
        System.out.println("getting index 8 element: " + list.get(8));
        System.out.println("Getting index 5 element: " + list.get(5));
        System.out.println("updated list : " + list);
        System.out.println("Index of element 13 is: " + list.indexOf(13));
        System.out.println("Index of element 5 is: " + list.indexOf(5));
        System.out.println("Index of element 6 is: " + list.indexOf(6));
        System.out.println(list);
        list.reverse();// Reversing my List
        System.out.println(list);
        System.out.println("After cutting the list from index 5: " + list.cut(5)); // 2 1 0 6
        System.out.println("Elements that were not included in the cut: " + list);// 13 12 5 4 3

    }

}
