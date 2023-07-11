package SinghSunpreet_lab1;

import ca.camosun.comp139.solution.lab3.ArrayStack;

/**
 *Patient when not Registered 
 * @author Sunpreet Singh C0436626
 */
public class Patient extends Person { // class Patient is a subclass of Person class
    //instance variables 

    public Doctor physician; //physician is a type Doctor

    public Nurse nurse;
//public int treatementExpenses;
    Bed bed;

    //constructor for patient class
//public Patient (String g,String s){
//    this.givenName = g;
//    this.surName = s;
//}
    //Patient When they arrive they are just person
    public Patient(String givenName, String surName) {
        super(givenName, surName);
    }
//Patient with Registration

    public Patient(String givenName, String surName, Doctor d, Nurse n) {
        super(givenName, surName);// Registration of patient, and assigning them docttors

        this.physician = d;
        this.nurse = n;

    }
// constrictor for a patient without anything

    public Patient() {
        super("", "");
    }

    /**
     * gets the bed assigned to the patient
     *
     * @return the assigned bed with a unique name
     */
    public void assignBed(Bed bed) {
        this.bed = bed;

    }

    /**
     * toString method for patient class which inherits to string from person
     * class and adds physician of type Doctor
     *
     * @return string with Patients first name and last name with the name of
     * physician assigned to them
     */
    @Override
    public String toString() {
        String phy = "";
        phy = super.toString();
        return phy;
    }

}
