package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private AppiumDriver driver;

    //Locators
    private By loginScreenTitle = By.id("login_screen_title");
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By rememberMeCheckbox = By.id("remember_me_checkbox");
    private By forgotPasswordButton = By.id("forgot_password");
    private By submitButton = By.id("submit");

    private By loginViaFacebookButton = By.id("facebook_submit");
    private By loginViaInstagramButton = By.id("instagram_submit");
    private By loginViaTwitterButton = By.id("twitter_submit");
    private By loginViaLinkedinButton = By.id("linkedin_submit");

    private By errorMessage = By.id("error-message-id");

    // Constructor
    public LoginPage(AppiumDriver driver) {
        this.driver=driver;
    }

    // Method to check if login screen is displayed
    public boolean isLoginScreenDisplayed() {
        try {
            WebElement title = driver.findElement(loginScreenTitle);
            return title.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Separate methods for each field
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void selectRememberMeCheckbox() {
        WebElement checkbox = driver.findElement(rememberMeCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void submitForgetPassword() {
        driver.findElement(forgotPasswordButton).click();
    }

    public void submitRegistration() {
        driver.findElement(submitButton).click();
    }

    public void clickLoginViaFacebook() {
        driver.findElement(loginViaFacebookButton).click();
    }

    public void clickLoginViaInstagram() {
        driver.findElement(loginViaInstagramButton).click();
    }

    public void clickLoginViaTwitter() {
        driver.findElement(loginViaTwitterButton).click();
    }

    public void clickLoginViaLinkedin() {
        driver.findElement(loginViaLinkedinButton).click();
    }
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
    public void clearPassword() {
        driver.findElement(passwordField).clear(); // Clear the password field
    }

    public String getPageTitle() {
            return driver.findElement(loginScreenTitle).getText();
    }
}
