
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    
    PrintWriter pw = null; // Print Wrtier to be used to write to the file

    /**
     * Gavin - Method used to add a new recipe.
     *
     * @param k Scanner used to get input
     * @param recipeList The file storing the recipes
     * @return Returns the newly created recipe
     */
    public Recipe addRecipe(Scanner k, File recipeList) {
        Recipe newR = new Recipe(); // Creating new recipe
        System.out.print("Recipe name: ");
        newR.name = k.nextLine().trim(); // Getting the recipe name
        System.out.print("Category of recipe: ");
        newR.category = k.nextLine().trim(); // Getting the category of the desert (example: dessert, soup, etc)
        addIngredients(newR, k); // Adding ingredients
        addSteps(newR, k); // Adding the steps to the recipe

        System.out.print("Total time (h:mn): ");
        newR.time = k.nextLine().trim(); // Getting the total amount of time to make the recipe
        System.out.print("Number of servings: ");
        newR.servings = Float.parseFloat(k.nextLine()); // Getting the number of servings that recipe makes

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
            Ingredient l = createIngredient(k); // Calling method to create an ingredient
            if (l == null) {
                return; // When no ingredient to add return
            }
            n.ingredients.add(l); // Add the ingredients to the ArrayList

        } while (true); // Continue until the user tells it to stop
    }

    /**
     * Gavin - Method to create an individual ingredient.
     *
     * @param k Scanner to get input
     * @return Returns an ingredient
     */
    public Ingredient createIngredient(Scanner k) {
        System.out.print("Enter ingredient name: ");
        String n = k.nextLine().trim(); // Getting the name of the ingredient
        if (n.equals("%")) {
            return null; // Return nothing when the user enters '%'
        }
        System.out.print("Enter unit of measurement: ");
        String u = k.nextLine().trim(); // Getting the units to measure the ingredients
        System.out.print("Amount: ");
        float a = Float.parseFloat(k.nextLine()); // Getting the amount of the ingredient

        return new Ingredient(u, n, a); // Return the new ingredient
    }

    /**
     * Gavin - Method used to add an individual step.
     *
     * @param k The scanner used
     * @return Returns a new step that can be added to a recipe
     */
    public Step createStep(Scanner k) {
        System.out.print("Enter step to be add: ");
        String temp = k.nextLine().trim(); // Getting the step

        if (temp.equals("%")) {
            return null; // When no more steps return
        }
        
        Step s = new Step(temp); // Getting the step to be entered

        return s; // Returning the new step
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
            step = createStep(k); // Calling method t create a new step

            if (step == null) {
                return; // When there is no more steps to be added just return
            }
            
            n.steps.add(step); // Adding the new step to the ArraylList
        } while (true);
    }

    /**
     * Gavin - Method used to write every recipe stored in an ArrayList to the
     * text file
     *
     * @param n The ArrayList of recipes that need to be written to the file
     * @param recipeList The file that the recipes need to be written to
     */
    public void writer(ArrayList<Recipe> n, File recipeList) {
        int l = n.size(); // Getting the length of the ArrayList
        try { // Try - catch to create the print writer
            PrintWriter writer = new PrintWriter(new FileWriter(recipeList, false));
            writer.close();
        } catch (IOException ex) {
            System.out.print("Problem creating the print writer.");
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < l; i++) {
            writeRecipe(n.get(i), recipeList, i); // Calling method to write everything to the file
        }
    }

    /**
     * Gavin - Method used to write the whole recipe to the file.
     *
     * @param n The recipe being used
     * @param recipeList The file holding all the recipes
     * @param time The number of times that the method has been called
     */
    public void writeRecipe(Recipe n, File recipeList, int time) {
        try { // Try - catch to create the print writer
            pw = new PrintWriter(new FileWriter(recipeList, true));
        } catch (IOException ex) {
            System.out.print("Problem creating the print writer.");
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (time > 0) {
            pw.println("");
        }
        pw.println(n.name); // Printing the name to the file
        pw.println(n.category);
        
        int size = n.ingredients.size(); // Getting the size of the ingredients ArrayList
        for (int i = 0; i < size; i++) { // For loop going through each ingredient
            pw.println(n.ingredients.get(i).toString()); // Writing ingredient and amount to file
        }
        
        pw.println(";;"); // Writing the delimiter
        pw.println(n.servings + " Servings");
        pw.println("Time " + n.time);
        
        size = n.steps.size(); // The number of steps in the recipe

        for (int i = 0; i < size; i++) {
            pw.println(n.steps.get(i).getStep()); // Printing the step to the file
        }
        pw.print("--"); // Writing delimiter between recipes to file

        pw.close(); // Closing the print writer
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

                // the first line is always the name, followed by category
                newRec.name = fileRead.nextLine();
                newRec.category = fileRead.nextLine();
                String temp = "";
                // the ingredients and steps are separated by two semicolons
                while (!temp.equals(";;")) {
                    temp = fileRead.next().trim();
                    if (temp.equals(";;")) {
                    } else {
                        // add the next ingredient, amount, and unit 
                        newRec.ingredients.add(new Ingredient(fileRead.next().trim(),
                                fileRead.nextLine().trim(), Float.parseFloat(temp)));
                    }
                }
                newRec.servings = Float.parseFloat(fileRead.next());
                fileRead.nextLine();
                fileRead.next();
                newRec.time = fileRead.nextLine().trim();
                // recipes are separated by two hyphens
                while (!temp.equals("--")) {
                    temp = fileRead.nextLine().trim();
                    // add every line to a next step 
                    if (temp.equals("--")) {
                    } else {
                        newRec.steps.add(new Step(temp));
                    }
                }
                // add the recipes to the list
                allRecipes.add(newRec);
                // the end of the for loop indicated the file has no more recipes
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }
        // sort the file of recipes
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
        //get the length of the file (each line)
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
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        // ask the user what recipe thy want to scale for
        System.out.println("What recipe do you want to scale?");
        String recForScaling = k.nextLine();
        // seach for the recipe and get its index in the array list
        int recIndex = searchRec(recipe, recForScaling);
        System.out.println("Current servings: " + recipe.get(recIndex).servings + " how many servings would you like to make? ");
        int recScaling = Integer.parseInt(k.nextLine());
        System.out.println("For " + recScaling + " servings of " + recipe.get(recIndex).name + " you will need");
        for (int i = 0; i < recipe.get(recIndex).ingredients.size(); i++) {
            Float scaledAmount = ((recipe.get(recIndex).ingredients.get(i).getA()) * recScaling);
            System.out.print(df.format(scaledAmount));
            System.out.println(" " + recipe.get(recIndex).ingredients.get(i).getU().toString() + " " + recipe.get(recIndex).ingredients.get(i).getI().toString());
        }
        
    }

    /**
     * Jordan
     *
     * A method that allows the user to enter a specific recipe and then edit it
     * (edit/add/remove ingredients or steps)
     *
     * @param l An array list of all the recipes
     * @param k The scanner being used to obtain information from the user
     */
    public Recipe editRecipe(ArrayList<Recipe> l, String n, Scanner k) {
        int a;
        Recipe h = null;
        a = searchRec(l, n); // Finding the recipe in the array list
        if (a != -1) {
            h = l.get(a); // Getting the recipe from the array list
        } else { // If there is a problem finding the recipe
            System.out.println("There was a problem finding that recipe. "
                    + "You will now be returned to the main menu.");
            return null; // Returns to the main menu 
        }
        System.out.println("What would you like to do?\n a- edit steps\n "
                + "b- edit ingredients\n c- remove steps\n d- remove ingredients\n e- return to the main menu"); // Obtains their choice in order to call the required method
        String choice = k.nextLine(); // Obtains the user's choice
        choice.toLowerCase();
        
        switch (choice) { // Switch statement to determine what method to call
            case "a":
                editRecipeSteps(h, k); // Calls method to edit the steps
                break;
            case "b":
                editRecipeIngredients(h, k); // Calls method to edit the ingredient
                break;
            case "c":
                removeRecipeSteps(h, k); // Calls method to remove steps
                break;
            case "d":
                removeRecipeIngredients(h, k); // Calls method to remove ingredients
                break;
            case "e":
                return null; // Returns to the main menu
            default:
                break;
        }
        return h;
    }

    /**
     * Jordan
     *
     * A method to add/edit the steps of a recipe
     *
     * @param n The recipe the user would like to edit
     * @param k The scanner being used to obtain information from the user
     */
    public void editRecipeSteps(Recipe n, Scanner k) {
        String step;
        System.out.print("Which step would you like to edit? Enter 0 to finish editing. "); // Asks the user for input
        int in = Integer.parseInt(k.nextLine()); // Obtains the index of the step from the user
        do { // Does this while the user doesn't want to exit
            if (in >= n.steps.size()) { // When the step is outside the list of steps
                addSteps(n, k); // Calls method to add a new step
            } else if (in < n.steps.size()) { // If the step is inside the list of steps
                System.out.println("This is the orginal step\n" + n.steps.get(in).getStep()
                        + "\nAre you sure you would like to edit this step?"
                        + "\n 1- yes\n 2- no"); // Outputs the original step and askes the user if they want to edit it
                int answer = k.nextInt(); // Obtains the answer from the user
                if (answer == 1) { // When the user would like to edit this step
                    System.out.println("What would you like to change it to?");
                    k.nextLine();
                    String newStep = k.nextLine(); // Obtains the new step from the user
                    newStep.toLowerCase();
                    n.steps.get(in).setStep(newStep); // Replaces the step with what the user has entered
                    System.out.println("This is the new step\n" + n.steps.get(in).getStep());
                } else { // When the user would not like to edit the step
                    System.out.println("You will now be returned to the main menu.");
                    return; // Returns the user to the main menu
                }
            }
            System.out.print("If you would like to edit another step, which one? Enter 0 to finish editing. ");
            in = k.nextInt();
        } while (in != 0);
        return;
    }

    /**
     * Jordan
     *
     * @param n The recipe the user would like to edit
     * @param k The scanner being used to obtain information from the user
     */
    public void editRecipeIngredients(Recipe n, Scanner k) {
        String ingredient;
        System.out.print("Which ingredient would you like to edit? Please enter the index of it. Enter 0 to finish editing. ");
        int in = Integer.parseInt(k.nextLine());
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
     * @param n
     * @param k
     */
    public void removeRecipeSteps(Recipe n, Scanner k) {
        String step;
        System.out.print("Which step would you like to remove? Enter 0 to finish removing. ");
        int in = k.nextInt();
        do {
            if (in < n.steps.size()) {
                System.out.print("Are you sure you want to remove: " + n.steps.get(in) + " from the steps?\n 1- yes\n 2- no\n");
                int answer = k.nextInt();
                if (answer == 1) {
                    System.out.println("You have removed " + n.steps.get(in) + " from the steps.");
                    n.steps.remove(in);
                } else if (answer == 2) {
                    System.out.println("You will now be returned to the main menu.");
                    return;
                }
            } else {
                System.out.println("There was a problem finding that ingredient. ");
            }
            System.out.print("If you would like to remove another step, which one? Enter 0 to finidh removing. ");
            in = k.nextInt();
        } while (in != 0);
    }

    /**
     * Jordan
     *
     * @param n
     * @param k
     */
    public void removeRecipeIngredients(Recipe n, Scanner k) {
        String ingredient;
        System.out.print("Which igredient would you like to remove? Please enter the index of it. Enter 0 to finish removing. ");
        int in = k.nextInt(); // Obtaining the index of the ingredient to be removed
        do { // Does this while the user doesn't want to exit
            if (in < n.ingredients.size()) { // If the ingredient is in the ingredient list
                System.out.print("Are you sure you want to remove: " + n.ingredients.get(in)
                        + " from the ingredient list?\n 1- yes\n 2- no\n"); // Asks the user if they would like to remove the specific ingredient
                int answer = k.nextInt(); // Obtains the user's answer
                if (answer == 1) { // When the user wants to remove the ingredient
                    System.out.println("You have removed: " + n.ingredients.get(in) + " from the ingredient list.");
                    n.ingredients.remove(in); // Removes the ingredient from the list
                } else if (answer == 2) { // When the user doesn't want to remove the ingredient
                    System.out.println("You will now be returned to the main menu");
                    return; // Returns to the main menu
                }
            } else { // If the ingredient is not in the list
                System.out.println("There was a problem finding that ingredient. ");
            }
            System.out.print("If you would like to remove another ingredient, which one? Enter 0 to finish removing. "); // Asks the user if they would like to edit another ingredient
            in = k.nextInt(); // Obtains the choice/ingredient from the user
        } while (in != 0);
        return; // Returns to the main menu
    }

    /**
     * Jordan
     *
     * A method to obtain a list of the ingredients of a certain recipe
     *
     * @param n An array list of all the recipes
     * @param k The scanner being used to obtain information from the user
     */
    public void groceryList(ArrayList<Recipe> n, Scanner k) {
        System.out.println("Which recipe would you like to obtain a grocery list for?");
        String r = k.nextLine(); // Obtains input from the user
        Recipe h = n.get(searchRec(n, r));
        ingredientScaling(n, k);
        for (int b = 0; b < h.ingredients.size(); b++) {
            System.out.println(h.ingredients.get(b).toString());
        }
    }

    /**
     * Gavin - Method used to search for a recipe by name.
     *
     * @param n The ArrayList of recipes to be searched through
     * @param name The name of the recipe the user is searching for
     * @return Returns the index of the recipe in the ArrayList
     */
    public int searchRec(ArrayList<Recipe> n, String name) {
        int length = n.size(); // Getting the length of the ArrayList
        name = name.toLowerCase(); // Setting all the letters in the name to lower case

        for (int i = 0; i < length; i++) {
            if (n.get(i).name.toLowerCase().trim().equals(name.trim())) { // Settin the name in the ArrayList to lower case to compare to the name given by user
                return i; // If they match return the index
            }
        }
        return -1; // When no recipe the same return -1 since it is not an index
    }

    /**
     * Gavin - Method used to search for an ingredient in an ArrayList of
     * ingredients
     *
     * @param n The ArrayList of ingredients being passed in
     * @param name The name of ingredient that the user is searching for
     * @return Returns the index of the ingredient
     */
    public int searchIng(ArrayList<Ingredient> n, String name) {
        int length = n.size(); // Getting the length of the ArrayList
        name = name.toLowerCase(); // Setting the name ot lower case

        for (int i = 0; i < length; i++) {
            if (n.get(i).getI().toLowerCase().equals(name)) { // Checking if the ingredients name set to lower case matches the search key
                return i; // If they match return the index
            }
        }
        
        return -1; // When no ingredient the same return -1 since it is not an index
    }

    /**
     * Gavin - Method used to search through all of the recipes for an
     * ingredient
     *
     * @param n The ArrayList of recipes to be searched through
     * @param name The key that the user is searching for
     * @return Returns an ArrayList of recipes that contain that ingredient
     */
    public ArrayList<Recipe> searchIngredient(ArrayList<Recipe> n, String name) {
        int length = n.size(); // Getting the size of the ArrayList
        ArrayList<Recipe> recWithIng = new ArrayList<Recipe>(); // Creating new ArrayList
        name = name.toLowerCase(); // Setting the nsearch key to lower case

        for (int i = 0; i < length; i++) {
            ArrayList<Ingredient> current = n.get(i).ingredients; // Getting the ArrayList of ingredients from the current recipe
            Collections.sort(current); // Sorting the ingredients using compareTo
            int li = current.size(); // Getting the number of ingredients in the ArrayList
            for (int j = 0; j < li; j++) {
                if (current.get(j).getI().toLowerCase().equals(name)) { // Comparing lower case ingredient with search key
                    recWithIng.add(n.get(i)); // Adding the ingredient to the ArrayList
                    j = li++; // Setting j to above the length to exit the for loop
                }
            }
        }
        return recWithIng; // Returning the ArrayList of recipes
    }

    /**
     * Gavin - Method used to search through the recipes based on a category
     * entered by the user.
     *
     * @param n The ArrayList to search through
     * @param cat The category to be searched for
     * @return Returns an ArrayList of recipes that are in that category
     */
    public ArrayList<Recipe> searchCategory(ArrayList<Recipe> n, String cat) {
        int length = n.size(); // Getting the length of the ArrayList
        Collections.sort(n, Recipe.CategoryComparator); // Sorting the ArrayList based of category using Comparator
        ArrayList<Recipe> recFromCat = new ArrayList<Recipe>(); // Creating new ArrayList to store recieps
        cat = cat.toLowerCase(); // Setting the category to lower case

        for (int i = 0; i < length; i++) {
            if (n.get(i).category.toLowerCase().equals(cat.toLowerCase())) { // Comparing the category given to recipe in lower case to the search key
                recFromCat.add(n.get(i)); // Adding the recipe to the ArrayList
            }
        }
        
        return recFromCat; // Returning the ArrayList
    }
}
