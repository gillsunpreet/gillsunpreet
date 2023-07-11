package FinalProject;

import Lab3.CircularQueue;
import SinghSunpreet_lab1.Bed;
import SinghSunpreet_lab1.Doctor;
import SinghSunpreet_lab1.Nurse;
import SinghSunpreet_lab1.Patient;
import java.util.Date;
import java.util.List;

/**
 *When a patient is registered has a doctor with a speciality, nurse and bed
 * @author Sunpreet Singh
 * C0436626
 */
public class RegisteredPatient extends Patient {
       Nurse n;
    Doctor d;
    Bed bed;
    String issue; // health issue
     String nameOfBed = "";
 
    Patient pat;
    
    public RegisteredPatient(Patient p,Doctor d, Nurse n){// Registration
          
          this.d = d;
          this.n = n;
          this.pat = p;
    }
   
    /**
     * assign a bed to our registered patient 
     * @param b = bed
     */
   public void assignBed(Bed b){
       this.bed =b;
     
   } 
   /**
    * gets the assigned bed to a specific patient
    * @return assigned bed
    */
   public Bed getassignBed(){
       return this.bed;
   }
   /**
    * Sets the nurse for our patient
    * @param n = nurse
    */
   public void setNurse(Nurse n){
       this.n = n;
   }
   /**
    * gets the assigned nurse to a specific patient 
    * @return assigned nurse
    */
   public Nurse getNurse(){
       return this.n;
   }
   /**
    * Calculates the total amount owed to the Hospital Facility by the patient 
    * @return int total amount owed by a patient 
    */
   public int totalAmountOwing(){
           return (amountOwing() + CostofBed());
       
   }
   /**
    * calculates the amount owed for a specific health issue
    * @return amount charged at our healthcare facility for a specific sickness
    */
      public int amountOwing(){
             int amountOwed;
        if(null==issue){
            return  amountOwed = 10000;
        }else switch (issue) {
            case "Heart Attack":
                return amountOwed = 15000;
            case "Brain Injury":
                return amountOwed = 50000;
            case "Cancer":
                return amountOwed = 60000;
            case "Pelvic Pain":
                return amountOwed = 0;
            default:
                return amountOwed = 10000;
        }
         
 
    }
 
      /**
       * calculates the cost of a specific bed thats assigned to the patient
       * @return cost of bed thats charged from the patient 
       */
       public int CostofBed(){
     //String nameOfBed = "";
      
     int bedCost;
    if (null == bed.location){
       return bedCost = 1000;
        
    }else switch (bed.location) {
            case "Emergency":
            case "Surgery":
                return bedCost = 500;
                
            case "Urgent Care":
              return bedCost = 300;
                
            case "Trauma Center":
               return bedCost = 800;
              
             default:
                return bedCost = 10000;
          
                
        }
   
          
}
       
      
      
    @Override
  public String toString(){
      String s = "";
      s =   "\n" + " Patient's Name: "  +this.pat +  " Assigned Doctor: " +this.d + " Assigned Nurse: "+ this.n;
      return s;
  }
}
