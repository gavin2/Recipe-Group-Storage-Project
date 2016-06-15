
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavin Christie
 */
public class Actions {

    PrintWriter pw = null;

    /**
     * Gavin - Method used to add a new recipe.
     *
     * @param k Scanner used to get input
     * @param recipeList The file storing the recipes
     */
    public Recipe addRecipe(Scanner k, File recipeList) {
        Recipe newR = new Recipe(); // Creating new recipe
        System.out.print("Recipe name: ");
        newR.name = k.nextLine(); // Getting the recipe name
        addIngredients(newR, k); // Adding ingredients
        addSteps(newR, k); // Adding the steps to the recipe

        return newR;
    }

    /**
     * Gavin - Method used to add an ingredient to the recipe.
     *
     * @param n The recipe that the ingredient is being added to
     * @param k The scanner used to get the input
     */
    public void addIngredients(Recipe n, Scanner k) {
        System.out.println("Enter % as a new ingredient to end list.");
        do {
            Ingredient l = createIngredient(k);
            if (l == null) {
                return;
            }
            n.ingredients.add(l);

        } while (true);
    }

    /**
     * Gavin - Method to create an individual ingredient.
     *
     * @param k Scanner to get input
     * @return Returns an ingredient
     */
    public Ingredient createIngredient(Scanner k) {
        System.out.print("Enter ingredient name: ");
        String n = k.nextLine().trim();
        if (n.equals("%")) {
            return null;
        }
        System.out.print("Enter unit of measurement: ");
        String u = k.nextLine().trim();
        System.out.print("Amount: ");
        float a = Float.parseFloat(k.nextLine());

        return new Ingredient(u, n, a);
    }

    /**
     * Gavin - Method used to add an individual step.
     *
     * @param k The scanner used
     * @return Returns a new step that can be added to a recipe
     */
    public Step createStep(Scanner k) {
        System.out.print("Enter step to be add: ");
        String temp = k.nextLine().trim();

        if (temp.equals("%")) {
            return null; // When no more steps return
        }

        Step s = new Step(temp); // Getting the step to be entered

        return s;
    }

    /**
     * Gavin - Method to add set to the recipe.
     *
     * @param n The recipe being added to
     * @param k Scanner used to enter information
     */
    public void addSteps(Recipe n, Scanner k) {
        Step step;
        System.out.println("Enter % as a new step to end list.");
        do {
            step = createStep(k);

            if (step == null) {
                return;
            }

            n.steps.add(step);
        } while (true);
    }

    /**
     * Gavin - Method used to write the whole recipe to the file.
     *
     * @param n The recipe being used
     * @param recipeList The file holding all the recipes
     */
    public void writeRecipe(Recipe n, File recipeList) {
        try { // Try - catch to create the print writer
            pw = new PrintWriter(new FileWriter(recipeList, true));
        } catch (IOException ex) {
            System.out.print("Problem creating the print writer.");
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }

        pw.println("\n" + n.name); // Printing the name tp the file

        int size = n.ingredients.size();
        for (int i = 0; i < size; i++) { // For loop going through each ingredient
            pw.println(n.ingredients.get(i).toString()); // Writing ingredient and amount to file
        }

        pw.println(";;"); // Writing the delimiter to the file

        size = n.steps.size(); // The number of steps in the recipe

        for (int i = 0; i < size; i++) {
            pw.println(n.steps.get(i).getStep()); // Printing the step to the file
        }
        pw.println("--");

        pw.close();
    }

    /**
     * Carter
     *
     * Read everything necessary from the file
     *
     * @param recipeList The file to be passed on to this method for reading
     */
    public ArrayList<Recipe> readRecipe(File recipeList) {

        ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();

        try {
            // create a scanner for the file
            Scanner fileRead = new Scanner(recipeList);
            int fileLength = fileLength(fileRead, recipeList);

            // for loop (to go through the entire file)
            for (int i = 0; i < fileLength; i++) {
                Recipe newRec = new Recipe();
                //every run of the for loop is a new recipe

                // the first line is always the name
                newRec.name = fileRead.nextLine();
                String temp = "";
                while (!temp.equals(";;")) {
                    temp = fileRead.next().trim();
                    if (temp.equals(";;")) {
                    } else {
                        newRec.ingredients.add(new Ingredient(fileRead.next().trim(),
                                fileRead.nextLine().trim(), Float.parseFloat(temp)));
                        /**
                         * Change above needs to be tested Check to see if new
                         * createIngredient and createStep can be use when
                         * reading from the file.
                         */
                    }
                }
                temp = fileRead.nextLine().trim();
                while (!temp.equals("--")) {
                    temp = fileRead.nextLine().trim();
                    // add every line to a next step 
                    if (temp.equals("--")) {
                    } else {
                        newRec.steps.add(new Step(temp));
                    }
                }
                allRecipes.add(newRec);
                // the end of the for loop indicated the file has no more recipes
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allRecipes;
    }

    /**
     * Carter
     *
     * @param fileRead the scanner for the file (so we don't have like, a
     * billion scanners)
     * @param file the file to be read from
     * @return an integer, representing how many recipes are in the file
     * (recipes are separated by two hyphens "--") "-1" means there is an error.
     */
    public int fileLength(Scanner fileRead, File file) {
        //get the length of the file (each string)
        int theCount = 0;
        try {
            fileRead = new Scanner(file).useDelimiter("--");
            while (fileRead.hasNext()) {
                fileRead.next();
                theCount++;
            }
            return theCount;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     * Jordan
     *
     * @param k
     * @param recipeList
     */
    public void editRecipe(ArrayList<Recipe> l, Scanner k) {
        System.out.println("What recipe would you like to edit?");
        String n = k.nextLine();
        Recipe h = searchRec(l, n);
        System.out.println("What would you like to do?\n a- edit steps\n b- edit ingredients\n c-remove steps\n d- remove ingredients");
        String choice = k.nextLine();
        choice.toLowerCase();
        if (choice.equals("a")) {
            editRecipeSteps(h, k);
        } else if (choice.equals("b")) {
            editRecipeIngredients(h, k);
        }
    }

    /**
     * Jordan
     *
     * @param recipeList
     * @param n
     * @param k
     */
    public void editRecipeSteps(Recipe n, Scanner k) {
        String step;
        System.out.println("Which step would you like to edit? Enter 0 to finish editing.");
        int in = k.nextInt();
        do {
            if (in > n.steps.size()) {
                addSteps(n, k);
            } else if (in < n.steps.size()) {
                System.out.println("This is the orginal step\n" + n.steps.get(in).getStep());
                System.out.println("What would you like to change it to?");
                k.nextLine();
                String newStep = k.nextLine();
                n.steps.get(in).setStep(newStep);
                System.out.println("This is the new step\n" + n.steps.get(in).getStep());
            }
            System.out.println("If you would like to edit another step, which one? Enter 0 to finish editing.");
            in = k.nextInt();
        } while (in != 0);
        System.exit(0);
    }

    /**
     * Jordan - is the biggest dick!
     *
     * @param recipeList
     * @param n
     * @param k
     */
    public void editRecipeIngredients(Recipe n, Scanner k) {
        String ingredient;
        System.out.println("Which ingredient would you like to edit? Please enter the index of it. Enter 0 to finish editing.");
        int in = k.nextInt();
        do {
            if (in > n.ingredients.size()) {
                addIngredients(n, k);
            } else if (in < n.ingredients.size()) {
                System.out.println("This is the original ingredient\n" + n.ingredients.get(in));
                Ingredient l = createIngredient(k);
                n.ingredients.add(in, l);
            }
        } while (in != 0);
    }

    //public void groceryList(ArrayList<Recipe> n,){
    //}
    /**
     * Gavin -
     *
     * @param n
     * @param name
     * @return
     */
    public Recipe searchRec(ArrayList<Recipe> n, String name) {
        int low = 0, high = n.size(), mid = 0; // Creating required variables

        while (low <= high) { // While the lowest position is equal to or less than the highest position
            mid = (low + high) / 2; // Finding the middle position
            if (n.get(mid).name.compareTo(name) == 0) { // Checking if middle term and word are equal
                return n.get(mid);
            } else if (n.get(mid).name.compareTo(name) > 0) { // Checking if the word is greater than the middle term using compareTo
                high = mid - 1; // Calculating new highest term
            } else { // If it does not meet any other requires, the word must be less than the middle term
                low = mid + 1; // Calculating new lowest term
            }
        }
        return null;
    }

    /**
     * Gavin -
     *
     * @param n
     * @param name
     * @return
     */
//    public Ingredient searchIng(ArrayList<Ingredient> n, String name) {
//        int low = 0, high = n.size(), mid = 0; // Creating required variables
//
//        while (low <= high) { // While the lowest position is equal to or less than the highest position
//            mid = (low + high) / 2; // Finding the middle position
//            if (n.get(mid).name.compareTo(name) == 0) { // Checking if middle term and word are equal
//                return n.get(mid);
//            } else if (n.get(mid).name.compareTo(name) > 0) { // Checking if the word is greater than the middle term using compareTo
//                high = mid - 1; // Calculating new highest term
//            } else { // If it does not meet any other requires, the word must be less than the middle term
//                low = mid + 1; // Calculating new lowest term
//            }
//        }
//        return null;
//    }

    /**
     * Gavin -
     *
     * @param n
     * @param name
     * @return
     */
//    public ArrayList<Recipe> searchIngredient(ArrayList<Recipe> n, String name) {
//        int length = n.size();
//
//        for (int i = 0; i < length; i++) {
//            ArrayList<Ingredient> current = n.get(i).ingredients;
//            Collections.sort(current);
//        }
//
//    }

}
