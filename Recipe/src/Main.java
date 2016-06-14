
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Gavin Christie
 */
public class Main {

    /**
     * Jordan'S To Do List: Editing recipes, Removing steps/ingredients
     *
     * DON'T FORGET TO PUT YOUR NAME ON ANYTHING YOU CREATE
     *
     * File IO - Adding recipes, reading recipes, editing recipes Adding - Ask
     * the user for the amount first (decimal) follow by the ingredient itself
     * (singular) - After a special character has been entered, the user will
     * enter the steps - Each new line is a different step (just like in the
     * text file)
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
        File recipeList = new File("RecipeList.txt");

        System.out.println("Add a");
        char action = k.nextLine().charAt(0);

        while (action != '-') {

            switch (action) {
                case 'a':
                    a.addRecipe(k, recipeList);
                    break;
                case 'b':
                    a.readRecipe(recipeList);
                    break;
                case 'c':
                    a.editRecipe(k, recipeList);
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
