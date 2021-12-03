/*
CS112 Final project
File: HABOVFFinalProject/HABOVFValMenu.java
Title: Val Menu and Meal Planner
Authors: Mia Jung (BO), Vanesa Farooq (VF), Hailin Kim (HK)
*/
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;

public class HABOVFValMenu {
    static Scanner cin = new Scanner(System.in); // a global scanner.
    static String restrictionList = ""; //a global variable for user's dietary pref/res
    static int calorie_intake = 0; // calories user plans to intake today.
    static ArrayList<Food> breakfast = new ArrayList<>();
    static ArrayList<Food> lunch = new ArrayList<>();
    static ArrayList<Food> dinner = new ArrayList<>();
    static String bmr_result = "";

    public static void main (String[] args){
        System.out.println("Welcome to our code.");
        char option;
        do{
            ArrayList<Food> F = new ArrayList<>();
            System.out.println("\nMenu: ");
            System.out.print("\n1. Program Description" +
                    " \n2. Authors" +
                    "\n3. Calculate your BMR & set your caloric intake plan" +
                    " \n4. Start building your own menu / Add another meal" +
                    "\n5. View Val tips" +
                    "\n6. View your final meal plan in a file" +
                    "\n0. Exit program."+
                    "\n\nEnter an option (1-6, 0 to quit): ");
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
                    bmr();
                    break;
                case '4':
                    diet(F);
                    break;
                case '5':
                    randomTip();
                    break;
                case '6':
                    getFinalFile();
                case '0':
                    System.out.println("\n** You entered option 0 to exit. Good bye! **");
                    break;
                default: System.out.println("\n** Input error! Enter a valid input, please! **");
            }
        } while (option != '0');
    }//main
    static void bmr(){
        System.out.println("\nWelcome to our bmr & daily recommended caloric intake calculator. " +
                "\nWe'll first be asking for your age, height, weight, and activity level.");

        ArrayList<Integer> info = getPhysicalInfo();
        // if metric, info[0]= age, info[1]= weight(kg), info[2] = height(cm).
        // if US units, info[0]= age, info[1]= weight(lbs), info[2]= height(ft), info[3]= height(in).

        int MJE_f_bmr = MJE_f(info); // gets BMR estimate using user info & the Mifflin St. Jeor Equation "for females".
        int MJE_m_bmr = MJE_m(info); // gets BMR estimate using user info & the Mifflin St. Jeor Equation "for males".
        int HBE_f_bmr = HBE_f(info); // gets BMR estimate using user info & the Revised Harris Benedict Equation "for females".
        int HBE_m_bmr = HBE_m(info); // gets BMR estimate using user info & the Revised Harris Benedict Equation "for males".

        bmr_result = "\nAccording to your physical information, your BMR estimate is around " + MJE_f_bmr + " to " + MJE_m_bmr +
                " calories (using the Mifflin St Jeor Equation)," +
                "\nor around " + HBE_f_bmr + " to " + HBE_m_bmr + " calories (using the Harris Benedict Equation).";
        System.out.println(bmr_result);

        System.out.println("\nBMR = the calories you need per day to maintain your body weight, " +
                "assuming you remain at rest the whole day." +
                "\nTo estimate the calories you actually need to maintain your body weight, physical activity level must be taken into account.");
        double activityFactor = getActivityFactor(); // finish implementing.

        System.out.println("\nAccording to your activity level, you need around " + (int)(MJE_f_bmr * activityFactor) +
                " to " + (int)(MJE_m_bmr * activityFactor) +  " calories (according to the Mifflin St.Jeor equation), " +
                "\nor about " + (int)(HBE_f_bmr * activityFactor) + " to " + (int)(HBE_m_bmr * activityFactor) +
                " calories (according to the Harris Benedict equation) per day in order to maintain your current weight.");

        System.out.println("\n     (NOTE: BMR & caloric need calculations are only rough estimates that depend " +
                "a wide range of variables.\n     If you're pregnant, breast-feeding, are a competitive athlete, " +
                "or have a metabolic disease such as diabetes, your caloric needs can be vastly different.\n     " +
                "If you eat fewer calories than your caloric needs and burn more calories through physical activity, " +
                "you lose weight. \n     It is inadvisable to cut back on calorie intake by more than 1,000 calories per day.)");

        System.out.print("\nNow that you know the estimate of your daily caloric needs, " +
                "\nenter the amount of calories you plan to intake today. " +
                "\n(you can enter 0 to skip the calorie counting function). " +
                "\nEnter here: ");

        calorie_intake = cin.nextInt();
        cin.nextLine();
        System.out.println("Your calorie intake input has been saved!");
    }//mbr
    static double getActivityFactor(){
        double activityFactor = 0.0;
        char op;
        do{
            System.out.println("\nList of activity levels:");
            System.out.print("1. Sedentary (little or no exercise, desk job)." +
                    " \n2. Lightly active (light exercise/ sports 1-3 days/week)." +
                    "\n3. Moderately active (moderate exercise/ sports 6-7 days/week)." +
                    "\n4. Very active (hard exercise every day, or exercising twice/day)." +
                    "\n5. Extra active (hard exercise 2 or more times per day, or training for marathon, or triathlon, etc. "+
                    "\n\nEnter an integer for your activity level: ");
            op = cin.next().charAt(0); // returns the character at index 0.
            cin.nextLine();
            switch(op){
                case '1':
                    activityFactor = 1.2; // sedentary
                    break;
                case '2':
                    activityFactor = 1.375; // lightly active
                    break;
                case '3':
                    activityFactor = 1.55; // moderately active
                    break;
                case '4':
                    activityFactor = 1.725; // Very Active
                    break;
                case '5':
                    activityFactor = 1.9; // Extra Active
                    break;
                default: System.out.println("\n** Input error! Enter a valid input, please! **");
            }
        } while (op!='1' && op!='2' && op!='3' && op!='4' && op!='5');
        return activityFactor;
    }//getActivityFactor
    static int HBE_f(ArrayList<Integer> info){
        // Revised Harris Benedict Equation for female BMR estimation.
        // female BMR = 447.593 + (9.247 × weight in kg) + (3.098 × height in cm) - (4.330 × age in years)

        double HBE_f_bmr = 0;

        if (info.size()==3){ // if metric units,
            // simply plug & chug into the equation.
            HBE_f_bmr = 447.593 + (9.247 * info.get(1)) + (3.098 * info.get(2)) - (4.330 * info.get(0));
        }
        else if (info.size()==4){ // if US units,
            //convert lbs to kg (1 lbs= 45359237 kg)
            // + convert feet & inches into cm (1 ft = 30.48 cm, and 1 in = 2.54 cm).
            HBE_f_bmr = 447.593 + (9.247 * info.get(1)*0.45359237) + ( 3.098 * (info.get(2)*30.48 + info.get(3)*2.54) ) - (4.330 * info.get(0));
        }

        return (int)(HBE_f_bmr);
    }//HBE_f
    static int HBE_m(ArrayList<Integer> info){
        // Revised Harris Benedict Equation for male BMR estimation.
        // male BMR = 88.362 + (13.397 × weight in kg) + (4.799 × height in cm) - (5.677 × age in years)

        double HBE_m_bmr = 0;

        if (info.size()==3){ // if metric units,
            // simply plug & chug into the equation.
            HBE_m_bmr = 88.362 + (13.397 * info.get(1)) + (4.799 * info.get(2)) - (5.677 * info.get(0));
        }
        else if (info.size()==4){ // if US units,
            //convert lbs to kg (1 lbs= 0.45359237 kg)
            // + convert feet & inches into cm (1 ft = 30.48 cm, and 1 in = 2.54 cm).
            HBE_m_bmr = 88.362 + (13.397 * info.get(1)*0.45359237) + ( 4.799 * (info.get(2)*30.48 + info.get(3)*2.54) ) - (5.677 * info.get(0));
        }

        return (int)(HBE_m_bmr);
    }//HBE_m
    static int MJE_f(ArrayList<Integer> info){
        // Mifflin St. Jeor Equation for female BMR estimation.
        // female BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) - 161
        double MJE_f_bmr = 0;
        if (info.size()==3){ // if metric units,
            // simply plug & chug into the equation.
            MJE_f_bmr = (10 * info.get(1)) + (6.25 * info.get(2)) - (5 * info.get(0)) - 161;
        }
        else if (info.size()==4){ // if US units,
            // convert lbs to kg (1 lbs= 0.45359237 kg)
            // + convert feet & inches into cm (1 ft = 30.48 cm, and 1 in = 2.54 cm).
            MJE_f_bmr = (10 * info.get(1)*0.45359237) + (6.25 * (info.get(2)*30.48 + info.get(3)*2.54)) - (5 * info.get(0)) - 161;
        }
        return (int)(MJE_f_bmr);
    }//MJE_f
    static int MJE_m(ArrayList<Integer> info){
        // Mifflin St. Jeor Equation for male BMR estimation.
        // male BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) + 5
        double MJE_m_bmr = 0;
        if (info.size()==3){ // if metric units,
            // simply plug & chug into the equation.
            MJE_m_bmr = (10 * info.get(1)) + (6.25 * info.get(2)) - (5 * info.get(0)) + 5;
        }
        else if (info.size()==4){ // if US units,
            // convert lbs to kg (1 lbs= 0.45359237 kg)
            // + convert feet & inches into cm (1 ft = 30.48 cm, and 1 in = 2.54 cm).
            MJE_m_bmr = (10 * info.get(1)*0.45359237) + (6.25 * (info.get(2)*30.48 + info.get(3)*2.54)) - (5 * info.get(0)) +5;
        }
        return (int)(MJE_m_bmr);
    }//MJE_m
    static ArrayList<Integer> getPhysicalInfo(){
        ArrayList<Integer> physicalInfo = new ArrayList<>(Arrays.asList());
        char op;
        do{
            System.out.print("Enter 1 to use metric units and 2 to use US units: ");
            op = cin.next().charAt(0);
            if (op=='1'){
                System.out.print("\nEnter an integer for your age (in yrs): ");
                physicalInfo.add(cin.nextInt());
                System.out.print("Enter an integer for your weight (in kg): ");
                physicalInfo.add(cin.nextInt());
                System.out.print("Enter an integer for your height (in cm): ");
                physicalInfo.add(cin.nextInt());
                cin.nextLine();
                System.out.println();
            }
            else if (op=='2'){
                System.out.print("\nEnter an integer for your age (in yrs): ");
                physicalInfo.add(cin.nextInt());
                System.out.print("Enter an integer for your weight (in pounds): ");
                physicalInfo.add(cin.nextInt());
                System.out.print("Enter 2 integers for your height (in ft and inches, separated by a space (e.g. 5 11 indicates 5 ft 11 inches): ");
                physicalInfo.add(cin.nextInt());
                physicalInfo.add(cin.nextInt());
                cin.nextLine();
                System.out.println();
            }
            else {
                System.out.println("Invalid Input. Try again!");
            }
        } while(op!='1'&& op!='2');
        return physicalInfo;
    }//getPhysicalInfo
    static void diet(ArrayList<Food> F){
        String option;
        ArrayList<String> O;
        ArrayList<String> Num = new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7","8","9","10"));
        boolean inputCorrect;
        do{
            inputCorrect = true;
            System.out.println("1. Vegetarian\n2. Vegan\n3. Pescatarian\n4. Halal\n" +
                    "5. Dairy-free\n6. Gluten-free\n7. Egg-free\n8. Keto\n9. No restrictions\n10. Enter your own restrictions list");
            System.out.print("Choose your dietary preferences/restrictions (enter all numbers that apply to you separated by commas without any spaces): ");
            option = cin.nextLine();
            O = new ArrayList<>(Arrays.asList(option.split(",")));

            //check for invalid input
            for(String op:O){
                if(!isNumeric(op)) { //non-numeric input
                    inputCorrect = false;
                    System.out.println("Invalid input! Enter 1-10.");
                    break;
                }
                else if(!Num.contains(op)) { //invalid numeric input
                    inputCorrect = false;
                    System.out.println("Invalid input! Enter 1-10.");
                    break;
                }
            }
        } while(!inputCorrect);

        String c = "";
        if(option.contains("10"))
            c = customDiet();

        String diet_num = option + "," + c;
        String[] Diet_num = diet_num.split(",");
//        System.out.println(Arrays.toString(Diet_num)); //for debugging

        System.out.println("Let's start building your meal plan!");
        String s = day() + "";
        String m = mealType();
        s = s + "_" + m; //saves the selected day of week, meal type
        read(s, F); //reads data for certain day of week, certain meal type
        ArrayList<Food> Possible = F;
        for (String d:Diet_num){
            exclude(d, Possible);//F now contains all the foods for certain day of week, certain meal type
        }
        System.out.println();
        System.out.println("Possible options: ");
        for(Food f: Possible){
            System.out.println("\t" + f);
//            System.out.println(f.ingredients); //for debugging
        }
        System.out.println();

        ArrayList<Food> Result = addItem(Possible); //non-salad foods
        for(Food f:Result){
            System.out.println("\t" + f);
        }
        System.out.println();

        char op;
        do {
            System.out.print("Would you like to make your own salad?(y/n) ");
            op = cin.next().charAt(0);
            cin.nextLine();
            switch (op){
                case 'y':
                    Result.addAll(salad(Diet_num)); //add salad ingredients chosen by user to the final result
                    break;
                case 'n':
                    break;
                default:
                    System.out.println("Invalid input! Enter y/n.");
            }
        } while (op != 'y' && op != 'n');

        System.out.println("Total items added: ");
        for(Food f:Result){
            System.out.println("\t" + f);
        }
        System.out.println();
        checkExceed(Result, Possible, Diet_num);

        if (m.equals("breakfast")){
            breakfast = Result;
        }
        else if (m.equals("lunch")){
            lunch = Result;
        }
        else{
            dinner = Result;
        }
    }//diet
    static String customDiet(){
        String option;
        ArrayList<String> O;
        ArrayList<String> Num = new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7","8"));
        boolean inputCorrect;
        do{
            inputCorrect = true;
            System.out.println("1. Fish\n2. Shellfish\n3. Tree nuts\n4. Peanuts\n" +
                    "5. Soybean\n6. Sesame\n7. Alcohol\n8. Coconut");
            System.out.print("Enter all numbers that apply to you separated by commas without any spaces: ");
            option = cin.nextLine();
            O = new ArrayList<>(Arrays.asList(option.split(",")));

            //check for invalid input
            for(String op:O){
                if(!isNumeric(op)) { //non-numeric input
                    inputCorrect = false;
                    System.out.println("Invalid input! Enter 1-8.");
                    break;
                }
                else if(!Num.contains(op)) { //invalid numeric input
                    inputCorrect = false;
                    System.out.println("Invalid input! Enter 1-8.");
                    break;
                }
            }
        } while(!inputCorrect);


        String result = "";
        for(String o:O){ //recoding the user input
            if(o.equals("1"))
                result = result + "11,";
            if(o.equals("2"))
                result = result + "12,";
            if(o.equals("3"))
                result = result + "13,";
            if(o.equals("4"))
                result = result + "14,";
            if(o.equals("5"))
                result = result + "15,";
            if(o.equals("6"))
                result = result + "16,";
            if(o.equals("7"))
                result = result + "17,";
            if(o.equals("8"))
                result = result + "18,";
        }
        return result;
    }//customDiet
    static char day(){
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
        return d;
    }//day
    static String mealType(){
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
        return meal;
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
            double cal = Double.parseDouble(M[1]);
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
        ArrayList<Food> bad = new ArrayList<>();
        //excludes meat/poultry/pork/turkey/seafood/fish/eggs/dairy/shellfish
        ArrayList<String> vegan = new ArrayList<>(Arrays.asList("beef", "meat", "steak", "pork", "lamb", "chicken", "turkey", "duck",
                "fish", "eggs", "egg", "dairy", "shellfish", "shrimp", "prawn", "crab", "lobster", "milk"));
        ArrayList<String> vegetarian = new ArrayList<>(Arrays.asList("steak", "beef", "meat", "pork", "lamb", "chicken", "chicken breast",
                "chicken thigh", "turkey", "duck", "fish", "shellfish", "shrimp", "prawn", "crab", "lobster"));
        ArrayList<String> pescatarian = new ArrayList<>(Arrays.asList("beef", "meat", "pork", "chicken", "lamb", "turkey", "duck"));
        ArrayList<String> halal = new ArrayList<>(Arrays.asList("yogurt", "gelatin", "beef", "meat", "steak", "pork", "lamb", "chicken", "turkey", "duck", "alcohol"));
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
                        for(String v:pescatarian){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                break;
                            }
                        }
                        break;
                    case "4": //halal
                        for(String v:halal){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                if(f.name.equals("Grilled Chicken Breast"))
                                    bad.remove(f);
                                break;
                            }
                        }
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
                        for(String v:eggFree){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                break;
                            }
                        }
                        break;
                    case "8": //keto
                        for(String v:keto){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                break;
                            }
                        }
                        break;
                    case "9": //no restrictions
                        break;
                    case "10": //move on to custom options
                        break;
                    case "11": //fish-free
                        if (fishFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "12": //shellfish-free
                        if (shellFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "13": //tree nuts-free
                        for(String v:treeNutsFree){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                break;
                            }
                        }
                        break;
                    case "14": //peanut-free
                        for(String v:peanutFree){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                break;
                            }
                        }
                        break;
                    case "15": //soy-free
                        for(String v:soyFree){
                            if (i.toLowerCase().contains(v)){
                                if (!bad.contains(f))
                                    bad.add(f);
                                break;
                            }
                        }
                        break;
                    case "16": //sesame-free
                        if (sesameFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "17": //alcohol-free
                        if (alcoholFree.equals(i.toLowerCase())) {
                            if (!bad.contains(f))
                                bad.add(f);
                            break;
                        }
                        break;
                    case "18": //coconut-free
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
    static ArrayList<Food> addItem(ArrayList<Food> Possible) {
        ArrayList<Food> Result = new ArrayList<>();

        ArrayList<String> F = new ArrayList<>(); //arraylist of names of possible foods
        for (Food p : Possible) {
            F.add(p.name);
        }

        //check for invalid input
        boolean inputCorrect;
        ArrayList<String> Op = new ArrayList<>();
        do {
            inputCorrect = true;
            System.out.println("Enter each item and its portion size in numbers(integers or decimals) you would like to add, separated by commas without any spaces after each line break(q to quit): ");
            System.out.print("Your menu: ");
            String option = "";
            while(!option.equals("q")){
                option = cin.nextLine();
                if(!option.equals("q")){
                    String[] tmp = option.split(",");
                    if (!F.contains(tmp[0])|!isNumeric(tmp[1])) {
                        inputCorrect = false;
                        System.out.println("Invalid input! There is no such food in the possible options.");
                        break;
                    }
                    else{
                        Op.addAll(Arrays.asList(tmp));
                    }
                }

            }
        } while (!inputCorrect);

        //add selected salad bar menu to the result
        for(int i=0; i<Op.size(); i+=2){
            for(Food f:Possible){
                if(f.name.equals(Op.get(i))){
                    double portion = Double.parseDouble(Op.get(i+1)); //user's portion size
                    Pattern p = Pattern.compile("\\d+.\\d+|\\d+");
                    Matcher m = p.matcher(f.serving);
                    if(m.find()) {
                        double serving = Double.parseDouble(m.group(0));
                        f.calories = f.calories * (portion/serving);
                        String tmp = f.serving.replace("(","");
                        f.serving = "(" + portion + tmp.replaceAll("[0-9]|\\.","");
                    }
                    else{
                        f.calories = f.calories * portion;
                        f.serving = "(" + portion + " " + f.serving.replace("(","");
                    }
                    Result.add(f);
                }
            }
        }
        System.out.println("Items added!");
        return Result;
    }//addItem
    static boolean isNumeric(String strNum) { //check if portion size input is numeric
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }//isNumeric
    static ArrayList<Food> salad(String[] Diet_num){
        ArrayList<Food> S = new ArrayList<>();//arraylist of salad ingredients
        readSalad(S);
        ArrayList<Food> Possible = S;
        for (String d:Diet_num){
            exclude(d, Possible);//F now contains all the foods for certain day of week, certain meal type
        }

        char op;
        ArrayList<Food> Result = new ArrayList<>();
        do{
            System.out.println("1. Salad\n2. Lettuce\n3. Veggies\n4. Toppings\n5. Fruits\n6. Condiments");
            System.out.print("Choose a type of salad ingredients you would like to add to your salad(1-6, 0 to quit): ");
            op = cin.next().charAt(0);
            cin.nextLine();
            System.out.println();
            ArrayList<Food> tmp = new ArrayList<>();
            switch(op){
                case '1':
                    System.out.println("Possible salad options for you: ");
                    for(Food f:Possible){
                        if (((Salad) f).type.equals("salad")) {
                            tmp.add(f);
                            System.out.println("\t" + f);
                        }
                    }
                    System.out.println();
                    addSalad(tmp, Result);
                    break;
                case '2':
                    System.out.println("Possible lettuce options for you: ");
                    for(Food f:Possible){
                        if (((Salad) f).type.equals("lettuce")) {
                            tmp.add(f);
                            System.out.println("\t" + f);
                        }
                    }
                    System.out.println();
                    addSalad(tmp, Result);
                    break;
                case '3':
                    System.out.println("Possible veggie options for you: ");
                    for(Food f:Possible){
                        if (((Salad) f).type.equals("vegetable")) {
                            tmp.add(f);
                            System.out.println("\t" + f);
                        }
                    }
                    System.out.println();
                    addSalad(tmp, Result);
                    break;
                case '4':
                    System.out.println("Possible toppings for you: ");
                    for(Food f:Possible){
                        if (((Salad) f).type.equals("topping")) {
                            tmp.add(f);
                            System.out.println("\t" + f);
                        }
                    }
                    System.out.println();
                    addSalad(tmp, Result);
                    break;
                case '5':
                    System.out.println("Possible fruits for you: ");
                    for(Food f:Possible){
                        if (((Salad) f).type.equals("fruit")) {
                            tmp.add(f);
                            System.out.println("\t" + f);
                        }
                    }
                    System.out.println();
                    addSalad(tmp, Result);
                    break;
                case '6':
                    System.out.println("Possible condiments for you: ");
                    for(Food f:Possible){
                        if (((Salad) f).type.equals("condiment")) {
                            tmp.add(f);
                            System.out.println("\t" + f);
                        }
                    }
                    System.out.println();
                    addSalad(tmp, Result);
                    break;
                case '0':
                    break;
                default:
                    System.out.println("Invalid input! Enter 1-6.");
            }
        } while(op != '0');
        return Result;
    }//salad
    static void addSalad(ArrayList<Food> Possible, ArrayList<Food> Result) {
        ArrayList<String> F = new ArrayList<>(); //arraylist of names of possible foods
        for (Food p : Possible) {
            F.add(p.name);
        }
    //check for invalid input
        boolean inputCorrect;
        ArrayList<String> Op = new ArrayList<>(); //arraylist of user's choice
        do {
            inputCorrect = true;
            System.out.println("Enter each item and its portion size in numbers(integers or decimals) you would like to add, separated by commas without any spaces after each line break(q to quit): ");
            System.out.print("Your menu: ");
            String option = "";
            while(!option.equals("q")){
                option = cin.nextLine();
                if(!option.equals("q")){
                    String[] tmp = option.split(",");
                    if (!F.contains(tmp[0])|!isNumeric(tmp[1])) {
                        inputCorrect = false;
                        System.out.println("Invalid input! There is no such food in the possible options.");
                        break;
                    }
                    else{
                        Op.addAll(Arrays.asList(tmp));
                    }
                }

            }
        } while (!inputCorrect);

        //add selected salad bar menu to the result
        for(int i=0; i<Op.size(); i+=2){
            for(Food f:Possible){
                if(f.name.equals(Op.get(i))){
                    double portion = Double.parseDouble(Op.get(i+1)); //user's portion size
                    Pattern p = Pattern.compile("\\d+.\\d+|\\d+");
                    Matcher m = p.matcher(f.serving);
                    if(m.find()) {
                        double serving = Double.parseDouble(m.group(0));
                        f.calories = f.calories * (portion/serving);
                        f.serving = "(" + portion + f.serving.replaceAll("[0-9]|\\.","") + ")";
                    }
                    else{
                        f.calories = f.calories * portion;
                        f.serving = "(" + portion + " " + f.serving + ")";
                    }
                    Result.add(f);
                }
            }
        }

        System.out.println("Items added!");
        for(Food f:Result){
            System.out.println("\t" + f);
        }
    }//addSalad
    static void readSalad(ArrayList<Food> S){
        Scanner fin = null;
        try{
            //read from a dataset for certain day based on the user's input for day of the week
            fin = new Scanner(new File("salad.txt"));
        } catch(Exception ex){
            System.out.println(ex);
            System.exit(1);
        }

        while(fin.hasNext()){
            String m = fin.nextLine();
//            System.out.println(m); //for debugging
            String[] M = m.split(",");
            double cal = Double.parseDouble(M[2]);
            String[] ing = M[4].split("/");
            ArrayList<String> Ing = new ArrayList<>(Arrays.asList(ing));
//            System.out.println(Arrays.toString(ing)); //for debugging
//            System.out.println(M[1]); //for debugging
            Salad c = new Salad(M[0], M[1], cal , M[3], Ing);
//            System.out.println(c); //for debugging
            S.add(c);
        }
        fin.close();
    }//readSalad
    static void checkExceed(ArrayList<Food> Result, ArrayList<Food> Possible, String[] Diet_num){
        double totalCal = 0.0;
        double dailyCal = (double)calorie_intake/3;
        for(Food f:Result){
            totalCal += f.calories;
        }

        String pattern = "###.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String totalCalFormat = decimalFormat.format(totalCal);
        String dailyCalFormat = decimalFormat.format(dailyCal);

        System.out.println("Total calories of your menu: " + totalCalFormat + "cal");
        System.out.println("Your daily caloric intake per meal: " + dailyCalFormat + "cal"); //for debugging
        System.out.println();
        if (totalCal > dailyCal) { //when calories greater than needed
            char op;
            do{
                System.out.print("Calories of your menu exceed your caloric intake. Would you like to remove some foods from your menu? (y/n) ");
                op = cin.next().charAt(0);
                cin.nextLine();
                switch(op){
                    case 'y':
                        double excess = totalCal - dailyCal;
                        String excessFormat = decimalFormat.format(excess);
                        System.out.println("Excess calories: " + excessFormat + "cal");
                        removeFood(Result);
                        System.out.println("Your final menu:");
                        for(Food f:Result){
                            System.out.println("\t" + f);
                        }
                        break;
                    case 'n':
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid input! Enter y/n.");
                }
            } while(op != 'y' && op != 'n');

        }
        else if (dailyCal - totalCal > 500) { //when 500+ calories fewer than needed
            char op;
            do{
                System.out.print("You're eating 500+ fewer calories than you need. Would you like to add more food? (y/n) ");
                op = cin.next().charAt(0);
                cin.nextLine();
                switch (op){
                    case 'y':
                        char op2;
                        double deficit;
                        String deficitFormat;
                        do{
                            System.out.println("Add from:\n1. Salad Bar Menu\n2. Daily Menu");
                            System.out.print("Option(1-2)? ");
                            op2 = cin.next().charAt(0);
                            cin.nextLine();
                            System.out.println();
                            switch (op2){
                                case '1':
                                    deficit = dailyCal - totalCal;
                                    deficitFormat = decimalFormat.format(deficit);
                                    System.out.println("Additional calories needed: " + deficitFormat + "cal");
                                    System.out.println();
                                    Result.addAll(salad(Diet_num));
                                    System.out.println("Total items added: ");
                                    for(Food f:Result){
                                        System.out.println("\t" + f);
                                    }
                                    break;
                                case '2':
                                    deficit = dailyCal - totalCal;
                                    deficitFormat = decimalFormat.format(deficit);
                                    System.out.println("Additional calories needed: " + deficitFormat + "cal");
                                    System.out.println();
                                    for(Food f:Possible){
                                        System.out.println(f);
                                    }
                                    System.out.println();
                                    Result.addAll(addItem(Possible));
                                    System.out.println("Total items added: ");
                                    for(Food f:Result){
                                        System.out.println("\t" + f);
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid input. Enter 1-2.");
                            }
                        } while(op2 != '1' && op2 != '2');
                        break;
                    case 'n':
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid input! Enter y/n.");
                }
            } while(op != 'y' && op != 'n');
        }
        else {
            System.out.println("You're eating just the right amount of calories. Goodbye!");
        }
    }//checkExceed
    static void removeFood(ArrayList<Food> Result){
        System.out.println("Your menu:");
        for(Food f:Result){
            System.out.println("\t" + f);
        }
        System.out.println();
        ArrayList<String> F = new ArrayList<>(); //arraylist of names of selected foods
        for (Food r : Result) {
            F.add(r.name);
        }

        boolean inputCorrect;
        ArrayList<String> Op = new ArrayList<>();
        do {
            inputCorrect = true;
            System.out.print("Enter food(s) and portion size you would like to remove: ");
            String option = "";
            while(!option.equals("q")){
                option = cin.nextLine();
                if(!option.equals("q")){
                    String[] tmp = option.split(",");
                    if (!F.contains(tmp[0])|!isNumeric(tmp[1])) {
                        inputCorrect = false;
                        System.out.println("Invalid input! There is no such food in the possible options.");
                        break;
                    }
                    else{
                        Op.addAll(Arrays.asList(tmp));
                    }
                }

            }
        } while (!inputCorrect);

        ArrayList<Food> Removed = new ArrayList<>();
        for(int i=0; i<Op.size(); i+=2){
            for(Food f:Result){
                if(f.name.equals(Op.get(i))){
                    double portion = Double.parseDouble(Op.get(i+1)); //user's portion size
                    Pattern p = Pattern.compile("\\d+.\\d+|\\d+");
                    Matcher m = p.matcher(f.serving);
                    if(m.find()) {
                        double serving = Double.parseDouble(m.group(0));
                        if(portion!=serving){ //when removing the partial portion of food
                            f.calories = f.calories * ((serving-portion)/serving);
                            String tmp = f.serving.replace("(","");
                            f.serving = "(" + (serving-portion) + tmp.replaceAll("[0-9]|\\.","");
                        }
                        else{ //when removing the entire portion
                            Removed.add(f);
                        }
                    }

                }
            }
        }
        Result.removeAll(Removed);
        System.out.println("Item(s) removed!");
        System.out.println();
    }//removeFood
    static void randomTip() {
        System.out.println();
        System.out.println("Surprise! Here is a randomly generated Val dining tip...");
        System.out.println();
        String[] recipe = {"Breakfast Sandwich: \n\tYou can’t go wrong with the combination of bread, eggs, cheese and meat.\n" +
                "\tGet a scoop of scrambled eggs from the hot breakfast, as well as sausage or bacon if you wish, and a slice of cheese.\n" +
                "\tToast a bagel or English muffin. Sprinkle eggs with salt and pepper and assemble the sandwich.\n" +
                "\tPlace in the panini press until the cheese has melted. Jazz it up with tomato, spinach or arugula if available.\n" +
                "\tDip in hot sauce or ketchup and eat. (If you are in a rush, you can try melting the cheese by placing it on top of the hot eggs in a closed container.)",
                "Spicy Mayo: \n\tSpicy mayo is an easy combination of condiments. Mix one packet of sriracha with one packet of mayonnaise.\n" +
                        "\tAdjust these as you like depending on your spice preferences. Add a bit of lemon or lime juice for some zing.\n" +
                        "\tNote: do not substitute hot sauce (or at least Texas Pete hot sauce…) for the sriracha.",
                "Microwave desserts and pastries: \n\tThis is not a recipe as much as a brilliant and wildly unique suggestion.\n" +
                        "\tStick muffins, pies, brownies, croissants and pastries in the microwave for about 20 seconds before consuming.\n" +
                        "\tMakes you feel super fancy and gives a warm feeling in your stomach (literally).",
                "Peanut Butter, Honey, and Banana Sandwich: \n\tSpread peanut butter and honey on bread and place banana slices on top.\n" +
                        "\tSprinkle on cinnamon too for some extra pizazz. Cover with another piece of bread and place it in the panini press to warm it up.\n" +
                        "\tThis is delicious — and nutritious!",
                "Mocha Made with Chocolate Milk: \n\tCombine two parts coffee (hot or iced) with one part chocolate milk.\n" +
                        "\tYou choose the milk — I suggest chocolate almond. Adjust coffee and milk levels to your liking.",
                "Mocha Made with Hot Chocolate Packet: \n\tStir a hot chocolate packet into a hot cup of coffee. Add creamer or milk if you wish.",
                "Peppermint Hot Chocolate: \n\tUnder the unfortunate assumption you make hot chocolate with a hot water base at Val, steep a mint tea bag in the water for a few minutes before pouring in the hot chocolate powder. \n" +
                        "\tMakes you feel like Christmas is near.",
                "Coffee Float: \n\tDessert for breakfast or coffee for dinner, the caffeine and sugar levels of a coffee float make it a great energy boost.\n" +
                        "\tScoop out a dish of vanilla or chocolate ice cream from the cooler and place it in a larger cup. Pour hot coffee over the top.\n" +
                        "\tEnjoy with a spoon or straw. Or a fork if you are feeling bold.",
                "Salad Dressing on Sandwich: \n\tPut balsamic dressing on a vegetarian’s dream sandwich of cheese and loads of veggies.\n" +
                        "\tItalian dressing also does the job well.",
                "Peanut Butter, Honey, Chia, and Banana Toast: \n\tSpread peanut butter and honey on a slice of buttered toast then add banana slices sprinkled with chia seeds on top for a delicious snack.",
                "On the Art of Panini Making: \n\tSpread butter on the outside of your bread before panini pressing it for the best toasted effect.",
                "Sesame Noodles with Justin's Peanut Butter and Soy Sauce: \n\tIf you happen to find sesame noodles at the pasta bar, try mixing in Justin's peanut butter and some soy sauce.",};

        System.out.println(randomString(recipe) + "\n\nTip credits to Sarah Weiner of the Amherst Student and the Val Dining Instagram page.");
    }//randomTip
    static String randomString(String[] options) {
        return options[(int) (options.length * Math.random())];
    }//randomString
    static void getFinalFile() {
        try {
            File ff = new File("final.txt");
            if (ff.createNewFile()) {
                System.out.println();
                System.out.println("File created: " + ff.getName());
                PrintWriter fout = new PrintWriter(ff);
                makeFinalFile(fout);
                System.out.println("Finished: " + ff.getName() + "\nCheck it!");
                fout.close();
            } else
                System.out.println("File already exists:" + ff.getName());
        } catch (IOException e) {
            System.out.println(e);
        }
    }//getFinalFile
    static void makeFinalFile(PrintWriter fout) {

        String pattern = "###.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        double totalCal = 0.0;
        fout.write("Here's your finalized plan for today!\n");
        if (calorie_intake!=0){
            fout.write("\nYour BMR estimation:\n");
            fout.write(bmr_result + "\n");
            fout.write("\nCalorie intake plan for the day: " + calorie_intake + " calories.\n");
        }

        fout.write("\nToday's meal plan:\n");
        if(!breakfast.isEmpty()){
            fout.write("\tYour breakfast:\n");
            for(Food f:breakfast){
                totalCal += f.calories;
                String calFormat = decimalFormat.format(f.calories);
                fout.write("\t\t" + f.name + " " + calFormat + " " + f.serving + "\n");
            }
        }
        if(!lunch.isEmpty()){
            fout.write("\tYour lunch:\n");
            for(Food f:lunch){
                totalCal += f.calories;
                String calFormat = decimalFormat.format(f.calories);
                fout.write("\t\t" + f.name + " " + calFormat + " " + f.serving + "\n");
            }
        }
        if(!dinner.isEmpty()){
            fout.write("\tYour dinner:\n");
            for(Food f:dinner){
                totalCal += f.calories;
                String calFormat = decimalFormat.format(f.calories);
                fout.write("\t\t" + f.name + " " + calFormat + " " + f.serving + "\n");
            }
        }
        String totalCalFormat = decimalFormat.format(totalCal);
        fout.write("\nTotal calories of your meal(s): " + totalCalFormat);
    }//makeFinalFile
}//class
////////////////////////////
class Food{
    String name;
    double calories;
    String serving;
    ArrayList<String> ingredients; //change to set
    Food(String name, double calories, String serving, ArrayList<String> ingredients){
        this.name = name;
        this.calories = calories;
        this.serving = serving;
        this.ingredients = ingredients;
    }//constructor
    public String toString(){
        String pattern = "###.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String caloriesFormat = decimalFormat.format(calories);
        return name + " " + caloriesFormat + "cal " + serving;
    }
}//Food
class Salad extends Food{
    String type;
    public Salad(String type, String name, double calories, String serving, ArrayList<String> ingredients){
        super(name, calories, serving, ingredients);
        this.type = type;
    }//constructor
    public String toString(){
        return super.toString();
    }
}//Salad


