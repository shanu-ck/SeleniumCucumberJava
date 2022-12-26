Feature: Verify logic test scenarios
  
  @FIR-TC-2 
  Scenario: Verify logic with credentials
    Given I am on the login page
    When I enter email "test@test.com" and password "testpassword"
    And I click on login button
    Then I verify dashbord page is opened

  @FIR-TC-5
  Scenario: Verify login failed failed scenario
    Given I am on the login page
    When I enter email "blah@blah.com" and password "veryeasy"
    And I click on login button
    Then I verify login failed with some error message
