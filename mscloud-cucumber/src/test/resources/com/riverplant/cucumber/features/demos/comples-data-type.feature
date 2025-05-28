Feature: Complex Data type
  Scenario: multiple then keyword
    Given the user account information
    Then we can found user "Riverplant" with password "123456", phone "232323" exists
    Then we can found user "Bird" with password "123456", phone "1212121" exists
    Then we can found user "Nina" with password "123456", phone "565656" exists


    Scenario: Use complex data type
      Given the user account information
      Then we verify following user exists
      |name       |password |phone  |
      |Riverplant |123456   |232323 |
      |Bird       |123456   |1212121|
      |Nina       |123456   |565656 |
