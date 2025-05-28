Feature: multiple scenario without background
  Scenario: Add
    Given x and y value
    And add operation
    When invoke calculate button
    Then the result is x+y


  Scenario: Sub
    Given x and y value
    And sub operation
    When invoke calculate button
    Then the result is x-y