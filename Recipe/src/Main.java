
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
     * Gavin: - Check the ingredients compareTo and Collections.sort() for errors
     * 
     * Download current text file
     * 
     * DON'T FORGET TO PUT YOUR NAME ON ANYTHING YOU CREATE
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

        System.out.println("Add a\nEdit recipe c\nSearch s\nSort with category f\nSearching every recipe for an ingredient h\nExit e");
        String action = k.nextLine().toLowerCase();

        while (!action.equals("-")) {

            switch (action) {
                case "a":
                    allRecipes.add(a.addRecipe(k, recipeList));
                    Collections.sort(allRecipes);
                    break;
                case "b":
                    a.readRecipe(recipeList); //testing purposes only
                    break;
                case "c":
                    a.editRecipe(allRecipes, k);
                    Collections.sort(allRecipes);
                    break;
                case "e":
                    a.writer(allRecipes, recipeList);
                    System.exit(0);
                case "f":
                    System.out.print("Enter category to search for: ");
                    String cat = k.nextLine().trim();
                    ArrayList<Recipe> category = a.searchCategory(allRecipes, cat);
                    break;
                case "s":
                    System.out.print("Name of recipe: ");
                    String name = k.nextLine().trim();
                    a.searchRec(allRecipes, name);
                    break;
                case "q":
                    a.ingredientScaling(allRecipes, k);
                    break;
                case "h":
                    System.out.print("Ingredient: ");
                    String test = k.nextLine();
                    ArrayList<Recipe> withIng = a.searchIngredient(allRecipes, test);
                    break;
                default:
                    break;
            }

            System.out.println("\nAdd a\nEdit recipe c\nSearch s\nSort with category f\nExit e");
            action = k.nextLine();
        }

    }

}
