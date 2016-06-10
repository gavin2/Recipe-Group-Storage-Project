
import java.io.File;
import java.io.FileNotFoundException;
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

    /**
     * Gavin
     */
    public void addRecipe(Scanner k) {
        Recipe newR = new Recipe();
        System.out.print("Recipe name: ");
        newR.name = k.nextLine();
        addIngredients(newR, k);

    }

    /**
     * Gavin
     */
    public void addIngredients(Recipe n, Scanner k) {
        String ingredient;
        float amount;
        System.out.println("Enter % to end ingredients list.");
        do {
            System.out.print("Enter ingredient name: ");
            ingredient = k.nextLine();
            if (ingredient.equals("%")) {
                return;
            }
            System.out.print("Enter amount: ");
            amount = Float.parseFloat(k.nextLine());
            n.ingredients.add(ingredient);
            n.amount.add(amount);

        } while (ingredient != "%");
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
            // for loop (to go through the entire file)

            // the first line is always the name
            newRec.name = fileRead.nextLine();
            while (!fileRead.equals(";;")) {
                newRec.ingredients.add(fileRead.nextFloat());
                newRec.ingredients.add(fileRead.nextLine());
                // add the next Float
                // add the next string
            }
            while (!fileRead.equals(";;")) {
                // add every line to a next step 
                newRec.steps.add(fileRead.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
// PUSH NEXT TIME 
