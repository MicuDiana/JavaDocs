package exercise.exercise0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Radu.Hoaghe on 4/20/2015.
 *
 * Exercise 0: Create a List (ArrayList or LinkedList), add elements to it and print all of them using ListIterator
 *             for loop and foreach loop.
 *
 */
public class Exercise0 {

    public Exercise0(){

    }

    public void iterateThroughList(){

        // TODO Exercise #0 a) Create a list (ArrayList or LinkedList) and add elements to it
        ArrayList<Integer> myList = new ArrayList<Integer>();
        // TODO Exercise #0 a) Don't forget to specify the type of the list (Integer, String etc.)
        myList.add(7);
        myList.add(8);
        myList.add(9);

        // TODO Exercise #0 b) Iterate through the list using ListIterator and print all its elements

        ListIterator<Integer> it = myList.listIterator();

        // TODO Exercise #0 c) Iterate through the list using classic for loop and print all its elements

        System.out.println("Iterate 1:");
        while(it.hasNext()){
            Integer i = it.next();
            System.out.println(i.toString());
        }

        System.out.println("Iterate 2:");
        // TODO Exercise #0 d) Iterate through the list using foreach loop and print all its elements
        for(Integer i : myList)
            System.out.println(i.toString());
    }

    public static void main(String[] args) {
        // TODO Exercise #0 e) Create a new instance of Exercise0 class and call the iterateThroughList() method
        Exercise0 list = new Exercise0();
        list.iterateThroughList();
    }
}
