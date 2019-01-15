# dtupay
Dtupay application

Token-service:
- domain/Token (TokenId(String), Valid(Boolean), CustomerId(String), Status(Enum))
- domain/TokenManager
  
- rest/TokensEndpoint
- rest/RestApplication

- exception/

- repository/ITokenRepositry 
- repository/TokenRepositry

Customer-service:
- domain/Customer (CustomerId(String), CprNumber(String), Name, List<Token>)
- domain/Token (TokenId(String))
- domain/CustomerController
  
- rest/CustomersEndpoint
- rest/RestApplication

- exception/

- repository/ICustomerRepositry 
- repository/CustomerRepositry



Payment-service:
- domain/BankAccount (AccountId(String), UserId(String), Balance(double))
- domain/PaymentController)
  - Transaction(MerchantId(String), CustomerId(String), Amount(Int),  )
  
- rest/?
- rest/?

- exception/

- repository/IBankAccountRepositry 
- repository/BankAccountRepositry



Merchant-service:
- domain/Merchant (MerchantId(String), CvrNumber(String), Name)
- domain/MerchantController)
  - RequestTransaction(MerchantId(String), TokenId(String), Amount(int))
  - 

- rest/MerchantsEndpoint
- rest/RestApplication

- exception/

- repository/IMerchantRepositry 
- repository/MerchantRepositry



EVERYBODY SHOULD WORK ON THIS FOR TOMORROW, PLEASE DO UNIT TESTING, (NOT CUCUMBER TESTING)
Best regards
Mikkel, Mads, Cristian, Tör
