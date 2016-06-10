
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
     * Gavin
     */
    public void addRecipe(Scanner k, File recipeList) {
        Recipe newR = new Recipe();
        System.out.print("Recipe name: ");
        newR.name = k.nextLine();
        addIngredients(newR, k);
        addSteps(newR, k);
        writeRecipe(newR, recipeList);

    }

    /**
     * Gavin
     */
    public void addIngredients(Recipe n, Scanner k) {
        String ingredient;
        float amount;
        System.out.println("Enter % as a new ingredient to end list.");
        do {
            System.out.print("Enter ingredient name: ");
            ingredient = k.nextLine().trim();
            if (ingredient.equals("%")) {
                return;
            }
            System.out.print("Enter amount: ");
            amount = Float.parseFloat(k.nextLine().trim());
            n.ingredients.add(ingredient);
            n.amount.add(amount);

        } while (!ingredient.equals("%"));
    }
    
    /**
     * Gavin
     * 
     * @param n
     * @param k 
     */
    public void addSteps(Recipe n, Scanner k) {
        String step;
        System.out.println("Enter % as a new step to end list.");
        do {
            System.out.print("Enter step to be add: ");
            step = k.nextLine().trim();
            if (step.equals("%")) {
                return;
            }
            n.steps.add(step);
        } while(!step.equals("%"));
    }
    
    /**
     * Gavin
     */
    public void writeRecipe(Recipe n, File recipeList) {
        try {
            pw = new PrintWriter(new FileWriter(recipeList, true));
        } catch (IOException ex) {
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pw.println("\n" + n.name);
        
        int sizeI = n.amount.size();
        for (int i = 0; i < sizeI; i++) {
            pw.println(n.amount.get(i) + " " + n.ingredients.get(i));
        }
        pw.println(";;");
        
        int sizeS = n.steps.size();
        for (int i = 0; i < sizeS; i++) {
            pw.println(n.steps.get(i));
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
     * (recipes are sparated by two hyphens "--")
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

}
