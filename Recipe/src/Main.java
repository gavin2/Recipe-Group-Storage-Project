
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
     * Jordan'S To Do List: Removing steps/ingredients
     *
     *
     * Gavin: - Ask them what sub category their recipe belongs in before they
     * add it --> soup entree desert appetizer
     * Make sure everything entered is in lowercase 
     *
     * DON'T FORGET TO PUT YOUR NAME ON ANYTHING YOU CREATE
     *
     * Searching for recipes based on categories
     *
     * Recipe scaling
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
     *
     * @param k
     * @param a
     */
    public static void getInput(Scanner k, Actions a) {
        File recipeList = new File("RecipeList.txt");
        ArrayList<Recipe> allRecipes = a.readRecipe(recipeList); // ArrayList to store all of the recipes, from file
        Collections.sort(allRecipes);

        System.out.println("Add a\nEdit recipe c\nExit e\nSearch s");
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
                    a.editRecipe(allRecipes, k);
                    break;
                case 'e':
                    a.writer(allRecipes, recipeList);
                    System.exit(0);
                case 's':
                    System.out.print("Name of recipe: ");
                    String name = k.nextLine();
                    a.searchRec(allRecipes, name);
                    break;
                case 'q':
                    a.ingredientScaling(allRecipes, k);
                default:
                    break;
            }

            System.out.println("Add a\nRead in recipes b\nEdit recipe c\nSearch s");
            action = k.nextLine().charAt(0);
        }

    }

}
