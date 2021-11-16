// This is a test comment. I'm practicing with Git!


public class Food {
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
