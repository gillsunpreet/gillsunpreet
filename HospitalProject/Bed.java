package SinghSunpreet_lab1;

/**
 *
 * @author Sunpreet Singh C0436626
 */
public class Bed {

    public String location;
    private static int id = 0;
    public int beddCost;
    Patient p = new Patient();
//   public int costPerDay;
    Bed bed;
//Constructor

    public Bed(String name) {
        this.location = name + "-" + id;
        this.id++;
        // this.location = name;

    }

    /**
     * sets up the bed for the patient
     *
     * @param b
     * @return
     */
    public void setBed(Bed b) {
        this.bed = b;
    }

    /**
     * gets the bed
     *
     * @return bed
     */

    public Bed getBed() {

        return this.bed;
    }

    /**
     * Sets the patient to the bed
     *
     * @param p = patient
     */
    public void setPatient(Patient p) {
        this.p = p;
    }

    @Override
    public String toString() {
        String s = "";
        s = " " + this.location + " ";
        return s;
    }

}
