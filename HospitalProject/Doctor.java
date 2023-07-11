package SinghSunpreet_lab1;

/**
 *
 * @author Sunpreet Singh C0436626
 */
public class Doctor extends Employee { //Doctor class is a subclass of class Employee
    //instance variables 

    public String speciality;
    public boolean treated;
    public final static boolean satisfied = true;
    public final static boolean notSatisfied = false;

//constructor for class Doctor
    public Doctor(String unit, String speciality, String givenName, String surName) {
        super(unit, givenName, surName);
        super.annualSalary = 250000;

        this.speciality = speciality;

    }

    public Doctor() {
        super("n/a", "n/a", "n/a");
        super.annualSalary = 250000;

        this.speciality = "n/a";

    }

    /**
     * gets the annual salary of a doctor
     *
     * @return annual salary amount of a doctor
     */
    public int getAnnualSalary() {
        return annualSalary;
    }

    /**
     * method toString() for class Doctor
     *
     * @return a string with doctor's unit number,speciality, given name and
     * first name
     */
    public String toString() {
        String d = "";
        d = super.toString() + " Speciality: " + speciality;
        return d;
    }

}
