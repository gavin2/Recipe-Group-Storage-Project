
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Gavin Christie
 */
public class Recipe implements Comparable<Recipe> {

    String name;
    String category;
    float servings;
    String time;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    ArrayList<Step> steps = new ArrayList<Step>();

    @Override
    public int compareTo(Recipe o) {
        return name.compareTo(o.name);
    }

    public static Comparator<Recipe> CategoryComparator = new Comparator<Recipe>() {
        public int compare(Recipe o1, Recipe o2) {
            String category1 = o1.category.toUpperCase();
            String category2 = o2.category.toUpperCase();
            
            return category1.compareTo(category2);
        }
    };
}
