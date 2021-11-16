/*
CS112 Final project
Title: Val Menu Revamped
Authors: Mia Jung (BO), Vanesa Farooq (VF), Hailin Kim (HK)
*/
import java.util.*;
public class ValMenu {
    static Scanner cin = new Scanner(System.in); // a global scanner.
    public static void main (String[] args){
        System.out.println("Welcome to our code.");
        char option;
        do{
            System.out.println("\nMenu: ");
            System.out.print("\n1. " +
                    " \n2. " +
                    " \n3. " +
                    "\n0. exit program."+
                    "\n\nEnter an option (1-3, 0 to quit): ");
            option = cin.next().charAt(0); // returns the character at index 0.
            cin.nextLine();
            switch(option){
                case '1': //dosomething; break;
                case '2': //dosomething; break;
                case '0': System.out.println("\n** You entered option 0 to exit. Good bye! **"); break;
                default: System.out.println("\n** Input error! Enter a valid input, please! **");
            }
        } while (option != '0');
    }//main
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

