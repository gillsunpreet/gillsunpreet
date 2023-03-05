package SinghSunpreet_lab2;

import java.time.LocalDate;
import java.time.Period;

/**
 * A horse, of course.
 * @author {@code Maxwell Terpstra <C0380979@intra.camosun.bc.ca>}
 */
public class Horse implements commonThings {
    /**
     * The name of this horse.
     */
    public String name;

    private final String breed;
    private final LocalDate born;

    public static final String[] PONY_STALL_SIZE = { "200", "100", "200" };
    public static final String[] LARGE_STALL_SIZE = { "400", "300", "400" };
    public static final String[] STANDARD_STALL_SIZE = { "400", "200", "300" };

    /**
     * Create a record for a horse that was just born.
     * @param breed horse breed (in lower case)
     */
    public Horse(String breed) {
        this(breed, null, LocalDate.now());
    }

    /**
     * Create a record for a Horse that was born some time ago.
     * @param breed horse breed (in lower case)
     * @param name name of the horse
     * @param born date the horse was born
     */
    public Horse(String breed, String name, LocalDate born) {
        this.breed = breed;
        this.name = name;
        this.born = born;
    }

    /**
     * Whether or not the given breed is described as a "pony"
     * @param breed horse breed (in lower case)
     * @return true if the breed is classified as a pony
     */
    public static boolean isPony(String breed) {
        switch (breed) {
            case "shetland":
            case "guoxia":
            case "danish sport":
                return true;
            default:
                return false;
        }
    }

    /**
     * Get the age (in years) of this horse.
     * @return number of years old
     */
    public int getAge() {
        return Period.between(LocalDate.now(), born).getYears();
    }

    /**
     * Get the minimum size of stall needed to stable this horse.
     * @return a 3-dimensional array of measurements in centimeters
     */
    @Override
    public String[] size() {
        if (Horse.isPony(this.breed)) {
            return PONY_STALL_SIZE;
        } else {
            switch (breed) {
                case "arabian":
                    return LARGE_STALL_SIZE;
                default:
                    return STANDARD_STALL_SIZE;
            }
        }
    }

    /**
     * Horses do not necessarily need electricity in their stables.
     * @return false
     */
    public boolean needsElectricity() { return false; }

    /**
     * Since horses are living creatures, they all need a source of water to survive.
     * @return true
     */
    public boolean needsWater() { return true; }

    /**
     * Whether or not this breed requires a heated stable.
     * @return true if the this horse breed is known to have poor health
     *     outcomes in unheated stables.
     */
    public boolean needsHeat() {
        return Horse.isPony(this.breed);
    }
    public String toString(){
        String str;
        str = "breed is: " + this.breed + "," + " Name of horse is: " + "," +
                this.name + "," + " Date of Birth is: " + this.born;
        
        return str;
    }
}