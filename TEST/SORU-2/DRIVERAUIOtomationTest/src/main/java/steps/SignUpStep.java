package steps;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.SignUpPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class SignUpStep {

    private AppiumDriver driver;
    private SignUpPage signUpPage;

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Given("User opens app")
    public void userOpensApp() {
        try {
            var options = new BaseOptions()
                    .amend("platformName", "Android")
                    .amend("appium:deviceName", "Pixel 6")
                    .amend("appium:automationName", "UiAutomator2")
                    .amend("appium:udid", "Android Emulator")
                    .amend("appium:avd", "Pixel_6_Pro_API_32")
                    .amend("appium:fastReset", true)
                    .amend("appium:newCommandTimeout", 5)
                    .amend("appium:ensureWebviewsHavePages", true)
                    .amend("appium:nativeWebScreenshot", true)
                    .amend("appium:connectHardwareKeyboard", true)
                    .amend("appium:app", "/Users/username/Desktop/appiumapk/selendroid-test-app.apk");

            driver = new AndroidDriver(this.getUrl(), options);

            // Creating the LoginPage object and passing only the driver
            signUpPage = new SignUpPage(driver);

            Thread.sleep(5000); // Wait for 5 seconds (adjust as needed)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("User is on the sign up screen")
    public void userIsOnTheSignUpScreen() {
        boolean isSignUpScreenDisplayed = signUpPage.isSignUpScreenDisplayed();
        Assert.assertTrue("User is not on the sign up screen", isSignUpScreenDisplayed);
    }

    @When("User fills in the sign up form with valid details")
    public void userFillsInTheSignUpFormWithValidDetails() {
        signUpPage.enterName("John");
        signUpPage.enterSurname("Doe");
        signUpPage.enterEmail("john.doe@example.com");
        signUpPage.enterPhoneNumber("+1234567890");
        signUpPage.selectCountryCode("US");
        signUpPage.selectCitizenship("American");
        signUpPage.enterDateOfBirth("01/01/1990");
        signUpPage.enterBirthPlace("New York");
        signUpPage.selectCurrentLocation("New York");
        signUpPage.enterPassword("SecurePassword123");
        signUpPage.enterConfirmPassword("SecurePassword123");
        signUpPage.enableTwoFactorAuthentication();
       // signUpPage.acceptTermsAndPrivacyPolicy();
    }

    @And("User taps on {string} button to submit the form")
    public void userTapsOnButtonToSubmitTheForm(String buttonName) {
        if ("Register".equalsIgnoreCase(buttonName)) {
            signUpPage.tapRegisterButton();
        }
    }

    @Then("User should receive a registration confirmation {string}")
    public void userShouldReceiveARegistrationConfirmation(String expectedMessage) {
        String actualMessage = signUpPage.getConfirmationMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @When("User enters {string} into the password field")
    public void userEntersIntoThePasswordField(String password) {
        signUpPage.enterPassword(password);
    }

    @Then("The password field should display characters as hidden")
    public void thePasswordFieldShouldDisplayCharactersAsHidden() {
        String passwordFieldValue = driver.findElement(signUpPage.getPasswordField()).getAttribute("value");
        String passwordFieldType = driver.findElement(signUpPage.getPasswordField()).getAttribute("type");

        // Assert that the field type is 'password' which means characters are hidden
        Assert.assertEquals("password", passwordFieldType);
        // Optionally, you can also verify that the entered password is not displayed as plain text
        Assert.assertNotEquals("MySecretPassword123", passwordFieldValue);
    }

    @When("User enters a password with less than {int} characters")
    public void userEntersAPasswordWithLessThanCharacters(int length) {
        String password = "a".repeat(length - 1); // Password length one less than specified
        signUpPage.enterPassword(password);
    }

    @Then("The password field should display an error message {string}")
    public void thePasswordFieldShouldDisplayAnErrorMessage(String errorMessage) {
        String actualMessage = driver.findElement(signUpPage.getErrorMessageField()).getText();
        Assert.assertEquals("Error message does not match", errorMessage, actualMessage);
        driver.quit();
    }

    @When("User enters a password without uppercase letters")
    public void userEntersAPasswordWithoutUppercaseLetters() {
        String password = "lowercase1!"; // A password with no uppercase letters
        signUpPage.enterPassword(password);
    }

    @When("User enters a password without lowercase letters")
    public void userEntersAPasswordWithoutLowercaseLetters() {
        String password = "UPPERCASE1!"; // A password that does not contain lowercase letters
        signUpPage.enterPassword(password);
    }

    @When("User enters a password without numbers")
    public void userEntersAPasswordWithoutNumbers() {
        String password = "Uppercase!"; // A password that does not contain numbers
        signUpPage.enterPassword(password);
    }

    @When("User enters a password without special characters")
    public void userEntersAPasswordWithoutSpecialCharacters() {
        String password = "Uppercase1"; // A password that does not contain special characters
        signUpPage.enterPassword(password);
    }

    @When("User enters the password {string} into the Password field")
    public void userEntersThePasswordIntoThePasswordField(String password) {
        signUpPage.enterPassword(password);
    }

    @And("User enters a different password {string} into the Confirm Password field")
    public void userEntersADifferentPasswordIntoTheConfirmPasswordField(String confirmPassword) {
        signUpPage.enterConfirmPassword(confirmPassword);
    }


    @When("User enters a date of birth that makes them younger than {int} years old")
    public void userEntersADateOfBirthThatMakesThemYoungerThanYearsOld(int age) {
        // Şu anki tarihten verilen yaşın öncesine göre bir tarih hesaplanır.
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = today.minusYears(age).plusDays(1); // Kullanıcının belirlenen yaşın altında kalmasını sağlamak için 1 gün eklenir.
        String formattedDateOfBirth = dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        signUpPage.enterDateOfBirth(formattedDateOfBirth);
    }

    @Then("The Date of Birth field should display an error message {string}")
    public void theDateOfBirthFieldShouldDisplayAnErrorMessage(String expectedErrorMessage) {
        String actualMessage = driver.findElement(signUpPage.getErrorMessageField()).getText();
        Assert.assertEquals("Error message does not match", expectedErrorMessage, actualMessage);
        driver.quit();
    }

    @When("User enters an invalid email address {string} into the Email field")
    public void userEntersAnInvalidEmailAddressIntoTheEmailField(String email) {
        signUpPage.enterEmail(email);
    }

    @Then("The Email field should display an error message {string}")
    public void theEmailFieldShouldDisplayAnErrorMessage(String expectedErrorMessage) {
        String actualMessage = driver.findElement(signUpPage.getErrorMessageField()).getText();
        Assert.assertEquals("Error message does not match", expectedErrorMessage, actualMessage);
        driver.quit();
    }

    @When("User fills in all required fields except citizenship")
    public void userFillsInAllRequiredFieldsExceptCitizenship() {
        signUpPage.enterName("John");
        signUpPage.enterSurname("Doe");
        signUpPage.enterEmail("john.doe@example.com");
        signUpPage.enterPhoneNumber("+1234567890");
        signUpPage.selectCountryCode("US");
        signUpPage.enterDateOfBirth("01/01/1990");
        signUpPage.enterBirthPlace("New York");
        signUpPage.selectCurrentLocation("New York");
        signUpPage.enterPassword("SecurePassword123");
        signUpPage.enterConfirmPassword("SecurePassword123");
        signUpPage.enableTwoFactorAuthentication();
        // Note: Citizenship is not selected
    }

    @Then("The {string} link should be visible and disabled")
    public void theLinkShouldBeDisabled(String expectedLinkText) {
        By linkLocator = signUpPage.getTermsAndPrivacyPolicyLink();
        WebElement link = driver.findElement(linkLocator);

        // Linkin metnini doğrulama
        String actualLinkText = link.getText();
        Assert.assertEquals("The link text does not match", expectedLinkText, actualLinkText);

        // Linkin görünür olup olmadığını kontrol etme
        boolean isDisplayed = link.isDisplayed();
        Assert.assertTrue("The link should be visible", isDisplayed);

        // Linkin tıklanabilir olup olmadığını kontrol etme
        boolean isEnabled = link.isEnabled();
        Assert.assertFalse("The link should be disabled", isEnabled);
    }

    @When("User selects {string} as citizenship")
    public void userSelectsAsCitizenship(String citizenship) {
        signUpPage.selectCitizenship(citizenship);
    }

    @And("User taps on the {string} link")
    public void userTapsOnTheLink(String linkText) {
        // Use the linkText parameter to locate the correct link
        WebElement link = driver.findElement(By.xpath("//android.widget.TextView[@text='" + linkText + "']"));
        link.click();
    }

    @Then("The {string} policy should be displayed")
    public void thePolicyShouldBeDisplayed(String policy) {
        WebElement header = signUpPage.getPolicyHeader();
        String headerText = header.getText();
        Assert.assertEquals("The " + policy + " policy should be displayed", policy, headerText);
    }

}
