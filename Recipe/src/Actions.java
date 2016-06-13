
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public void addRecipe(Scanner k, File recipeList) {
        Recipe newR = new Recipe(); // Creating new recipe
        System.out.print("Recipe name: ");
        newR.name = k.nextLine(); // Getting the recipe name
        addIngredients(newR, k); // Adding ingredients
        addSteps(newR, k); // Adding the steps to the recipe
        writeRecipe(newR, recipeList); // Writing the recipe to the file

    }

    /**
     * Gavin - Method used to add an ingredient to the recipe.
     * 
     * @param n The recipe that the ingredient is being added to
     * @param k The scanner used to get the input
     */
    public void addIngredients(Recipe n, Scanner k) {
        String ingredient;
        float amount;
        System.out.println("Enter % as a new ingredient to end list.");
        do {
            System.out.print("Enter ingredient name: ");
            ingredient = k.nextLine().trim(); // Getting the name of the recipe
            if (ingredient.equals("%")) {
                return; // When no more ingredients to be entered return
            }
            System.out.print("Enter amount (numeric only): ");
            amount = Float.parseFloat(k.nextLine().trim()); // Add the amount of the ingredient
            n.ingredients.add(ingredient); // Add the ingredient to the array list
            n.amount.add(amount); // Add the amount to the array list

        } while (!ingredient.equals("%"));
    }

    /**
     * Gavin - Method to add set to the recipe.
     * 
     * @param n The recipe being added to
     * @param k Scanner used to enter information
     */
    public void addSteps(Recipe n, Scanner k) {
        String step;
        System.out.println("Enter % as a new step to end list.");
        do {
            System.out.print("Enter step to be add: ");
            step = k.nextLine().trim(); // Getting the step to be entered
            if (step.equals("%")) {
                return; // When no more steps return
            }
            n.steps.add(step);
        } while (!step.equals("%"));
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

        pw.println("\n" + n.name);

        
        pw.println("\n" + n.name); // Printing the name tp the file
       
        int sizeI = n.amount.size();
        for (int i = 0; i < sizeI; i++) { // For loop going through each ingredient
            pw.println(n.amount.get(i) + " " + n.ingredients.get(i)); // Writing ingredient and amount to file
        }

        pw.println(";;"); // Writing the delimiter to the file
        
        int sizeS = n.steps.size(); // The number of steps in the recipe

        for (int i = 0; i < sizeS; i++) {
            pw.println(n.steps.get(i)); // Printing the step to the file
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
    public void readRecipe(File recipeList) {
        Recipe newRec = new Recipe();
        try {
            // create a scanner for the file
            Scanner fileRead = new Scanner(recipeList);
            int fileLength = fileLength(fileRead, recipeList);

            // for loop (to go through the entire file)
            for (int i = 0; i < fileLength; i++) {
                //every run of the for loop is a new recipe

                // the first line is always the name
                newRec.name = fileRead.nextLine();
                String temp = "";
                while (!temp.equals(";;")) {
                    temp = fileRead.next().trim();
                    if (temp.equals(";;")) {
                    } else {
                        newRec.amount.add(Float.parseFloat(temp));
                        newRec.ingredients.add(fileRead.nextLine().trim());
                    }
                    // add the next Float
                    // add the next string
                }
                temp = fileRead.nextLine().trim();
                while (!temp.equals("--")) {
                    temp = fileRead.nextLine().trim();
                    // add every line to a next step 
                    if (temp.equals("--")) {
                    } else {
                        newRec.steps.add(temp);
                    }
                }
                // the end of the for loop indicated the file has no more recipes
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carter
     *
     * @param fileRead the scanner for the file
     * @param file the file to be read from
     * @return an integer, representing how many recipes are in the file
     * (recipes are separated by two hyphens "--")
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
    public void editRecipe(Scanner k, File recipeList){
        System.out.println("What recipe would you like to edit?");
        String n = k.nextLine();
        System.out.println("What would you like to do?\n a- edit steps\n b- edit ingredients\n c-remove steps\n d- remove ingredients");
        String choice = k.nextLine();
        choice.toLowerCase();
        if(choice.equals("a")){
            //editRecipeSteps(recipeList, n, k);
        }
        else if(choice.equals("b")){
            //editRecipeIngredients(recipeList, n, k);
        }
    }

    /**
     * Jordan
     * 
     * @param recipeList
     * @param n
     * @param k 
     */
    public void editRecipeSteps(File recipeList, Recipe n, Scanner k) {
        readRecipe(recipeList);
        String step;
        System.out.println("Which step would you like to edit? Enter 0 to finish editing.");
        int in = k.nextInt();
        do {
            if (in > n.steps.size()) {
                addSteps(n, k);
            } else if (in < n.steps.size()) {
                System.out.println("This is the orginal step/n" + n.steps.get(in));
                String newStep = k.nextLine();
                n.steps.add(in, newStep);
            }
            System.out.println("If you would like to edit another step, which one? Enter 0 to finish editing.");
        } while (in != 0);

    }

    /**
     * Jordan
     * 
     * @param recipeList
     * @param n
     * @param k 
     */
    public void editRecipeIngredients(File recipeList, Recipe n, Scanner k) {
        readRecipe(recipeList);
        String ingredient;
        System.out.println("Which ingredient would you like to edit? Please enter the index of it. Enter 0 to finish editing.");
        int in = k.nextInt();
        do{
            if(in > n.ingredients.size()){
                addIngredients(n,k);
            }
            else if(in < n.ingredients.size()){
                System.out.println("This is the original ingredient/n" + n.ingredients.get(in));
                String newIngredient = k.nextLine();
                n.ingredients.add(in, newIngredient);
            }
        }while(in != 0);
    }
    

}
