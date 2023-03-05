/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SinghSunpreet_lab2;

/**
 *
 * @author Sunpreet Singh
 */

    /**
 * A bicycle.
 * @author {@code Maxwell Terpstra <C0380979@intra.camosun.bc.ca>}
 */
public class Bicycle implements commonThings {
    private final int speeds;
    private final boolean collapsible;
    private final boolean electricAssist;
    

    private static final String[] SIZE = { "120", "30", "150" };
    private static final String[] COLLAPSED_SIZE = { "50", "50", "50" };

    /**
     * Create a new Bicycle
     * @param speeds number of gear speeds
     * @param canCollapse whether or not the bicycle is collapsible
     * @param electric whether or not the bicycle has an electric assist motor
     */
    public Bicycle(int speeds, boolean canCollapse, boolean electric) {
        this.speeds = speeds;
        this.collapsible = canCollapse;
        this.electricAssist = electric;
        
    }

    /**
     * Get the dimensions of this bicycle, in centimeters.
     * @return a 3D array of measurements in centimeters.
     */
    public String[] size() {
        return SIZE;
    }

    /**
     * Get the minimum amount of space to store this bicycle.
     * @return a 3D array of measurements in centimeters.
     */
    public String[] storageSize() {
        if (collapsible) {
            return COLLAPSED_SIZE;
        } else {
            return SIZE;
        }
    }

    /**
     * Get the number of gear speeds this bicycle has.
     * @return number of speeds
     */
    public int speeds() { return this.speeds; }

    /**
     * Whether or not this bicycle has an electric drive system.
     * @return true if this bicycle needs to
     */
    @Override
    public boolean needsElectricity() { 
        return this.electricAssist;
    }
   /** public String toString(){
        String str;
        str = "speed is " + this.speeds + " collapse or not: " + this.collapsible + " electric or not: "
                + this.electricAssist;
        return str;
    }*/
}

