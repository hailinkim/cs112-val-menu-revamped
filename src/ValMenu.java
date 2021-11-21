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
        ArrayList<Food> F = new ArrayList<>();

        do{
            System.out.println("\nMenu: ");
            System.out.print("\n1. Program Description" +
                    " \n2. Authors" +
                    " \n3. Start building your own menu" +
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
                    diet(F);
                    break;
                case '0': System.out.println("\n** You entered option 0 to exit. Good bye! **"); break;
                default: System.out.println("\n** Input error! Enter a valid input, please! **");
            }
        } while (option != '0');
    }//main
    static void diet(ArrayList<Food> F){
        System.out.println("1. Vegetarian\n2. Vegan\n3. Pescatarian\n4. Halal\n" +
                "5. Dairy-free\n6. Gluten-free\n7. Egg-free\n8. Keto\n9. Enter your own restrictions list");
        System.out.print("Choose your dietary preferences/restrictions (enter all numbers that apply to you separated by space):");
        String option = cin.nextLine();

        String c = "";
        if(option.contains("9"))
            c = customDiet();

        String diet_num = option + " " + c;
        String[] Diet_num = diet_num.split(" ");

        System.out.println("Let's start building your meal plan!");
        String s = day(F); //saves the selected day of week, meal type
        read(s, F); //reads data for certain day of week, certain meal type
        ArrayList<Food> Result = F;
        for (String d:Diet_num){
            Result = exclude(d, Result); //F now contains all the foods for certain day of week, certain meal type
        }
        System.out.println("possible: ");
        for(Food f: Result){
            System.out.println(f.name);
        }
    }//diet
    static String customDiet(){
        System.out.println("1. Fish\n2. Shellfish\n3. Tree nuts\n4. Peanuts\n" +
                "5. Gluten/Wheat\n6. Soybean\n7. Sesame\n8. Alcohol\n9. Coconut");
        System.out.print("Enter all numbers that apply to you separated by space: ");
        String option = cin.nextLine();
        cin.nextLine();
        String[] O = option.split(" ");
        String result = "";
        for(String o:O){ //recoding the user input
            if(o.equals("1"))
                result = result + "10";
            if(o.equals("2"))
                result = result + "11 ";
            if(o.equals("3"))
                result = result + "12 ";
            if(o.equals("4"))
                result = result + "13 ";
            if(o.equals("5"))
                result = result + "14 ";
            if(o.equals("6"))
                result = result + "15 ";
            if(o.equals("7"))
                result = result + "16 ";
            if(o.equals("8"))
                result = result + "17 ";
            if(o.equals("9"))
                result = result + "18 ";
        }
        return result;
    }//customDiet
    static String day(ArrayList<Food> F){
        System.out.println("1. Monday\n2. Tuesday\n3. Wednesday\n4. Thursday\n5. Friday\n6. Saturday\n7. Sunday");
        System.out.print("Choose day of the week: ");
        char d = cin.next().charAt(0); //choice of day
        cin.nextLine();

        System.out.println("1. Breakfast\n2. Lunch\n3. Dinner");
        System.out.print("Enter your option(1-3): ");
        char m = cin.next().charAt(0); //choice of meal type
        cin.nextLine();
        String meal = "";
        do{
            switch(m){
                case '1':
                    meal = "breakfast";
                    break;
                case '2':
                    meal = "lunch";
                    break;
                case '3':
                    meal = "dinner";
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } while(m != '1' && m!= '2' && m!= '3');

        return d + "_" + meal;
    }
    static void read(String s, ArrayList<Food> F){
        Scanner fin = null;
        try{
            //read from a dataset for certain day based on the user's input for day of the week
            fin = new Scanner(new File("day" + s + ".txt"));
        } catch(Exception ex){
            System.out.println(ex);
            System.exit(1);
        }

        while(fin.hasNext()){
            String m = fin.nextLine();
//            System.out.println(m); //for debugging
            String[] M = m.split(",");
            String[] ing = M[2].split("/");
            ArrayList<String> Ing = new ArrayList<>(Arrays.asList(ing));
//            System.out.println(Arrays.toString(ing)); //for debugging
            Food c = new Food(M[0], M[1], M[2], Ing);
            F.add(c);
        }
        fin.close();
    }//read

    static ArrayList<Food> exclude(String d, ArrayList<Food> F){ //String d represents each of the dietary preferences selected by the user
//        ArrayList<Food> possible = F;
        ArrayList<Food> bad = new ArrayList<>();
        //excludes meat/poultry/pork/turkey/seafood/fish/eggs/dairy/shellfish
        ArrayList<String> vegan = new ArrayList<>(Arrays.asList("beef", "meat", "pork", "chicken", "turkey", "duck",
                "fish", "eggs", "dairy", "shellfish", "shrimp", "prawn", "crab", "lobster"));
        ArrayList<String> vegetarian = new ArrayList<>(Arrays.asList("beef", "meat", "pork", "chicken", "turkey", "duck",
                "fish", "shellfish", "shrimp", "prawn", "crab", "lobster"));
        ArrayList<String> pescatarian = new ArrayList<>(Arrays.asList("beef", "meat", "pork", "chicken", "turkey", "duck"));
        String halal = "chicken"; //chicken is the only halal option in Val
        String dairyFree = "dairy";
        String glutenFree = "gluten";
        String eggFree = "egg";

        for(Food f:F){
            for(String i:f.ingredients){
                switch(d) {
                    case "1":
                        if (vegetarian.contains(i.toLowerCase())) {
                            bad.add(f);
                            break;
                        }
                        break;
                    case "2":
                        if (vegan.contains(i.toLowerCase())) {
                            bad.add(f);
                            break;
                        }
                        break;
                    default:
                            System.out.println("invalid");
                }
//                if(d.equals("1")){
//                    if (vegetarian.contains(i.toLowerCase())) {
//                        bad.add(f);
//                        break;
//                    }
//                }
            }//inner for loop
        }//outer for loop
        F.removeAll(bad);
        return F;
    }//exclude
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

