package FinalProject;

import SinghSunpreet_lab1.Bed;
import SinghSunpreet_lab1.Doctor;
import SinghSunpreet_lab1.Nurse;
import SinghSunpreet_lab1.Patient;
import ca.camosun.comp139.solution.lab3.ArrayStack;
import ca.camosun.comp139.solution.lab3.CircularQueue;
import ca.camosun.comp139.solution.lab3.SimpleQueue;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * This class generates 5 doctors, 5 nurses objects for our Healthcare Facility
 *
 * @author Sunpreet Singh C0436626
 */
public class Generator {

    Nurse nurse;
    Doctor doctor;
    Patient patient;

    // creates an array of string type with many given names to choose from
    static String[] sickness = {"Heart Attack", "AIDS", "Pelvic pain",
        "Brain Injury", "Cancer", "Dengue", "Breathing Problem"};

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
//creates an array surnames of string type with many surnames to choose from
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

    static Random r = new Random(); //creates a random number

    /**
     * this methods creates Doctor objects as Linked List that we use in our
     * healthcare facility
     *
     * @return
     */
    public static List<Doctor> addDoctor() {
        java.util.List<Doctor> docList = new java.util.LinkedList<>();
        //By acticating the lines of code below, hospital facility will throw an exception when 
        //no more doctors are available and we try to assign a doctor
//if(docList.isEmpty()){
//    throw new NoSuchElementException("Queue is empty");
//}
        Doctor d1 = new Doctor("Neurology", "Neurologist", "Kendall", "Charlie");

        Doctor d2 = new Doctor("Coronary care", "Cardiologist", "Jordan", "Williams");

        Doctor d3 = new Doctor("Gynecology", "Gyenecologist", "Zhang", "Lei");

        Doctor d4 = new Doctor("Oncology", "Oncologist", "Henry", "Cejudo");

        Doctor d5 = new Doctor("Pulmonary", "Pulmonologist", "Chael", "Sonnen ");

        docList.add(d1);
        docList.add(d2);
        docList.add(d3);
        docList.add(d4);
        docList.add(d5);

        return docList;

    }

    /**
     * Creates Nurses for our Healthcare Facility
     *
     * @return
     */
    public static SimpleQueue<Nurse> addNurse() {

        SimpleQueue<Nurse> nurses = new SimpleQueue<>();
        //by acticating the lines of code below oue hospita; facility will throw an exception when 
        // no nurses are left to assign
//if(nurses.capacity() == 0){
//    throw new NoSuchElementException("No more Nurses Presesnt");
//    
//}
        Nurse n1 = new Nurse("Neurology", "CM", "Punk");
        n1.setShift(1);

        Nurse n2 = new Nurse("Gyenecology", "Ronda", "Rousey");
        n2.setShift(2);

        Nurse n3 = new Nurse("Neurology", "Cody", "Rhodes");
        n3.setShift(1);

        Nurse n4 = new Nurse("Oncology", "Holly", "Molly");
        n4.setShift(3);

        Nurse n5 = new Nurse("Coronary Care", "Amanda", "Nunnes");
        n5.setShift(3);

        nurses.enqueue(n1);
        nurses.enqueue(n2);
        nurses.enqueue(n3);
        nurses.enqueue(n4);
        nurses.enqueue(n5);

        return nurses;

    }

    /**
     * create beds for our healthcare facility as stacks
     *
     * @return
     */
    static ArrayStack<Bed> addBeds() {
        //Creating Beds as stack
        ArrayStack<Bed> stkBeds = new ArrayStack<>();

//Activate these lines of code and our hospital facility class will throw an empty stack exception 
//        when no beds are available
//        if(stkBeds.isEmpty()){
//            throw new NoSuchElementException("No more beds are available");
//        }
        stkBeds.push(new Bed("Emergency"));
        stkBeds.push(new Bed("Surgery"));
        stkBeds.push(new Bed("Urgent Care"));
        stkBeds.push(new Bed("Coronary Ward"));
        stkBeds.push(new Bed("Trauma Center"));
        // System.out.println(stkBeds);
        return stkBeds;

    }

    /**
     * Create patients for our Hospital facility Patients are person when not
     * registered in our hospital facility put patient objects in Circular array
     *
     * @param num
     * @return a queue of patients
     */
    static CircularQueue<Patient> generateTestPatients(int num) {

        Patient p1;

        CircularQueue<Patient> patQueue = new CircularQueue<>(num);

        int i = 0;
        while (i < num) {
            int randomNumber = r.nextInt(givenName.length);
            String first = (givenName[randomNumber]);
            String last = (surnames[randomNumber]);
            // assignedNurse = nurses.dequeue();
            // patientBed =  stkBeds.pop();
            p1 = new Patient(first, last);
            // p1.assignBed(patientBed);
            System.out.print(p1 + " \n");
            // assignedNurse.

            patQueue.enqueue(p1);
            i++;
        }
        return patQueue;
    }

}
