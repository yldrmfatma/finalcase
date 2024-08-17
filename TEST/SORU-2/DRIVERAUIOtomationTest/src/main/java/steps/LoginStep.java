package steps;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pages.FacebookLoginPage;
import pages.ForgotPasswordPage;
import pages.LoginPage;

import org.junit.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginStep {

    private AppiumDriver driver;
    private LoginPage loginPage;
    private ForgotPasswordPage forgotPasswordPage;
    private FacebookLoginPage facebookLoginPage;

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Given("User open apps")
    public void userOpenApps() {
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
            loginPage = new LoginPage(driver);

            Thread.sleep(5000); // Wait for 5 seconds (adjust as needed)
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Given("User is on the login screen")
    public void userIsOnTheLoginScreen() {
        boolean isLoginScreenDisplayed = loginPage.isLoginScreenDisplayed(); // Bu metodu LoginPage'de tanımlayın
        if (!isLoginScreenDisplayed) {
            throw new AssertionError("User is not on the login screen");
        }
    }

    @When("User enters valid email {string}")
    public void userEntersValidEmail(String email) {
        loginPage.enterEmail(email);
    }

    @And("User enters valid password {string}")
    public void userEntersValidPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("User selects {string} checkbox")
    public void userSelectsCheckbox(String checkboxName) {
        if (checkboxName.equalsIgnoreCase("Remember Me")) {
            loginPage.selectRememberMeCheckbox();
        }
    }

    @And("User submits login form")
    public void userSubmitsLoginForm() {
        loginPage.submitRegistration();
    }

    @Then("User should be redirected to the home page")
    public void userShouldBeRedirectedToTheHomePage() {
        // Check a unique element on the dashboard screen
        boolean isOnDashboard = !driver.findElements(By.id("dashboard-element-id")).isEmpty();
        // Verify whether the user is on the Dashboard screen
        Assert.assertTrue("User is not on the dashboard page", isOnDashboard);
        driver.quit();
    }

    @When("User enters invalid email {string}")
    public void userEntersInvalidEmail(String email) {
        loginPage.enterEmail(email);
    }

    @Then("User should see an error message {string}")
    public void userShouldSeeAnErrorMessage(String expectedErrorMessage) {
        WebElement errorMessageElement = driver.findElement(By.id("error-message-id")); // Hata mesajı öğesinin ID'si
        String actualErrorMessage = errorMessageElement.getText();
        Assert.assertEquals("Error message did not match", expectedErrorMessage, actualErrorMessage);
        driver.quit();
    }

    @And("User leaves password field empty")
    public void userLeavesPasswordFieldEmpty() {
        loginPage.clearPassword();
    }

    @When("User clicks on {string}")
    public void userClicksOn(String buttonName) {
        if (buttonName.equalsIgnoreCase("Forgot Password")) {
            loginPage.submitForgetPassword();
            forgotPasswordPage = new ForgotPasswordPage(driver); // Switch to new page
        }
    }

    @And("User enters their email {string}")
    public void userEntersTheirEmail(String email) {
        forgotPasswordPage.enterEmail(email);
    }

    @And("User submits forget password form")
    public void userSubmitsForgetPasswordForm() {
        forgotPasswordPage.submitForgetPasswordForm();
    }

    @Then("User should see a confirmation message {string}")
    public void userShouldSeeAConfirmationMessage(String expectedMessage) {
        boolean isMessageDisplayed = forgotPasswordPage.isConfirmationMessageDisplayed();
        Assert.assertTrue("Confirmation message is not displayed", isMessageDisplayed);
        // Check the content of the message
        String actualMessage = driver.findElement(By.id("confirmation_message")).getText();
        Assert.assertEquals("The confirmation message text is incorrect", expectedMessage, actualMessage);
        driver.quit();
    }

    @When("User taps on {string} button")
    public void userTapsOnButton(String buttonName) {
        if (buttonName.equals("Login via Facebook")) {
            loginPage.clickLoginViaFacebook();
        }
    }

    @Then("User should be redirected to Facebook login page")
    public void userShouldBeRedirectedToFacebookLoginPage() {
        // Initialize FacebookLoginPage after redirection
        facebookLoginPage = new FacebookLoginPage(driver);
        boolean isOnFacebookLoginPage = facebookLoginPage.areLoginFieldsDisplayed();
        Assert.assertTrue("User is not on Facebook login page", isOnFacebookLoginPage);
    }

    @And("User should see Facebook login fields")
    public void userShouldSeeFacebookLoginFields() {
        boolean areFieldsDisplayed = facebookLoginPage.areLoginFieldsDisplayed();
        Assert.assertTrue("Facebook login fields are not displayed", areFieldsDisplayed);
    }

    @When("User enters valid Facebook credentials")
    public void userEntersValidFacebookCredentials() {
        facebookLoginPage.enterEmail("user@example.com");
        facebookLoginPage.enterPassword("password123");
    }

    @And("User submits Facebook login form")
    public void userSubmitsFacebookLoginForm() {
        facebookLoginPage.submitLoginForm();
    }


}
