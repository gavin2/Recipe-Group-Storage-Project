
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Gavin Christie, Jordan Hurley and Carter Ford
 * 
 * This program is to be used as a virtual recipe book. Recipes can be stored to
 * a text file, they can be edited (add ingredients or steps, rewrite previously
 * stored steps, create a shopping list, scale the shopping list which means calculate
 * the amount of each ingredient required to make an input number of servings) and
 * the program has many more features.
 * 
 * The goal for the program was to have a fully working GUI so that the user could
 * selected what they wanted to do using buttons and drop down menus. So key 
 * features that we had hoped to complete with the GUI were: when adding a new 
 * recipe a page of text files would be shown where the user could more easily 
 * write the recipe, use the same screen to edit recipes except all the previously 
 * stored information would be written in the text fields.  We feel as though 
 * a GUI would have made the program more user friendly and less complicated however
 * we sadly ran out of time.
 */
public class Main {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        Actions a = new Actions();
        getInput(k, a);
    }

    /**
     * Method that controls the user input and calls appropriate methods.
     *
     * @param k The scanner used to get user input
     * @param a The actions class instance used
     */
    public static void getInput(Scanner k, Actions a) {
        File recipeList = new File("RecipeList.txt");
        ArrayList<Recipe> allRecipes = a.readRecipe(recipeList); // ArrayList to store all of the recipes, from file

        System.out.println("Add a\nEdit recipe c\nCreate a grocery list d\nSearch s\nSort with category f\nSearching every recipe for an ingredient h\nExit e");
        String action = k.nextLine().toLowerCase();

        while (!action.equals("-")) {

            switch (action) {
                case "a":
                    allRecipes.add(a.addRecipe(k, recipeList));
                    Collections.sort(allRecipes); // Sorts the recipes alphabetically
                    break;
                case "b":
                    a.readRecipe(recipeList); //testing purposes only
                    break;
                case "c":
                    System.out.print("What recipe would you like to edit? ");
                    String n = k.nextLine(); // Accepting the name of the recipe from the user
                    Recipe r = a.editRecipe(allRecipes, n, k); // Editing the recipe
                    if (r == null) {
                    } else { // Writing the new recipe to the array list in place of the other one
                        int b = a.searchRec(allRecipes, n);
                        allRecipes.remove(b); // Removing the original recipe from the array list 
                        allRecipes.add(b, r); // Replacing the recipe
                    }
                    Collections.sort(allRecipes);
                    break;
                case "d":
                    a.groceryList(allRecipes, k); // Create a grocery list
                    break;
                case "e":
                    a.writer(allRecipes, recipeList); // Write to file before closing
                    System.exit(0); // Closing
                case "f":
                    System.out.print("Enter category to search for: ");
                    String cat = k.nextLine().trim(); // Getting the category to be searched for
                    ArrayList<Recipe> category = a.searchCategory(allRecipes, cat); // Creating ArrayList of recipes from the category
                    if (category.isEmpty()) { // If there's no recipe from that category
                        System.out.print("No elements under that category.");
                    }
                    break;
                case "s":
                    System.out.print("Name of recipe: ");
                    String name = k.nextLine().trim(); // Getting search term
                    a.searchRec(allRecipes, name); // Searching for recipe entered
                    break;
                case "q":
                    a.ingredientScaling(allRecipes, k);
                    break;
                case "h":
                    System.out.print("Ingredient: ");
                    String test = k.nextLine(); // Getting ingredient to search for
                    ArrayList<Recipe> withIng = a.searchIngredient(allRecipes, test); // Creating ArrayList to hold all the recipes with that ingredient
                    break;
                default:
                    break;
            }

            System.out.println("\nAdd a\nEdit recipe c\nSearch s\nSort with category f\nExit e");
            action = k.nextLine(); // Getting next action
        }

    }

}
