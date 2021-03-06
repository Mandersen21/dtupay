Feature: Token generator
 generate tokens for customer

  Scenario: Request Token
  
	Given a registered customer
	Given the customer has no more than one valid token
	When the customer requests 5 tokens from the token service
	Then 5 tokens are generated by the token service
	And the token service assigns 5 tokens to the registered customer
	
  Scenario: Request Token Denied
  
 	Given a registered customer with a bank account 	
	Given the customer has more than one valid token 
	When the customer requests 5 tokens from the token service
	Then the request is denied