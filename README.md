Note: Mindset and Exploratory testing exercises are in /docs folder

Automated Testing exercise 

Implementation notes:
- Page object and locator syntax I followed the official Selenium guidelines
  https://www.selenium.dev/documentation/en/guidelines_and_recommendations/page_object_models/
  
- Wanted to try out selenium-jupiter instead of normal because it had some interesting features: 
  tight integration with junit5, out-of-the-box cross browser, android devices and docker containers
  https://bonigarcia.github.io/selenium-jupiter/

- For multiple browsers Selenium-jupiter offers easy browser switching through feeding the driver type  
  (put example code on page) however still a bit unwieldy to instantiate so would investigate Selenide in future to solve this
  
- Could not test Inspect Permissions as it requires a Premium Confluence account


  
Issues found:
- inconsistent tagging: some elements are 'data-test-id', some are 'data-testid'
  
- all the dropdown fields in the Restrictions modal have no unique ID or tags
  which makes it difficult to test them individually.

  
