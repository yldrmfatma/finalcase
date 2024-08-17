package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import steps.DatabaseConnectionStep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class SignUpPage {
    private AppiumDriver driver;
    private DatabaseConnectionStep dbConnection;


    //Register button on the Login screen that opens when the application is first opened
    private By registerButton = By.id("com.example.yourapp:id/register_button");
    private By signUpPageTitle = By.id("com.example.yourapp:id/signup_title");

    // Locators on Register Page
    private By usernameField = By.id("username");
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By phoneNumberField = By.id("phone_number");
    private By firstNameField = By.id("first_name");
    private By lastNameField = By.id("last_name");
    private By birthDateField = By.id("birth_date");
    private By birthPlaceField = By.id("birth_place");
    private By currentLocationField = By.id("current_location");
    private By citizenshipField = By.id("citizenship");
    private By profilePictureUrlField = By.id("profile_picture_url");
    private By consentDateField = By.id("consent_date");
    private By consentTypeField = By.id("consent_type");
    private By otpCheckbox = By.id("otp_verification_checkbox"); // OTP onay kutusu
    private By submitButton = By.id("submit");
    private By errorMessage = By.cssSelector(".error-message");
    private By termsAndPrivacyPolicyLink = By.id("terms_and_privacy_policy_link");
    private By policyHeader = By.id("policy_header"); // Policy header (modify this as needed)

    // Constructor
    public SignUpPage(AppiumDriver driver, DatabaseConnectionStep dbConnection) {
        this.driver = driver;
        this.dbConnection = dbConnection;
    }

    // Click the Register button
    public void clickRegisterButton() {
        WebElement button = driver.findElement(registerButton);
        button.click();
    }

    // Registration page
    public boolean isSignUpPageDisplayed() {
        WebElement title = driver.findElement(signUpPageTitle);
        return title.isDisplayed();
    }

    // Separate methods for each field
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void enterPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            driver.findElement(phoneNumberField).sendKeys(phoneNumber);
        }
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterBirthDate(LocalDate birthDate) {
        driver.findElement(birthDateField).sendKeys(birthDate.toString());
    }

    public void enterBirthPlace(String birthPlace) {
        driver.findElement(birthPlaceField).sendKeys(birthPlace);
    }

    public void enterCurrentLocation(String currentLocation) {
        driver.findElement(currentLocationField).sendKeys(currentLocation);
    }

   public void enterCitizenship(String citizenship) {
        driver.findElement(citizenshipField).sendKeys(citizenship);
    }

    public void enterProfilePictureUrl(String profilePictureUrl) {
        if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {
            driver.findElement(profilePictureUrlField).sendKeys(profilePictureUrl);
        }
    }

    public void enterConsentDate(ZonedDateTime consentDate) {
        driver.findElement(consentDateField).sendKeys(consentDate.toString());
    }

    public void enterConsentType(String consentType) {
        driver.findElement(consentTypeField).sendKeys(consentType);
    }
    public void selectOtpVerificationCheckbox() {
        WebElement checkbox = driver.findElement(otpCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void submitRegistration() {
        driver.findElement(submitButton).click();
    }

    public String getErrorMessage() {
        WebElement errorElement = driver.findElement(errorMessage);
        return errorElement.getText();
    }

    // Method to verify registration in the database
    public boolean isUserRegisteredInDatabase(String username, String email) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND email = ?");
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if user is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void selectSignupMethod(String signupMethod) {
        WebElement signupMethodElement = driver.findElement(By.id("signup_method"));
        Select dropdown = new Select(signupMethodElement);
        dropdown.selectByValue(signupMethod);
    }

    public void checkOtpCheckbox(String otpCheckbox) {
        WebElement checkbox = driver.findElement(By.id("otp_checkbox"));
        if (otpCheckbox.equalsIgnoreCase("true") && !checkbox.isSelected()) {
            checkbox.click();
        } else if (otpCheckbox.equalsIgnoreCase("false") && checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public boolean isGdprComplianceEnforced() {
        return driver.findElement(termsAndPrivacyPolicyLink).isDisplayed();
    }

    public void clickOnTermsAndPrivacyPolicyLink() {
        driver.findElement(termsAndPrivacyPolicyLink).click();
    }

    public void waitForPrivacyPolicyPageToLoad() {
    }

    public String getCitizenship() {
        WebElement citizenshipElement = driver.findElement(citizenshipField);
        Select dropdown = new Select(citizenshipElement);
        return dropdown.getFirstSelectedOption().getText();
    }

    public String getExpectedPolicyHeaderForCitizenship(String citizenship) {
        // Vatandaşlığa göre beklenen gizlilik politikası başlığı
        switch (citizenship) {
            case "FR":
                return "GDPR Privacy Policy";
            case "TR":
                return "KVKK Privacy Policy";
            default:
                return "Drivera Privacy Policy";
        }
        
    }

    public String getPolicyHeader() {
        return driver.findElement(policyHeader).getText();
    }

    public void setConsentTypeFromPolicyHeader() {
        String policyHeaderText = getPolicyHeader();
    }

    public void performSocialMediaLogin(String provider) {
        if (provider.equals("Google")) {
            driver.findElement(By.id("google_login_button")).click();
        }
    }
}
