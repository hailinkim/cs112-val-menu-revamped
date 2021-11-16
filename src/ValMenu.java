/*
CS112 Final project
Title: Val Menu Revamped
Authors: Mia Jung (BO), Vanesa Farooq (VF), Hailin Kim (HK)
*/
import java.io.File;
import java.util.*;
public class ValMenu {
    static Scanner cin = new Scanner(System.in); // a global scanner.
    public static void main (String[] args){
        System.out.println("Welcome to our code.");
        char option;
        String d = ""; //dietary preferences

        ArrayList<Food> B = new ArrayList<>();
        readBreakfast(B);
        ArrayList<Food> L = new ArrayList<>();
        readLunch(L);
        ArrayList<Food> D = new ArrayList<>();
        readDinner(D);

        do{
            System.out.println("\nMenu: ");
            System.out.print("\n1. Program Description" +
                    " \n2. Authors" +
                    " \n3. Enter your dietary preferences/restrictions" +
                    " \n4. Start building your menu" +
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
                    d = diet();
                    break;
                case '4':
                    menu(d);
                    break;
                case '0': System.out.println("\n** You entered option 0 to exit. Good bye! **"); break;
                default: System.out.println("\n** Input error! Enter a valid input, please! **");
            }
        } while (option != '0');
    }//main
    static void menu(String d){
        System.out.println("1. Breakfast\n2. Lunch\n3. Dinner");
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
        return option + c;
    }//diet
    static String customDiet(){
        System.out.println("1. Fish\n2. Shellfish\n3. Tree nuts\n4. Peanuts\n" +
                "5. Gluten/Wheat\n6. Soybean\n7. Sesame\n8. Alcohol\n9. Coconut");
        System.out.print("Enter all numbers that apply to you separated by space: ");
        String option = cin.nextLine();
        cin.nextLine();
        return option;
    }//customDiet
    static void readBreakfast(ArrayList<Food> F){
        Scanner fin = null;
        try{
            fin = new Scanner(new File("breakfast.txt"));
        } catch(Exception ex){ //"Exception" is a comprehensive term for all the exceptions
            System.out.println(ex);
            System.exit(1);
        }

        while(fin.hasNext()){
            String m = fin.nextLine();
//            System.out.println(m);
            String[] M = m.split(",");
            String[] ing = M[2].split("/");
//            System.out.println(Arrays.toString(ing));
            Food c = new Food(M[0], M[1], ing);
            F.add(c);
        }
        fin.close();
    }//readBreakfast
    static void readLunch(ArrayList<Food> F){
        Scanner fin = null;
        try{
            fin = new Scanner(new File("lunch.txt"));
        } catch(Exception ex){ //"Exception" is a comprehensive term for all the exceptions
            System.out.println(ex);
            System.exit(1);
        }

        while(fin.hasNext()){
            String m = fin.nextLine();
//            System.out.println(m);
            String[] M = m.split(",");
            String[] ing = M[2].split("/");
//            System.out.println(Arrays.toString(ing));
            Food c = new Food(M[0], M[1], ing);
            F.add(c);
        }
        fin.close();
    }//readLunch
    static void readDinner(ArrayList<Food> F){
        Scanner fin = null;
        try{
            fin = new Scanner(new File("dinner.txt"));
        } catch(Exception ex){ //"Exception" is a comprehensive term for all the exceptions
            System.out.println(ex);
            System.exit(1);
        }

        while(fin.hasNext()){
            String m = fin.nextLine();
//            System.out.println(m);
            String[] M = m.split(",");
            String[] ing = M[2].split("/");
//            System.out.println(Arrays.toString(ing));
            Food c = new Food(M[0], M[1], ing);
            F.add(c);
        }
        fin.close();
    }//readDinner
}//class
////////////////////////////
class Food{
    String name;
    String calories;
    String[] ingredients;
    Food(String name, String calories, String[] ingredients){
        this.name = name;
        this.calories = calories;
        this.ingredients = ingredients;
    }//constructor
    public String toString(){
        return name + " " + calories;
    }
}

