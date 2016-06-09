
import java.util.Scanner;

/**
 *
 * @author Gavin Christie
 */
public class Main {

    /**
     * File IO
     *  - Adding recipes, reading recipes, editing recipes
     * 
     * Searching for recipes
     * 
     * Generate shopping lists
     *  - Recipe scaling
     * 
     * GUI - Last Please
     * 
     */
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        Actions a = new Actions();
        getInput(k, a);
    }
    
    public static void getInput(Scanner k, Actions a) {
        
        System.out.println("Add a");
        String action = Character.toString(k.nextLine().charAt(0));
        
    }
    
}
