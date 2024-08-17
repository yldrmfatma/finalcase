Feature: Feature: db foreign keys and columns verification

  Background: User Opens app
    Given User opens app

  @validRegistration
  Scenario: User signs up with valid credentials
    Given user is on signup page
    When user enters valid data
      | username     | email                | password | phone_number | first_name | last_name | birth_date | birth_place | current_location | citizenship | profile_picture_url | consent_date            | consent_type | signup_method   | otp_checkbox |
      | testuser     | testuser@example.com  | P@ssw0rd | 1234567890   | John       | Doe       | 1990-01-01 | City        | Current City    | US          | http://example.com/pic.jpg | 2024-01-01T00:00:00Z | manual_signup | social_media   |false        |
    Then user should be registered in the database

  @hashPasswordControl
  Scenario: Check if password is hashed correctly
    Given user is on signup page
    When user enters valid data
      | username     | email                 | password | phone_number | first_name | last_name | birth_date | birth_place | current_location | citizenship | profile_picture_url | consent_date            | consent_type | signup_method   | otp_checkbox |
      | testuser1    | testuser1@example.com | P@ssw0rd | 8234567890   | Johny      | Doe       | 1990-01-01 | City        | Current City    | US          | http://example.com/pic.jpg | 2024-01-01T00:00:00Z | manual_signup | social_media   |false        |
    Then password should be hashed in the database

  @uniqueFieldTest_1
  Scenario: Verify uniqueness of username
    Given user is on signup page
    When user enters data with an already registered username
      | username     | email                | password | phone_number | first_name | last_name | birth_date | birth_place | current_location | citizenship | profile_picture_url | consent_date            | consent_type | signup_method   | otp_checkbox |
      | existinguser | newemail@example.com | P@ssw0rd | 1234567890   | John       | Doe       | 1990-01-01 | City        | Current City     | US          | http://example.com/pic.jpg | 2024-01-01T00:00:00Z | manual_signup | social_media   |false        |
    Then the system should prevent the registration and show a "Username already exists" error

  @uniqueFieldTest_2
  Scenario: Verify uniqueness of email
    Given user is on signup page
    When user attempts to sign up with an already registered email
      | username     | email                | password | phone_number | first_name | last_name | birth_date | birth_place | current_location | citizenship | profile_picture_url | consent_date            | consent_type | signup_method   | otp_checkbox |
      | newusername  | existingemail@example.com | P@ssw0rd | 1234567890   | John       | Doe       | 1990-01-01 | City        | Current City     | US          | http://example.com/pic.jpg | 2024-01-01T00:00:00Z | manual_signup | social_media   | false        |
    Then the system should prevent the registration and show a "Email already exists" error

  @uniqueFieldTest_3
  Scenario: Verify uniqueness of phone number
    Given user is on signup page
    When user attempts to sign up with an already registered phone number
      | username     | email                | password | phone_number | first_name | last_name | birth_date | birth_place | current_location | citizenship | profile_picture_url | consent_date            | consent_type | signup_method   | otp_checkbox |
      | newusername  | newemail@example.com | P@ssw0rd | existingphone123 | John       | Doe       | 1990-01-01 | City        | Current City     | US          | http://example.com/pic.jpg | 2024-01-01T00:00:00Z | manual_signup | social_media   |  false        |
    Then the system should prevent the registration and show a "Phone number already exists" error

  @foreignKeyTest @deleteProcess
  Scenario: Deleting a user should also delete related signup method
    Given the user with username "testuser" is deleted
    Then the related signup method should also be deleted

  @foreignKeyTest @OTPVerify
  Scenario: User registers and verifies OTP
    Given user is on signup page
    When user enters valid data
      | username   | email                 | password   | phone_number | first_name | last_name | birth_date | birth_place | current_location | citizenship | profile_picture_url | consent_date          | consent_type   | otp_checkbox |
      | testuser   | testuser@example.com   | password123 | 1234567890   | John       | Doe       | 1990-01-01 | Example City | Example Location | US           | http://example.com/pic.jpg | 2024-08-12T10:15:30Z | user_consent  | true         |
    And the user selects the OTP verification checkbox and submits
    When the user verifies the OTP
    Then the user record should exist in the users table
    And the OTP verification record should exist for the user
    And the OTP verification should be marked as verified
    Then the OTP verification record should be associated with the user

  @birthDateFormatValidity
  Scenario: Verify birth date format and validity
    Given user is on signup page
    When user enters data with an invalid birth date format
      | username     | email                | password | phone_number | first_name | last_name | birth_date | birth_place | current_location | citizenship | profile_picture_url | consent_date            | consent_type | signup_method   | otp_checkbox |
      | invaliduser  | invaliduser@example.com | P@ssw0rd | 1234567890   | John       | Doe       | 01-01-1990 | City        | Current City     | US          | http://example.com/pic.jpg | 2024-01-01T00:00:00Z | manual_signup | social_media   | false        |
    Then the system should prevent the registration and show an "Invalid birth date format" error
    And the birth date should be stored in the correct "YYYY-MM-DD" format

  @citizenshipPrivacyPolicy
  Scenario: Verify citizenship-based privacy policy enforcement
    Given user is on signup page
    When user enters valid data with european citizenship
      | username     | email                | password | phone_number | first_name | last_name | birth_date | birth_place | current_location | citizenship | profile_picture_url | consent_date            | consent_type | signup_method   | otp_checkbox |
      | euuser       | euuser@example.com   | P@ssw0rd | 1122334455   | Anna       | Smith     | 1985-05-05 | City        | Current City     | FR          | http://example.com/pic3.jpg | 2024-05-01T00:00:00Z | manual_signup | social_media   | false        |
    Then the system should enforce GDPR compliance
    And the correct privacy policy should be displayed based on the citizenship

  @socialLogin @foreignKeyTest
  Scenario: Verify user registration and social login details in the database
    Given user is on signup page
    When the user performs social media login with provider "Google" and user ID "google_user_123"
    Then the user should be present in the users table with email "test@example.com"
    And the social media login details should be present in the social_login table