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

        do{
            ArrayList<Food> F = new ArrayList<>();
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
                    System.out.println("This program was designed for CS112 final project.\n" +
                            "The program's database is based on Val menu scraped from AC Nutrition website.\n" +
                            "It allows users to choose Breakfast/Lunch/Dinner menu based on their dietary preferences and restrictions.");
                    break;
                case '2':
                    System.out.println("This program was designed by Amherst College sophomores, Mia Jung, Vanesa Farooq, and Angelica Kim.");
                    break;
                case '3':
                    diet(F);
                    break;
                case '0':
                    System.out.println("\n** You entered option 0 to exit. Good bye! **");
                    break;
                default: System.out.println("\n** Input error! Enter a valid input, please! **");
            }
        } while (option != '0');
    }//main
    static void diet(ArrayList<Food> F){
        String option;
        ArrayList<String> O;
        do{
            System.out.println("1. Vegetarian\n2. Vegan\n3. Pescatarian\n4. Halal\n" +
                    "5. Dairy-free\n6. Gluten-free\n7. Egg-free\n8. Keto\n9. Enter your own restrictions list");
            System.out.print("Choose your dietary preferences/restrictions (enter all numbers that apply to you separated by space): ");
            option = cin.nextLine();
            O = new ArrayList<>(Arrays.asList(option.split(" ")));

            //if O includes invalid input
            if (!(O.contains("1") || O.contains("2") || O.contains("3") || O.contains("4") ||
                    O.contains("5") || O.contains("6") || O.contains("7") || O.contains("8") || O.contains("9"))) {
                System.out.println("Invalid input! Enter 1-9.");
            }
        } while(!(O.contains("1") || O.contains("2") || O.contains("3") || O.contains("4") ||
                O.contains("5") || O.contains("6") || O.contains("7") || O.contains("8") || O.contains("9")));

        String c = "";
        if(option.contains("9"))
            c = customDiet();

        String diet_num = option + " " + c;
        String[] Diet_num = diet_num.split(" ");
//        System.out.println(Arrays.toString(Diet_num)); //for debugging

        System.out.println("Let's start building your meal plan!");
        String s = day(F); //saves the selected day of week, meal type
        read(s, F); //reads data for certain day of week, certain meal type
        ArrayList<Food> Possible = F;
        for (String d:Diet_num){
            exclude(d, Possible);//F now contains all the foods for certain day of week, certain meal type
        }
        System.out.println("Possible options: ");
        for(Food f: Possible){
            System.out.println(f);
//            System.out.println(f.ingredients); //for debugging
        }
        System.out.println();

        addItem(Possible);
        System.out.println();

        char op;
        do {
            System.out.print("Would you like to make your own salad?(y/n) ");
            op = cin.next().charAt(0);
            switch (op){
                case 'y':
                    salad(Diet_num);
                    break;
                case 'n':
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input! Enter y/n.");
            }
        } while (op != 'y' && op != 'n');
    }//diet
    static String customDiet(){
        String option;
        ArrayList<String> O;
        do{
            System.out.println("1. Fish\n2. Shellfish\n3. Tree nuts\n4. Peanuts\n" +
                    "5. Soybean\n6. Sesame\n7. Alcohol\n8. Coconut");
            System.out.print("Enter all numbers that apply to you separated by space: ");
            option = cin.nextLine();
            O = new ArrayList<>(Arrays.asList(option.split(" ")));

            //if O includes invalid input
            if (!(O.contains("1") || O.contains("2") || O.contains("3") || O.contains("4") ||
                    O.contains("5") || O.contains("6") || O.contains("7") || O.contains("8"))) {
                System.out.println("Invalid input! Enter 1-8.");
            }
        } while(!(O.contains("1") || O.contains("2") || O.contains("3") || O.contains("4") ||
                O.contains("5") || O.contains("6") || O.contains("7") || O.contains("8")));

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
        }
        return result;
    }//customDiet
    static String day(ArrayList<Food> F){
        char d;
        do{
            System.out.println("1. Monday\n2. Tuesday\n3. Wednesday\n4. Thursday\n5. Friday\n6. Saturday\n7. Sunday");
            System.out.print("Choose day of the week: ");
            d = cin.next().charAt(0); //choice of day
            cin.nextLine();

            //if O includes invalid input
            if (!(d == '1' || d == '2' || d == '3' || d == '4' || d == '5' || d == '6' || d == '7')) {
                System.out.println("Invalid input! Enter 1-7.");
            }
        } while(!(d == '1' || d == '2' || d == '3' || d == '4' || d == '5' || d == '6' || d == '7'));

        char m;
        do{
            System.out.println("1. Breakfast\n2. Lunch\n3. Dinner");
            System.out.print("Enter your option(1-3): ");
            m = cin.next().charAt(0); //choice of meal type
            cin.nextLine();
            if (!(m == '1' || m == '2' || m == '3')) {
                System.out.println("Invalid input! Enter 1-3.");
            }
        } while(!(m == '1' || m == '2' || m == '3'));

        String meal = "";
        switch(m) {
            case '1':
                meal = "breakfast";
                break;
            case '2':
                meal = "lunch";
                break;
            case '3':
                meal = "dinner";
                break;
        }
        return d + "_" + meal;
    }//day

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
            int cal = Integer.parseInt(M[1]);
            String[] ing = M[3].split("/");
            ArrayList<String> Ing = new ArrayList<>(Arrays.asList(ing));
//            System.out.println(Arrays.toString(ing)); //for debugging
//            System.out.println(M[1]); //for debugging
            Food c = new Food(M[0], cal , M[2], Ing);
//            System.out.println(c);
            F.add(c);
        }
        fin.close();
    }//read

    static void exclude(String d, ArrayList<Food> Possible){ //String d represents each of the dietary preferences selected by the user
//        ArrayList<Food> possible = F;
        ArrayList<Food> bad = new ArrayList<>();
        //excludes meat/poultry/pork/turkey/seafood/fish/eggs/dairy/shellfish
        ArrayList<String> vegan = new ArrayList<>(Arrays.asList("beef", "meat", "steak", "pork", "chicken", "turkey", "duck",
                "fish", "eggs", "egg", "dairy", "shellfish", "shrimp", "prawn", "crab", "lobster", "milk"));
        ArrayList<String> vegetarian = new ArrayList<>(Arrays.asList("steak", "beef", "meat", "pork", "chicken", "chicken breast",
                "chicken thigh", "turkey", "duck", "fish", "shellfish", "shrimp", "prawn", "crab", "lobster"));
        ArrayList<String> pescatarian = new ArrayList<>(Arrays.asList("beef", "meat", "pork", "chicken", "turkey", "duck"));
        String halal = "chicken"; //chicken is the only halal option in Val
        String dairyFree = "dairy";
        String glutenFree = "gluten";
        ArrayList<String> eggFree = new ArrayList<>(Arrays.asList("egg", "eggs"));
        ArrayList<String> keto = new ArrayList<>(Arrays.asList("gluten", "flour", "wheat", "potato", "potatoes", "rice", "pasta"));
        String fishFree = "fish";
        String shellFree = "shellfish";
        ArrayList<String> treeNutsFree = new ArrayList<>(Arrays.asList("tree nut", "tree nuts", "pistachio", "pistachios",
                "chestnut", "chestnuts", "hazelnut", "hazelnuts", "pecan", "pecans", "hickory nut", "hickory nuts",
                "brazil nut", "brazil nuts", "macadamia", "macadamia nut", "macadamia nuts", "cashews", "cashew",
                "cashew nut", "cashew nuts", "walnut", "walnuts", "pine nut", "pine nuts", "almond", "almond nuts"));
        ArrayList<String> peanutFree = new ArrayList<>(Arrays.asList("peanut", "peanuts"));
        ArrayList<String> soyFree = new ArrayList<>(Arrays.asList("soy", "soybean", "soybeans"));
        String sesameFree = "sesame";
        String alcoholFree = "alcohol";
        String coconutFree = "coconut";

        for(Food f:Possible){
            for(String i:f.ingredients){
                switch(d) {
                    case "1": //vegetarian
                        for(String v:vegetarian){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                break;
                            }
                        }
                        break;
                    case "2": //vegan
                        for(String v:vegan){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                break;
                            }
                        }
                        break;
                    case "3": //pescatarian
                        if (pescatarian.contains(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "4": //halal
                        //codes for halal to be added
                        break;
                    case "5": //dairy-Free
                        if (dairyFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "6": //gluten-Free
                        if (glutenFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "7": //egg-Free
                        if (eggFree.contains(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "8": //keto
                        if (keto.contains(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "9": //move on to custom options
                        break;
                    case "10": //fish-free
                        if (fishFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "11": //shellfish-free
                        if (shellFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "12": //tree nuts-free
                        if (treeNutsFree.contains(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "13": //peanut-free
                        if (peanutFree.contains(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "14": //soy-free
                        if (soyFree.contains(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "15": //sesame-free
                        if (sesameFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "16": //alcohol-free
                        if (alcoholFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "17": //coconut-free
                        if (coconutFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                }//switch
            }//inner for loop
        }//outer for loop
        Possible.removeAll(bad);
    }//exclude
    static void addItem(ArrayList<Food> Possible) {
        ArrayList<String> F = new ArrayList<>(); //arraylist of names of possible foods
        for (Food p : Possible) {
            F.add(p.name);
        }

        boolean inputError;
        ArrayList<String> Result;
        do {
            inputError = false;
            System.out.print("Enter the items you would like to add from the possible options, separated by commas: ");
            String option = cin.nextLine();
            String[] Op = option.split(",");
            Result = new ArrayList<>(Arrays.asList(Op));
            for (String r : Result) {
                if (!F.contains(r)) {
                    inputError = true;
                    System.out.println("Invalid input! There is no such food in the possible options.");
                }
            }
        } while (inputError);

        System.out.println("Items added!");
        System.out.println(Result);
    }//addItem
    static void salad(String[] Diet_num){

    }//salad
}//class
////////////////////////////
class Food{
    String name;
    int calories;
    String serving;
    ArrayList<String> ingredients; //change to set
    Food(String name, int calories, String serving, ArrayList<String> ingredients){
        this.name = name;
        this.calories = calories;
        this.serving = serving;
        this.ingredients = ingredients;
    }//constructor
    public String toString(){
        return name + " " + calories + "cal " + serving;
    }
}

