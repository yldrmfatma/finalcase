Feature: Login functionality verification

  Background: User Opens app
    Given User open apps

  @trueLogin
  Scenario: Successful login with valid credentials
    Given User is on the login screen
    When User enters valid email "user@example.com"
    And User enters valid password "password123"
    And User selects "Remember Me" checkbox
    And User submits login form
    Then User should be redirected to the home page

  @invalidEmail
  Scenario: Login with invalid email
    Given User is on the login screen
    When User enters invalid email "invalidemail.com"
    And User enters valid password "password123"
    And User submits login form
    Then User should see an error message "Invalid email format"

  @invalidPassword
  Scenario: Login without entering password
    Given User is on the login screen
    When User enters valid email "user@example.com"
    And User leaves password field empty
    And User submits login form
    Then User should see an error message "Password is required"

  @forgotPassword
  Scenario: Forgot password flow
    Given User is on the login screen
    When User clicks on "Forgot Password"
    And User enters their email "user@example.com"
    And User submits forget password form
    Then User should see a confirmation message "Password reset link sent to your email"

  @loginViaFacebook
  Scenario: Login via Facebook
    Given User is on the login screen
    When User taps on "Login via Facebook" button
    Then User should be redirected to Facebook login page
    And User should see Facebook login fields
    When User enters valid Facebook credentials
    And User submits Facebook login form
    Then User should be redirected to the home page