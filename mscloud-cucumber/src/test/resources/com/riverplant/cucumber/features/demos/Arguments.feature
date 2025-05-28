Feature: Arguments
  Scenario: arguments test
    Given the name is "river" and age is 46
    When format the input
    Then the format string is "river-46"