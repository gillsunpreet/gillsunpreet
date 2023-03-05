package SinghSunpreet_lab2;

/**
 * A stand-alone freezer unit.
 * @author {@code Maxwell Terpstra <C0380979@intra.camosun.bc.ca>}
 * Sunpreet Singh 
 * C0436626
 */
public class Freezer implements commonThings {
    /**public enum Size {
        CHEST("5x3x4"),
        UPRIGHT("3x3x5"),
        DRAWER("2x2x4");
        private String actual;
        Size(String s) {
            this.actual = s;
        }
        String dimensions() { return this.actual; }
    }*/
public static final String[] Size = {"5x3x4","3x3x5","2x2x4"};
    /**
     * Whether or not the freezer door is open.
     */
    public boolean open = false;

    private int temp;

    
    private final boolean electric;
    private final boolean waterFlow;

    /**
     * Constructs a Freezer of the given type.
     * @param s freezer size
     * @param electric whether or not the freezer uses electricity
     * @param water whether or not the
     */
    public Freezer( boolean electric, boolean water) {
        
        this.temp = 1;
        this.electric = electric;
        this.waterFlow = water;
    }

    /**
     * Return the dimensions of this Freezer in feet as a
     * {@code <width>x<depth>x<height>} String value.
     * @return dimensions in feet
     */
@Override
    public String [] size() { 
        return Size;
    }

    /**
     * Whether or not this freezer requires an electrical hookup.
     * @return true if the freezer uses electricity
     */
    public boolean needsElectricity() { 
        return this.electric; 
    }

    /**
     * Whether or not this freezer requires a water-line hookup.
     * @return true if the freezer uses water
     */
    public boolean needsWater() { return this.waterFlow; }

    /**
     * Get the current temperature setting
     * @return a unitless setting number, from 0 to 5
     */
    public int getTemperature() {
        return this.temp;
    }

    /**
     * Change the freezer temperature setting.
     * @param t temperature setting, from 0 to 5
     */
    public void setTemperature(int t) {
        if (t <= 0) {
            this.temp = 0;
        } else if (t >= 5) {
            this.temp = 5;
        } else {
            this.temp = t;
        }
    }
    public String toString(){
        String fr;
        fr = " Size of freezer is: " + Size + "," + " Freezer is electric: "
                + this.electric + "," + " Freezer needs water: " + this.waterFlow;
        
        
        
        return fr;
        
    }
}