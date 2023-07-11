package SinghSunpreet_lab1;

/**
 *
 * @author Sunpreet Singh
 * C0436626
 */
public class Nurse extends Employee { // Nurse class is a subclass of Employee
   //instance variables
    public static final int SHIFT_A = 1;
    public static final int SHIFT_B =2;
    public static final int SHIFT_C =3;
    private  int shift = 1;
   
    
    
    
    //Nurse class constructor
    public Nurse(String unit, String givenName, String surName){
        super(unit, givenName, surName);
        super.annualSalary = 80000;
        
    }
   
 
   
    /**
     * method setShift
     * sets the shift of nurses
     * @param shift 
     */
     public void setShift( int shift){
       
        this.shift = shift;
         
        switch(this.shift){
             case SHIFT_B:
             super.annualSalary = 85000;
           
             break;
             
             case SHIFT_C:
             super.annualSalary = 90000;
             
             break;
             
             default:
                 super.annualSalary = 80000;
                 
         }
         
     
          /**
     * method getShift 
     * gets the shift of nurses
     * @param shift
     * @return shift 
     */
              
}
     
      public int getShift(int shift){
       return shift;
   }
     /**
      * method setannualSalary
      * sets the annual salary of nurses depending upon their shift type
      */
      public void setannualSalary(){
     
     int annualSalary = 80000;
     if(this.shift==SHIFT_B){
         annualSalary = 85000;
         ;
     }else if(this.shift== SHIFT_C){
         annualSalary = 90000;
         ;
     }
     
       this.annualSalary = annualSalary;
       
 }
      /**
       * gets the annual salary of a nurse
       * @return the annual salary of a nurse
       */
      public int getAnnualSalary() {
        return annualSalary;
    }
     
     
     
     
     
     
    @Override
     public String toString(){
         String str;
    str = super.toString() +  "," + " shift " + this.shift + ")";
    
    return str;
}
    
   
    
    
    
}
