library(robotstxt)
library(rvest)
library(dplyr)
library(tidyr)
library(readr)
library(stringr)
dates <- seq(as.Date("2021-10-10"), as.Date("2021-10-11"), "day")
dates <- seq(as.Date("2021-10-10"), as.Date("2021-11-06"), "day")
# dates <- as.Date("2021-10-10") + 0:1

ac_nutrition_url <- "https://www.amherst.edu/campuslife/housing-dining/dining/menu/"
url_dates <- c("2021-10-10/2021-10-16")
url_dates <- c("2021-10-10/2021-10-16", "2021-10-17/2021-10-23",
               "2021-10-24/2021-10-30", "2021-10-31/2021-11-06")
paths_allowed(ac_nutrition_url)


breakfast <- NULL
for(i in url_dates){
  url <- paste0(ac_nutrition_url, i)
  for(j in dates){
    d <- format(as.Date(j, origin="1970-01-01"))
    menu <- paste0("#dining-menu-", d, "-Breakfast p")
    course <- paste0("#dining-menu-", d, "-Breakfast .dining-course-name")
    n <- as.numeric(difftime(d, "2021-10-10")) + 1
    tmp <- url %>%
      read_html() %>%
      html_elements(menu) %>%
      html_text() %>%
      as.data.frame() 

    tmp2 <- url %>%
      read_html() %>%
      html_elements(course) %>%
      html_text() %>%
      as.data.frame() %>% 
      mutate(day = paste0("day", n))
    
    breakfast <- rbind(breakfast, cbind(tmp, tmp2)) 

  }
}

colnames(breakfast)[1] <- "menu"
colnames(breakfast)[2] <- "dining_course"
write_csv(breakfast, "breakfast.csv")

breakfast <- read_csv("breakfast.csv")
breakfast2 <- breakfast %>%  
  separate_rows(menu, sep = ";") %>% 
  mutate(menu = str_trim(menu)) %>% 
  mutate(meal_type = "breakfast") %>% 
  select(day, meal_type, dining_course, menu)
write_csv(breakfast2, "breakfast2.csv")

  

lunch <- NULL
for(i in url_dates){
  url <- paste0(ac_nutrition_url, i)
  for(j in dates){
    d <- format(as.Date(j, origin="1970-01-01"))
    menu <- paste0("#dining-menu-", d, "-Lunch p")
    course <- paste0("#dining-menu-", d, "-Lunch .dining-course-name")
    n <- as.numeric(difftime(d, "2021-10-10")) + 1
    tmp <- url %>%
      read_html() %>%
      html_elements(menu) %>%
      html_text() %>%
      as.data.frame() 
    
    tmp2 <- url %>%
      read_html() %>%
      html_elements(course) %>%
      html_text() %>%
      as.data.frame() %>% 
      mutate(day = paste0("day", n))
    
    lunch <- rbind(lunch, cbind(tmp, tmp2)) 
    
  }
}
colnames(lunch)[1] <- "menu"
colnames(lunch)[2] <- "dining_course"
write_csv(lunch, "lunch.csv")

lunch <- read_csv("lunch.csv")
lunch2 <- lunch %>%  
  separate_rows(menu, sep = ";") %>% 
  mutate(menu = str_trim(menu)) %>% 
  mutate(meal_type = "lunch") %>% 
  select(day, meal_type, dining_course, menu)
write_csv(lunch2, "lunch2.csv")

dinner <- NULL
for(i in url_dates){
  url <- paste0(ac_nutrition_url, i)
  for(j in dates){
    d <- format(as.Date(j, origin="1970-01-01"))
    menu <- paste0("#dining-menu-", d, "-Dinner p")
    course <- paste0("#dining-menu-", d, "-Dinner .dining-course-name")
    n <- as.numeric(difftime(d, "2021-10-10")) + 1
    tmp <- url %>%
      read_html() %>%
      html_elements(menu) %>%
      html_text() %>%
      as.data.frame() 
    
    tmp2 <- url %>%
      read_html() %>%
      html_elements(course) %>%
      html_text() %>%
      as.data.frame() %>% 
      mutate(day = paste0("day", n))
    
    dinner <- rbind(dinner, cbind(tmp, tmp2)) 
    
  }
}
colnames(dinner)[1] <- "menu"
colnames(dinner)[2] <- "dining_course"
write_csv(dinner, "dinner.csv")

dinner <- read_csv("dinner.csv")
dinner2 <- dinner %>%  
  separate_rows(menu, sep = ";") %>% 
  mutate(menu = str_trim(menu)) %>% 
  mutate(meal_type = "dinner") %>% 
  select(day, meal_type, dining_course, menu)
write_csv(dinner2, "dinner2.csv")
