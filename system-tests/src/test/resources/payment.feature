Feature: Title of your feature
  I want to use this template for my feature file

  Scenario: Simple payment
	Given a registered customer with a bank account
	And a registered merchant with a bank account
	And the customer has one unused token
	When the merchant scans the token and requests payment for 1 kroner using the token
	Then the payment succeeds and the money is transferred from the customer bank account to the merchant bank account
	