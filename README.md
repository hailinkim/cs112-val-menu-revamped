Title: Val Menu and Meal Planner

Authors: Mia Jung (BO), Vanesa Farooq (VF), Hailin Kim (HK)

Date: November 29, 2021

Course: CS-112

Files used: "Val Menu".csv

Software used: IntelliJ and R Studio

Abstract:

For our project, we created a program that allows the user to create their Val meal plan on a specific day of
the week (where each day of the week has unique meal choices). We calculate the user's BMR (Basal metabolic rate), which
is the calories the user needs per day to maintain the user's current body weight. We allow the user to view how many
calories they need per day according to their activity level and allow them to enter how many calories they plan to
intake on a certain day of the week.
We placed 7 days worth of meal data from AC Nutrition (we automated the data collection process through statistical
software called RStudio) into text files and sorted it by breakfast, lunch, and dinner. We then sorted each meal by type
of ingredients, calorie count, and portion size. We also take into account any food restrictions (i.e. allergies to
gluten or eggs) or dietary preferences (if the user is currently abiding by a specific diet, i.e. vegetarian or keto).
With that user-inputted information, we then are able to sort through our meal data for meals that match the criteria of
the user's dietary needs and subsequently present them with the meals best suited to them.
From there, the user is able to choose which meals they would prefer and the program checks for them whether they exceed
their calorie plan. Finally, the user is able to generate a file containing their day's complete meal plan along with
their BMR and calorie information if they had chosen to provide it.