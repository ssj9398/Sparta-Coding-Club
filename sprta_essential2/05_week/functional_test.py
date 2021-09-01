from selenium import webdriver

path = './chromedriver'
# Django 8000퐅, html title Django 포함되어
driver = webdriver.Chrome(path)
driver.get('http://localhost:8000')

assert "worked" in driver.title