"""
Description:
* Before using this script download the web chrome driver from this link -> https://chromedriver.chromium.org/
* You should check your chrome version before downloading the chrome driver as only the dedicated chrome driver will work.
* Also install selenium for your python using pip command -> pip install selenium
"""

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.keys import Keys
import time


def posting_tweet():

    PATH = "F:\\chromedriver.exe"
    driver = webdriver.Chrome(PATH)
    driver.get("https://twitter.com/home")

    try:
        loginpage = WebDriverWait(driver, 15).until(
            EC.presence_of_element_located((By.LINK_TEXT, "Log in"))
        )
        loginpage.click()

        username = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.NAME, "session[username_or_email]"))
        )
        username.send_keys(user_id)

        password = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.NAME, "session[password]"))
        )
        password.send_keys(user_pas)

        password.send_keys(Keys.RETURN)

        tweettextBox = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "//div[@data-testid='tweetTextarea_0']"))
        )
        tweettextBox.click()
        tweettextBox.send_keys(your_tweet)

        tweetbutton = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "//div[@data-testid='tweetButtonInline']"))
        )
        tweetbutton.click()

        time.sleep(15)

    except():
        driver.quit()


if __name__ == '__main__':
    user_id = input("Enter user id:")
    user_pas = input("Enter user password:")
    your_tweet = input("Enter a tweet:")
    posting_tweet()