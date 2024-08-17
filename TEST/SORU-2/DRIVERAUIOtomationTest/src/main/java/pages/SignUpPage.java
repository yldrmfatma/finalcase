package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SignUpPage {

    private AppiumDriver driver;

    public SignUpPage(AppiumDriver driver) {
        this.driver=driver;
    }
    // Locators
    private By nameField = By.id("name");
    private By surnameField = By.id("surname");
    private By emailField = By.id("email");
    private By phoneNumberField = By.id("phone_number");
    private By countryCodeDropdown = By.id("country_code");
    private By citizenshipDropdown = By.id("citizenship");
    private By dateOfBirthField = By.id("date_of_birth");
    private By birthPlaceField = By.id("birth_place");
    private By currentLocationDropdown = By.id("current_location");
    private By passwordField = By.id("password");
    private By confirmPasswordField = By.id("confirm_password");
    private By twoFactorAuthToggle = By.id("two_factor_auth");
    private By registerButton = By.id("register");
    private By confirmationMessage = By.id("confirmation_message");
    private By errorMessageField = By.id("error_message");
    // Locator for Term and Privacy Policy link
    private By termsAndPrivacyPolicyLink = By.id("terms_and_privacy_policy_link");
    // Locators for policy content
    private By policyHeader = By.id("policy_header"); // Policy header (modify this as needed)



    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterSurname(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPhoneNumber(String phoneNumber) {
        driver.findElement(phoneNumberField).sendKeys(phoneNumber);
    }

    public void selectCountryCode(String countryCode) {
        WebElement dropdown = driver.findElement(countryCodeDropdown);
        dropdown.click(); // Open the dropdown
        driver.findElement(By.xpath("//option[text()='" + countryCode + "']")).click();
    }

    public void selectCitizenship(String citizenship) {
        WebElement dropdown = driver.findElement(citizenshipDropdown);
        dropdown.click(); // Open the dropdown
        driver.findElement(By.xpath("//option[text()='" + citizenship + "']")).click();
    }

    public void enterDateOfBirth(String dateOfBirth) {
        driver.findElement(dateOfBirthField).sendKeys(dateOfBirth);
    }

    public void enterBirthPlace(String birthPlace) {
        driver.findElement(birthPlaceField).sendKeys(birthPlace);
    }

    public void selectCurrentLocation(String location) {
        WebElement dropdown = driver.findElement(currentLocationDropdown);
        dropdown.click(); // Open the dropdown
        driver.findElement(By.xpath("//option[text()='" + location + "']")).click();
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);
    }

    public void enableTwoFactorAuthentication() {
        WebElement toggle = driver.findElement(twoFactorAuthToggle);
        if (!toggle.isSelected()) {
            toggle.click();
        }
    }

    public void tapRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public String getConfirmationMessage() {
        return driver.findElement(confirmationMessage).getText();
    }

    public boolean isSignUpScreenDisplayed() {
        WebElement registerButtonElement = driver.findElement(registerButton);
        // Check if the Register button is visible and the screen is displayed
        return registerButtonElement.isDisplayed();
    }
    public WebElement getPolicyHeader() {
        return driver.findElement(policyHeader);
    }

    // Method to get Term and Privacy Policy link
    public By getTermsAndPrivacyPolicyLink() {
        return  By.id("terms_and_privacy_policy_link");
    }

    public By getErrorMessageField() {
        return errorMessageField;
    }

    public By getPasswordField() {
        return passwordField;
    }

}
