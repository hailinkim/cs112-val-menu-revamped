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
breakfast3 <- breakfast2 %>% 
  #ingredients column
  mutate(ingredients = str_replace_all(ingredients, ",", "/"),
         #contains column
         contains = str_replace_all(contains, ", ", "/"),
         calories = str_replace(calories, " ", "_")) %>%
  separate(calories, c("calories", "serving_size"), sep = "_") %>% 
  #combine 'ingredients' and 'contains' columns
  unite("ingredients2", ingredients:contains, sep = "/", remove = TRUE) %>% 
  mutate(ingredients = str_remove_all(ingredients2, "/NA")) %>% 
  select(-ingredients2) %>% 
  drop_na() %>% 
  mutate(calories = as.integer(calories)) 

# breakfast3 <- breakfast3 %>% 
#   mutate(calories = str_replace(calories, " ", "cal "))

day1_breakfast <- breakfast3 %>% 
  filter(day == "day1") %>% 
  select(-day)
write_csv(day1_breakfast, "day1_breakfast.txt", col_names = FALSE)

day2_breakfast <- breakfast3 %>% 
  filter(day == "day2") %>% 
  select(-day)
write_csv(day2_breakfast, "day2_breakfast.txt", col_names = FALSE)

day3_breakfast <- breakfast3 %>% 
  filter(day == "day3") %>% 
  select(-day)
write_csv(day3_breakfast, "day3_breakfast.txt", col_names = FALSE)

day4_breakfast <- breakfast3 %>% 
  filter(day == "day4") %>% 
  select(-day)
write_csv(day4_breakfast, "day4_breakfast.txt", col_names = FALSE)

day5_breakfast <- breakfast3 %>% 
  filter(day == "day5") %>% 
  select(-day)
write_csv(day5_breakfast, "day5_breakfast.txt", col_names = FALSE)

day6_breakfast <- breakfast3 %>% 
  filter(day == "day6") %>% 
  select(-day)
write_csv(day6_breakfast, "day6_breakfast.txt", col_names = FALSE)

day7_breakfast <- breakfast3 %>% 
  filter(day == "day7") %>% 
  select(-day)
write_csv(day7_breakfast, "day7_breakfast.txt", col_names = FALSE)



#wrangling lunch data
lunch <- read_csv("lunch.csv")
lunch2 <- lunch %>% 
  #rename the days
  mutate(day = case_when(
    day == "day6" ~ "day1",
    day == "day7" ~ "day2",
    day == "day8" ~ "day3",
    day == "day9" ~ "day4",
    day == "day10" ~ "day5",
    day == "day11" ~ "day6",
    day == "day12" ~ "day7",
  )) %>% 
  #remove unnecessary columns from the data
  select(-c(meal_type, dining_course)) %>% 
  #reorder columns
  select(day, menu, calories, ingredients, contains)
lunch3 <- lunch2 %>% 
  mutate(ingredients = str_replace_all(ingredients, ",", "/"),
         #contains column
         contains = str_replace_all(contains, ", ", "/"),
         calories = str_replace(calories, " ", "_")) %>%
  separate(calories, c("calories", "serving_size"), sep = "_") %>% 
  #combine 'ingredients' and 'contains' columns
  unite("ingredients2", ingredients:contains, sep = "/", remove = TRUE) %>% 
  mutate(ingredients = str_remove_all(ingredients2, "/NA")) %>% 
  select(-ingredients2) %>% 
  drop_na() %>% 
  mutate(calories = as.integer(calories)) 
  
  

day1_lunch <- lunch3 %>% 
  filter(day == "day1") %>% 
  select(-day)
write_csv(day1_lunch, "day1_lunch.txt", col_names = FALSE)

day2_lunch <- lunch3 %>% 
  filter(day == "day2") %>% 
  select(-day)
write_csv(day2_lunch, "day2_lunch.txt", col_names = FALSE)

day3_lunch <- lunch3 %>% 
  filter(day == "day3") %>% 
  select(-day)
write_csv(day3_lunch, "day3_lunch.txt", col_names = FALSE)

day4_lunch <- lunch3 %>% 
  filter(day == "day4") %>% 
  select(-day)
write_csv(day4_lunch, "day4_lunch.txt", col_names = FALSE)

day5_lunch <- lunch3 %>% 
  filter(day == "day5") %>% 
  select(-day)
write_csv(day5_lunch, "day5_lunch.txt", col_names = FALSE)

day6_lunch <- lunch3 %>% 
  filter(day == "day6") %>% 
  select(-day)
write_csv(day6_lunch, "day6_lunch.txt", col_names = FALSE)

day7_lunch <- lunch3 %>% 
  filter(day == "day7") %>% 
  select(-day)
write_csv(day7_lunch, "day7_lunch.txt", col_names = FALSE)


#wrangling dinner data
dinner <- read_csv("dinner.csv")
dinner2 <- dinner %>% 
  #rename the days
  mutate(day = case_when(
    day == "day6" ~ "day1",
    day == "day7" ~ "day2",
    day == "day8" ~ "day3",
    day == "day9" ~ "day4",
    day == "day10" ~ "day5",
    day == "day11" ~ "day6",
    day == "day12" ~ "day7",
  )) %>% 
  #remove unnecessary columns from the data
  select(-c(meal_type, dining_course)) %>% 
  #reorder columns
  select(day, menu, calories, ingredients, contains)
dinner3 <- dinner2 %>% 
  mutate(ingredients = str_replace_all(ingredients, ",", "/"),
         #contains column
         contains = str_replace_all(contains, ", ", "/"),
         calories = str_replace(calories, " ", "_")) %>%
  separate(calories, c("calories", "serving_size"), sep = "_") %>% 
  #combine 'ingredients' and 'contains' columns
  unite("ingredients2", ingredients:contains, sep = "/", remove = TRUE) %>% 
  mutate(ingredients = str_remove_all(ingredients2, "/null")) %>% 
  select(-ingredients2) %>% 
  drop_na() %>% 
  mutate(calories = as.integer(calories)) 

day1_dinner <- dinner3 %>% 
  filter(day == "day1") %>% 
  select(-day)
write_csv(day1_dinner, "day1_dinner.txt", col_names = FALSE)

day2_dinner <- dinner3 %>% 
  filter(day == "day2") %>% 
  select(-day)
write_csv(day2_dinner, "day2_dinner.txt", col_names = FALSE)

day3_dinner <- dinner3 %>% 
  filter(day == "day3") %>% 
  select(-day)
write_csv(day3_dinner, "day3_dinner.txt", col_names = FALSE)

day4_dinner <- dinner3 %>% 
  filter(day == "day4") %>% 
  select(-day)
write_csv(day4_dinner, "day4_dinner.txt", col_names = FALSE)

day5_dinner <- dinner3 %>% 
  filter(day == "day5") %>% 
  select(-day)
write_csv(day5_dinner, "day5_dinner.txt", col_names = FALSE)

day6_dinner <- dinner3 %>% 
  filter(day == "day6") %>% 
  select(-day)
write_csv(day6_dinner, "day6_dinner.txt", col_names = FALSE)

day7_dinner <- dinner3 %>% 
  filter(day == "day7") %>% 
  select(-day)
write_csv(day7_dinner, "day7_dinner.txt", col_names = FALSE)


#salad bar
salad_scraped <- read_csv("salad_scraped.csv")
salad_scraped2 <- salad_scraped %>% 
  select(-"# of Servings") %>% 
  dplyr::rename(type = "Menu Items Table", name = "Item Name", serving_size = "Serving Size")
salad_scraped3 <- salad_scraped2[-1,]
salad_scraped3[c(1:36), 1] <-"condiment"
salad_scraped3 <- salad_scraped3[-37,]
salad_scraped3[c(37:54), 1] <- "fruit"
salad_scraped3 <- salad_scraped3[-55,]
salad_scraped3[c(55:76), 1] <- "topping"
salad_scraped3 <- salad_scraped3[-77,]
salad_scraped3[c(77:79), 1] <- "lettuce"
salad_scraped3 <- salad_scraped3[-80,]
salad_scraped3[c(80:100), 1] <- "vegetable"

salad <- lunch %>% 
  filter(dining_course == "Salad Bar") %>% 
  distinct(menu, .keep_all = TRUE)
salad2 <- salad %>% 
  mutate(ingredients = str_replace_all(ingredients, ",", "/"),
         #contains column
         contains = str_replace_all(contains, ", ", "/"),
         calories = str_replace(calories, " ", "_")) %>%
  separate(calories, c("calories", "serving_size"), sep = "_") %>% 
  #combine 'ingredients' and 'contains' columns
  unite("ingredients2", ingredients:contains, sep = "/", remove = TRUE) %>% 
  mutate(ingredients = str_remove_all(ingredients2, "/NA"),
         calories = as.integer(calories),
         type = "salad") %>% 
  select(-c(day:dining_course, ingredients2), name = menu)
salad3 <- salad2 %>% 
  bind_rows(salad_scraped3) %>% 
  select(type, name:ingredients)

write_csv(salad3, "salad.csv", col_names = FALSE)
write_csv(salad3, "salad.txt", col_names = FALSE)
