Feature: Title of your feature
 Merchant features

  Scenario: customer gets list of Transactions
	Given a registered customer with a bank account
	Given that the customer has perfomed transactions
	When the customer request for a list of transactions
	Then DTUPay replies with a list of transactions
	
  Scenario: merchant gets list of Transactions
	Given a registered merchant with a bank account
	Given that the merchant has ben involved in transactions
	When the merchant request for a list of transactions
	Then DTUPay replies with a list of transactions
	