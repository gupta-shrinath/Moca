import requests
from bs4 import BeautifulSoup

url = "https://www.girlscript.tech/events"
html = requests.get(url)

print(html)

soup = BeautifulSoup(html.content, 'html.parser')

reg_links = soup.find_all("a", class_="theme-btn btn-style-two btn-sm")[:3]
reg_links = [ link["href"] for link in reg_links ]

titles = soup.find_all("h4")[:3]
titles = [ title.text for title in titles]

date_time = soup.find_all("li", class_="theme_color")[:3]
date_time = [ dt.text for dt in date_time]

print(titles, reg_links, date_time)
