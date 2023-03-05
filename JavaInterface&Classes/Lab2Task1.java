/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SinghSunpreet_lab2;

/**
 *
 * @author Sunpreet Singh C0436626
 */
public class Lab2Task1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
// creating a new bicycle object with commonThings interface 
        commonThings c = new Bicycle(1, true, false);

        System.out.println(c);

        System.out.println(c.needsElectricity());// testing needsElectricity method thats in the interface
        // creating a new freezer object with the common things interface  
        commonThings f = new Freezer(true, true);
        System.out.println(f);
        System.out.println(f.needsElectricity());// testing this method that was listed in our interface
//creates a new horse with common things interface 
        commonThings h1 = new Horse("arabian");
        System.out.println(h1); // print name of horse, name is null and default date
        //creates a new object horse 2 with common things interface
//commonThings h2 = new Horse("pony" , "shetland" , (01-11-2020));
        // creates a new water cooler object with interface
        commonThings w = new WaterCooler(true, true);
        System.out.println(w);// this watercooler object inherits the toString() method from its 

        //parent class and itself
        System.out.println(w.needsElectricity()); // calls the interface method 

    }
}
