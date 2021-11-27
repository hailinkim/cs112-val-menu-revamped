library(RSelenium)
browser <- rsDriver(port = 1234L, browserName = "firefox")
browser$open()
browser$navigate("https://acnutrition.amherst.edu/NetNutrition/1#")
elem <- browser$findElement(using = "css", 'table.cbo_nn_sideUnitTable')
menu3 <- elem %>% 
  html_table()
library(XML)
html <- browser$getPageSource()[[1]]
menu3 <- read_html(html) %>% 
  html_elements("tr td") %>% 
  html_element("div") %>% 
  purrr::pluck(2)
  
  html_table()
  
doc <- htmlParse(browser$getPageSource()[[1]])
readHTMLTable(doc)

library(readr)
library(dplyr)
t <- read_csv("/Users/angelica/Desktop/insurance.csv") 
t <- t %>% 
  select(-version)
