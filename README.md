Implementation notes:
- Page object and locator syntax I followed the official Selenium guidelines
  https://www.selenium.dev/documentation/en/guidelines_and_recommendations/page_object_models/
  
- Wanted to try out selenium-jupiter instead of normal because it had some interesting features: 
  tight integration with junit5, out-of-the-box cross browser, android devices and docker containers
  https://bonigarcia.github.io/selenium-jupiter/
  
- Could not test Inspect Permissions as it requires a Premium account

- For multiple browsers Selenium-jupiter offers easy browser switching through feeding the driver type  
  (put example code on page) however still a bit unwieldy to instantiate so would investigate Selenide to solve this
  
Issues/annoyances found:
- inconsistent tagging: some elements are 'data-test-id', some are 'data-testid'
  
- all the dropdown fields in the Restrictions modal have no unique ID or tags
  (all just <div class="css-4avucx-control">) which makes it more difficult to 
  test them individually.

  
