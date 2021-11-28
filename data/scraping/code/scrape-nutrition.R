library(RSelenium)
library(rvest)
browser <- remoteDriver(port = 1236L, browserName = "firefox")
browser$open()
browser$navigate("https://acnutrition.amherst.edu/NetNutrition/1#")
elem <- browser$findElement(using = "css", '.cbo_nn_itemHover')$getElementText()
html <- browser$getPageSource()[[1]]

salad_scraped <- read_html(html) %>% 
  html_nodes("table.cbo_nn_itemGridTable") %>% 
  .[[1]] %>% 
  html_table(fill=T)
write_csv(salad_scraped, "salad_scraped.csv")

i <- read_html(html) %>% 
  html_nodes(".cbo_nn_itemHover") %>% 
  html_attr("onkeyup")


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

