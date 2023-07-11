package FinalProject;

import SinghSunpreet_lab1.Bed;
import SinghSunpreet_lab1.Doctor;
import SinghSunpreet_lab1.Nurse;
import SinghSunpreet_lab1.Patient;
import ca.camosun.comp139.solution.lab3.ArrayStack;
import ca.camosun.comp139.solution.lab3.CircularQueue;
import ca.camosun.comp139.solution.lab3.SimpleQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Sunpreet Singh 
 * C0436626
 * our main Hospital where there are patients, nurses and
 * doctors
 */
public class hospitalFacility {

    /**
     * @param args the command line arguments
     */
    static String[] givenName = {
        "Avery", "Riley", "Jordan", "Angel", "Peyton",
        "Quinn", "Hayden", "Taylor", "Alexis", "Rowan",
        "Charlie", "Emerson", "Finley", "River", "Emery",
        "Morgan", "Elliot", "London", "Eden", "Elliott",
        "Karter", "Dakota", "Reese", "Remington", "Payton",
        "Amari", "Phoenix", "Kendall", "Harley", "Rylan",
        "Marley", "Dallas", "Skyler", "Spencer", "Sage",
        "Kyrie", "Ellis", "Rory", "Remi", "Justice",
        "Ali", "Haven", "Tatum", "Arden", "Linden",
        "Devon", "Rebel", "Rio", "Ripley", "Frankie"
    };
    static String[] surnames = {"Smith", "Brown", "Tremblay", "Martin", "Roy",
        "Wilson", "Macdonald", "Gagnon", "Johnson", "Taylor",
        "Cote", "Campbell", "Anderson", "Leblanc", "Lee",
        "Jones", "White", "Williams", "Miller", "Thompson",
        "Gauthier", "Young", "Van", "Morin", "Bouchard",
        "Scott", "Stewart", "Belanger", "Reid", "Pelletier",
        "Moore", "Lavoie", "King", "Robinson", "Levesque",
        "Murphy", "Fortin", "Gagne", "Wong", "Clark",
        "Johnston", "Clarke", "Ross", "Walker", "Thomas",
        "Boucher", "Landry", "Kelly", "Bergeron", "Davis"
    };

    public static void main(String[] args) {
        int day = 0;
        boolean released = false;

        java.util.List<Doctor> docList = new java.util.LinkedList<>();
        //calling addDoctor method from the Generator class 
        docList = Generator.addDoctor();

        SimpleQueue<Nurse> nurses = new SimpleQueue<>();
        // calling addNurse()method from generator class to add nurses to the facility
        nurses = Generator.addNurse();
        ArrayStack<Bed> stkBeds = new ArrayStack<>();
        // calling addBeds() method from generator class to add beds to our facility
        stkBeds = Generator.addBeds();
        // Adding patients to our facility

        System.out.println("List of Available Patients: ");
        CircularQueue<Patient> patientList = Generator.generateTestPatients(5);

//Registration begins here , Assigning a specialist doctor and the first available Nurse to the Patirnt
        day = 1;
        while (day <= 2) {
// Registration of Patient 1
            RegisteredPatient pat1 = new RegisteredPatient(patientList.dequeue(), docList.get(3), nurses.dequeue());

            pat1.issue = "Cancer";// patient 1 health problem

            pat1.assignBed(stkBeds.pop());// assigning bed to the patient from stack of beds
            System.out.println("\n" + "Assigned bed of Patient 1: " + pat1.getassignBed());// Assigning a bed to patient
            System.out.println(pat1);

            //Registration of Patient 2
            RegisteredPatient pat2 = new RegisteredPatient(patientList.dequeue(), docList.get(0), nurses.dequeue());
            pat2.issue = "Brain Injury";
            pat2.assignBed(stkBeds.pop());
            System.out.println("\n" + "Assigned bed of Patient 2: " + pat2.getassignBed());// Assigning a bed to patient
            System.out.println(pat2);

            // Resgistration of Patient 3
            RegisteredPatient pat3 = new RegisteredPatient(patientList.dequeue(), docList.get(2), nurses.dequeue());
            pat3.issue = "Pelvic Pain";
            pat3.assignBed(stkBeds.pop());
            System.out.println("\n" + "Assigned bed of Patient 3: " + pat3.getassignBed());// Assigning a bed to patient
            System.out.println(pat3);

            //Registration of Patient 4
            RegisteredPatient pat4 = new RegisteredPatient(patientList.dequeue(), docList.get(1), nurses.dequeue());
            pat4.issue = "Heart Attack";
            pat4.assignBed(stkBeds.pop());
            System.out.println("\n" + "Assigned bed of Patient 4: " + pat4.getassignBed());// Assigning a bed to patient
            System.out.println(pat4);

            //Registration of Patient 5
            RegisteredPatient pat5 = new RegisteredPatient(patientList.dequeue(), docList.get(4), nurses.dequeue());
            pat5.issue = "Lack of Breathing";
            pat5.assignBed(stkBeds.pop());
            System.out.println("\n" + "Assigned bed of Patient 5: " + pat5.getassignBed());// Assigning a bed to patient
            System.out.println(pat5);
            day++;
            System.out.println("   ");
            System.out.println("After Treatment   ");
            System.out.println("   ");
            System.out.println("patient 1 is treated ");
            System.out.println("patient 2 is treated ");
            System.out.println("patient 3 is treated ");
            System.out.println("patient 4 is treated ");
            System.out.println("patient 5 is treated ");
            System.out.println("   ");
            // Reporting
            // Displaying total amount owed by patients that is bed charges + treatment charges
            System.out.println("Total Amount owed by PAtient 1: " + pat1.totalAmountOwing());
            System.out.println("Total Amount owed by PAtient 2: " + pat2.totalAmountOwing());
            System.out.println("Total Amount owed by PAtient 3: " + pat3.totalAmountOwing());
            System.out.println("Total Amount owed by PAtient 4: " + pat4.totalAmountOwing());
            System.out.println("Total Amount owed by PAtient 5: " + pat5.totalAmountOwing());

            // Creating a list of patient bills to sort the bills from less to more
            //patient's amount owed sorting starts here
            int billofP1 = pat1.CostofBed() + pat1.amountOwing();
            int billofP2 = pat2.CostofBed() + pat2.amountOwing();
            int billofP3 = pat3.CostofBed() + pat3.amountOwing();
            int billofP4 = pat4.CostofBed() + pat4.amountOwing();
            int billofP5 = pat5.CostofBed() + pat5.amountOwing();
            //Sorting bills of Patients in Ascending order 
            List<Integer> billList = new ArrayList<>();
            billList.add(billofP1);
            billList.add(billofP2);
            billList.add(billofP3);
            billList.add(billofP4);
            billList.add(billofP5);
            // Sorting the bill of patients in Ascending Order
            //Lowest to greatest
            Collections.sort(billList);
            System.out.println("Sorting Treated patients bill in Ascending Order: ");
            for (int i = 0; i < billList.size(); i++) {

                System.out.println(billList.get(i));
            }
            //Patient amount owed sorting ends here

            //Total amount owed by the Patients to the facility
            System.out.println("Total amount owed by patients to our Healthcare facility" + '\n' + "$"
                    + (pat1.amountOwing() + pat2.amountOwing() + pat3.amountOwing() + pat4.amountOwing()
                    + pat5.amountOwing()));

            // Total amount owed to the Nurses by the Health Care Facility
            System.out.println("Total amount owed to the Nurses by the Facility:  "
                    + "$" + (pat1.getNurse().getAnnualSalary() + pat2.getNurse().getAnnualSalary()
                    + pat3.getNurse().getAnnualSalary() + pat4.getNurse().getAnnualSalary()
                    + pat5.getNurse().getAnnualSalary()));

            // Total amount owed to the Doctors by our Facility
            System.out.println("Total amount owed to Doctors by our Healthcare Facility: "
                    + "$" + (pat1.d.getAnnualSalary() + pat2.d.getAnnualSalary()
                    + pat3.d.getAnnualSalary() + pat4.d.getAnnualSalary() + pat5.d.getAnnualSalary()));

            //Total amount owed to all the Employees by Our Healthcare FAcility
            System.out.println("Total amount owed to all Employees by our Healthcare Facility: "
                    + "$" + (pat1.d.getAnnualSalary() + pat2.d.getAnnualSalary()
                    + pat3.d.getAnnualSalary() + pat4.d.getAnnualSalary() + pat5.d.getAnnualSalary()
                    + pat1.getNurse().getAnnualSalary() + pat2.getNurse().getAnnualSalary()
                    + pat3.getNurse().getAnnualSalary() + pat4.getNurse().getAnnualSalary()
                    + pat5.getNurse().getAnnualSalary()));

            // Getting the Beds back from the treated patients so these 
            //are the available Beds now
            ArrayStack<Bed> tempStk = new ArrayStack<>();
            tempStk.push(pat1.getassignBed());
            tempStk.push(pat2.getassignBed());
            tempStk.push(pat3.getassignBed());
            tempStk.push(pat4.getassignBed());
            tempStk.push(pat5.getassignBed());
            System.out.println("List Available Beds for the new patients: ");
            System.out.println(tempStk);

            // creating a temporary queue to store all the assigned nurses because once you dequeue to assign
            // them to patients this will bring them back when you need them after the patient has 
            //emp been discharged
            SimpleQueue<Nurse> temp = new SimpleQueue<>();
            temp.enqueue(pat1.getNurse());
            temp.enqueue(pat2.getNurse());
            temp.enqueue(pat3.getNurse());
            temp.enqueue(pat4.getNurse());
            temp.enqueue(pat5.getNurse());
            System.out.println("List of Available nurses after the Patients have been Treated: " + temp);

            day++;

        }
    }
}
