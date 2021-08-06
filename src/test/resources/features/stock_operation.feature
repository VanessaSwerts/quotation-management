Feature: Client can insert new quotes to an exists Stock

  Scenario Outline: Insert new quotes to a Stock
    Given a stock with id "<stockId>" and the quote with "<date>" and "<price>"
    When client request the api
    Then the quote is created and the client receive the response <status> 
		And and JSON with "<stockId>" and quotes
		
    Examples: 
      | stockId  |    date     |  price  | status |
      | vale5    |  2021-06-01 | 	 10		 |   201  |
      | vale5    |  2021-07-03 |   14    |   201  |
      | vale5    |  2021-08-01 |   11    |   201  |
      
   Scenario Outline: Try insert new quotes to a Stock that dont exists
    Given a stock with id "<stockId>" and the quote with "<date>" and "<price>"
    When client request the api
    Then the quote is not created and the client receive the response <status> 
		And and JSON with the field "<field>" and a error "<message>"
		
    Examples: 
      | stockId  |    date     |  price  | status |  field    |             message                       |
      | vale5    |  2021-14-01 |   11    |   400  |   quotes  |   Date or Price is not valid!             |
      | vale5    |  2021-04-01 |   -11   |   400  |		quotes	|   Date or Price is not valid!             |
      
 Scenario: List a stock operation by stockId
    Given a stock with id "testId"
    When client request the api passing an stockId that dont exists
    Then the client receive the response status 404 and an error "Could not find a stock with id: testId"