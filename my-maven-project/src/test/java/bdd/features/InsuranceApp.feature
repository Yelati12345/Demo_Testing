Feature: User Information Form

  Scenario: Fill the insurance form
    Given the user is on the registration form
    When the user enters "John" in the First Name field
    Then the First Name field should accept the input
    When the user enters "Doe" in the Last Name field
    Then the Last Name field should accept the input
    When the user enters "Testing" in the Middle Name field
    Then the Middle Name field should accept the input
    When user click on the Next button
    When the user enters "01-01-1990" in the Date of Birth field

