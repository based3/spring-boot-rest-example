Feature: Admin Hotels test script
  for help, see: https://github.com/intuit/karate/wiki/IDE-Support

  Background:
    * def wsPath = 'api/hotels/'
    * url urlBaseApi
    * def hotelJsonSchema = { id: '#number', name: '#string', description: '#string', city: '#string', rating: '#number' }

  Scenario: Get all hotels and then get the first hotel by id
    Given path wsPath
    When method GET
    Then status 200
    And assert responseTime < 1000
    And match each response == hotelJsonSchema
    * def first = response[0]

    Given path wsPath + 'id/', first.id
    When method GET
    Then status 200
    And assert responseTime < 1000
    And match response == hotelJsonSchema
    And match response contains { id: '#(first.id)' }
    * print 'first.id: ', first.id

  Scenario: Create a new Hotel, Get it and Update it
    * def jlocale = Java.type('java.util.Locale')
    * def fakerObj = new faker(new jlocale("us"))
    * def fName = fakerObj.company()
    * def fDescription = fName + " " + fakerObj.company()
    * def fCity = fakerObj.address().city();
    * def fRate = new faker_number().numberBetween(0, 10)
    * def hotel =
      """
      {
        "id" : 0,
        "name" : '#(fName)',
        "description" : '#(fDescription)',
        "city" : '#(fCity)',
        "rate" : '#(fRate)'
      }
      """

    Given path wsPath
    And request hotel
    When method POST
    Then status 200
    And assert responseTime < 1000
    And match response == hotelJsonSchema
    And match response contains hotelJsonSchema
    * print 'Created hotel id is: ', response.id

    * print 'Update Hotel'
    Given path wsPath + 'id/' + response.id
    And request hotel
    When method PUT
    Then status 200
    And assert responseTime < 1000
    And match response == hotelJsonSchema
    And match response contains { "id": #(response.id) }

    Given path wsPath + 'id/', response.id
    When method GET
    Then status 200
    And assert responseTime < 1000
    And match response == hotelJsonSchema
    And match response contains hotelJsonSchema

    Given path wsPath + 'id/', response.id
    When method DELETE
    Then status 204
    And assert responseTime < 1000
    #And match response == hotelJsonSchema
    #And match response contains hotelJsonSchema
