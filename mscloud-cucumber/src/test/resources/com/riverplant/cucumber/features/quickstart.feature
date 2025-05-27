Feature: Test Git web Application
   In order to Test Git web Application
   As a user
   I want to specify the application flow

  Scenario: Web Site loads
    application page load should be quick

    Given application URL is ready with the user
    When user enters the URL in browser
    Then application page loads