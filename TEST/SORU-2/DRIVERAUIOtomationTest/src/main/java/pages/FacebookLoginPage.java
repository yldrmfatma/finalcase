package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FacebookLoginPage {
    private AppiumDriver driver;

    // Locators for Facebook login page
    private By emailField = By.id("email");
    private By passwordField = By.id("pass");
    private By loginButton = By.name("login");

    // Constructor
    public FacebookLoginPage(AppiumDriver driver) {
        this.driver = driver;
    }

    // Method to check if the login fields are displayed
    public boolean areLoginFieldsDisplayed() {
        try {
            WebElement emailElement = driver.findElement(emailField);
            WebElement passwordElement = driver.findElement(passwordField);
            return emailElement.isDisplayed() && passwordElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Method to enter email
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    // Method to enter password
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    // Method to submit the login form
    public void submitLoginForm() {
        driver.findElement(loginButton).click();
    }
}
