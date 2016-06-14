/**
 *
 * @author Gavin Christie
 */
public class Step {
    private String step;
    
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
