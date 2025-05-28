Feature: test multiple with conditions&action&expected

  Scenario: simple scenario for multiple conditions
    Given jie open the url of git
    And jie open the url of jenkins
    When jie click build job link
    Then jie the job will be started
    When jie click the job id link
    And jie choice the job console
    And jie goto the console page
    Then jie the job building log will be print
    And jie the last job can see successfully state
    But jie the job will not running at current

