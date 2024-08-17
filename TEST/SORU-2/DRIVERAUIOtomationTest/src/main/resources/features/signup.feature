Feature: Registration scenario verifications

  Background: User Opens app
    Given User opens app

  @signUpPositiveTest
  Scenario: User successfully signs up
    Given User is on the sign up screen
    When User fills in the sign up form with valid details
    And User taps on "Register" button to submit the form
    Then User should receive a registration confirmation "Registration successful"

  @passwordVisibilityTest
  Scenario: User verifies that password field hides characters
    Given User is on the sign up screen
    When User enters "MySecretPassword123" into the password field
    Then The password field should display characters as hidden

  @passwordMinLengthTest
  Scenario: Password field should require at least 8 characters
    Given User is on the sign up screen
    When User enters a password with less than 8 characters
    Then The password field should display an error message "Password must be at least 8 characters long"

  @passwordUppercaseTest
  Scenario: Password field should require at least one uppercase letter
    Given User is on the sign up screen
    When User enters a password without uppercase letters
    Then The password field should display an error message "Password must contain at least one uppercase letter"

  @passwordLowercaseTest
  Scenario: Password field should require at least one lowercase letter
    Given User is on the sign up screen
    When User enters a password without lowercase letters
    Then The password field should display an error message "Password must contain at least one lowercase letter"

  @passwordNumberTest
  Scenario: Password field should require at least one number
    Given User is on the sign up screen
    When User enters a password without numbers
    Then The password field should display an error message "Password must contain at least one number"

  @passwordSpecialCharTest
  Scenario: Password field should require at least one special character
    Given User is on the sign up screen
    When User enters a password without special characters
    Then The password field should display an error message "Password must contain at least one special character"

  @passwordMismatchTest
  Scenario: Password and Confirm Password fields should not mismatch
    Given User is on the sign up screen
    When User enters the password "MySecurePassword123" into the Password field
    And User enters a different password "DifferentPassword456" into the Confirm Password field
    And User taps on "Register" button
    Then The password field should display an error message "Passwords do not match"

  @invalidDateOfBirthTest
  Scenario: Date of Birth field should not accept users under 18 years old
    Given User is on the sign up screen
    When User enters a date of birth that makes them younger than 18 years old
    And User taps on "Register" button
    Then The Date of Birth field should display an error message "You must be at least 18 years old to register"

  @invalidEmailFormatTest
  Scenario: Email field should not accept invalid email addresses
    Given User is on the sign up screen
    When User enters an invalid email address "invalid-email-format" into the Email field
    And User taps on "Register" button
    Then The Email field should display an error message "Please enter a valid email address"

  @noCitizenshipPolicyTest
  Scenario: "Term and Privacy Policy" link should be disabled if citizenship is not provided
    Given User is on the sign up screen
    When User fills in all required fields except citizenship
    Then The "Terms and Privacy Policy" link should be visible and disabled

  @turkishCitizenshipPolicyTest
  Scenario: Display KVKK policy for Turkish citizenship
    Given User is on the sign up screen
    When User selects "Turkish" as citizenship
    And User taps on the "Term and Privacy Policy" link
    Then The "KVKK" policy should be displayed

  @europeanCitizenshipPolicyTest
  Scenario: Display KVKK policy for Turkish citizenship
    Given User is on the sign up screen
    When User selects "Europe" as citizenship
    And User taps on the "Term and Privacy Policy" link
    Then The "GDPR" policy should be displayed

