/*
CS112 Final project
Title: Val Menu Revamped
Authors: Mia Jung (BO), Vanesa Farooq (VF), Hailin Kim (HK)
*/
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
public class ValMenu {
    static Scanner cin = new Scanner(System.in); // a global scanner.
    static String restrictionList = ""; //a global variable for user's dietary pref/res
    public static void main (String[] args){
        System.out.println("Welcome to our code.");
        char option;
        char day;
        String diet = ""; //dietary preferences
        ArrayList<Food> F = new ArrayList<>();

        do{
            System.out.println("\nMenu: ");
            System.out.print("\n1. Program Description" +
                    " \n2. Authors" +
                    " \n3. Enter your dietary preferences/restrictions" +
                    " \n4. Start building your meal plan" +
                    "\n0. Exit program."+
                    "\n\nEnter an option (1-3, 0 to quit): ");
            option = cin.next().charAt(0); // returns the character at index 0.
            cin.nextLine();
            switch(option){
                case '1':
                    System.out.println("This program was designed for CS112 final project." +
                            "The program's database is based on Val menu scraped from AC Nutrition website." +
                            "It allows users to choose Breakfast/Lunch/Dinner menu based on their dietary preferences and restrictions.");
                    break;
                case '2':
                    System.out.println("This program was designed by Amherst College sophomores, Mia Jung, Vanesa Farooq, and Angelica Kim.");
                    break;
                case '3':
                    diet = diet();
                    break;
                case '4':
                    day(F);
                    menu(diet, F);
                    break;
                case '0': System.out.println("\n** You entered option 0 to exit. Good bye! **"); break;
                default: System.out.println("\n** Input error! Enter a valid input, please! **");
            }
        } while (option != '0');
    }//main
    static void day(ArrayList<Food> F){
        System.out.println("1. Monday\n2. Tuesday\n3. Wednesday\n4. Thursday\n5. Friday\n6. Saturday\n7. Sunday");
        System.out.print("Choose day of the week: ");
        char op = cin.next().charAt(0);
        cin.nextLine();
        read(op, F);
    }
    static void menu(String diet, ArrayList<Food> F){
        char option;
        ArrayList<Food> Result = new ArrayList<>();
        do{
            System.out.println("1. Breakfast\n2. Lunch\n3. Dinner");
            System.out.print("Enter your option(1-3): ");
            option = cin.next().charAt(0);
            switch(option){
                case '1':
                    for(Food f: F){
                        if(f.type.equals("Breakfast"))
                            Result.add(f); //creates a list of breakfast foods
                    }
                    exclude(Result, diet);
                    break;
                case '2':
                    for(Food f: F){
                        if(f.type.equals("Lunch"))
                            Result.add(f); //creates a list of luncb foods
                    }
                    exclude(Result, diet);
                    break;
                case '3':
                    for(Food f: F){
                        if(f.type.equals("Dinner"))
                            Result.add(f); //creates a list of dinner foods
                    }
                    exclude(Result, diet);
                    break;
                case '0':
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input");
            }

        } while(option != '0');
    }//menu
    static void exclude(ArrayList<Food> F, String diet){
        ArrayList<Food> Possible = new ArrayList<>();
        //to be added; pseudocode done
    }

    static String diet(){
        System.out.println("1. Vegetarian\n2. Vegan\n3. Pescatarian\n4. Halal\n" +
                "5. Dairy-free\n6. Gluten-free\n7. Egg-free\n8. Keto\n9. Enter your own restrictions list");
        System.out.print("Choose your dietary preferences/restrictions (enter all numbers that apply to you separated by space):");
        String option = cin.nextLine();
        cin.nextLine();

        String c = "";
        if(option.contains("9"))
            c = customDiet();
        return option + c; //fix
    }//diet
    static String customDiet(){
        System.out.println("1. Fish\n2. Shellfish\n3. Tree nuts\n4. Peanuts\n" +
                "5. Gluten/Wheat\n6. Soybean\n7. Sesame\n8. Alcohol\n9. Coconut");
        System.out.print("Enter all numbers that apply to you separated by space: ");
        String option = cin.nextLine();
        cin.nextLine();
        return option;
    }//customDiet
    static void read(char day, ArrayList<Food> F){
        Scanner fin = null;
        try{
            //read from a dataset for certain day based on the user's input for day of the week
            fin = new Scanner(new File("day" + day + ".txt"));
//            fin = new Scanner(new File("day" + day + "breakfast.txt"));
//            fin = new Scanner(new File("day" + day + "lunch.txt"));
//            fin = new Scanner(new File("day" + day + "dinner.txt"));
        } catch(Exception ex){
            System.out.println(ex);
            System.exit(1);
        }

        while(fin.hasNext()){
            String m = fin.nextLine();
//            System.out.println(m); //for debugging
            String[] M = m.split(",");
            String[] ing = M[3].split("/");
            ArrayList<String> Ing = new ArrayList<>(Arrays.asList(ing));
//            System.out.println(Arrays.toString(ing));
            Food c = new Food(M[0], M[1], M[2], Ing);
            F.add(c);
        }
        fin.close();
    }//read
}//class
////////////////////////////
class Food{
    String name;
    String type;
    String calories;
    ArrayList<String> ingredients; //change to set
    Food(String name, String type, String calories, ArrayList<String> ingredients){
        this.name = name;
        this.type = type;
        this.calories = calories;
        this.ingredients = ingredients;
    }//constructor
    public String toString(){
        return name + " " + calories;
    }
}

