
import java.util.ArrayList;

/**
 *
 * @author Gavin Christie
 */
public class Recipe implements Comparable<Recipe> {
    String name;
    float servings;
    String time;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    ArrayList<Step> steps = new ArrayList<Step>();

    @Override
    public int compareTo(Recipe o) {
        return name.compareTo(o.name);
    }
}
