package steps;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.SignUpPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SignUpPageStep {
    private AppiumDriver driver;
    private SignUpPage signUpPage;
    private DatabaseConnectionStep dbConnection;
    private Connection connection;
    private String storedHash;
    private int userId;
    private int otpId;
    private String userID;


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
            dbConnection = new DatabaseConnectionStep();
            signUpPage = new SignUpPage(driver, dbConnection);

            Thread.sleep(5000); // Wait for 5 seconds (adjust as needed)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("user is on signup page")
    public void userIsOnSignupPage() {
        signUpPage.clickRegisterButton();
        boolean isDisplayed = signUpPage.isSignUpPageDisplayed();
        if (!isDisplayed) {
            throw new AssertionError("User is not on the signup page");
        }
    }

    private void fillSignUpForm(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps(String.class, String.class).get(0);

        signUpPage.enterUsername(data.get("username"));
        signUpPage.enterEmail(data.get("email"));
        signUpPage.enterPassword(data.get("password"));
        signUpPage.enterPhoneNumber(data.get("phone_number"));
        signUpPage.enterFirstName(data.get("first_name"));
        signUpPage.enterLastName(data.get("last_name"));
        signUpPage.enterBirthDate(LocalDate.parse(data.get("birth_date")));
        signUpPage.enterBirthPlace(data.get("birth_place"));
        signUpPage.enterCurrentLocation(data.get("current_location"));
        signUpPage.enterCitizenship(data.get("citizenship"));
        signUpPage.enterProfilePictureUrl(data.get("profile_picture_url"));
        signUpPage.enterConsentDate(ZonedDateTime.parse(data.get("consent_date")));
        signUpPage.enterConsentType(data.get("consent_type"));
        if (Boolean.parseBoolean(data.get("otp_checkbox"))) {
            signUpPage.selectOtpVerificationCheckbox();
        }
    }

    @When("user enters valid data")
    public void userEntersValidData(io.cucumber.datatable.DataTable dataTable) {
        fillSignUpForm(dataTable);
        signUpPage.submitRegistration();
    }

    @Then("user should be registered in the database")
    public void userShouldBeRegisteredInTheDatabase(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps(String.class, String.class).get(0);

        String username = data.get("username");
        String email = data.get("email");

        assertTrue(signUpPage.isUserRegisteredInDatabase(username, email));
        driver.quit();
    }

    @Then("password should be hashed in the database")
    public void passwordShouldBeHashedInTheDatabase() {
        try {
            connection = dbConnection.getConnection();
            String query = "SELECT password_hash FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "testuser");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                storedHash = resultSet.getString("password_hash");
            }
            assertTrue(storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @When("user enters data with an already registered username")
    public void userEntersDataWithAnAlreadyRegisteredUsername(io.cucumber.datatable.DataTable dataTable) {
        fillSignUpForm(dataTable);
        signUpPage.submitRegistration();
    }

    @Then("the system should prevent the registration and show a {string} error")
    public void theSystemShouldPreventTheRegistrationAndShowAError(String expectedErrorMessage) {
        String actualErrorMessage = signUpPage.getErrorMessage();
        assertTrue(actualErrorMessage.contains(expectedErrorMessage));
        driver.quit();
    }

    @When("user attempts to sign up with an already registered email")
    public void userAttemptsToSignUpWithAnAlreadyRegisteredEmail(io.cucumber.datatable.DataTable dataTable) {
        fillSignUpForm(dataTable);
        signUpPage.submitRegistration();
    }

    @When("user attempts to sign up with an already registered phone number")
    public void userAttemptsToSignUpWithAnAlreadyRegisteredPhoneNumber(io.cucumber.datatable.DataTable dataTable) {
        fillSignUpForm(dataTable);
        signUpPage.submitRegistration();
    }

    @Given("the user with username {string} is deleted")
    public void theUserWithUsernameIsDeleted(String username) {
        String query = "SELECT user_id FROM users WHERE username = ?";
        String deleteQuery = "DELETE FROM users WHERE username = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("user_id");
                }
            }

            deleteStatement.setString(1, username);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Then("the related signup method should also be deleted")
    public void theRelatedSignupMethodShouldAlsoBeDeleted() {
        try {
            // Check if the signup_method record related to the deleted user_id exists
            String checkQuery = "SELECT * FROM signup_method WHERE user_id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, userId);
            ResultSet resultSet = checkStatement.executeQuery();

            // If the result set is empty, it means the related signup_method was deleted
            assertFalse(resultSet.next());
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @And("the user selects the OTP verification checkbox and submits")
    public void theUserSelectsTheOTPVerificationCheckboxAndSubmits() {
        signUpPage.selectOtpVerificationCheckbox();
        signUpPage.submitRegistration();
    }
    private void simulateOtpVerification() {
        String fetchOtpQuery = "SELECT otp_id FROM otp_verifications WHERE user_id = ?";
        String updateOtpQuery = "UPDATE otp_verifications SET is_verified = true WHERE otp_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement fetchOtpStatement = connection.prepareStatement(fetchOtpQuery);
             PreparedStatement updateOtpStatement = connection.prepareStatement(updateOtpQuery)) {

            fetchOtpStatement.setInt(1, userId);
            try (ResultSet resultSet = fetchOtpStatement.executeQuery()) {
                if (resultSet.next()) {
                    otpId = resultSet.getInt("otp_id");
                }
            }

            updateOtpStatement.setInt(1, otpId);
            updateOtpStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @When("the user verifies the OTP")
    public void theUserVerifiesTheOTP() {
        simulateOtpVerification();// Simulate OTP verification process
    }

    @Then("the user record should exist in the users table")
    public void theUserRecordShouldExistInTheUsersTable() {
        try {
            String checkUserQuery = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery);
            checkUserStatement.setInt(1, userId);
            ResultSet resultSet = checkUserStatement.executeQuery();

            assertTrue(resultSet.next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("the OTP verification record should exist for the user")
    public void theOTPVerificationRecordShouldExistForTheUser() {
        try {
            String checkOtpQuery = "SELECT * FROM otp_verifications WHERE user_id = ?";
            PreparedStatement checkOtpStatement = connection.prepareStatement(checkOtpQuery);
            checkOtpStatement.setInt(1, userId);
            ResultSet resultSet = checkOtpStatement.executeQuery();

            assertTrue(resultSet.next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("the OTP verification should be marked as verified")
    public void theOTPVerificationShouldBeMarkedAsVerified() {
        try {
            String checkOtpVerificationQuery = "SELECT is_verified FROM otp_verifications WHERE otp_id = ?";
            PreparedStatement checkOtpVerificationStatement = connection.prepareStatement(checkOtpVerificationQuery);
            checkOtpVerificationStatement.setInt(1, otpId);
            ResultSet resultSet = checkOtpVerificationStatement.executeQuery();

            if (resultSet.next()) {
                boolean isVerified = resultSet.getBoolean("is_verified");
                assertTrue(isVerified);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("the OTP verification record should be associated with the user")
    public void theOTPVerificationRecordShouldBeAssociatedWithTheUser() {
        try {
            String checkOtpVerificationQuery = "SELECT * FROM otp_verifications WHERE user_id = ?";
            PreparedStatement checkOtpVerificationStatement = connection.prepareStatement(checkOtpVerificationQuery);
            checkOtpVerificationStatement.setInt(1, userId);
            ResultSet resultSet = checkOtpVerificationStatement.executeQuery();

            assertTrue(resultSet.next());
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @When("user enters data with an invalid birth date format")
    public void userEntersDataWithAnInvalidBirthDateFormat(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps(String.class, String.class).get(0);

        try {
            // Attempt to parse the birth date
            LocalDate.parse(data.get("birth_date"), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            // Simulate entering an invalid date format
            signUpPage.enterBirthDate(LocalDate.parse("1990-01-01"));  // Assume the correct format is used after the error
        }

        // Fill the remaining form
        signUpPage.enterUsername(data.get("username"));
        signUpPage.enterEmail(data.get("email"));
        signUpPage.enterPassword(data.get("password"));
        signUpPage.enterPhoneNumber(data.get("phone_number"));
        signUpPage.enterFirstName(data.get("first_name"));
        signUpPage.enterLastName(data.get("last_name"));
        signUpPage.enterBirthPlace(data.get("birth_place"));
        signUpPage.enterCurrentLocation(data.get("current_location"));
        signUpPage.enterCitizenship(data.get("citizenship"));
        signUpPage.enterProfilePictureUrl(data.get("profile_picture_url"));
        signUpPage.enterConsentDate(ZonedDateTime.parse(data.get("consent_date")));
        signUpPage.enterConsentType(data.get("consent_type"));
        if (Boolean.parseBoolean(data.get("otp_checkbox"))) {
            signUpPage.selectOtpVerificationCheckbox();
        }

        // Submit the form
        signUpPage.submitRegistration();
    }

    @Then("the system should prevent the registration and show an {string} error")
    public void theSystemShouldPreventTheRegistrationAndShowAnError(String expectedErrorMessage) {
        String actualErrorMessage = signUpPage.getErrorMessage();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage));
    }

    @And("the birth date should be stored in the correct {string} format")
    public void theBirthDateShouldBeStoredInTheCorrectFormat(String correctFormat) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT birth_date FROM users WHERE username = ?");
            stmt.setString(1, "invaliduser");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedDate = rs.getString("birth_date");
                Assert.assertTrue(storedDate.matches(correctFormat)); // Check if the date matches YYYY-MM-DD format
            } else {
                Assert.fail("User not found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Database error.");
            driver.quit();
        }
    }

    @When("user enters valid data with european citizenship")
    public void userEntersValidDataWithEuropeanCitizenship(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps(String.class, String.class).get(0);

        // Kullanıcı verilerini gir
        signUpPage.enterUsername(data.get("username"));
        signUpPage.enterEmail(data.get("email"));
        signUpPage.enterPassword(data.get("password"));
        signUpPage.enterPhoneNumber(data.get("phone_number"));
        signUpPage.enterFirstName(data.get("first_name"));
        signUpPage.enterLastName(data.get("last_name"));
        signUpPage.enterBirthDate(LocalDate.parse(data.get("birth_date")));
        signUpPage.enterBirthPlace(data.get("birth_place"));
        signUpPage.enterCurrentLocation(data.get("current_location"));
        signUpPage.enterCitizenship(data.get("citizenship"));
        signUpPage.enterProfilePictureUrl(data.get("profile_picture_url"));
        signUpPage.enterConsentDate(ZonedDateTime.parse(data.get("consent_date")));
        signUpPage.selectSignupMethod(data.get("signup_method"));
        signUpPage.checkOtpCheckbox(data.get("otp_checkbox"));
    }

    @Then("the system should enforce GDPR compliance")
    public void theSystemShouldEnforceGDPRCompliance() {
        boolean isGdprComplianceEnforced = signUpPage.isGdprComplianceEnforced();
        Assert.assertTrue("GDPR compliance is not enforced", isGdprComplianceEnforced);
    }

    @And("the correct privacy policy should be displayed based on the citizenship")
    public void theCorrectPrivacyPolicyShouldBeDisplayedBasedOnTheCitizenship() {
        signUpPage.clickOnTermsAndPrivacyPolicyLink();

        // There may be a suitable waiting period for the mobile application, this should not be forgotten
        signUpPage.waitForPrivacyPolicyPageToLoad();

        // Check correct policy title by citizenship information
        String citizenship = signUpPage.getCitizenship();
        String expectedPolicyHeader = signUpPage.getExpectedPolicyHeaderForCitizenship(citizenship);
        String actualPolicyHeader = signUpPage.getPolicyHeader();

        Assert.assertEquals("Displayed policy header does not match the expected policy", expectedPolicyHeader, actualPolicyHeader);

        // Set consent type by citizenship
        signUpPage.setConsentTypeFromPolicyHeader();
        driver.quit();
    }

    @When("the user performs social media login with provider {string} and user ID {string}")
    public void theUserPerformsSocialMediaLoginWithProviderAndUserID(String provider, String userID) {
        this.userID = userID;
        signUpPage.performSocialMediaLogin(provider);
    }

    @Then("the user should be present in the users table with email {string}")
    public void theUserShouldBePresentInTheUsersTableWithEmail(String email) throws Exception {
        Connection connection = dbConnection.getConnection();
        String query = "SELECT * FROM users WHERE email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        assertTrue(resultSet.next());

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @And("the social media login details should be present in the social_login table")
    public void theSocialMediaLoginDetailsShouldBePresentInTheSocial_loginTable() throws Exception {
        Connection connection = dbConnection.getConnection();
        String query = "SELECT * FROM social_login WHERE user_id = ? AND provider = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userID);
        preparedStatement.setString(2, "Google");
        ResultSet resultSet = preparedStatement.executeQuery();

        assertTrue(resultSet.next());

        resultSet.close();
        preparedStatement.close();
    }
}
