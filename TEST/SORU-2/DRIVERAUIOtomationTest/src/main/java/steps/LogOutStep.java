package steps;

import com.google.common.collect.ImmutableMap;
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
import pages.LogOutPage;
import pages.LoginPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class LogOutStep {

    private AppiumDriver driver;
    private LogOutPage logOutPage;
    private LoginPage loginPage;

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
            logOutPage = new LogOutPage(driver);

            Thread.sleep(5000); // Wait for 5 seconds (adjust as needed)
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @And("User is logged in")
    public void userIsLoggedIn() {
        LoginPage loginPage = new LoginPage(driver);

        // Check if the login screen is displayed
        if (!loginPage.isLoginScreenDisplayed()) {
            // Log in if necessary
            loginPage.enterEmail("test@example.com"); // Replace with valid email
            loginPage.enterPassword("password123");   // Replace with valid password
            loginPage.submitRegistration();

            // Optionally, add a wait or check to ensure login is successful
            WebElement postLoginElement = driver.findElement(By.id("post_login_element")); // Replace with actual locator
            Assert.assertTrue("Post-login element is not visible", postLoginElement.isDisplayed());

        }
    }

    @Given("User is on the settings page")
    public void userIsOnTheSettingsPage() {
        String pageTitle = logOutPage.getPageTitle();
        Assert.assertEquals("User is not on the settings page", "Settings", pageTitle);

        boolean isLogoutButtonVisible = logOutPage.isLogoutButtonVisible();
        Assert.assertTrue("Logout button is not visible", isLogoutButtonVisible);
    }

    @When("User taps on {string} button to logout app")
    public void userTapsOnButtonToLogoutApp(String buttonName) {
        if ("Logout".equalsIgnoreCase(buttonName)) {
            logOutPage.tapLogoutButton();
        } else {
            throw new IllegalArgumentException("Unsupported button: " + buttonName);
        }
    }

    @Then("User should be redirected to the login screen")
    public void userShouldBeRedirectedToTheLoginScreen() {
        String pageTitle = loginPage.getPageTitle();
        Assert.assertEquals("Login", pageTitle); // Replace "Login" with the actual title of the login page
    }

    @And("User should see a success message {string}")
    public void userShouldSeeASuccessMessage(String expectedMessage) {
        String actualMessage = logOutPage.getSuccessMessage();
        Assert.assertEquals("Success message does not match", expectedMessage, actualMessage);
        driver.quit();
    }


    @When("The user does not interact with the system for 15 minutes")
    public void theUserDoesNotInteractWithTheSystemForMinutes() {
        // Send app to home screen
        driver.executeScript("mobile: background", ImmutableMap.of("seconds", 900)); // 15 dakika
    }

    @Then("The user should be automatically logged out and redirected to the login page")
    public void theUserShouldBeAutomaticallyLoggedOutAndRedirectedToTheLoginPage() {
        Assert.assertTrue("Login page is not displayed", loginPage.isLoginScreenDisplayed());
    }

    @And("A message should be displayed saying {string}")
    public void aMessageShouldBeDisplayedSaying(String expectedMessage) {
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertEquals("Error message does not match", expectedMessage, actualMessage);
    }
}
