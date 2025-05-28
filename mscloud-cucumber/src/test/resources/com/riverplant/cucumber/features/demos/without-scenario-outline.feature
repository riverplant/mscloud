Feature: without scenario outline

  Background:
    Given open the login page

  Scenario: username is wrong
    When user "riverplant" with password "123456"
    Then the user login failed


  Scenario: password is wrong
    When user "admin" with password "654321"
    Then the user login failed

    Scenario: username and password is correct
      When user "admin" with password "123456"
      Then the user login success

