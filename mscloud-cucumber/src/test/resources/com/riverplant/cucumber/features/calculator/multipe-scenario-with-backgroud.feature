Feature: multiple scenario with background
  Background:
    Given x and y value
  Scenario: Add
    And add operation
    When invoke calculate button
    Then the result is x+y


  Scenario: Sub
    And sub operation
    When invoke calculate button
    Then the result is x-y