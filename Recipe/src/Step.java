/**
 * @author Gavin Christie
 * 
 * This class is the template for the steps in a recipe.  It just holds the step
 * as a string. Although it probably could have been done with a simple string in
 * the recipe class, a new step class was used to allow for future changes.
 */
public class Step {
    private String step; // String value for the step
    
    /**
     * Gavin - Constructor to initialize all the variables when a new step is created.
     * 
     * @param s The writing for the step
     */
    public Step(String s) {
        step = s;
    }
    
    /**
     * Gavin - Method used to get the value of step.
     * 
     * @return Returns the value of step as a string
     */
    public String getStep() {
        return step;
    }
    
    /**
     * Gavin - Method used to set the step to a new value.
     * 
     * @param s The new value for step
     */
    public void setStep(String s) {
        step = s;
    }
    
}
