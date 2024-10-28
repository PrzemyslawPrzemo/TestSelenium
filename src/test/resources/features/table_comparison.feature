@tableComparisonTests
Feature: Table Data Comparison

  Scenario: Compare data between tables
    Given I am on the tables comparison page
    When I collect data from "Table 1"
    And I collect data from "Table 2"
    Then I should see differences between tables