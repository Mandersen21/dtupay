#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Title of your feature
 Merchant features

  @tag1
  Scenario: customer gets list of Transactions
	Given a registered customer with a bank account
	Given that the customer has perfomed transactions
	When the customer request for a list of transactions
	Then DTUPay replies with a list of transactions
	
  @tag2
  Scenario: merchant gets list of Transactions
	Given a registered merchant with a bank account
	Given that the merchant has perfomed transactions
	When the merchant request for a list of transactions
	Then DTUPay replies with a list of transactions
	