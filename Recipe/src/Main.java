
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Gavin Christie
 */
public class Main {

    /**
     * Jordan'S To Do List: Editing recipes, Removing steps/ingredients
     *
     *
     * Gavin: - When a new recipe is created, add it in alphabetical order to
     * the ArrayList - Create method to uses writeRecipe to write the whole
     * ArrayList to the file when the program is closed - Searching: ingredient
     * - Sort ArrayList of Recipes
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
        ArrayList<Recipe> allRecipes = a.readRecipe(recipeList); // ArrayList to store all of the recipes, from file, 

        System.out.println("Add a\nRead in recipes b\nEdit recipe c\nSearch s");
        char action = k.nextLine().charAt(0);

        while (action != '-') {

            switch (action) {
                case 'a':
                    allRecipes.add(a.addRecipe(k, recipeList));
                    Collections.sort(allRecipes);
                    break;
                case 'b':
                    a.readRecipe(recipeList); //testing purposes only
                    break;
                case 'c':
                    a.editRecipe(k, recipeList);
                    break;
                case 's':
                    System.out.print("Name of recipe: ");
                    String name = k.nextLine();
                    a.searchRec(allRecipes, name);
                default:
                    break;
            }

            System.out.println("Add a\nRead in recipes b\nEdit recipe c\nSearch s");
            action = k.nextLine().charAt(0);
        }

    }

}
