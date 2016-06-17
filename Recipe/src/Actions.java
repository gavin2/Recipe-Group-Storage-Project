
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
        newR.name = k.nextLine().trim(); // Getting the recipe name
        System.out.print("Category of recipe: ");
        newR.category = k.nextLine().trim();
        addIngredients(newR, k); // Adding ingredients
        addSteps(newR, k); // Adding the steps to the recipe

        System.out.print("Total time (h:mn): ");
        newR.time = k.nextLine().trim();
        System.out.print("Number of servings");
        newR.servings = Float.parseFloat(k.nextLine());

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
     * Gavin -
     *
     * @param n
     * @param recipeList
     */
    public void writer(ArrayList<Recipe> n, File recipeList) {
        int l = n.size();
        for (int i = 0; i < l; i++) {
            writeRecipe(n.get(i), recipeList);
        }
    }

    /**
     * Gavin - Method used to write the whole recipe to the file.
     *
     * @param n The recipe being used
     * @param recipeList The file holding all the recipes
     */
    public void writeRecipe(Recipe n, File recipeList) {
        try { // Try - catch to create the print writer
            pw = new PrintWriter(new FileWriter(recipeList, false));
        } catch (IOException ex) {
            System.out.print("Problem creating the print writer.");
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }

        pw.println("\n" + n.name + "\n" + n.category); // Printing the name to the file

        int size = n.ingredients.size();
        for (int i = 0; i < size; i++) { // For loop going through each ingredient
            pw.println(n.ingredients.get(i).toString()); // Writing ingredient and amount to file
        }

        pw.println(";;\n" + n.servings + " Servings" + "\nTime " + n.time); // Writing the delimiter to the file

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
                newRec.category = fileRead.nextLine();
                String temp = "";
                while (!temp.equals(";;")) {
                    temp = fileRead.next().trim();
                    if (temp.equals(";;")) {
                    } else {
                        newRec.ingredients.add(new Ingredient(fileRead.next().trim(),
                                fileRead.nextLine().trim(), Float.parseFloat(temp)));
                    }
                }
                newRec.servings = Float.parseFloat(fileRead.next());
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
        Collections.sort(allRecipes);
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
     * Carter
     *
     *
     * @param recipe the recipe array list
     * @param k the scanner to be passed on to this method
     */
    public void ingredientScaling(ArrayList<Recipe> recipe, Scanner k) {
        System.out.println("Current servings: " + recipe.get(0) + "\n how many servings would you like to make? ");
        int scalingNum = k.nextInt();

    }

    /**
     * Jordan 
     * 
     * A method that allows the user to enter a specific recipe and then edit it
     * 
     * @param l An array list of all the recipes
     * @param k The scanner being used to obtain information from the user
     */
    public void editRecipe(ArrayList<Recipe> l, Scanner k) {
        int a;
        Recipe h = null;
        System.out.print("What recipe would you like to edit?");
        String n = k.nextLine();
        n.toLowerCase();
        a = searchRec(l, n);
        if (a != -1) {
            h = l.get(a);
        }
        else{
            System.out.println("There was a problem finding that recipe");
            return;
        }

        System.out.println("What would you like to do?\n a- edit steps\n "
                + "b- edit ingredients\n c-remove steps\n d- remove ingredients\n e- return to main menu");
        String choice = k.nextLine();
        choice.toLowerCase();

        switch (choice) {
            case "a":
                editRecipeSteps(l, h, k);
                break;
            case "b":
                editRecipeIngredients(l, h, k);
                break;
            case "c":
                //removeRecipeSteps
                break;
            case "d":
                //removeRecipeIngredients
                break;
            case "e":
                return;
            default:
                break;
        }

    }

    /**
     * Jordan
     * 
     * @param l An array list of all the recipes
     * @param n The recipe the user would like to edit
     * @param k The scanner being used to obtain information from the user
     */
    public void editRecipeSteps(ArrayList<Recipe> l, Recipe n, Scanner k) {
        String step;
        System.out.print("Which step would you like to edit? Enter 0 to finish editing. ");
        int in = k.nextInt();
        do {
            if (in > n.steps.size()) {
                addSteps(n, k);
            } else if (in < n.steps.size()) {
                System.out.println("This is the orginal step\n" + n.steps.get(in).getStep());
                System.out.println("What would you like to change it to?");
                k.nextLine();
                String newStep = k.nextLine();
                newStep.toLowerCase();
                n.steps.get(in).setStep(newStep);
                System.out.println("This is the new step\n" + n.steps.get(in).getStep());
            }
            System.out.print("If you would like to edit another step, which one? Enter 0 to finish editing. ");
            in = k.nextInt();
        } while (in != 0);
        return;
    }

    /**
     * Jordan
     * 
     * @param l An array list of all the recipes
     * @param n The recipe the user would like to edit
     * @param k The scanner being used to obtain information from the user
     */
    public void editRecipeIngredients(ArrayList<Recipe> l, Recipe n, Scanner k) {
        String ingredient;
        System.out.print("Which ingredient would you like to edit? Please enter the index of it. Enter 0 to finish editing. ");
        int in = k.nextInt();
        do {
            if (in > n.ingredients.size()) {
                addIngredients(n, k);
            } else if (in < n.ingredients.size()) {
                System.out.println("This is the original ingredient\n" + n.ingredients.get(in));
                Ingredient i = createIngredient(k);
                n.ingredients.add(in, i);
                System.out.println("This is the new ingredient\n" + n.ingredients.get(in));
            }
            System.out.print("If you would like to edit another ingredient, which one? Enter 0 to finish editing. ");
            in = k.nextInt();
        } while (in != 0);
        return;
    }

    /**
     * Jordan
     * 
     * A method 
     * 
     * @param n An array list of all the recipes
     * @param k The scanner being used to obtain information from the user
     */
    public void groceryList(ArrayList<Recipe> n, Scanner k) {
        System.out.println("Which recipe would you like to obtain a grocery list for?");
        String r = k.nextLine().toLowerCase();
        Recipe h = n.get(searchRec(n, r));
        System.out.println("How many servings would you like to have?");
        int s = k.nextInt();
        //Call carter's thing
        for (int b = 0; b < h.ingredients.size(); b++) {
            System.out.println(h.ingredients.get(b).toString());
        }
    }

    /**
     * Gavin -
     *
     * @param n
     * @param name
     * @return
     */
    public int searchRec(ArrayList<Recipe> n, String name) {
        int length = n.size();
        name = name.toLowerCase();
        
        for (int i = 0; i < length; i++) {
            if (n.get(i).name.toLowerCase().equals(name)) {
                return i;
            }
        }
        return -1; // When no recipe the same return -1 since it is not an index
    }

    /**
     * Gavin -
     *
     * @param n
     * @param name
     * @return
     */
    public int searchIng(ArrayList<Ingredient> n, String name) {
        int length = n.size();
        name = name.toLowerCase();
        
        for (int i = 0; i < length; i++) {
            if (n.get(i).getI().toLowerCase().equals(name)) {
                return i;
            }
        }
        
        return -1; // When no ingredient the same return -1 since it is not an index
    }

    /**
     * Gavin -
     *
     * @param n
     * @param name
     * @return
     */
    public ArrayList<Recipe> searchIngredient(ArrayList<Recipe> n, String name) {
        int length = n.size();
        ArrayList<Recipe> recWithIng = new ArrayList<Recipe>();
        name = name.toLowerCase();

        for (int i = 0; i < length; i++) {
            ArrayList<Ingredient> current = n.get(i).ingredients;
            Collections.sort(current);
            int li = current.size();
            for (int j = 0; j < li; j++) {
                if (current.get(j).getI().toLowerCase().equals(name)) {
                    recWithIng.add(n.get(i));
                    j = li++;
                }
            }
        }
        return recWithIng;
    }
    
    public ArrayList<Recipe> searchCategory(ArrayList<Recipe> n, String cat) {
        int length = n.size();
        Collections.sort(n, Recipe.CategoryComparator);
        ArrayList<Recipe> recFromCat = new ArrayList<Recipe>();
        cat = cat.toLowerCase();
        
        for (int i = 0; i < length; i++) {
            if (n.get(i).category.toLowerCase().equals(cat.toLowerCase())) {
                recFromCat.add(n.get(i));
            }
        }
        
        return recFromCat;
    }
}
