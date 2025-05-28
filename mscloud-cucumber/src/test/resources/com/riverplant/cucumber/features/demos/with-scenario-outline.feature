Feature: with scenario outline

  Background:
    Given open the login page testing scenario outline

  Scenario Outline: username or password is wrong
    When use "<UserName>" with password "<Password>" testing scenario outline
    Then the user or password is invalid

    Examples:
      | UserName | Password |
      | admin    | 654321   |
      | nina     | 123456   |

    Scenario: username and password is correct
      When use "admin" with password "123456" testing scenario outline
      Then the user or password is correct

