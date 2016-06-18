
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Gavin Christie
 * 
 * This is the template for all the recipes stored and used by the program. It 
 * holds the name, category, number of servings, total time, ingredients list, and 
 * steps list of a recipe.  It also has methods used for comparing recipes.
 */
public class Recipe implements Comparable<Recipe> {

    String name; // Name of recipe
    String category; // Category example: dessert, soup...
    float servings; // Number of servings the recipe makes
    String time; // Total time
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(); // ArrayList of ingredients
    ArrayList<Step> steps = new ArrayList<Step>(); // ArrayList of steps

    /**
     * Gavin - Method used to sort the recipes alphabetically
     * 
     * @param o Recipe being compared to this recipe
     * @return Returns an integer value based on the results of the comparison,
     * less than zero is before in the alphabet, greater than zero is after, and 
     * zero means the same value
     */
    @Override
    public int compareTo(Recipe o) {
        return name.compareTo(o.name);
    }

    /**
     * Gavin - Method used to sort the recipes based on alphabetical order of the 
     * category of the recipes.
     */
    public static Comparator<Recipe> CategoryComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe o1, Recipe o2) {
            String category1 = o1.category.toLowerCase();
            String category2 = o2.category.toLowerCase();
            
            return category1.compareTo(category2);
        }
    };
}
