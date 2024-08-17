Feature: Logout functionality verifications

  Background: User is on the settings screen
    Given User open apps
    And User is logged in

  @logoutSuccess
  Scenario: User logs out successfully
    Given User is on the settings page
    When User taps on "Logout" button to logout app
    Then User should be redirected to the login screen
    And User should see a success message "You have been successfully logged out"

  @session-timeout
  Scenario: User is automatically logged out due to inactivity
    When The user does not interact with the system for 15 minutes
    Then The user should be automatically logged out and redirected to the login page
    And A message should be displayed saying "Your session has expired due to inactivity. Please log in again."
