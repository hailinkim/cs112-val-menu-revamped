library(readr)
library(dplyr)
library(janitor)
library(stringr)
library(tidyr)

#wrangling breakfast data
breakfast <- read_csv("breakfast.csv")
breakfast2 <- breakfast %>% 
  #rename the days
  mutate(day = case_when(
    l == "day6" ~ "day1",
    l == "day7" ~ "day2",
    l == "day8" ~ "day3",
    l == "day9" ~ "day4",
    l == "day10" ~ "day5",
    l == "day11" ~ "day6",
    l == "day12" ~ "day7",
  )) %>% 
  #remove unnecessary columns from the data
  select(-c(l, meal_type, dining_course)) %>% 
  #reorder columns
  select(day, menu, calories, ingredients, contains = Contains)


menu2 <- menu %>% 
  mutate(calories = str_replace(X2, " ", "_"),
         ingredients = str_replace_all(X3, "�", " "),
         ingredients = str_replace_all(ingredients, ", ", "/")
         ) %>% 
  select(-c(X2, X3))

write_csv(menu2, "/Users/angelica/Desktop/menu2.csv")

menu <- read_csv("/Users/angelica/cs112/day6_breakfast.csv", col_names = FALSE)
menu2 <- menu %>% 
  mutate(X6 = str_replace_all(X6, ",", "/"),
         X7 = str_replace_all(X7, ", ", "/")) %>% 
  unite("X8", X6:X7, sep = "/", remove = TRUE) %>% 
  mutate(X8 = str_remove_all(X8, "/NA")) %>% 
  select(-c(X1:X3))
  
write_csv(menu2, "/Users/angelica/cs112-val-menu-revamped/day6_breakfast.csv")

