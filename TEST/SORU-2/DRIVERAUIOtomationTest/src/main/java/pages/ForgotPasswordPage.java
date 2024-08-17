package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ForgotPasswordPage {
    private AppiumDriver driver;

    // Locators
    private By emailField = By.id("email");
    private By submitButton = By.id("submit"); // Submit button for password reset
    private By confirmationMessage = By.id("confirmation_message");

    // Constructor
    public ForgotPasswordPage(AppiumDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    //Method of submitting the password reset form
    public void submitForgetPasswordForm() {
        driver.findElement(submitButton).click();
    }

    // Method to check confirmation message
    public boolean isConfirmationMessageDisplayed() {
        return driver.findElement(confirmationMessage).isDisplayed();
    }
}
