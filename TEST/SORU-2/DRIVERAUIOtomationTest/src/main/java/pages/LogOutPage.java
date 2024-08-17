package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import java.util.NoSuchElementException;

public class LogOutPage {

    private AppiumDriver driver;

    // Locators for logout page elements
    private By pageTitle = By.id("settings_page_title");
    private By logoutButton = By.id("logout");
    private By successMessage = By.id("success_message");

    public LogOutPage(AppiumDriver driver) {
        this.driver=driver;
    }
    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    // Methods to interact with the logout page elements
    public void tapLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }

    public By getLogoutButton() {
        return logoutButton;
    }

    public By getSuccessMessageField() {
        return successMessage;
    }
    // Method to check if the logout button is visible
    public boolean isLogoutButtonVisible() {
        try {
            // Check if the logout button is displayed
            return driver.findElement(logoutButton).isDisplayed();
        } catch (NoSuchElementException e) {
            return false; // If the button is not found, return false
        }
    }

}
