/**
 *
 * @author Gavin Christie
 */
public class Ingredient {
    private String ingredient;
    private float amount;
    private String unit;
    
    /**
     * Gavin - Constructor used to initialize the ingredient class by giving the
     * variables a value.  This is used in the creation of a new ingredient.
     * 
     * @param u The units of measurement for the ingredient
     * @param i The name of the ingredient
     * @param a The amount of the ingredient used
     */
    public Ingredient(String u, String i, float a) {
        ingredient = i;
        amount = a;
        unit = u;
    }
    
    /**
     * Gavin - Method to get the ingredient name.
     * @return Returns string of the ingredients name
     */
    public String getI() {
        return ingredient;
    }
    
    /**
     * Gavin - Method to set the ingredients name.
     *
     * @param i String containing the name of the ingredient
     */
    public void setI(String i) {
        ingredient = i;
    }
    
    /**
     * Gavin - Method to get the value of the float containing the amount of the
     * ingredient to be used for the recipe.
     * 
     * @return Float containing the amount of the ingredient to be used
     */
    public float getA() {
        return amount;
    }
    
    /**
     * Gavin - Method to set the value of the amount of the ingredient needed to
     * make the recipe.
     * 
     * @param a The new value of amount
     */
    public void setA(float a) {
        amount = a;
    }
    
    /**
     * Gavin - Method to get the unit of measurement for the amount of the ingredient
     * required to make the recipe as a string.
     * 
     * @return The unit as a string
     */
    public String getU() {
        return unit;
    }
    
    /**
     * Gavin - Method to set the unit of measurement for the ingredient.
     * 
     * @param u The new unit to be used
     */
    public void setU(String u) {
        unit = u;
    }

    /**
     * Gavin - Method to return the information about the ingredient as a string.
     * 
     * @return String with the value of the name of the ingredient and stuff
     */
    public String toString() {
        return amount + " " + unit + " " + ingredient;
    }
}
