import java.util.Scanner;

/**
 *
 * @author Gavin Christie
 */
public class Main {

    /**
     * File IO - Adding recipes, reading recipes, editing recipes
     *
     * Searching for recipes
     *
     * Generate shopping lists - Recipe scaling
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

    /**
     * Gavin
     *
     * @param k
     * @param a
     */
    public static void getInput(Scanner k, Actions a) {
        System.out.println("Add a");
        char action = k.nextLine().charAt(0);

        while (action != '-') {

            switch (action) {
                case 'a':
                    a.addRecipe();
                    break;
                default:
                    System.out.println("Command not recognized. Please try again.");
                    action = k.nextLine().charAt(0);
            }

            System.out.println("Add a");
            action = k.nextLine().charAt(0);
        }

    }

}
