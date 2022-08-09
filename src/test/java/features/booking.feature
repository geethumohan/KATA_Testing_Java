Feature: Book a room

  @book
  Scenario: Option to book a room is available on the page
    Given I am on the home page
    When I browse through the page
    Then I have the option to book a room

  @book
    # Currently the application does no display the checkout date correctly after booking, so these tests will fail on booking validation
  Scenario Outline: Book a single room for two nights
    Given I am on the home page
    When I open the option to book a room
    And I fill the form with '<firstName>', '<lastName>', '<email>' and '<phone>'
    And I select booking dates based '<checkin>' dates
    And I confirm the booking
    Then I get the booking successful confirmation for the provided dates

    Examples:
      | firstName | lastName | email                   | phone        | checkin    |
      | User      | One      | useronetest02@gmail.com | 918893911789 | 2022-01-05 |
      | User      | Two      | usertwotest02@gmail.com | 918893911789 | 2023-01-11 |


