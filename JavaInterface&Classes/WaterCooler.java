/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SinghSunpreet_lab2;

/**
 *watercooler is extending freezer class 
 * @author Sunpreet Singh
 * C0436626
 */
public class WaterCooler extends Freezer  implements commonThings {
    private final int price = 2000; // set price of the water cooler


    
    public WaterCooler( boolean electric, boolean water ){
        
       super(electric,water); //using super keyword to call the parent class variables
       
    }
    @Override
     public String [] size() { 
       return super.size();
    }
     
     public int price(){
        return price;
    
    
    }
     public String toString(){
         String wc;
         wc = super.toString() + "," + " Price of water cooler is " + price;
         return wc;
     }
}
